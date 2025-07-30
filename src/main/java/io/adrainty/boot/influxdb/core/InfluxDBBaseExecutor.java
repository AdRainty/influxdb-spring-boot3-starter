package io.adrainty.boot.influxdb.core;

import org.influxdb.InfluxDB;

import java.util.List;
import java.util.Map;

/**
 * <p>InfluxDBExecutor</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description 执行器
 * @since 2025/7/30 15:58:17
 */

public class InfluxDBBaseExecutor implements InfluxDBExecutor {

    private final InfluxDB influxDB;

    public InfluxDBBaseExecutor(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    @Override
    public List<Map<String,Object>> select(String sql, String database) {
        return List.of();
    }

    @Override
    public void insert(Object[] args) {

    }

}
