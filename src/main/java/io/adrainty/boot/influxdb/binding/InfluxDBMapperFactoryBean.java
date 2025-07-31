package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.core.InfluxDBBaseExecutor;
import io.adrainty.boot.influxdb.handler.InfluxDBParameterHandler;
import io.adrainty.boot.influxdb.handler.InfluxDBResultSetHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.reflect.Proxy;

/**
 * <p>InfluxDBMapperFactoryBean</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapperFactoryBean
 * @since 2025/7/30 16:47:58
 */

@SuppressWarnings({"unchecked", "unused"})
public class InfluxDBMapperFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    private Class<T> mapperInterface;

    private T mapperProxy;

    @Resource
    private InfluxDBBaseExecutor executor;

    @Resource
    private InfluxDBParameterHandler parameterHandler;

    @Resource
    private InfluxDBResultSetHandler resultSetHandler;

    private InfluxDBMapperInvocationHandler handler;

    public InfluxDBMapperFactoryBean() {}

    public InfluxDBMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() {
        return mapperProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setRegistry(InfluxDBMapperRegistry registry) {
        this.handler.setMapperRegistry(registry);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(mapperInterface, "Mapper interface must not be null");
        this.handler = new InfluxDBMapperInvocationHandler(executor, parameterHandler, resultSetHandler);

        // 创建动态代理
        mapperProxy = (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(),
                new Class[]{mapperInterface},
                handler
        );
    }
}
