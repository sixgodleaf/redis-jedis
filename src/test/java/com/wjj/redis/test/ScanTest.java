package com.wjj.redis.test;

import com.wjj.redis.util.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ScanResult;

import java.util.List;


/**
 * @Author: wangjunjie 2019/8/26 21:51
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/8/26 21:51
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScanTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void test1() {
        for (int x= 50; x<70;++x) {
            redisClient.set("test:scan:"+x,"1");
        }
    }

    @Test
    public void testScan() {
        String cursor="0";

        int count = 0;
        while (true) {
            ScanResult<String> scanResult = redisClient.scan("test:scan:*", cursor,10);
            System.out.println(scanResult.getCursor());
            cursor = scanResult.getCursor();
            if ("0".equals(cursor)) {
                break;
            }
            List<String> result = scanResult.getResult();
            for (String s : result) {
                ++count;
                System.out.println(s);
            }
            System.out.println(count);
        }


    }

}
