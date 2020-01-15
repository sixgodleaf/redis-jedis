package com.wjj.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 位图
 */
public class RedisBitMapClient {

    private JedisPool jedisPool;

    public RedisBitMapClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    /**
     * @param key
     * @param offset 偏移量
     * @param flag   true 1 false 0
     * @return true 重复设置（偏移量和flag一样）
     */
    public boolean setBit(String key, int offset, boolean flag) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setbit(key, offset, flag);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param key
     * @param offset 偏移量
     * @return true 1 false 0
     */
    public boolean getBit(String key, int offset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getbit(key, offset);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * @param key
     * @return
     */
    public Long bitCount(String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.bitcount(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回的首次偏移量，
     *
     * @param key
     * @param flag true 1 false 0
     * @return -1 没有设置
     */
    public Long bitpos(String key, boolean flag) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.bitpos(key, flag);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
