package util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: 𝓛.𝓕.𝓠
 */
public class TimeTest {
    public static void main(String[] args) {
        Integer a = 5;
        Integer b = 6;
        DecimalFormat df=new DecimalFormat("0.00");

        System.out.println(df.format((float)a/b));
        System.out.println(df.format(a/(float)b));
        System.out.println(df.format((float)a/(float)b));
        System.out.println(df.format((float)(a/b)));
    }
    public static void testTime(){
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
