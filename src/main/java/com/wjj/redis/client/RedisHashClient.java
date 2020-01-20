package com.wjj.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

public class RedisHashClient {

    private JedisPool jedisPool;

    public RedisHashClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @param key
     * @param field
     * @param value
     * @return 成功 1 替换（已经存在key,field）0
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hset(key, field, value);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 如果不存在，则返回null
     * @param key
     * @param field
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hget(key, field);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * hincrBy 增加
     * @param key
     * @param field
     * @param incrValue
     * @return
     */
    public Long hincrBy(String key, String field, long incrValue) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hincrBy(key, field, incrValue);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * hgetAll 获取key 的属性和属性值
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hgetAll(key);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

}
