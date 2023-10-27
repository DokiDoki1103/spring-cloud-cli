package com.example.web.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangxiaoyuan
 * @date 2020/11/21 22:53
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    public Long getKeyExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public long redisRateLimitCount(String redisKey, int periodSeconds) {

        // 获取当前时间戳
        long now = System.currentTimeMillis();
        // 计算当前时间段的结束时间
        // 删除时间段之前的请求记录
//        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, now - periodSeconds * 1000L);

        if (!hasKey(redisKey)) {
            // 将当前时间作为分值和成员值添加到 ZSet 中
            redisTemplate.opsForZSet().add(redisKey, now, now);
            // 设置为当天 23:59:59 过期
            LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
            Instant instant = endOfDay.atZone(ZoneId.systemDefault()).toInstant();
            // 设置过期时间为当前时间段的结束时间
            redisTemplate.expireAt(redisKey, instant);
        } else {
            //直接增加成员
            redisTemplate.opsForZSet().add(redisKey, now, now);
        }
        // 统计当前时间段内的请求次数
        long count = redisTemplate.opsForZSet().count(redisKey, now - periodSeconds * 1000L + 1, now);


        return count;
    }


    /**
     * Redis 限流方法
     *
     * @param periodSeconds 时间段，单位为秒
     * @param count         最大请求数
     * @return 是否允许请求
     */

    public boolean redisRateLimit(String redisKey, int periodSeconds, int count) {
        long l = redisRateLimitCount(redisKey, periodSeconds);

        // 判断请求次数是否超过限制
        return count < l;
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, int time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
//    @Async
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public int sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 取出指定key开头的所有key
     *
     * @param key
     * @return
     */
    public Set<String> getKeys(String key) {
        return redisTemplate.keys(key + "*");
    }

    /**
     * 取出指定key的值
     *
     * @param
     * @return
     */

    public String randomKey() {
        return String.valueOf(redisTemplate.randomKey());
    }

    public JSONArray getValues(Set<String> keys) {
        JSONArray parse = (JSONArray) JSONArray.parse(JSON.toJSONString(redisTemplate.opsForValue().multiGet(keys)));
        return parse;
    }
}
