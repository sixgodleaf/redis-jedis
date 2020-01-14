package com.wjj.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class RedisZsetClient {

    private JedisPool jedisPool;

    public RedisZsetClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    //zset

    /**
     * 返回 分数值
     *
     * @param key
     * @param value
     * @return
     */
    public Double zscore(String key, String value) {
        Jedis jedis = null;
        Double res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zscore(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }


    /**
     * 新增
     *
     * @param key
     * @param value
     * @param score
     * @return 1 不存在value  0 存在了value
     */
    public Long zadd(String key, String value, double score) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zadd(key, score, value);
        }  finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 查询
     * @param key
     * @param value
     * @return
     */
    public Long zrank(String key, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zrank(key,  value);
        }  finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 移除有序集中的一个或多个成员，不存在的成员将被忽略
     *
     * @param key
     * @param values
     * @return 成功删除的数量
     */
    public Long zrem(String key, String... values) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zrem(key, values);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    //zset

    /**
     * 获取set<string> 集合 正序（score值从小到大 倒序：zrevrange方法）
     *
     * @param key
     * @param start 开始位置  全部 0
     * @param end   结束位置 全部 -1
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zrange(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 获取set<string> 集合 正序（score值从小到大 倒序：zrevrange方法）
     *
     * @param key
     * @param start 开始位置  全部 0
     * @param end   结束位置 全部 -1
     * @return
     */
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        Set<Tuple> tuples = null;
        try {
            jedis = jedisPool.getResource();
            tuples = jedis.zrangeWithScores(key, start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return tuples;
    }

    /**
     * 获取set<string> 集合 倒序
     *
     * @param key
     * @param start 开始位置  全部 0
     * @param end   结束位置 全部 -1
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zrevrange(key, start, end);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    /**
     * 获取set<string> 集合 倒序
     *
     * @param key
     * @param start 开始位置  全部 0
     * @param end   结束位置 全部 -1
     * @return
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        Set<Tuple> tuples = null;
        try {
            jedis = jedisPool.getResource();
            tuples = jedis.zrevrangeWithScores(key, start, end);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return tuples;
    }

    //zset

    /**
     * 获取 value 的数量
     *
     * @param key
     * @return null 为0
     */
    public Long zcard(String key) {
        Jedis jedis = null;
        Long res = 0L;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zcard(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }

    //zset

    /**
     * 删除score值 在start 和 end 之间的数据
     *
     * @param key
     * @param start
     * @param end
     * @return 1 删除了数据 0 没有删除数据（数据不在区间内）
     */
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long res = 0L;
        try {
            jedis = jedisPool.getResource();
            res = jedis.zremrangeByScore(key, start, end);
        }  finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return res;
    }


    /**
     * 通过分数返回有序集合指定区间内的成员
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = jedisPool.getResource();
            if (min == null) {
                min = "-inf";
            }
            if (max == null) {
                max = "+inf";
            }
            set = jedis.zrangeByScore(key, min, max);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return set;
    }

    /**
     * 通过分数返回有序集合指定区间内的成员 包含分数
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        Jedis jedis = null;
        Set<Tuple> tuples = null;
        try {
            jedis = jedisPool.getResource();
            if (min == null) {
                min = "-inf";
            }
            if (max == null) {
                max = "+inf";
            }
            tuples = jedis.zrangeByScoreWithScores(key, min, max);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return tuples;
    }

    /**
     * 通过分数返回有序集合指定区间内的成员  倒序（从高到低）
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrevrangeByScore(String key, String min, String max) {
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = jedisPool.getResource();
            if (min == null) {
                min = "-inf";
            }
            if (max == null) {
                max = "+inf";
            }
            set = jedis.zrevrangeByScore(key, min, max);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return set;
    }

    /**
     * 通过分数返回有序集合指定区间内的成员 包含分数 倒序（从高到低）
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String min, String max) {
        Jedis jedis = null;
        Set<Tuple> tuples = null;
        try {
            jedis = jedisPool.getResource();
            if (min == null) {
                min = "-inf";
            }
            if (max == null) {
                max = "+inf";
            }
            tuples = jedis.zrevrangeByScoreWithScores(key, min, max);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return tuples;
    }

}
