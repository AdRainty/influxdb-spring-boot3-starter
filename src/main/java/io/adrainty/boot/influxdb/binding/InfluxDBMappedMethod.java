package io.adrainty.boot.influxdb.binding;

import lombok.Data;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午9:56
 */

@Data
public class InfluxDBMappedMethod {

    private String methodName;

    private String sqlCommand;

    private Class<?> returnType;

}
