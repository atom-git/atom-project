package com.atom.common.util;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zr
 * @description redis的操作类
 * @date 27/12/2017
 */
@Component
@Slf4j
@SuppressWarnings("unused")
public class RedisUtil {

    /**
     * redis工具类
     */
    private static RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 自动注入需要的
     */
    @Resource
    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * redisTemplate获取方法用于特殊的取值行为
     * @return 返回redisTemplate
     */
    public static RedisTemplate<Serializable, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 写入缓存
     *
     * @param key   key
     * @param value value
     */
    public static void set(String key, Object value) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
        } catch (Exception e) {
            log.error("redis写入失败：key:{}，value:{}，原因:{}", key, value, e.getMessage());
            throw e;
        }
    }

    /**
     * 写入缓存
     *
     * @param key        key
     * @param value      value
     * @param expireTime 失效时间
     */
    public static void set(String key, Object value, Long expireTime) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("redis写入失败：key:{}，value:{}，expireTime:{}，原因:{}", key, value, expireTime, e.getMessage());
            throw e;
        }
    }

    /**
     * 写入缓存
     *
     * @param key   key
     * @param field field
     * @param value value
     */
    public static void setMap(String key, String field, Object value) {
        try {
            HashOperations<Serializable, Serializable, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, field, value);
        } catch (Exception e) {
            log.error("redis写入失败：key:{}，value:{}，原因:{}", key, value, e.getMessage());
            throw e;
        }
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return 返回对象
     */
    public static String getString(String key) {
        return get(key) != null ? get(key).toString() : "";
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return 返回对象
     */
    public static Object get(String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 根据key field读取缓存
     *
     * @param key   key
     * @param field field
     * @return 返回对象
     */
    public static String getString(String key, String field) {
        return get(key, field) != null ? get(key, field).toString() : "";
    }

    /**
     * 根据key field读取缓存
     *
     * @param key   key
     * @param field field
     * @return 返回对象
     */
    public static Object get(String key, String field) {
        HashOperations<Serializable, Serializable, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, field);
    }

    /**
     * 根据key 读取缓存，返回map类型的对象
     *
     * @param key   key
     * @return 返回对象
     */
    public static Object getMap(String key) {
        HashOperations<Serializable, Serializable, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 要删除的Key
     */
    public static void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 正则模式
     */
    public static void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    public static void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 删除hash表中的key
     *
     * @param key   key
     * @param field field
     */
    public static void remove(String key, String field) {
        HashOperations<Serializable, Serializable, Object> hashOperations = redisTemplate.opsForHash();
        if (hashOperations.hasKey(key, field)) {
            hashOperations.delete(key, field);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return 是否存在key
     */
    public static boolean exists(String key) {
        return Validator.isNotEmpty(key) && Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return 是否存在key
     */
    public static boolean exists(String key, String field) {
        HashOperations<Serializable, Serializable, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(key, field);
    }

}
