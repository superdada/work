package cn.com.ailbb.util;

import java.text.DecimalFormat;

/**
 * Created by WildMrZhang on 2017/3/16.
 */
public class DataUtil {
    public static String ObjectToString(Object obj){
        if(null == obj)
            return null;
        else
            return obj.toString();
    }
    public static boolean checkObj(Object obj) {
        boolean bool = true;
        if (obj == null || "".equals(obj.toString().trim()))
            bool = false;
        return bool;
    }
    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean checkStr(String str) {
        boolean bool = true;
        if (str == null || "".equals(str.trim()))
            bool = false;
        return bool;
    }
    public static String ObjectToFormat100DoubleString(Object obj){
        if(null == obj)
            return null;
        else
            return StringToFormat100DoubleString(obj.toString());
    }

    public static String ObjectToFormatDoubleString(Object obj){
        if(null == obj)
            return null;
        else
            return StringToFormatDoubleString(obj.toString());
    }

    public static String StringToFormat100DoubleString(String str){
        if(null == str)
            return str;
        else
        return StringToFormatDecimal(StringToDouble(str)*100);
    }

    public static String StringToFormatDoubleString(String str){
        if(null == str)
            return str;
        else
            return StringToFormatDecimal(StringToDouble(str));
    }

    public static double StringToDouble(String str){
        if(null == str) return 0;

        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 字符串转数值，如果字符串为空，则返回-1；
     * String s = "";
     * toInt(s); // -1
     * @param str
     * @return
     */
    public static int StringToInt(String str) {
        return "".equals(str) ? -1 : Integer.parseInt(str);
    }
    public static String StringToFormatDecimal(Object str){
        if(null == str) return null;

        try {
            return new DecimalFormat("#.00").format(str);
        } catch (Exception e) {
            return str.toString();
        }
    }

}
