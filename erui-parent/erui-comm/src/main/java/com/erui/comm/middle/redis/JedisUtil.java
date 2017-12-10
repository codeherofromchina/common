package com.erui.comm.middle.redis;


import com.erui.comm.util.data.string.StringUtils;
import redis.clients.jedis.Jedis;

public class JedisUtil {
	
    public static Jedis createJedis(String host, int port) {
        Jedis jedis = new Jedis(host, port);

        return jedis;
    }

    public static Jedis createJedis(String host, int port, String passwrod) {
        Jedis jedis = new Jedis(host, port);

        if (!StringUtils.isEmpty(passwrod))
            jedis.auth(passwrod);
        
        return jedis;
    }
}
