package com.wjj.redis.test;

import com.wjj.redis.util.RedisClient;
import com.wjj.redis.util.ScriptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangjunjie 2019/7/3 15:05
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/3 15:05
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class LuaTest {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void readScript() throws IOException {
        String script = ScriptUtil.getScript("/lua/limit.lua");
        System.out.println(script);
    }

    @Test
    public void loadScript() throws IOException {
        //64e8e3ad38be22342dd54e22af55f7ad6f726ef8

        String script = ScriptUtil.getScript("/lua/limit.lua");
        //向redis加载lua脚本
        Jedis jedis = jedisPool.getResource();
        String sign = jedis.scriptLoad(script);

        for (int i=0;i<10;++i) {
            List<String> key = new ArrayList<>();
            key.add("age");
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(3));
            args.add(String.valueOf(3));
            args.add(String.valueOf(i));
            args.add("script1");
            //运行脚本
            Long result = (Long) jedis.evalsha(sign, key, args);
            System.out.println(result);
        }

        System.out.println(sign);

    }

    @Test
    public void testScript() throws IOException {

        String script = ScriptUtil.getScript("/lua/limit.lua");

        for (int i=0;i<10;++i) {
            List<String> key = new ArrayList<>();
            key.add("age");
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(3));
            args.add(String.valueOf(3));
            args.add(String.valueOf(i));
            args.add("script1");
            Long result = (Long) redisClient.eval(script, key, args);
            System.out.println(result);
        }

    }
}
