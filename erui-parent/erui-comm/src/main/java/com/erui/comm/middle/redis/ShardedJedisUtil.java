package com.erui.comm.middle.redis;

import redis.clients.jedis.ShardedJedis;

public  class ShardedJedisUtil {

    public static void main(String[] aa) {

        ShardedJedis jedis = JedisPoolUtil.getJedis();
        jedis.set("zhihai", "shuai");
        System.out.println(jedis.get("zhihai"));
        JedisPoolUtil.returnRes(jedis);
    }


    public static boolean exists(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            return jedis.exists(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return false;
    }


    public static Integer getInteger(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            if (jedis.exists(key)) {
                return Integer.parseInt(jedis.get(key));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return null;
    }

    public static void setExpire(String key, String value, String nxxx, String expx, long expire) {
        ShardedJedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            jedis.set(key, value, nxxx, expx, expire); // 设置60分钟过期
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JedisPoolUtil.returnRes(jedis);
        }
    }

}
