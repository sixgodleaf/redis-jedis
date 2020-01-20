package com.wjj.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisListClient {

    private JedisPool jedisPool;

    public RedisListClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * list 添加
     *
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lpush(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 获取list 列表
     *
     * @param key
     * @param start 开始
     * @param end   结束  -1 为最后
     * @return
     */
    public List lrange(String key, int start, int end) {
        Jedis jedis = null;
        List res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lrange(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * @param key
     * @param start
     * @param end   -1 表示列表的最后一个元素
     */
    public void ltrim(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.ltrim(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 根据参数 index 的值，移除列表中与参数 VALUE 相等的元素
     *
     * @param key
     * @param value 删除的值
     * @param count 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     *              从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     *              移除表中所有与 VALUE 相等的值
     */
    public void lrem(String key, String value, int count) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lrem(key, count, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
