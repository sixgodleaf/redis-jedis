package com.wjj.redis.test;

import com.wjj.redis.util.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Author: wangjunjie 2019/8/27 16:52
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/8/27 16:52
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class HsetTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void test1() {
        Long read = redisClient.hincrBy("content:8859", "news", 1);
        System.out.println(read);
    }

    @Test
    public void test2() {
        Map<String, String> map = redisClient.hgetAll("content:8859");
        String read = map.get("read");
        String collection = map.get("collection");
        String share = map.get("share");
        System.out.println(read!=null ? Integer.parseInt(read):0);
        System.out.println(collection!=null ? Integer.parseInt(collection):0);
        System.out.println(share!=null ? Integer.parseInt(share):0);
    }
}
