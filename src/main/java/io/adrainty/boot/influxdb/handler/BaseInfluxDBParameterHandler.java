package io.adrainty.boot.influxdb.handler;

import io.adrainty.boot.influxdb.annotation.InfluxDBParam;

import java.lang.reflect.Parameter;

/**
 * <p>BaseInfluxDBParameterHandler</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBParameterHandler
 * @since 2025/7/31 14:59:20
 */

public class BaseInfluxDBParameterHandler implements InfluxDBParameterHandler {

    @Override
    public String handleParameter(Parameter[] parameters, Object[] args, String sql) {
        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameters[i].getType();
            String parameterName = parameters[i].getName();

            InfluxDBParam param = parameters[i].getAnnotation(InfluxDBParam.class);
            if (param != null) {
                parameterName = param.value();
            }

            if (parameterType == String.class) {
                sql = sql.replaceAll("#\\{" + parameterName + "}", "'" + args[i] + "'");
            } else {
                sql = sql.replaceAll("#\\{" + parameterName + "}", args[i].toString());
            }
            sql = sql.replaceAll("\\$\\{" + parameterName + "}", args[i].toString());
        }
        return sql;
    }

}
