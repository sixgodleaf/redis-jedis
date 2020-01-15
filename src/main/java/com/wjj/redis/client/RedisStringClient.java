package com.wjj.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisStringClient {

    private JedisPool jedisPool;

    public RedisStringClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取值
     *
     * @param key 如果不存在，则返回null
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }



    /**
     * @param key
     * @param value
     * @return 成功 key
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param key
     * @return value(增加后的值)
     */
    public Long incr(String key) {
        Jedis jedis = null;
        Long value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.incr(key);
            return value;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param key
     * @param incrValue 增加后的值
     * @return value(增加后的值)
     */
    public Long incrBy(String key, int incrValue) {
        Jedis jedis = null;
        Long value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.incrBy(key, incrValue);
            return value;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
