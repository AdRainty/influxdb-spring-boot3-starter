# influxdb-spring-boot3-starter

Use influxdb like mybatis & mybatis plus

## Base environment

- Java 17
- Springboot 3

## Build

~~~shell
mvn clean install
~~~

## Usage

pom.xml
~~~xml
<dependency>
    <groupId>io.adrainty</groupId>
    <artifactId>influxdb-spring-boot3-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
~~~

[see test case](src/test/java/io/adrainty/boot/influxdb/TestRunner.java)

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