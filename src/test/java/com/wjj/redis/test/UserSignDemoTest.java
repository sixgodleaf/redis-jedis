package com.wjj.redis.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

/**
 * @Author: wangjunjie 2019/6/26 16:21
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/6/26 16:21
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserSignDemoTest {

    @Autowired
    private JedisPool jedisPool;
}
