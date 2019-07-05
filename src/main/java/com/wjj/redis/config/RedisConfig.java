package com.wjj.redis.config;

import io.rebloom.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: wangjunjie 2019/4/29 15:05
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 15:05
 */

@Configuration
public class RedisConfig {

    @Value("${redis-student.redis.host}")
    private String host;

    @Value("${redis-student.redis.port}")
    private int port;

    @Value("${redis-student.redis.password}")
    private String password;

    @Value("${redis-student.redis.database}")
    private int database;

    @Value("${redis-student.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${redis-student.redis.jedis.pool.min-idle}")
    private int minIdle;


    @Value("${redis-student.redis.jedis.pool.max-active}")
    private int maxActive;

    private int timeout=10000;

    @Bean
    public JedisPool redisPoolFactory()  throws Exception{
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setMinIdle(minIdle);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port,timeout , password,database);
        return jedisPool;
    }



}
