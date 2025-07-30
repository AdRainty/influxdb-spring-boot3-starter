package io.adrainty.boot.influxdb.annotation;

import java.lang.annotation.*;

/**
 * <p>TableName</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description 表名
 * @since 2025/7/30 16:17:58
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface InfluxDBTable {

    String value() default "";

    String schema() default "";

}
