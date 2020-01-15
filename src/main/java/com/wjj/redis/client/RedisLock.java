package com.wjj.redis.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangjunjie 2019/4/29 17:22
 * @Description: redis 分布式锁
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 17:22
 */

@Component
@Slf4j
public class RedisLock implements InitializingBean {

    private static final String LOCK_PREFIX = "lock:";

    private static final String LOCK_SUCCESS = "OK";

    private String unlockLuaId = "";

    @Autowired
    private JedisPool jedisPool;


    /**
     * 等待一定时间，尝试获取锁
     *
     * @param lockName    锁名称，和业务相关；如：订单编号
     * @param value       拥有者代码，使用锁的唯一标识，建议：使用进程编号+线程ID组成
     * @param waitTimeout 获取锁的等待时间（毫秒），如果锁被使用中，将在时间内等待锁释放，并获取锁；如果超过时间任然未获取到锁，则返回获取锁失败。
     * @param timeoutTime 锁的超时时间（毫秒），到达超时时间未释放锁，将自动释放锁，防止死锁
     * @return
     */
    public boolean waitLock(String lockName, String value, long waitTimeout, long timeoutTime) {
        long starTime = System.currentTimeMillis();
        try {
            if (!tryLock(lockName, value, timeoutTime)) {
                long sleepTime = getSleepTime(waitTimeout);
                for (;  System.currentTimeMillis() <starTime + waitTimeout; ) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (tryLock(lockName, value, timeoutTime)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            log.error("获取redis锁失败,lockName:{}", lockName, e);
        }

        return true;

    }

    /**
     * 尝试获取锁，如果没获取到锁，立即返回
     *
     * @param lockName    锁名称，和业务相关；如：订单编号
     * @param value       拥有者代码，使用锁的唯一标识，建议：使用进程编号+线程ID组成
     * @param timeoutTime 锁的超时时间（毫秒），到达超时时间未释放锁，将自动释放锁，防止死锁
     * @return
     */
    public boolean tryLock(String lockName, String value, long timeoutTime) {
        return lock(lockName, value, timeoutTime);
    }

    /**
     * 获取锁成功 返回true; 失败返回false
     */
    private boolean lock(String lockName, String value, long timeoutTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.set(LOCK_PREFIX + lockName, value, SetParams.setParams().nx().px(timeoutTime));
            if (result != null && result.equals(LOCK_SUCCESS)) {
                return true;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }

    /**
     * 释放redis 锁
     * @param lockName 锁名称，和业务相关；如：订单编号
     * @param value 拥有者代码，使用锁的唯一标识，建议：使用进程编号+线程ID组成
     * @return
     */
    public boolean unLock(String lockName, String value) {
        List<String> keys = new ArrayList<>();
        keys.add(LOCK_PREFIX+lockName);
        List<String> args = new ArrayList<>();
        args.add(value);
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            Object result=jedis.evalsha(this.unlockLuaId,keys,args);
            if ((long)result==1) {
                return true;
            }else {
                return false;
            }
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取 每次尝试获取锁，休眠时间
     *
     * @return
     */
    private long getSleepTime(long waitTimeout) {
        int interval = 50;
        if (waitTimeout <= 100) {
            //等待时间小于100毫秒时，每次等待20毫秒
            interval = 50;
        } else if (waitTimeout <= 200) {
            //等待时间小于200毫秒时，每次等待30毫秒
            interval = 80;
        } else if (waitTimeout <= 500) {
            //等待时间小于500毫秒时，每次等待50毫秒
            interval = 100;
        } else if (waitTimeout <= 1000) {
            //等待时间小于1000毫秒时，每次等待100毫秒
            interval = 150;
        } else if (waitTimeout <= 2000) {
            //等待时间小于2000毫秒时，每次等待150毫秒
            interval = 180;
        } else if (waitTimeout <= 5000) {
            //等待时间小于5000毫秒时，每次等待300毫秒
            interval = 300;
        } else if (waitTimeout <= 10000) {
            //等待时间小于10000毫秒时，每次等待500毫秒
            interval = 500;
        } else if (waitTimeout <= 20000) {
            //等待时间小于20000毫秒时，每次等待1000毫秒
            interval = 1000;
        } else {
            //否则，每次等待2000毫秒
            interval = 2000;
        }
        return interval;
    }

    /**
     * 加载释放 redis lua 脚本
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // lua 脚本
        String luaScript = "if redis.call('get', KEYS[1]) == " +
                "ARGV[1] then return redis.call('del', KEYS[1]) else " +
                "return 0 end";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            this.unlockLuaId = jedis.scriptLoad(luaScript);
        }finally {
            if (jedis!=null) {
                jedis.close();
            }
        }


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
