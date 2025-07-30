package io.adrainty.boot.influxdb.annotation;

import java.lang.annotation.*;

/**
 * <p>InfluxDBMapper</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description Spring注解扫描用
 * @since 2025/7/30 16:23:29
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface InfluxDBMapper {
}
