package io.adrainty.boot.influxdb.annotation;

import io.adrainty.boot.influxdb.binding.InfluxDBMapperBeanDefinitionRegistrar;
import io.adrainty.boot.influxdb.configuration.InfluxDBConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午10:04
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({InfluxDBConfig.class, InfluxDBMapperBeanDefinitionRegistrar.class})
@SuppressWarnings("unused")
public @interface EnableInfluxDBMappers {

    String[] value() default {};

}
