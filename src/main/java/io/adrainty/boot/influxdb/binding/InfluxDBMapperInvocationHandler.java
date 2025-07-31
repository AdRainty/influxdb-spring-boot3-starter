package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.core.InfluxDBBaseExecutor;
import io.adrainty.boot.influxdb.handler.InfluxDBParameterHandler;
import io.adrainty.boot.influxdb.handler.InfluxDBResultSetHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * <p>InfluxDBMapperInvocationHandler</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapperInvocationHandler
 * @since 2025/7/30 16:52:18
 */

@Slf4j
public class InfluxDBMapperInvocationHandler implements InvocationHandler {

    private final InfluxDBBaseExecutor executor;

    private final InfluxDBParameterHandler parameterHandler;

    private final InfluxDBResultSetHandler resultSetHandler;

    @Setter
    private InfluxDBMapperRegistry mapperRegistry;

    public InfluxDBMapperInvocationHandler(InfluxDBBaseExecutor executor,
                                           InfluxDBParameterHandler parameterHandler,
                                           InfluxDBResultSetHandler resultSetHandler) {
        this.parameterHandler = parameterHandler;
        this.resultSetHandler = resultSetHandler;
        this.executor = executor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getDeclaringClass().equals(BaseInfluxDBMapper.class)) {
            // BaseInfluxDBMapper 定义的
            executor.insert(args);
            return null;
        } else {
            InfluxDBMappedMethod mappedStatement = mapperRegistry.getMappedStatement(proxy.getClass(), method.getName());

            Class<?> returnType = mappedStatement.getReturnType();
            Parameter[] parameters = method.getParameters();
            String sqlCommand = mappedStatement.getSqlCommand();
            log.debug("sqlCommand: {}", sqlCommand);

            String parseSql = parameterHandler.handleParameter(parameters, args, sqlCommand);
            List<Map<String, Object>> selectResult = executor.select(parseSql, null);
            return resultSetHandler.handleResultSet(selectResult, method, sqlCommand, returnType);
        }
    }

}
