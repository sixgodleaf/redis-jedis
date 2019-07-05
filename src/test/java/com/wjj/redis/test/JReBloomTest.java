package com.wjj.redis.test;

import com.wjj.redis.util.RedisUtil;
import com.wjj.redis.util.ScriptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangjunjie 2019/7/4 11:41
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/4 11:41
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class JReBloomTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() {
        /*for (int i=0;i<1000;++i) {
            redisUtil.jReBloomAdd("newbloom:189",String.valueOf(i));
        }*/
        int falses=0;
        for (int i=1000;i<3000;++i) {
            if(redisUtil.jReBloomAdd("newbloom:189",String.valueOf(i))) {
                ++falses;
            }else {
                System.out.println(i);
            }
        }

        System.out.println(falses);
    }

    @Test
    public void createFilter() {
        try {
            String res = redisUtil.createFilter("bloom:211", 10000, 0.9);
            System.out.println(res);
        }catch (JedisDataException e) {
            System.out.println("ERR item exists");
        }


    }
}


