package com.wjj.redis.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.wjj.redis.util.GsonUtil;
import com.wjj.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: wangjunjie 2019/7/4 10:28
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/4 10:28
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaLimit {

    @Autowired
    private RedisUtil redisUtil;


}
