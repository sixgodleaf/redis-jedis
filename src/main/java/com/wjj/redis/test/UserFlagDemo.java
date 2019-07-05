package com.wjj.redis.test;

import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: wangjunjie 2019/6/26 16:18
 * @Description: 用户标签
 * @Version: 1.0.0
 * @Modified By: xxx 2019/6/26 16:18
 */


public class UserFlagDemo {
    private Jedis jedis = JedisUtil.getJedis();

    /**
     * 设置用户标签
     */
    public void doFlag(long pid,String key) {
        jedis.setbit(key,pid,true);
    }


    public boolean checkFlag(long pid,String key) {
        return jedis.getbit(key,pid);
    }

    public List<Long> getFlagUser(String key) {

        List<Long> longs = jedis.bitfield(key, "GET", "u63", "100");
        for (Long aLong : longs) {
            System.out.println(aLong);
        }
        return longs;
    }

    public static void main(String[] args) {
        UserFlagDemo userFlagDemo = new UserFlagDemo();
        /*userFlagDemo.doFlag(120,"zhuoai");
        boolean flag = userFlagDemo.checkFlag(120, "zhuoai");
        System.out.println(flag);*/
        userFlagDemo.getFlagUser("zhuoai");
    }


}
