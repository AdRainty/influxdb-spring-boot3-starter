package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.configuration.InfluxDBMapperProperties;
import io.adrainty.boot.influxdb.exception.BindingException;
import io.adrainty.boot.influxdb.parsing.XNode;
import io.adrainty.boot.influxdb.parsing.XPathParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>InfluxDBMapperRegistry</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @since 2025/7/31 14:16:14
 */

@Slf4j
@SuppressWarnings("unused")
public class InfluxDBMapperRegistry {

    private final Map<Class<?>, Map<String, InfluxDBMappedMethod>> influxDBMappedMethodMap;

    private final Map<String, Class<?>> objectMap;

    public InfluxDBMapperRegistry(List<InfluxDBMapperFactoryBean<?>> factoryBeanList, InfluxDBMapperProperties properties) {
        this.influxDBMappedMethodMap = new HashMap<>(factoryBeanList.size());
        this.objectMap = new HashMap<>(factoryBeanList.size());

        initCacheMap(factoryBeanList);
        resolveXmlFileList(properties);
    }

    private void initCacheMap(List<InfluxDBMapperFactoryBean<?>> factoryBeanList) {
        for (InfluxDBMapperFactoryBean<?> factoryBean : factoryBeanList) {
            Class<?> objectType = factoryBean.getObjectType();
            factoryBean.setRegistry(this);
            if (objectType != null) {
                this.objectMap.put(objectType.getName(), objectType);
                this.influxDBMappedMethodMap.put(objectType, new HashMap<>());
            }
        }
    }

    private void resolveXmlFileList(InfluxDBMapperProperties properties) {
        try {
            if (StringUtils.hasLength(properties.getMapperLocation())) {
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(properties.getMapperLocation());
                log.info("InfluxDBMapperRegistry load mapper file: {}, fileSize: {}", properties.getMapperLocation(), resources.length);
                for (Resource resource : resources) {
                    try (InputStream is = resource.getInputStream()) {
                        XPathParser xPathParser = new XPathParser(is);
                        XNode xNode = xPathParser.evalNode("/mapper");
                        String namespace = xNode.getStringAttribute("namespace");
                        Class<?> clazz = objectMap.get(namespace);
                        Map<String, InfluxDBMappedMethod> mappedMethodMap = influxDBMappedMethodMap.get(clazz);

                        List<XNode> children = xNode.getChildren();
                        for (XNode node : children) {
                            InfluxDBMappedMethod mappedMethod = new InfluxDBMappedMethod();
                            mappedMethod.setMethodName(node.getStringAttribute("id"));
                            mappedMethod.setSqlCommand(node.getStringBody());
                            String resultType = node.getStringAttribute("resultType");
                            Class<?> className = Class.forName(resultType);
                            mappedMethod.setReturnType(className);

                            mappedMethodMap.put(mappedMethod.getMethodName(), mappedMethod);
                        }

                        influxDBMappedMethodMap.put(clazz, mappedMethodMap);
                    }
                }
            }
        } catch (IOException e) {
            throw new BindingException("InfluxDBMapperRegistry load mapper file error", e);
        } catch (ClassNotFoundException e) {
            throw new BindingException("Result type error: ", e);
        }
    }

    public <T> InfluxDBMappedMethod getMappedStatement(Class<T> mapperInterface, String methodName) {
        Map<String, InfluxDBMappedMethod> mappedStatementMap = this.influxDBMappedMethodMap.get(mapperInterface);
        if (mappedStatementMap == null) {
            return null;
        }
        return mappedStatementMap.get(methodName);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getClass(String className) {
        return (Class<T>) this.objectMap.get(className);
    }

}
