package io.adrainty.boot.influxdb.handler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * <p>InfluxDBResultSetHandler</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBResultSetHandler
 * @since 2025/7/31 15:00:48
 */
public interface InfluxDBResultSetHandler {

    Object handleResultSet(List<Map<String, Object>> reultList, Method method, String sql, Class<?> resultType);

}



