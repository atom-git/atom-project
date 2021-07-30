package com.atom.common.pojo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author zr
 * @description 全局配置读取类
 * @date 2020/3/10
 */
@Configuration
@SuppressWarnings("unused")
public class GlobalConfig {

    /**
     * 全局的配置文件
     */
    private static Environment environment;

    @Resource(name = "environment")
    public void setEnvironment(Environment environment) {
        GlobalConfig.environment = environment;
    }

    /**
     * 根据key查找配置项
     * @param key key
     * @return 返回配置
     */
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 根据key查找配置项int值
     * @param key key
     * @return 返回配置
     */
    public static int getPropertyInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * 根据key查找配置项boolean值
     * @param key key
     * @return 返回配置
     */
    public static boolean getPropertyBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * 根据key查找配置项
     * @param key key
     * @param params 格式化参数
     * @return 返回配置
     */
    public static String getProperty(String key, Object... params) {
        String val = environment.getProperty(key);
        return val == null ? "" : String.format(val, params);
    }
}
