package com.wjj.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangjunjie 2019/5/11 14:26
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/5/11 14:26
 */

@Configuration
public class RedissonManagerConfig {

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

    private int timeout=6000;

    @Bean
    public RedissonClient redissonSingle() {
        String address = "redis://"+this.host+":"+this.port;
        Config config = new Config();
        config.useSingleServer().
                setAddress(address).
                setPassword("921210").
                setDatabase(3);
        config.setLockWatchdogTimeout(20*1000);
        return Redisson.create(config);
    }
}
