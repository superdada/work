package cn.com.ailbb.database;

/**
 * Created by xzl on 2017/5/16.
 */
public interface Dialect {
    /**
     * 日期按格式转成字符串
     * 如果格式字符串为空默认格式为YYYY-MM-DD hh:mm:ss
     * @param col 字段名
     * @param format 有以下枚举：
     * 				 日期时间：""或YYYY-MM-DD hh:mm:ss
     *               日期：yyyy-MM-dd
     *               时间：HH:mm:ss
     *               年月：yyyy-MM
     *               月日：MM-dd
     *               时分：HH:mm
     *               分秒：mm:ss
     */
    public String datetimeTostring(String col, String format);
}
