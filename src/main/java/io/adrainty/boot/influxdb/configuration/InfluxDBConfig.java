package io.adrainty.boot.influxdb.configuration;

import io.adrainty.boot.influxdb.properties.InfluxDBProperties;
import io.adrainty.boot.influxdb.registery.InfluxDBMapperRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>InfluxDBConfig</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description TODO
 * @since 2025/7/30 16:41:45
 */

@Configuration
@EnableConfigurationProperties({
        InfluxDBProperties.class
})
public class InfluxDBConfig {

    @Bean
    public InfluxDBMapperRegistry influxDBMapperRegistry(InfluxDBProperties properties) {
        return new InfluxDBMapperRegistry(properties);
    }

}
