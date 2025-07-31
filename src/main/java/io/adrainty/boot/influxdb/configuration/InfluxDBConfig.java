package io.adrainty.boot.influxdb.configuration;

import io.adrainty.boot.influxdb.binding.InfluxDBMapperFactoryBean;
import io.adrainty.boot.influxdb.binding.InfluxDBMapperRegistry;
import io.adrainty.boot.influxdb.core.InfluxDBBaseExecutor;
import io.adrainty.boot.influxdb.handler.BaseInfluxDBParameterHandler;
import io.adrainty.boot.influxdb.handler.BaseInfluxDBResultSetHandler;
import io.adrainty.boot.influxdb.handler.InfluxDBParameterHandler;
import io.adrainty.boot.influxdb.handler.InfluxDBResultSetHandler;
import org.influxdb.InfluxDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>InfluxDBConfig</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBConfig
 * @since 2025/7/30 16:41:45
 */

@Configuration
@EnableConfigurationProperties({
        InfluxDBMapperProperties.class
})
public class InfluxDBConfig {

    @Bean
    public InfluxDBBaseExecutor influxDBExecutor(@Autowired InfluxDB influxDB) {
        return new InfluxDBBaseExecutor(influxDB);
    }

    @Bean
    public InfluxDBMapperRegistry influxDBMapperRegistry(
            @Autowired List<InfluxDBMapperFactoryBean<?>> factoryBeanList,
            @Autowired InfluxDBMapperProperties properties
    ) {
        return new InfluxDBMapperRegistry(factoryBeanList, properties);
    }

    @Bean
    @ConditionalOnMissingBean(InfluxDBParameterHandler.class)
    public InfluxDBParameterHandler influxDBParameterHandler() {
        return new BaseInfluxDBParameterHandler();
    }

    @Bean
    @ConditionalOnMissingBean(InfluxDBResultSetHandler.class)
    public InfluxDBResultSetHandler influxDBResultSetHandler() {
        return new BaseInfluxDBResultSetHandler();
    }

}
