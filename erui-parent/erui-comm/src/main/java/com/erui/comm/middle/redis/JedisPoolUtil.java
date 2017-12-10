package com.erui.comm.middle.redis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisPoolUtil {

    private static ShardedJedisPool pool;

	/**
	 * 建立连接池 真实环境，一般把配置参数缺抽取出来。
	 * 
	 */
    private static void createJedisPool() {
    	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    	config.setTestOnReturn(false);
    	config.setTestWhileIdle(true);
    	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
        config.setTestOnBorrow(false);  
    	// 设置最大连接数
        config.setMaxTotal(300);
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(100000);
        // 设置空间连接
        config.setMaxIdle(20);
        // 创建连接池
        pool = new ShardedJedisPool(config, getConfig());
    }
    private static List<JedisShardInfo> getConfig()
    {
    	List<JedisShardInfo> shards = null;
	    Properties pro = new Properties();  
	    InputStream in = new BufferedInputStream(JedisPoolUtil.class.getResourceAsStream("/comm.properties"));
	    try {
			pro.load(in);
			String servers [] = pro.getProperty("redis.server").split(",");
			String password = pro.getProperty("redis.password");
			shards = new ArrayList<JedisShardInfo>();
			for (int i = 0; i < servers.length; i++) {
				String [] server = servers[i].split(":");
				JedisShardInfo si = new JedisShardInfo(server[0], Integer.parseInt(server[1]));
				si.setPassword(password);
				shards.add(si);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return shards;
	    
    }
	 /**
	 * 在多线程环境同步初始化
	 */
    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }

	 /**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 */
    public static ShardedJedis getJedis() {

        if (pool == null)
            poolInit();
        return pool.getResource();
    }

	/**
	 * 归还一个连接
	 * 
	 * @param jedis
	 */
    public static void returnRes(ShardedJedis jedis) {
    	if (jedis != null) {
    		jedis.close();
    	}
    }
    
    /**-------------------------------过滤静态化添加-----------------------------------**/
    /**
     * 简单设置一个键值，无序不重复可覆盖
     * @param key
     * @param value
     * @return 实际插入的元素数量
     */
    public static long sadd(String key, String... value) {
		long ret = -1;
		ShardedJedis jedis = null;
        boolean oprSuccess = true;
        
		try {
			jedis = JedisPoolUtil.getJedis();
			
            ret = jedis.sadd(key , value);
            if (ret <= 0){
            	ret = jedis.sadd(key , value);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
			oprSuccess = false;
            if (jedis != null){
            	JedisPoolUtil.returnRes(jedis);
            }
		} finally{
			if (jedis!=null) {
				JedisPoolUtil.returnRes(jedis);
			}
		}
		
        return ret;
	}
	
    /**
     * 简单的获取一个键值的集合
     * @param key
     * @return
     */
	public static Set<String> smembers(String key) {
		Set<String> ret = null;
		ShardedJedis jedis = null;
        boolean oprSuccess = true;
        try {
        	jedis = JedisPoolUtil.getJedis();
            ret = jedis.smembers(key);
        }catch (Exception e){
            oprSuccess = false;
            if (jedis != null){
            	JedisPoolUtil.returnRes(jedis);
            }
        }finally {
            if (oprSuccess){
            	JedisPoolUtil.returnRes(jedis);
            }
        }
        return ret;
	}
	
	/**
	 * 从一个set中移除一个元素
	 * @param key
	 * @param value
	 * @return
	 */
	public static long removeValFromSets(String key, String value) {
		long ret = -1;
		ShardedJedis jedis = null;
        boolean oprSuccess = true;
        try {
        	jedis = JedisPoolUtil.getJedis();
            ret = jedis.srem(key, value);
        }catch (Exception e){
            oprSuccess = false;
            if (jedis != null){
            	JedisPoolUtil.returnRes(jedis);
            }
        }finally {
            if (oprSuccess){
            	JedisPoolUtil.returnRes(jedis);
            }
        }
        return ret;
	}
	/***
	 * 根据key获取自增数
	 */
	public static long getPcount(String key)
	{
		long incount = 0;
		ShardedJedis jedis = null;
        boolean oprSuccess = true;
        try {
        	jedis = JedisPoolUtil.getJedis();
        	incount = jedis.incr(key);
        }catch (Exception e){
            oprSuccess = false;
            if (jedis != null){
            	JedisPoolUtil.returnRes(jedis);
            }
        }finally {
            if (oprSuccess){
            	JedisPoolUtil.returnRes(jedis);
            }
        }
        return incount;
	}
}