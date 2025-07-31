package io.adrainty.boot.influxdb.mapper;

import io.adrainty.boot.influxdb.annotation.InfluxDBMapper;
import io.adrainty.boot.influxdb.annotation.InfluxDBParam;
import io.adrainty.boot.influxdb.binding.BaseInfluxDBMapper;
import io.adrainty.boot.influxdb.domain.Log;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午7:40
 */

@InfluxDBMapper
public interface LogMapper extends BaseInfluxDBMapper<Log> {

    Log customSelect(@InfluxDBParam(value = "tid") String tid);

}
