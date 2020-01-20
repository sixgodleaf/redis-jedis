package com.wjj.redis.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import java.util.*;


/**
 * @Author: wangjunjie 2019/4/29 14:38
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 14:38
 */

@Component
@Slf4j
public class RedisClient implements InitializingBean {

    @Autowired
    private JedisPool jedisPool;

    private RedisStringClient redisStringClient;
    private RedisZsetClient redisZsetClient;
    private RedisListClient redisListClient;
    private RedisHashClient redisHashClient;
    private RedisSetClient redisSetClient;
    private RedisBitMapClient redisBitMapClient;
    private RedisBloomClient redisBloomClient;


    /**
     * 获取jedis (使用后一定要关闭)
     *
     * @return jeis
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设置过期时间
     * @param key
     * @param value
     * @return
     */
    public Long expire(String key, int value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 执行lua脚本
     * @param luaScript
     * @param key
     * @param args
     * @return
     */
    public Object eval(String luaScript, List<String> key, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.eval(luaScript, key, args);
        }  finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    //HyperLogLog 不精确去重
    /**
     * 添加元素
     * @param key
     * @param value 1 添加成功 0 没成功 （可能有重复）
     */
    public Long pfAdd(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.pfadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 统计数量
     * @param key
     * @return
     */
    public Long pfCount(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.pfcount(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 遍历 key
     * @param key    key 正则表达式
     * @param cursor 游标 起始位置0 结束为0
     * @param limit  遍历的次数
     * @return
     */
    public ScanResult<String> scan(String key, String cursor, int limit) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(key);
            scanParams.count(limit);
            return jedis.scan(cursor, scanParams);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.redisStringClient = new RedisStringClient(jedisPool);
        this.redisZsetClient = new RedisZsetClient(jedisPool);
        this.redisSetClient = new RedisSetClient(jedisPool);
        this.redisHashClient = new RedisHashClient(jedisPool);
        this.redisListClient = new RedisListClient(jedisPool);
        this.redisBitMapClient = new RedisBitMapClient(jedisPool);
        this.redisBloomClient = new RedisBloomClient(jedisPool);
    }

    public RedisStringClient string() {
        return redisStringClient;
    }

    public RedisZsetClient zset() {
        return redisZsetClient;
    }

    public RedisSetClient set() {
        return redisSetClient;
    }

    public RedisHashClient hash() {
        return redisHashClient;
    }

    public RedisListClient list() {
        return redisListClient;
    }

    public RedisBitMapClient bitMap() {
         return redisBitMapClient;
    }

    public RedisBloomClient bloom() {
        return redisBloomClient;
    }
}
