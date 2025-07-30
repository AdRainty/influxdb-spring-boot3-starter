package io.adrainty.boot.influxdb.registery;

import io.adrainty.boot.influxdb.properties.InfluxDBProperties;
import io.adrainty.boot.influxdb.scanner.InfluxDBMapperScanner;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * <p>InfluxDBMapperRegistry</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapperRegistry
 * @since 2025/7/30 16:24:11
 */
public class InfluxDBMapperRegistry implements BeanDefinitionRegistryPostProcessor {

    private final InfluxDBProperties properties;

    public InfluxDBMapperRegistry(InfluxDBProperties properties) {
        this.properties = properties;
    }


    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        InfluxDBMapperScanner mapperScanner = new InfluxDBMapperScanner(registry);
        mapperScanner.scan(properties.getMapperBasePackage());
    }

}
