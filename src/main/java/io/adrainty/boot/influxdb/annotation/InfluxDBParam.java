package io.adrainty.boot.influxdb.annotation;

import java.lang.annotation.*;

/**
 * <p>Param</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description 参数
 * @since 2025/7/30 16:12:34
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface InfluxDBParam {

    String value();

}
