package cn.com.ailbb.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class TimeUtil {
    public static Timestamp DateToTimestamp(){
        return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public static String CustomToMinDateText(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(MinTextToDate("2017-03-09 00:00"));
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(MinTextToDate("2017-02-22 00:00"));
    }

    public static Date TextToDate(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date MinTextToDate(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp DateToTimestamp(Date date){
        return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

    public static String TextToMinDateText(String date){
        if(null == date) return date;
        return DateToMinDateText(TextToDate(date));
    }

    public static String TextToDayDateText(String date){
        return TextToDayDateText(TextToDate(date.toString()));
    }

    public static String TextToDayDateText(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String DateToMinDateText(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String getNowFormatText(){
        return new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
    }
}
