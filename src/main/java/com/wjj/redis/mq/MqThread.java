package com.wjj.redis.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: wangjunjie 2019/4/30 10:22
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/30 10:22
 */

@Component
public class MqThread extends Thread {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MqHandler mqHandler;

    @Override
    public void run() {
        System.out.println("thread.....");
        Jedis jedis = jedisPool.getResource();
        jedis.subscribe(mqHandler,"message1");

    }
}
