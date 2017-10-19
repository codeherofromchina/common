package com.erui.comm.middle.redis;

import redis.clients.jedis.ShardedJedis;

public  class ShardedJedisUtil {
	
	public static void main(String [] aa)
	{
		ShardedJedis jedis = JedisPoolUtil.getJedis();
		jedis.sadd("zhihai","shuai");
		System.out.println(jedis.get("foo"));
		JedisPoolUtil.returnRes(jedis);
	}
}
