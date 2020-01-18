package com.post.ibaties.primary.xxx.crawl.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: 𝓛.𝓕.𝓠
 */
public class TimeTest {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        System.out.println(dqrq);
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String time = format.format(start);
        System.out.println(time);
        System.out.println(time.replace("-",""));

    }
}
