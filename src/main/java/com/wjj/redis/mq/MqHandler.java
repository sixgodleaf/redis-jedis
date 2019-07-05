package com.wjj.redis.mq;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

/**
 * @Author: wangjunjie 2019/4/30 10:19
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/30 10:19
 */

@Component
public class MqHandler extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(">> 接收到了来自 " + channel + " 的消息： " + message);
    }
}
