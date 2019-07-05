package com.wjj.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: wangjunjie 2019/6/26 13:51
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/6/26 13:51
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class BitMapTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test1() {
        Jedis jedis = jedisPool.getResource();
        Boolean setbit = jedis.setbit("sign1:pid", 3,true);
    }

    @Test
    public void getBit() {
        Jedis jedis = jedisPool.getResource();
        Boolean setbit = jedis.getbit("sign1:pid", 2);
        System.out.println(setbit);
    }

    @Test
    public void bittops() {
        Jedis jedis = jedisPool.getResource();
        Long bitcount = jedis.bitpos("sign1:187",true);
        System.out.println(bitcount);
    }

    @Test
    public void test() {
        long v=2;
        v >>= 1;
        boolean b = v >> 1 << 1 != v;
        System.out.println(b);
        System.out.println(v);
    }
}
