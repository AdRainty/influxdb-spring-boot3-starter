package io.adrainty.boot.influxdb.configuration;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
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
@ConfigurationProperties(prefix = "influx.mapper")
public class InfluxDBMapperProperties implements InitializingBean {

    private String mapperLocation;

    private Boolean camelCaseToUnderscore = true;

    @Override
    public void afterPropertiesSet() {
        System.out.println("InfluxDBProperties.afterPropertiesSet");
        System.out.println(mapperLocation);
    }

}
