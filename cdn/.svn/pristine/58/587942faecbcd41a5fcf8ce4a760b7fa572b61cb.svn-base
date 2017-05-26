package cn.com.ailbb.database;

import cn.com.ailbb.util.DataUtil;
import org.springframework.stereotype.Repository;

/**
 * Created by xzl on 2017/5/16.
 */
@Repository
public class VerticaDialect implements Dialect {
    /**
     * update by wenzheli 2017-4-10 vertica方言
     * 加入了yyyy-MM-dd格式处理
     */
    public String datetimeTostring(String col, String format) {

        if (DataUtil.checkStr(format)) {
            if ("yyyy-MM-dd".equals(format))
                format = "YYYY-MM-DD";
                //add by liuchaobiao 2012/9/14
            else if ("yyyy-MM".equals(format)) {
                format = "YYYY-MM";
            } else if ("HH:mm:ss".equals(format)) {
                format = "hh24:mi:ss";
            } else if ("MM-dd".equals(format)) {
                format = "MM-DD";
            } else if ("HH:mm".equals(format)) {
                format = "hh24:mi";
            } else if ("mm:ss".equals(format)) {
                format = "mi:ss";
            } else if ("yyyy".equals(format)) {
                format = "YYYY";
            } else if ("MM".equals(format)) {
                format = "MM";
            } else if ("dd".equals(format)) {
                format = "DD";
            } else if ("HH".equals(format)) {
                format = "hh24";
            } else if ("mm".equals(format)) {
                format = "mi";
            } else if ("ss".equals(format)) {
                format = "ss";
            } else {
                format = "YYYY-MM-DD hh24:mi:ss";
            }
        } else {
            format = "YYYY-MM-DD hh24:mi:ss";
        }
        //format = StringUtil.checkStr(format) ? format : "'%Y-%m-%d %H:%i:%s'";
        return "to_char(" + col + ", '" + format + "')";
    }


}
