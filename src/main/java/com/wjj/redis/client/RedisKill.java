package com.wjj.redis.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangjunjie 2019/9/23 17:29
 * @Description: redis 秒杀活动相关
 * @Version: 1.0.0
 * @Modified By: xxx 2019/9/23 17:29
 */

@Component
@Slf4j
public class RedisKill implements InitializingBean {



    private String secKillLuaId = "";

    private JedisPool jedisPool;

    private static final String SEC_KILL_COUNT_PREFIX="sec_kill_count:";

    private static final String SEC_KILL_USER_PREFIX="sec_kill_user:";


    public RedisKill(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public long redisKill(String killId,String uid) {
        List<String> keys = new ArrayList<>();
        keys.add(SEC_KILL_COUNT_PREFIX+killId);
        keys.add(SEC_KILL_USER_PREFIX+killId);
        keys.add(uid);
        List<String> args = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object result = jedis.evalsha(this.secKillLuaId, keys,args);
            return (long)result;
        }finally {
            if (jedis!=null) {
                jedis.close();
            }

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // lua 脚本
        StringBuilder luaScript = new StringBuilder();
        luaScript.append("if redis.call('sismember',KEYS[2],KEYS[3]) == 1 then ");
        luaScript.append("return 100 ");
        luaScript.append("else ");
        luaScript.append("local num = redis.call('decr',KEYS[1]) ");
        luaScript.append("if num < 0 then ");
        luaScript.append("return 101 ");
        luaScript.append("else ");
        luaScript.append("if redis.call('sadd',KEYS[2],KEYS[3]) == 1 then ");
        luaScript.append("return 200 ");
        luaScript.append("else ");
        luaScript.append("return 103 ");
        luaScript.append("end ");
        luaScript.append("end ");
        luaScript.append("end ");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            this.secKillLuaId = jedis.scriptLoad(luaScript.toString());
        }catch (Exception e) {
            log.error("加载秒杀活动lua脚本异常",e);
        } finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
    }
}
