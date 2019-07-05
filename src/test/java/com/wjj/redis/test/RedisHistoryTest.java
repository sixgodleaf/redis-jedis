package com.wjj.redis.test;

import com.wjj.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;

/**
 * @Author: wangjunjie 2019/5/10 14:02
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/5/10 14:02
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisHistoryTest {

    @Autowired
    private RedisUtil redisUtil;

    //阅读历史
    @Test
    public void addReadHistory() {
        Random random = new Random();
        for (int i=0;i<50;++i) {
            String value = random.nextInt(50)+"new";
            int score = random.nextInt(50);
            redisUtil.zadd("read_history:188",value,score);
        }
        redisUtil.expire("read_history:187",86400);
    }


    @Test
    public void getReadHistory() {
        Set<String> set = redisUtil.zrevrange("read_history:188", 0, 2);
        for (String s : set) {
            System.out.println(s);
        }
    }
}
