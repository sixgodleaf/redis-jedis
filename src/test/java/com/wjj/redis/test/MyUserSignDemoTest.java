package com.wjj.redis.test;

import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MyUserSignDemoTest {

    @Autowired
    private MyUserSignDemo myUserSignDemo;

    @Test
    public void doSignTest() {
        LocalDate localDate = LocalDate.now().minusDays(10);
        boolean flag = myUserSignDemo.doSign(188, localDate);
        System.out.println(flag);
    }

    @Test
    public void checkSignTest() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        boolean flag = myUserSignDemo.checkSign(187, localDate);
        System.out.println(flag);
    }

    @Test
    public void getSignCountTest() {
        LocalDate localDate = LocalDate.now();
        Long signCount = myUserSignDemo.getSignCount(187, localDate);
        System.out.println(signCount);
    }

    @Test
    public void getSignInfoTest() {
        LocalDate localDate = LocalDate.now();
        Map<String, Boolean> signInfo = myUserSignDemo.getSignInfo(187, localDate);
        for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

    @Test
    public void getContinuousSignCount() {
        LocalDate localDate = LocalDate.now();
        long signCount = myUserSignDemo.getContinuousSignCount(188, localDate);
        System.out.println(signCount);
    }

    @Test
    public void getFirstSignDate() {
        LocalDate localDate = LocalDate.now();
        LocalDate date = myUserSignDemo.getFirstSignDate(188, localDate);
        if (date==null) {
            System.out.println("这个月还没签到");
        }else {
            String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(format);
        }

    }
}