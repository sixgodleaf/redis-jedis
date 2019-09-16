package com.wjj.redis.test;

import com.wjj.redis.util.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: wangjunjie 2019/9/16 09:50
 * @Description: 分布试锁测试
 * @Version: 1.0.0
 * @Modified By: xxx 2019/9/16 09:50
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Test
    public void test1() {
        boolean result = redisLock.tryLock("test1", "test1", 10 * 1000);
        if (result) {
            System.out.println("获得锁");
        }
        boolean resultUnLock = redisLock.unLock("test1", "test1");
        if (resultUnLock) {
            System.out.println("释放锁");
        }
    }


    @Test
    public void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(20);
        for (int i=0;i<20;++i) {
            int index=i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    boolean result = redisLock.waitLock("test2", "test2:"+index,60*1000, 30 * 1000);
                    if (result) {
                        System.out.println("获取锁");
                        try {
                            Thread.sleep(3*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        boolean unLock = redisLock.unLock("test2", "test2:" + index);
                        if (unLock) {
                            System.out.println("释放锁");
                        }
                    }else {
                        System.out.println("没有获取到锁");
                    }

                    latch.countDown();
                }
            });
        }

        latch.await();
        System.out.println("完成");
    }
}
