package io.adrainty.boot.influxdb.core;

import java.util.List;
import java.util.Map;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午9:48
 */
public interface InfluxDBExecutor {

    /**
     * 查询
     * @param sql sql
     * @param database 数据库
     * @return 结果集
     */
    List<Map<String,Object>> select(String sql, String database);

    /**
     * 插入
     * @param args 参数
     */
    void insert(Object[] args);

}
