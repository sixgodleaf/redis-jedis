package com.wjj.redis.component;

import com.wjj.redis.mq.MqThread;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: wangjunjie 2019/4/30 09:48
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/30 09:48
 */

//@Component
public class RedisConsumer implements InitializingBean {

    @Autowired
    private MqThread mqThread;


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bbbbbb");
        mqThread.start();
    }
}
