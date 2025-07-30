package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.registery.InfluxDBMapperInvocationHandler;
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

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(mapperInterface, "Mapper interface must not be null");

        // 创建动态代理
        mapperProxy = (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(),
                new Class[]{mapperInterface},
                new InfluxDBMapperInvocationHandler()
        );
    }
}
