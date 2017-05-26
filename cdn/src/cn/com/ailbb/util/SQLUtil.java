package cn.com.ailbb.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class SQLUtil {
    private static Logger logger = Logger.getLogger(SQLUtil.class); // log4j日志
    private static String select_sql = "SELECT %s FROM %s WHERE %s";

    public static String getQuerySql(String table){
        return getQuerySql(table, null);
    }

    public static String getQuerySql(String table, Map<String,String> whereMap){
        return getQuerySql(table, null, whereMap);
    }

    public static String getQuerySql(String table, Map<String,String> selectMap, Map<String,String> whereMap){
        return getQuerySql(table, selectMap, getWhere(whereMap));
    }

    public static String getQuerySql(String table, Map<String,String> selectMap, String where){
        return getQuerySql(table, getSelect(selectMap), where);
    }

    public static String getQuerySql(String table, String select, String where){
        String sql = String.format(select_sql, select, table, where);
        logger.info(sql);
        return sql;
    }

    public static String getSelect(Map<String,String> map){
        if(null == map) return " * ";

        List<String> list = new ArrayList<>();

        for(String key : map.keySet())
            list.add(String.format("%s as %s", key, map.get(key)));

        return StringUtils.join(list," , ");
    }
    
    private static String getWhere(Map<String,String> map){
        if(null == map) return " 1 = 1 ";

        List<String> list = new ArrayList<>();
        for(String key : map.keySet())
            list.add(String.format("%s = '%s'", key, map.get(key)));

        return StringUtils.join(list, " AND ");
    }
}
