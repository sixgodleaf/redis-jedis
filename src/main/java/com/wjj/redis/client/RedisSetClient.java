package com.wjj.redis.client;

import redis.clients.jedis.JedisPool;

public class RedisSetClient {

    private JedisPool jedisPool;

    public RedisSetClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


}
