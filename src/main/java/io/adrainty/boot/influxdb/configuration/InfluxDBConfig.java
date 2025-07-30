package io.adrainty.boot.influxdb.configuration;

import io.adrainty.boot.influxdb.core.InfluxDBBaseExecutor;
import io.adrainty.boot.influxdb.properties.InfluxDBProperties;
import io.adrainty.boot.influxdb.binding.InfluxDBMapperRegistry;
import org.influxdb.InfluxDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        InfluxDBProperties.class
})
public class InfluxDBConfig {

    @Bean
    public InfluxDBMapperRegistry influxDBMapperRegistry(@Autowired InfluxDBProperties properties) {
        return new InfluxDBMapperRegistry(properties);
    }

    @Bean
    public InfluxDBBaseExecutor influxDBExecutor(@Autowired InfluxDB influxDB) {
        return new InfluxDBBaseExecutor(influxDB);
    }

}
