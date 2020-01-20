package com.wjj.redis.client;

import com.wjj.redis.JReBloom.JReBloomClinet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 布隆过滤器
 */
public class RedisBloomClient {

    private JedisPool jedisPool;

    public RedisBloomClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    // jReBloom redis布隆过滤器

    /**
     * 添加元素
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean jReBloomAdd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.add(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 添加元素
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean[] jReBloomAddMulti(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.addMulti(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断元素是否存在
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean jReBloomExists(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.exists(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断元素是否存在
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean[] jReBloomExistsMulti(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.existsMulti(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 自定义布隆过滤器
     * @param name         key
     * @param initCapacity 预计放入元素数量
     * @param errorRate    错误率  小于1
     * @return OK 创建成功 已存在抛出异常
     */
    public String createFilter(String name, long initCapacity, double errorRate) {

        if (errorRate >= 1) {
            return "false";
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.createFilter(jedis, name, initCapacity, errorRate);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
