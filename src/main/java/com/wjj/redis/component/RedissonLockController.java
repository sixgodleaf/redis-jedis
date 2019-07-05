package com.wjj.redis.component;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wangjunjie 2019/5/11 15:32
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/5/11 15:32
 */

//@RestController
@RequestMapping("/redisson")
public class RedissonLockController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/lock1")
    public String testLock1() throws InterruptedException {
        RLock lock = redissonClient.getLock("redisson1");
        lock.lock();
        try {
            System.out.println("1111111111");
            return "success";
        }finally {
            lock.unlock();
        }

    }

    @GetMapping("/lock2")
    public String testLock2() throws InterruptedException {
        RLock lock = redissonClient.getLock("redisson1");
        lock.lock(15*1000,TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(1000*60);
            System.out.println("1111111111");
            return "success";
        }finally {
            lock.unlock();
        }

    }
}
