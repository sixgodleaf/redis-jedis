package com.wjj.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: wangjunjie 2019/6/26 16:22
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/6/26 16:22
 */
public class JedisUtil {


    public static Jedis getJedis() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(10000);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "39.106.193.32", 6379,10000 , "921210",3);
        return jedisPool.getResource();
    }

    public JedisUtil() {

    }
}
