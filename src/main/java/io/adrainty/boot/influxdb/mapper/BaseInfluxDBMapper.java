package io.adrainty.boot.influxdb.mapper;

import io.adrainty.boot.influxdb.annotation.InfluxDBParam;

import java.io.Serializable;

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
     * @return 插入数量
     */
    int insert(T entity);

    /**
     * 根据id删除数据
     * @param id id
     * @return 删除数量
     */
    int deleteById(Serializable id);

    /**
     * 根据id更新数据
     * @param entity 实体
     * @return 更新数量
     */
    int updateById(@InfluxDBParam("entity") T entity);

    /**
     * 根据id查询数据
     * @param id id
     * @return 实体
     */
    T selectById(Serializable id);

}
