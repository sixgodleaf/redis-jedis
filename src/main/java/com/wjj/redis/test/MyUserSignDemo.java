package com.wjj.redis.test;

import com.wjj.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: wangjunjie 2019/6/27 10:18
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/6/27 10:18
 */

@Service
@Slf4j
public class MyUserSignDemo {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户签到
     *
     * @param pid       用户标识
     * @param localDate 当天日期
     * @return 之前的签到状态 true 重复签到 false 当天第一次签到
     */
    public boolean doSign(int pid, LocalDate localDate) {
        //偏移量
        int offset = localDate.getDayOfMonth() - 1;
        String signKey = buildSignKey(pid, localDate);
        return redisUtil.setBit(signKey, offset, true);
    }

    /**
     * 检查用户是否签到
     *
     * @param pid  用户ID
     * @param date 日期
     * @return 当前的签到状态
     */
    public boolean checkSign(int pid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        String signKey = buildSignKey(pid, date);
        return redisUtil.getBit(signKey, offset);
    }

    /**
     * 获取用户签到次数
     *
     * @param pid  用户ID
     * @param date 日期
     * @return 当前的签到次数
     */
    public Long getSignCount(int pid, LocalDate date) {

        String signKey = buildSignKey(pid, date);
        return redisUtil.bitCount(signKey);
    }

    /**
     * 获取当月首次签到日期
     *
     * @param pid  用户ID
     * @param date 日期
     * @return 首次签到日期
     */
    public LocalDate getFirstSignDate(int pid, LocalDate date) {
        String signKey = buildSignKey(pid, date);
        Long offset = redisUtil.bitpos(signKey, true);
        if (offset==-1) {
            return null;
        }
        return date.withDayOfMonth((int) (offset + 1));
    }

    /**
     * 获取当月连续签到次数
     *
     * @param pid  用户ID
     * @param date 日期
     * @return 当月连续签到次数
     */
    public long getContinuousSignCount(int pid, LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        String fieldType = String.format("u%d", dayOfMonth);
        String signKey = buildSignKey(pid, date);
        Jedis jedis = null;
        List<Long> signLogList = null;
        try {
            jedis = JedisUtil.getJedis();
            signLogList = jedis.bitfield(signKey, "GET", fieldType, "0");
        } catch (Exception e) {
            log.error("redis处理异常", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (signLogList == null || signLogList.size() == 0) {
            return 0;
        }

        Long signLog = signLogList.get(0);
        int signCount=0;
        for (int index = 0; index < dayOfMonth; ++index) {
            if (signLog>>1<<1==signLog) {
                break;
            }
            ++signCount;
            signLog>>=1;
        }
        return signCount;
    }

    /**
     * 获取当月签到情况
     *
     * @param pid  用户ID
     * @param date 日期
     * @return Key为签到日期，Value为签到状态的Map
     */
    public Map<String, Boolean> getSignInfo(int pid, LocalDate date) {
        TreeMap<String, Boolean> signInfoMap = new TreeMap<>();
        int lengthOfMonth = date.lengthOfMonth();
        String fieldType = String.format("u%d", lengthOfMonth);
        String signKey = buildSignKey(pid, date);
        Jedis jedis = null;
        List<Long> signLogList = null;
        try {
            jedis = JedisUtil.getJedis();
            signLogList = jedis.bitfield(signKey, "GET", fieldType, "0");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (signLogList != null && signLogList.size() > 0) {
            Long signLog = signLogList.get(0);
            for (int index = lengthOfMonth; index > 0; --index) {
                LocalDate localDate = date.withDayOfMonth(index);
                signInfoMap.put(formatDate(localDate, "yyyy-MM-dd"), signLog >> 1 << 1 != signLog);
                signLog >>= 1;
            }

        }

        return signInfoMap;
    }


    private String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    private String buildSignKey(int uid, LocalDate date) {
        return String.format("u:sign:%d:%s", uid, formatDate(date));
    }
}
