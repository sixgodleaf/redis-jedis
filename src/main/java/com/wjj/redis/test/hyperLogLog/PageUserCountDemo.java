package com.wjj.redis.test.hyperLogLog;

import com.wjj.redis.test.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * @Author: wangjunjie 2019/7/5 15:42
 * @Description: 页面阅读量统计，同一用户只算一次 使用redis HyperLogLog
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/5 15:42
 */
public class PageUserCountDemo {

    private Jedis jedis = JedisUtil.getJedis();

    public long add(String key,String uid) {
       return jedis.pfadd(key,uid);
    }

    public long get(String key) {
       return jedis.pfcount(key);
    }


    public static void main(String[] args) {
        //test1();
        test2();
    }


    public static void test1() {
        PageUserCountDemo pageUserCountDemo = new PageUserCountDemo();
        for (int i=0;i<1000;++i) {
            pageUserCountDemo.add("uv:2","u"+i);
        }
        System.out.println(pageUserCountDemo.get("uv:2"));
    }

    /**
     * 查看 redis HyperLogLog 精确性
     */
    public static void test2() {
        PageUserCountDemo pageUserCountDemo = new PageUserCountDemo();
        int falese=0;
        for (int i=0;i<2000;++i) {
            long result = pageUserCountDemo.add("uv:2", "u" + i);
            if (result!=0) {
                ++falese;
                System.out.println(i);
            }
        }
        System.out.println("falese: "+falese);
    }


}
