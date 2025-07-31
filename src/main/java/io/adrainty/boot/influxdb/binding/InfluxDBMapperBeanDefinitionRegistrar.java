package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.annotation.EnableInfluxDBMappers;
import io.adrainty.boot.influxdb.annotation.InfluxDBMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>InfluxDBMapperRegistry</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapperRegistry
 * @since 2025/7/30 16:24:11
 */

@SuppressWarnings("unused")
public class InfluxDBMapperBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private final Map<Class<?>, InfluxDBMapper> knownMappers;

    private BeanDefinitionRegistry beanRegistry;

    public InfluxDBMapperBeanDefinitionRegistrar() {
        this.knownMappers = new HashMap<>();
    }

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata metadata,
                                        @NotNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(EnableInfluxDBMappers.class.getName()));

        String[] basePackages;
        if (attributes == null) {
            String packageName = ClassUtils.getPackageName(metadata.getClassName());
            basePackages = new String[]{packageName};
        } else {
            basePackages = attributes.getStringArray("basePackages");
            if (basePackages.length == 0) {
                String packageName = ClassUtils.getPackageName(metadata.getClassName());
                basePackages = new String[]{packageName};
            }
        }

        InfluxDBMapperScanner mapperScanner = new InfluxDBMapperScanner(registry);
        mapperScanner.scan(basePackages);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return this.knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        this.knownMappers.put(type, type.getAnnotation(InfluxDBMapper.class));
    }

}
