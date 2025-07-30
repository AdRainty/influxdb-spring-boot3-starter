package io.adrainty.boot.influxdb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>InfluxDBProperties</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBProperties
 * @since 2025/7/30 16:38:28
 */

@Data
@ConfigurationProperties(prefix = "spring.influx.mapper")
public class InfluxDBProperties {

    private String mapperLocation;

    private String mapperBasePackage;

}
