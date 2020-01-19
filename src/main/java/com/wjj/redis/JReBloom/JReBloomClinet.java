package com.wjj.redis.JReBloom;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangjunjie 2019/7/4 22:16
 * @Description: redis 布隆过滤器 jedis扩展指令
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/4 22:16
 */
public class JReBloomClinet {

    /**
     * 添加
     * @param jedis
     * @param key
     * @param value
     * @return true 添加成功 false 添加失败（已经添加过）
     */
    public static boolean add(Jedis jedis,String key, String value) {
        Connection client = jedis.getClient();
        client.sendCommand(JReBloomCommand.ADD,key,value);
        return client.getIntegerReply()==1;
    }

    /**
     * 批量添加
     * @param jedis
     * @param key
     * @param value
     * @return true 添加成功 false 添加失败（已经添加过）
     */
    public static boolean[] addMulti(Jedis jedis,String key, String ...value) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(key);
        arr.addAll(Arrays.asList(value));

        Connection client = jedis.getClient();
        client.sendCommand(JReBloomCommand.MADD, (String[])arr.toArray((String[])value));
        List<Long> replyLongList = client.getIntegerMultiBulkReply();

        boolean[] ret = new boolean[value.length];
        for (int i = 0; i < replyLongList.size(); i++) {
            ret[i] = replyLongList.get(i) != 0;
        }
        return ret;
    }

    /**
     * 判断是否存在
     * @param jedis
     * @param key
     * @param value
     * @return true 存在 false 不存在
     */
    public static boolean exists(Jedis jedis,String key, String value) {
        Connection client = jedis.getClient();
        client.sendCommand(JReBloomCommand.EXISTS,key,value);
        return client.getIntegerReply()==1;
    }

    /**
     * 批量判断是否存在
     * @param jedis
     * @param key
     * @param value
     * @return true 存在 false 不存在
     */
    public static boolean[] existsMulti(Jedis jedis,String key,String ...value) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(key);
        arr.addAll(Arrays.asList(value));

        Connection client = jedis.getClient();
        client.sendCommand(JReBloomCommand.MEXISTS, (String[])arr.toArray((String[])value));
        List<Long> replyLongList = client.getIntegerMultiBulkReply();

        boolean[] ret = new boolean[value.length];
        for (int i = 0; i < replyLongList.size(); i++) {
            ret[i] = replyLongList.get(i) != 0;
        }
        return ret;
    }

    /**
     *
     * 创建置顶过滤器
     * @param jedis
     * @param name
     * @param initCapacity 预计放入的元素数量
     * @param errorRate 错误率 <1
     * @return "ok" 成功 失败抛出异常
     */
    public static String createFilter(Jedis jedis,String name, long initCapacity, double errorRate) {
        Connection client = jedis.getClient();
        client.sendCommand(JReBloomCommand.RESERVE,name, errorRate + "", initCapacity + "");
        return client.getStatusCodeReply();
    }


}
