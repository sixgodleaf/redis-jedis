package com.wjj.redis.util;

import com.wjj.redis.JReBloom.JReBloomClinet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import java.util.*;


/**
 * @Author: wangjunjie 2019/4/29 14:38
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/4/29 14:38
 */

@Component
@Slf4j
public class RedisClient implements InitializingBean {

    @Autowired
    private JedisPool jedisPool;

    private RedisStringClient redisStringClient;

    private RedisZsetClient redisZsetClient;


    /**
     * 设置过期时间
     *
     * @param key
     * @param value
     * @return
     */
    public Long expire(String key, int value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    //hash

    /**
     * @param key
     * @param field
     * @param value
     * @return 成功 1 替换（已经存在key,field）0
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hset(key, field, value);

        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 如果不存在，则返回null
     *
     * @param key
     * @param field
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hget(key, field);

        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * hincrBy 增加
     *
     * @param key
     * @param field
     * @param incrValue
     * @return
     */
    public Long hincrBy(String key, String field, long incrValue) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hincrBy(key, field, incrValue);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * hgetAll 获取key 的属性和属性值
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hgetAll(key);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * redis  list
     */

    /**
     * list 添加
     *
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lpush(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 获取list 列表
     *
     * @param key
     * @param start 开始
     * @param end   结束  -1 为最后
     * @return
     */
    public List lrange(String key, int start, int end) {
        Jedis jedis = null;
        List res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lrange(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key
     * @param start
     * @param end   -1 表示列表的最后一个元素
     */
    public void ltrim(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.ltrim(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 根据参数 index 的值，移除列表中与参数 VALUE 相等的元素
     *
     * @param key
     * @param value 删除的值
     * @param count 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     *              从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     *              移除表中所有与 VALUE 相等的值
     */
    public void lrem(String key, String value, int count) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lrem(key, count, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 执行lua脚本
     *
     * @param luaScript
     * @param key
     * @param args
     * @return
     */
    public Object eval(String luaScript, List<String> key, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.eval(luaScript, key, args);
        } catch (Exception e) {

            log.error(e.getMessage());
            return -1L;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 分布式锁
     *
     * @param key
     * @param value
     * @param time
     * @return 成功ok 失败为null
     */
    public String setNx(String key, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value, SetParams.setParams().nx().ex(time));
        } catch (Exception e) {

            log.error(e.getMessage());
            return "0";
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    //bitmap 位图

    /**
     * @param key
     * @param offset 偏移量
     * @param flag   true 1 false 0
     * @return true 重复设置（偏移量和flag一样）
     */
    public boolean setBit(String key, int offset, boolean flag) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setbit(key, offset, flag);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @param key
     * @param offset 偏移量
     * @return true 1 false 0
     */
    public boolean getBit(String key, int offset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getbit(key, offset);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * @param key
     * @return
     */
    public Long bitCount(String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.bitcount(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回的首次偏移量，
     *
     * @param key
     * @param flag true 1 false 0
     * @return -1 没有设置
     */
    public Long bitpos(String key, boolean flag) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.bitpos(key, flag);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取jedis (使用后一定要关闭)
     *
     * @return jeis
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }


    //HyperLogLog 不精确去重

    /**
     * 添加元素
     *
     * @param key
     * @param value 1 添加成功 0 没成功 （可能有重复）
     */
    public Long pfAdd(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.pfadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 统计数量
     *
     * @param key
     * @return
     */
    public Long pfCount(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.pfcount(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 遍历 key
     *
     * @param key    key 正则表达式
     * @param cursor 游标 起始位置0 结束为0
     * @param limit  遍历的次数
     * @return
     */
    public ScanResult<String> scan(String key, String cursor, int limit) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(key);
            scanParams.count(limit);
            return jedis.scan(cursor, scanParams);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }


    // jReBloom redis布隆过滤器

    /**
     * 添加元素
     *
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean jReBloomAdd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.add(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 添加元素
     *
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean[] jReBloomAddMulti(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.addMulti(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断元素是否存在
     *
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean jReBloomExists(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.exists(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断元素是否存在
     *
     * @return true 没有vlaue值，添加  false 已经存在 value
     */
    public boolean[] jReBloomExistsMulti(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.existsMulti(jedis, key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 自定义布隆过滤器
     *
     * @param name         key
     * @param initCapacity 预计放入元素数量
     * @param errorRate    错误率  小于1
     * @return OK 创建成功 已存在抛出异常
     */
    public String createFilter(String name, long initCapacity, double errorRate) {

        if (errorRate >= 1) {
            return "false";
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JReBloomClinet.createFilter(jedis, name, initCapacity, errorRate);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.redisStringClient = new RedisStringClient(jedisPool);
        this.redisZsetClient = new RedisZsetClient(jedisPool);
    }

    public RedisStringClient string() {
        return redisStringClient;
    }

    public RedisZsetClient zset() {
        return redisZsetClient;
    }
}
