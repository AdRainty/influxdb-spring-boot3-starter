# influxdb-spring-boot3-starter

Use influxdb like mybatis & mybatis plus

## Base environment

- Java 17
- Springboot 3

## Build

~~~shell
mvn clean install
~~~

## Pom dependency

pom.xml
~~~xml
<dependency>
    <groupId>io.adrainty</groupId>
    <artifactId>influxdb-spring-boot3-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
~~~

## Config 

application.yaml

~~~yaml
spring:
  influx:
    url: http://localhost:8086
    user: admin
    password: admin
influx:
  mapper:
    mapper-location: classpath:mapper/influxdb/*.xml
~~~

- `spring.influx`: influxdb config
- `influx.mapper.mapper-location`: mapper xml location, like mybatis

## Usage

~~~java
import io.adrainty.boot.influxdb.binding.BaseInfluxDBMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@InfluxDBMapper
public interface YourOwnMapper extends BaseInfluxDBMapper<YourOwnEntity> {
}

@Repository
public class Test {

    @Resource
    private YourOwnMapper yourOwnMapper;

    public void test() {
        YourOwnEntity yourOwnEntity = new YourOwnEntity();
        // ...... add properties
        yourOwnMapper.insert(yourOwnEntity);
    }
}
~~~

You can see [test case](src/test/java/io/adrainty/boot/influxdb/TestRunner.java) for detail.

## Extend

1. InfluxDBParameterHandler and InfluxDBResultHandler

You can implement `InfluxDBParameterHandler` or `InfluxDBResultHandler` to add your own handler.

~~~java
public interface InfluxDBParameterHandler {

    String handleParameter(Parameter[] parameters, Object[] args, String sql);

}

public interface InfluxDBResultSetHandler {

    Object handleResultSet(List<Map<String, Object>> reultList, Method method, String sql, Class<?> resultType);

}
~~~

2. Scan mapper annotation

AutoConfiguration will default scan the mapper with `@InfluxDBMapper` annotation. If you want to scan other mapper, you can use `EnableInfluxDBMappers` to custom the other package.

~~~java
import io.adrainty.boot.influxdb.annotation.EnableInfluxDBMappers;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableInfluxDBMappers(value = {"io.adrainty.boot.influxdb.mapper"})
public class InfluxDBConfig {
    // ...others
}
~~~