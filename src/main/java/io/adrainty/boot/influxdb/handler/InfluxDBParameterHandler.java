package io.adrainty.boot.influxdb.handler;

import java.lang.reflect.Parameter;

/**
 * <p>InfluxDBParameterHandler</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBParameterHandler
 * @since 2025/7/31 14:58:29
 */
public interface InfluxDBParameterHandler {

    String handleParameter(Parameter[] parameters, Object[] args, String sql);

}
