package com.wjj.redis.test;

import com.wjj.redis.client.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @Author: wangjunjie 2019/4/29 14:58
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 14:58
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisZsetTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void test1() {
        Set<Tuple> stars = redisClient.zset().zrevrangeWithScores("stars", 5, 9);
        System.out.println(stars.size());
        for (Tuple star : stars) {
            System.out.println(star.getElement()+":"+star.getScore());
        }
    }

    @Test
    public void test2() {
        Long index = redisClient.zset().zrank("stars", "gulinaza1");
        System.out.println(index);
    }



}
