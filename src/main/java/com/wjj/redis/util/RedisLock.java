package com.wjj.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: wangjunjie 2019/4/29 17:22
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 17:22
 */

@Component
public class RedisLock {

    @Autowired
    private RedisUtil redisUtil;

    public boolean lock(String key, String value) {
        String nx1 = redisUtil.setNx(key, value, 60 * 60);
        if (nx1==null) {
            return false;
        }
        return true;
    }

    /*public boolean unlock(String key,String value) {
        String luaScript = "if redis.call('get', KEYS[1]) == " +
                "ARGV[1] then return redis.call('del', KEYS[1]) else " +
                "return 0 end";
        Object eval = redisUtil.eval(luaScript, key, value);
        if ((long)eval==1) {
            return true;
        }else {
            return false;
        }
    }*/
}
