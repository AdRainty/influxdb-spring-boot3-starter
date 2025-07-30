package io.adrainty.boot.influxdb;

import io.adrainty.boot.influxdb.domain.Log;
import io.adrainty.boot.influxdb.mapper.LogMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/30 下午7:25
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunnerApplication.class)
public class TestRunner {

    @Resource
    private LogMapper logMapper;

    @Test
    public void testInsert() {
        Log log = Log.builder()
                .tid(UUID.randomUUID().toString())
                .rspCode("200")
                .rspMsg("Success")
                .build();
        logMapper.insert(log);
    }

    @Test
    public void testInsertBatch() {
        List<Log> logList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Log log = Log.builder()
                    .tid(UUID.randomUUID().toString())
                    .rspCode("200")
                    .rspMsg("Success")
                    .build();
            logList.add(log);
        }
        logMapper.insertBatch(logList);
    }

    @Test
    public void testXmlSQL() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        List<Log> log = logMapper.customSelect(start.toString(), end.toString());
        TestRunner.log.info("testXmlSQL: {}", log);
        Assertions.assertNotNull(log);
    }


}
