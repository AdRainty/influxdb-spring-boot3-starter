package io.adrainty.boot.influxdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午7:39
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "log", database = "point_data")
public class Log {

    @Column(name = "tid", tag = true)
    private String tid;

    @Column(name = "rsp_code")
    public String rspCode;

    @Column(name = "rsp_msg")
    public String rspMsg;

}
