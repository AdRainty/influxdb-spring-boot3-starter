package io.adrainty.boot.influxdb.binding;

import io.adrainty.boot.influxdb.annotation.EnableInfluxDBMappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

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


}
