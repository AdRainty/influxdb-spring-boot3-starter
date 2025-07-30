package io.adrainty.boot.influxdb.mapper;

import java.util.List;

/**
 * <p>InfluxDBMapper</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapper
 * @since 2025/7/30 16:10:12
 */
public interface BaseInfluxDBMapper<T> {

    /**
     * 插入数据
     * @param entity 实体
     */
    void insert(T entity);

    /**
     * 批量插入数据
     * @param entityList 实体列表
     */
    void insertBatch(List<T> entityList);

}
