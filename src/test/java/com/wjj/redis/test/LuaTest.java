package com.wjj.redis.test;

import com.wjj.redis.util.RedisUtil;
import com.wjj.redis.util.ScriptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private RedisUtil redisUtil;

    @Test
    public void readScript() throws IOException {
        String script = ScriptUtil.getScript("/lua/limit.lua");
        System.out.println(script);
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
            Long result = (Long) redisUtil.eval(script, key, args);
            System.out.println(result);
        }

    }
}
