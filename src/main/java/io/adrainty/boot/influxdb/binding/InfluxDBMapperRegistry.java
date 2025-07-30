package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.annotation.InfluxDBMapper;
import io.adrainty.boot.influxdb.properties.InfluxDBProperties;
import io.adrainty.boot.influxdb.scanner.InfluxDBMapperScanner;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>InfluxDBMapperRegistry</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapperRegistry
 * @since 2025/7/30 16:24:11
 */

@SuppressWarnings("unused")
public class InfluxDBMapperRegistry implements BeanDefinitionRegistryPostProcessor {

    private final InfluxDBProperties properties;

    private final Map<Class<?>, InfluxDBMapper> knownMappers;

    public InfluxDBMapperRegistry(InfluxDBProperties properties) {
        this.properties = properties;
        this.knownMappers = new HashMap<>();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        InfluxDBMapperScanner mapperScanner = new InfluxDBMapperScanner(registry);
        mapperScanner.scan(properties.getMapperBasePackage());
    }

    public <T> boolean hasMapper(Class<T> type) {
        return this.knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        this.knownMappers.put(type, type.getAnnotation(InfluxDBMapper.class));
    }

}
