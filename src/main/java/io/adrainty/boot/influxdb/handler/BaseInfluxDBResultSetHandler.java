package io.adrainty.boot.influxdb.handler;

import cn.hutool.core.bean.BeanUtil;
import io.adrainty.boot.influxdb.exception.BindingException;
import lombok.SneakyThrows;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>BaseInfluxDBResultSetHandler</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description BaseInfluxDBResultSetHandler
 * @since 2025/7/31 15:01:26
 */
public class BaseInfluxDBResultSetHandler implements InfluxDBResultSetHandler {

    @Override
    public Object handleResultSet(List<Map<String, Object>> reultList, Method method, String sql, Class<?> resultType) {
        Class<?> returnTypeTarget = method.getReturnType();

        // 如果结果为空直接返回空构建
        if (CollectionUtils.isEmpty(reultList)) {
            if (returnTypeTarget == List.class) {
                return new ArrayList<>();
            } else if (returnTypeTarget == Map.class) {
                return new HashMap<>();
            } else if (returnTypeTarget == String.class) {
                return null;
            } else {
                return convertStringToObject(resultType, "0");
            }
        }
        // 当前method声明返回结果不为list,且resultType与method声明返回结果类型不匹配
        if (returnTypeTarget != List.class && resultType != returnTypeTarget) {
            throw new BindingException("return type is not equal to declare type");
        }
        // 当前method声明返回结果不为list,且resultType与method声明返回结果类型匹配
        if (returnTypeTarget != List.class) {
            // 结果不唯一则抛出异常
            if (reultList.size() != 1) {
                throw new RuntimeException("返回结果不唯一");
            }
            Map<String, Object> mapHandler = reultList.get(0);
            // 单个Map类型
            if (resultType == Map.class) {
                return mapHandler;
                // 单个自定义类型
            } else if (!ClassUtils.isPrimitiveOrWrapper(resultType)) {
                return BeanUtil.toBean(mapHandler, resultType);
                // 单个JDK提供指定类型
            } else {
                if (mapHandler.size() != 2) {
                    throw new RuntimeException("返回结果非单值");
                }
                for (String key : mapHandler.keySet()) {
                    if (!key.equals("time") && Objects.nonNull(mapHandler.get(key))) {
                        String target = String.valueOf(mapHandler.get(key)).replace(".0", "");
                        return convertStringToObject(resultType, target);
                    }
                }
            }
        }
        // 当前method声明返回结果为list
        if (returnTypeTarget == List.class) {
            // 驼峰处理
            // list的内部为map结果
            if (resultType == Map.class) {
                return reultList;
                // list的内部为自定义类型
            } else if (!ClassUtils.isPrimitiveOrWrapper(resultType)) {
                return reultList.stream().map(each -> BeanUtil.toBean(each, resultType)).toList();
                // list的内部为JDK提供指定类型
            } else {
                List<Object> listResult = new ArrayList<>();
                reultList.forEach(mapHandler -> {
                    if (mapHandler.size() != 2) {
                        throw new RuntimeException("返回结果非单值");
                    }
                    for (String key : mapHandler.keySet()) {
                        if (!key.equals("time") && Objects.nonNull(mapHandler.get(key))) {
                            String target = String.valueOf(mapHandler.get(key)).replace(".0", "");
                            listResult.add(convertStringToObject(resultType, target));
                        }
                    }
                });
                return listResult;
            }
        }
        return null;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T convertStringToObject(Class<?> clazz, String str) {
        if (clazz == String.class) {
            return (T) str; // 如果目标类型是 String，则直接返回字符串
        } else if (ClassUtils.isPrimitiveOrWrapper(clazz)) {
            // 获取目标类型的构造函数，参数为 String 类型的参数
            Constructor<?> constructor = clazz.getConstructor(String.class);
            return (T) constructor.newInstance(str); // 使用构造函数创建目标类型的对象
        } else {
            return (T) clazz.getDeclaredConstructor().newInstance();
        }
    }

}
