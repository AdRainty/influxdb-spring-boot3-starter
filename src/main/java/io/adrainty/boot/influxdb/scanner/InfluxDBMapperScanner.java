package io.adrainty.boot.influxdb.scanner;

import io.adrainty.boot.influxdb.annotation.InfluxDBMapper;
import io.adrainty.boot.influxdb.binding.InfluxDBMapperFactoryBean;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * <p>InfluxDBMapperScanner</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description InfluxDBMapper扫描器
 * @since 2025/7/30 16:26:23
 */
public class InfluxDBMapperScanner extends ClassPathBeanDefinitionScanner {

    public InfluxDBMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
        this.addIncludeFilter(
                new AnnotationTypeFilter(InfluxDBMapper.class)
        );
    }

    @NotNull
    @Override
    protected Set<BeanDefinitionHolder> doScan(@NotNull String... basePackages) {
        // 1. 扫描并获取Bean定义
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        // 2. 处理每个接口定义
        for (BeanDefinitionHolder holder : beanDefinitions) {
            AnnotatedBeanDefinition definition = (AnnotatedBeanDefinition) holder.getBeanDefinition();

            // 3. 获取接口类名
            String interfaceClassName = definition.getBeanClassName();

            // 4. 设置Bean类为工厂Bean
            definition.getConstructorArgumentValues().addGenericArgumentValue(interfaceClassName);
            definition.setBeanClassName(InfluxDBMapperFactoryBean.class.getName());
        }

        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(@NotNull AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

}
