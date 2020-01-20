package com.wjj.redis.test;

import com.wjj.redis.client.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangjunjie 2019/4/29 14:58
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 14:58
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private JedisPool jedisPool;

    /**
     * get
     */
    @Test
    public void test0() {
        String result = redisClient.string().get("wjj");
        if (result==null) {
            System.out.println("result...null");
        }else {
            System.out.println(result);
        }
    }

    @Test
    public void test1() {
        Long count = redisClient.zset().zremrangeByScore("age",0,20);
        System.out.println(count);
    }

  /*  @Test
    public void test2() {
        String nx1 = redisClient.setNx("nx1", "1", 60 * 60);
        System.out.println(nx1);
    }

    @Test
    public void test3() {
        String nx1 = redisClient.setNx("nx1", "1", 60 * 60);
        System.out.println(nx1);
    }
*/

 /*   *//**
     * 分布式锁 加锁
     *//*
    @Test
    public void test4() {
        String nx1 = redisClient.setNx("nx2", "8845", 60 * 60);
        System.out.println(nx1);
    }
*/
    /**
     * 分布式锁 解锁
     */
    @Test
    public void test5() {
        String luaScript = "if redis.call('get', KEYS[1]) == " +
                "ARGV[1] then return redis.call('del', KEYS[1]) else " +
                "return 0 end";
        List<String> key = new ArrayList<>();
        key.add("nx2");
        List<String> args = new ArrayList<>();
        args.add("8845");
        Long result = (Long) redisClient.eval(luaScript, key, args);
        System.out.println(result);

    }

    @Test
    public void test6() {
        Jedis jedis = jedisPool.getResource();
        jedis.publish("message1","我是你哥哥2");

    }

    @Test
    public void hset() {
        Long thumb = redisClient.hash().hset("thumb", "c1::u1", "1");
        System.out.println(thumb);
    }

    @Test
    public void thumb() {

        String luaScript = "if redis.call('hset','thumb',KEYS[1],ARGV[1]) == 1 " +
                "then return redis.call('hincrby','thumb:count',KEYS[2],1) " +
                "else return 0 end";

        Jedis jedis = jedisPool.getResource();
        List<String> key = new ArrayList<>();
        key.add("c2::u3");
        key.add("c2");
        List<String> args = new ArrayList<>();
        args.add("1");
        Long result = (Long)jedis.eval(luaScript, key, args);
        System.out.println(result);
    }

    @Test
    public void hGet() {
        String hget = redisClient.hash().hget("wjj1", "age");
        System.out.println(hget);
    }


    /**
     * 去重 请求
     */
    @Test
    public void test7() {

        String luaScript = "redis.call('set',KEYS[1],ARGV[1]);" +
                "return redis.call('expire',KEYS[1],ARGV[2]) " ;

        List<String> key = new ArrayList<>();
        key.add("cash:442");
        List<String> args = new ArrayList<>();
        args.add("88589");
        args.add("36000");
        long result = (long) redisClient.eval(luaScript, key, args);
        System.out.println(result);
    }


    /**
     * 去重 提交请求
     */
    @Test
    public void test8() {

        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1]" +
                "then return redis.call('del',KEYS[1]) else return 0 end" ;

        List<String> key = new ArrayList<>();
        key.add("cash:442");
        List<String> args = new ArrayList<>();
        args.add("88589");
        long result = (long) redisClient.eval(luaScript, key, args);
        System.out.println(result);
    }


    /**
     * 限流
     */
    @Test
    public void test9() {

        String luaScript = "redis.call('zremrangeByScore',KEYS[1],0,ARGV[3]);" +
                "if (redis.call('zcard',KEYS[1]) < tonumber(ARGV[4]))" +
                "then return redis.call('zadd',KEYS[1],ARGV[2],ARGV[1]) " +
                "else return 0 end";
        for (int i=0;i<10;++i) {
            long currentTimeMillis = System.currentTimeMillis();
            List<String> key = new ArrayList<>();
            key.add("read:428");
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(currentTimeMillis));
            args.add(String.valueOf(currentTimeMillis));
            long arg2 = currentTimeMillis-60*1000;
            args.add(String.valueOf(arg2));
            args.add("5");
            long result = (long) redisClient.eval(luaScript, key, args);
            if (result==1) {
                System.out.println("success..."+i);
            }else {
                System.out.println("fail...."+i);
            }
        }

    }

    /**
     * 限流
     */
    @Test
    public void test10() {

        String luaScript =
                "redis.call('zremrangeByScore',KEYS[1],0,ARGV[1]);if (redis.call('zcard',KEYS[1]) < tonumber(ARGV[2])" +
                        ")" +
                        "then return redis.call('zadd',KEYS[1],ARGV[3],ARGV[4]) else return 0 end";
        long currentTimeMillis = System.currentTimeMillis();
        List<String> key = new ArrayList<>();
        key.add("age");
        List<String> args = new ArrayList<>();
        args.add("23");
        args.add("3");
        args.add("23");
        args.add("cxh");
        long result = (long) redisClient.eval(luaScript, key, args);
        System.out.println(result);
    }


}
