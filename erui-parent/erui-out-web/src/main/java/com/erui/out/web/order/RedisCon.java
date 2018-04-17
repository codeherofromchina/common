package com.erui.out.web.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 17:00 2018/3/8
 * @modified By
 */
public class RedisCon {
    @Autowired
    private StringRedisTemplate redisTemplate;
    public void saveUser() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String key = "string_redis_template";
        String v = "use StringRedisTemplate set k v";
        vop.set(key, v);
        String value = vop.get(key);
        System.out.println(value);
    }
    public static void main(String [] args){
        System.out.println();
    }
}