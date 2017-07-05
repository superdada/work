package cn.com.ailbb.service;

/**
 * Created by xzl on 2017/5/16.
 */

 import cn.com.ailbb.interfaces.RealTimeOnlineInterface;
 import cn.com.ailbb.obj.RealTimeOnlineData1;
 import cn.com.ailbb.obj.RealTimeOnlineData2;
 import cn.com.ailbb.obj.RealTimeOnlineData3;
 import cn.com.ailbb.util.DataUtil;
 import cn.com.ailbb.util.TimeUtil;
 import org.apache.log4j.Logger;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.stereotype.Service;

 import java.util.*;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
@Service
public class RealTimeOnlineServiceImpl implements RealTimeOnlineService{
    @Autowired
    JdbcTemplate jdbcTemplateVertica; // 数据库连接
    //定义数据表
    private static final String Dbschedule = "shcdn.";
    private static final String Table_realtime_allnet = Dbschedule+"r_realtime2_10000";
    private static final String Table_realtime_server = Dbschedule+"r_realtime2_10100";
    private static final String Table_realtime_user = Dbschedule+"r_realtime2_10003";
    private static Logger logger = Logger.getLogger(RealTimeOnlineService.class); // log4j日志

    @Override
    public RealTimeOnlineData1 handler1(Map<String,String> params) {
        //String cdn_cache_all_Condition=params.get("cache_cdn")==null||"ALL".equals(params.get("cache_cdn").toUpperCase())?"cache_cdn='ALL' or cache_cdn is null":" cache_cdn='"+params.get("cache_cdn")+"'";
        String cdn_cache_all_Condition =" cache_cdn='"+params.get("cache_cdn")+"'";
        String querySQL="SELECT * FROM "+Table_realtime_allnet+" where "+cdn_cache_all_Condition+"  ORDER BY ttime DESC limit 1";
        logger.info(querySQL);
        Map<String,Object> map = jdbcTemplateVertica.queryForMap(querySQL);
        RealTimeOnlineData1 realTimeOnlineData1 = null;
        if(null != map) {
            Object health_rate = map.get("health_rate");
            Object hit_rate = map.get("hit_rate");
            Object avg_dl_speed = map.get("avg_dl_speed");
            Object delay=map.get("delay");
            Object bandwidth = map.get("bandwidth");
            Object visit_cnt = map.get("visit_cnt");
            Object ttime = map.get("ttime");
            Object cache_cdn=map.get("cache_cdn");

            realTimeOnlineData1 = new RealTimeOnlineData1(
                    DataUtil.ObjectToString(health_rate),
                    DataUtil.ObjectToString(hit_rate),
                    DataUtil.ObjectToString(avg_dl_speed),
                    DataUtil.ObjectToString(delay),
                    DataUtil.ObjectToString(bandwidth),
                    DataUtil.ObjectToString(visit_cnt),
                    DataUtil.ObjectToString(ttime),
                    DataUtil.ObjectToString(cache_cdn)
            );
        }
        return realTimeOnlineData1;
    }

    @Override
    public RealTimeOnlineData2 handler2(Map<String, String> params) {
        String IPtype=params.get("IPtype");
        String index=params.get("index");
        String cache_cdn_condition=" cache_cdn='"+params.get("cache_cdn")+"'";

        switch (IPtype) {
            case "data_center":
                Map<String,Object> data_center = jdbcTemplateVertica.queryForMap(String.format("SELECT %s as time FROM %s where "+cache_cdn_condition+"  order by ttime desc limit 1", "ttime",Table_realtime_server));
                return new RealTimeOnlineData2(TimeUtil.TextToMinDateText(data_center.get("time").toString()),
                        jdbcTemplateVertica.queryForList(String.format("SELECT %s as name, %s as value FROM %s where ttime = cast('%s' as timestamp) and "+cache_cdn_condition+" order by %s",
                                "server_city", index, Table_realtime_server, data_center.get("time").toString(), "server_city"))// 数据中心位置表
                ); // 用户位置表
            case "user":
                String sql="SELECT %s as time FROM %s where "+cache_cdn_condition+" and user_city is not null order by %s desc limit 1";
                sql=String.format(sql, "ttime", Table_realtime_user, "ttime");
                Map<String,Object> user = jdbcTemplateVertica.queryForMap(sql);
                return new RealTimeOnlineData2(TimeUtil.TextToMinDateText(user.get("time").toString()),
                        jdbcTemplateVertica.queryForList(String.format("SELECT %s as name, %s as value FROM %s where ttime = cast('%s' as timestamp) and "+cache_cdn_condition+" and user_city is not null order by %s",
                                "user_city", index, Table_realtime_user, user.get("time").toString(), "user_city"))
                ); // 用户位置表
        }
        return null;
    }

    @Override
    public RealTimeOnlineData3 handler3(Map<String, String> params) {
        String index=params.get("index");
        String timeType=params.get("timeType");
        String startTime=params.get("startTime");
        String endTime=params.get("endTime");
        String cache_cdn_condition=" cache_cdn='"+params.get("cache_cdn")+"'";

        String querySQL=String.format("SELECT %s as time FROM %s where "+cache_cdn_condition+" order by ttime desc limit 1", "ttime", Table_realtime_allnet);
        logger.info(querySQL);
        Map<String,Object> ttime = jdbcTemplateVertica.queryForMap(querySQL);
        Date date = TimeUtil.TextToDate(DataUtil.ObjectToString(ttime.get("time")));
        String timeWhere = "";
        switch (timeType) {
            case "3hours":
                timeWhere = sqlTimeFormat(date, 3);
                break;
            case "today":
                timeWhere = sqlTimeFormat(date, 24);
                break;
            case "7days":
                timeWhere = sqlTimeFormat(date, 24*7);
                break;
            case "30days":
                timeWhere = sqlTimeFormat(date, 24*30);
                break;
            case "custom":
                timeWhere = sqlTimeFormat(startTime, endTime);
                break;
        }
        RealTimeOnlineData3 realTimeOnlineData3 = new RealTimeOnlineData3();
        List<Map<String,Object>> data = jdbcTemplateVertica.queryForList(String.format("SELECT ttime as x, %s as y FROM "+Table_realtime_allnet+" where %s and "+cache_cdn_condition+"  order by ttime ", index, timeWhere));
        for(Map<String,Object> map : data) {
            Object x = map.get("x");
            if(null != x) {
                realTimeOnlineData3.addX(TimeUtil.TextToMinDateText(x.toString()));
                realTimeOnlineData3.addY(map.get("y"));
            }
        }
        return realTimeOnlineData3;
    }

//    @Override
//    public RealTimeOnlineData3 handler3(String index, String timeType, String startTime, String endTime) {
//
//        Map<String,Object> ttime = jdbcTemplateVertica.queryForMap(String.format("SELECT %s as time FROM %s where (cache_cdn='ALL' or cache_cdn is null) order by ttime desc limit 1", "ttime", "shcdn.r_realtime_10000"));
//        Date date = TimeUtil.TextToDate(DataUtil.ObjectToString(ttime.get("time")));
//        String timeWhere = "";
//        switch (timeType) {
//            case "3hours":
//                timeWhere = sqlTimeFormat(date, 3);
//                break;
//            case "today":
//                timeWhere = sqlTimeFormat(date, 24);
//                break;
//            case "7days":
//                timeWhere = sqlTimeFormat(date, 24*7);
//                break;
//            case "30days":
//                timeWhere = sqlTimeFormat(date, 24*30);
//                break;
//            case "custom":
//                timeWhere = sqlTimeFormat(startTime, endTime);
//                break;
//        }
//        RealTimeOnlineData3 realTimeOnlineData3 = new RealTimeOnlineData3();
//        List<Map<String,Object>> data = jdbcTemplateVertica.queryForList(String.format("SELECT ttime as x, %s as y FROM shcdn.r_realtime_10000 where %s and (cache_cdn='ALL' or cache_cdn is null)  order by ttime ", index, timeWhere));
//        for(Map<String,Object> map : data) {
//            Object x = map.get("x");
//            if(null != x) {
//                realTimeOnlineData3.addX(TimeUtil.TextToMinDateText(x.toString()));
//                realTimeOnlineData3.addY(map.get("y"));
//            }
//        }
//        return realTimeOnlineData3;
//    }

    public String sqlTimeFormat(String startTime, String endTime){
        return String.format(" ttime < cast('%s' as timestamp) AND ttime >= cast('%s' as timestamp) ", TimeUtil.DateToTimestamp(TimeUtil.MinTextToDate(endTime)),
                TimeUtil.DateToTimestamp(TimeUtil.MinTextToDate(startTime)));
    }

    public String sqlTimeFormat(Date date, long num){
        return String.format(" ttime < cast('%s' as timestamp) AND ttime >= cast('%s' as timestamp) ", TimeUtil.DateToTimestamp(date),
                TimeUtil.DateToTimestamp(new Date(date.getTime() - num*60*60*1000)));
    }
}

