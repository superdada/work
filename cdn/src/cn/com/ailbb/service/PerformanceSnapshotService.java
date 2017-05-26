package cn.com.ailbb.service;

import cn.com.ailbb.obj.ItemData;
import cn.com.ailbb.server.dao.DataCenterDao;
import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.util.MailUtil;
import cn.com.ailbb.util.SQLUtil;
import cn.com.ailbb.util.TimeUtil;
import cn.com.ailbb.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
@Service
public class PerformanceSnapshotService {

    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    @Resource
    private DataCenterDao dataCenterDao;
    private static Logger logger = Logger.getLogger(PerformanceSnapshotService.class); // log4j日志

    private static Map<String, String> indexTypeMap = new HashMap<String,String>(){{
        put("healthy","health_level");
        put("data_rate","low_speed_level");
        put("hit_rate","hit_level");
        put("delay_rate","delay_level");
    }};

    private static Map<String, String> indexTransMap = new HashMap<String,String>(){{
        put("healthy","健康度");
        put("data_rate","低速率比例");
        put("hit_rate","命中率");
        put("delay_rate","延迟");
    }};

    /**
     * 转换数据
     * @param list
     * @return
     */
    public List<ItemData> dataHandler(List<Map<String,Object>> list, String dimension,  String index,boolean enableAlarm) throws Exception{
        Map<String, ItemData> data = new TreeMap<>();
        List<ItemData> formatData = new ArrayList<>();
        List<String> alarmList= new ArrayList<>();
        boolean isAlarm = false;

        for(Map<String,Object> map : list) {
            if(null == map.get("id")) continue;
            String id = map.get("id").toString();
            String name = map.get("name")!=null?map.get("name").toString():null;
            DataCenterStatus dataCenterStatus = dataCenterDao.getCenterStatus(id, dimension);

            String belong_to = map.get("belong_to")!=null?map.get("belong_to").toString():null;
            String rankindex = map.get("rank")!=null?map.get("rank").toString():null;
            int type = null == map.get("type") ? 0 : Integer.parseInt(map.get("type").toString());
            String type_trans = getAlarmType(type);
            String time = TimeUtil.TextToMinDateText(map.get("time")!=null?map.get("time").toString():"");

            if(null == data.get(id)) {
                if(null == dataCenterStatus) {
                    data.put(id, new ItemData(id, name, belong_to, rankindex,new HashMap<String,Object>() {{
                        put("time", time);
                        put("type", type_trans);
                    }}, true, true));
                } else {
                    data.put(id, new ItemData(id, name, belong_to,rankindex, new HashMap<String,Object>() {{
                        put("time", time);
                        put("type", dataCenterStatus.isMonitorFlag() ? type_trans : null);
                    }}, dataCenterStatus.isMonitorFlag(), dataCenterStatus.isAlarmFlag()));
                }
            } else if(null == dataCenterStatus || dataCenterStatus.isMonitorFlag()){
                data.get(id).putIndexes(new HashMap<String,Object>() {{
                    put("time", time);
                    put("type", type_trans);
                }});
            }

            /**
             * 如果处于告警状�?�，则收集告警信�?
             */
            if((null == dataCenterStatus || dataCenterStatus.isAlarmFlag()) && type > 2) {
                isAlarm = true;
                if(dimension.equals("data_center")) {
                    alarmList.add(String.format("时间 [ %s ]， 数据中心 [%s]，地市 [%s]，指标 [ %s ]，出现告警情况，严重值 [ %s ]，请及时查看！", time, name, belong_to, indexTransMap.get(index), type_trans));
                }else if(dimension.equals("ICP")){
                    alarmList.add(String.format("时间 [ %s ]，ICP地址 [%s]，ICP名称 [%s]，指标 [ %s ]，出现告警情况，严重值 [ %s ]，请及时查看！", time, name, belong_to, indexTransMap.get(index), type_trans));
                }else {
                    alarmList.add(String.format("时间 [ %s ]，业务类型 [%s]，指标 [ %s ]，出现告警情况，严重值 [ %s ]，请及时查看！", time, name, indexTransMap.get(index), type_trans));
                }
            }

        }

        try {
            if(isAlarm && enableAlarm)
                for(String address : MailUtil.getAlarmReceiveInternetAddress())  // 发�?�邮�?
                    MailUtil.send(address, "自动告警信息", StringUtils.join(alarmList, "\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String s : data.keySet())
            formatData.add(data.get(s));

        //根据icp_rank排序
        if("ICP".equals(dimension)){
            sortByICP_Rank(formatData);
        }
        return formatData;
    }

    /**
     * 根据icp_rank冒泡排序
     * @param list
     */
    public void sortByICP_Rank(List<ItemData> list){
        //将list集合中的数据转成数组，再进行排序
        ItemData[] datas=new ItemData[list.size()];
        for(int i=0;i<list.size();i++){
            datas[i]=list.get(i);
        }

        for(int i=0;i<datas.length;i++){
            for(int j=0;j<datas.length-i-1;j++){
                int befCount=Integer.parseInt(datas[j].getRanknum());
                int nextCount=Integer.parseInt(datas[j+1].getRanknum());
                if(befCount>nextCount) {
                    ItemData item = datas[j+1];
                    datas[j+1]=datas[j];
                    datas[j]=item;
                }
            }
        }

        //清空重置list集合
        list.clear();

        //把排序后数组中的数据放入list集合
        for(int i=0;i<datas.length;i++){
            list.add(datas[i]);
        }
    }

    private String sqlTimeFormat(String idText, String id, String time, int num){
        return String.format(" %s ='%s' AND %s ", idText, id, sqlTimeFormat(time, num));
    }

    private String sqlTimeFormat(String idText, String id, String time, int num,String cache_cdn){
        return String.format(" %s ='%s' AND %s ", idText, id, sqlTimeFormat(time, num));
    }

    public static String sqlTimeFormat(String time, int num){
        return String.format(" ttime <= cast('%s' as timestamp) AND ttime > cast('%s' as timestamp) ", TimeUtil.DateToTimestamp(TimeUtil.MinTextToDate(time)),
                TimeUtil.DateToTimestamp(new Date(TimeUtil.MinTextToDate(time).getTime() - num*60*60*1000)));
    }

    /**
     * 翻译告警
     * @param type
     * @return
     */
    private String getAlarmType(int type) {
        switch (type) {
            case 1:
                return "normal";
            case 2:
                return "warning";
            case 3:
                return "danger";
            default:
                return null;
        }
    }

    /**
     * 翻译告警
     * @param type
     * @return
     */
    private String getAlarmType(Object type) {
        return null == type ? null : getAlarmType(Integer.parseInt(type.toString()));
    }

    public List<Map<String, Object>> transData(List<Map<String, Object>> data) throws Exception{
        for(Map<String, Object> map : data){
            map.put("type", getAlarmType(map.get("type")));
            map.put("d_id", map.get("id"));
            map.remove("id");
        }
        return data;
    }

    /**
     * 查询数据
     * @return
     */
    public List<Map<String, Object>> queryData(Map<String,Object> params) throws Exception{

        String dimension=params.get("dimension")!=null?params.get("dimension").toString():null;
        String index= params.get("index")!=null?params.get("index").toString():null;
        String time= params.get("time")!=null?params.get("time").toString():null;
        String dimension_id=params.get("dimension_id")!=null?params.get("dimension_id").toString():null;
        String cache_cdn=params.get("cache_cdn")!=null?params.get("cache_cdn").toString():null;

        Map<String,String> selectMap;
        String idName="";
        String tableName = "";
        String limitControl="";
        StringBuffer strBuff=new StringBuffer("");
        if(cache_cdn!=null&&!"".equals(cache_cdn.trim())){
            if ("CDN".equals(cache_cdn)) {
                strBuff.append(" and cache_cdn=\'CDN\'");
            }else if("CACHE".equals(cache_cdn)){
                strBuff.append(" and cache_cdn=\'CACHE\'");
            }else{
                strBuff.append(" and cache_cdn in('ALL',NULL)");
            }
        }
        StringBuffer selectColumns=new StringBuffer("");
        switch (dimension) {
            case "data_center":
                idName ="dc_id";
                selectMap = new HashMap<String,String>(){{
                    put("dc_id", "id");
                    put("dc_name", "name");
                    put("dc_city", "belong_to");
                    put("ttime", "time");
                    put(indexTypeMap.get(index), "type");
                }};
                tableName = "shcdn.r_snapshot_22000";
                break;
            case "ICP":
                idName ="icp_name";
                selectMap = new HashMap<String,String>(){{
                    put("icp_name", "id");
                    put("icp_rank", "rank");
                    put("icp_address", "belong_to");
                    put("ttime", "time");
                    put(indexTypeMap.get(index), "type");
                    put("cache_cdn","cache_cdn");
                }};
                tableName = "shcdn.r_snapshot_20100";
                 selectColumns.append(SQLUtil.getSelect(selectMap)+",").append(String.format("%s as %s","icp_name","name"));
                 strBuff.append(" and icp_rank<=100 ");
                break;
            case "service_type"://业务类型维度
                idName ="service_type";
                selectMap=new HashMap<String,String>();
                selectMap.put("service_type","id");
                selectMap.put("ttime","time");
                selectMap.put(indexTypeMap.get(index),"type");
                tableName="shcdn.r_snapshot_21002";
                selectColumns.append(SQLUtil.getSelect(selectMap)+",").append(String.format("%s as %s","service_type","name"));
                break;
            default:
                return null;
        }
        if(dimension_id !=null){
            strBuff.append(String.format(" AND %s = '%s'",idName,dimension_id));
        }
        limitControl=strBuff.toString();
        //如果没有传递时间参数，则获取最新的时间
        if(time == null){
            String querySQL=String.format("SELECT %s as time FROM %s where "+limitControl.substring(4)+" order by ttime desc limit 1", "ttime", tableName);
            logger.info(querySQL);
            Map<String,Object> ttime =jdbcTemplateVertica.queryForMap(querySQL);
            time = DataUtil.ObjectToString(ttime.get("time"));
        }

        //sql语句where部分
        String whereCondition=  sqlTimeFormat(time, 24) + limitControl + " order by ttime";

        String sql=SQLUtil.getQuerySql(tableName,selectColumns.toString(),whereCondition);
       if("ICP".equals(dimension) || "service_type".equals(dimension)){
           return jdbcTemplateVertica.queryForList(sql);
       }
        return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(tableName, selectMap, whereCondition));
    }

//    /**
//     * 查询数据
//     * @param dimension
//     * @param index
//     * @return
//     */
//    public List<Map<String, Object>> queryData(String dimension, String index, String time, String dimension_id) throws Exception{
//        Map<String,String> selectMap;
//        String idName;
//        String tableName = "";
//        String limitControl="";
//
//        switch (dimension) {
//            case "data_center":
//                selectMap = new HashMap<String,String>(){{
//                    put("dc_id", "id");
//                    put("dc_name", "name");
//                    put("dc_city", "belong_to");
//                    put("ttime", "time");
//                    put(indexTypeMap.get(index), "type");
//                }};
//                idName = "dc_id";
//                tableName = "shcdn.r_snapshot_22000";
//                break;
//            case "ICP":
//                idName = "icp_id";
//                    put("icp_id", "id");
//                selectMap = new HashMap<String,String>(){{
//                    put("icp_address", "name");
//                    put("icp_name", "belong_to");
//                    put("ttime", "time");
//                    put(indexTypeMap.get(index), "type");
//                    put("visit_cnt","visit_cnt");
//        }};
//                tableName = "shcdn.r_snapshot_20100";
//                limitControl=" and icp_id in (SELECT icp_id from shcdn.r_snapshot_20100 where "+sqlTimeFormat(null == time ? TimeUtil.CustomToMinDateText() : time, 24)+" group by icp_id order by sum(visit_cnt) desc limit 500)";
//                break;
//            default:
//                return null;
//        }
//
//        final String finalIdName = idName;
//        return jdbcTemplateVertica.queryForList(
//                    SQLUtil.getQuerySql(tableName, selectMap,
//                            (null == dimension_id ? "" : String.format(" %s = '%s' And ", finalIdName, dimension_id))
//                                    + sqlTimeFormat(null == time ? TimeUtil.CustomToMinDateText() : time, 24) + limitControl + " order by ttime  "));
//    }

    /**
     * 查询数据详单
     * @return
     */
    public List<Map<String, Object>> queryDataList(Map<String,String[]> params){
        String dimension=params.get("dimension")[0];
        int type=Integer.parseInt(params.get("type")[0]);
        String id=params.get("id")[0];
        int hour=Integer.parseInt(params.get("hour")[0]);
        String cache_cdn=params.get("cache_cdn")[0]==null?null:params.get("cache_cdn")[0].toUpperCase();
        String time=TimeUtil.DateToMinDateText(new Date(TimeUtil.MinTextToDate(params.get("time")[0]).getTime()));
        /*String service_type ="";
         if(dimension =="service_type"){
            String[] service_types=params.get("service_type")==null?null:params.get("service_type");
            service_type=service_types[0];
        }
       if(service_types!=null){
            try{
                service_type=new String(service_types[0].getBytes("iso-8859-1"),"utf-8");
            }catch(Exception e){
                e.printStackTrace();
            }
        }*/
        String limitControl="";
        StringBuffer strBuff=new StringBuffer("");
        if(cache_cdn!=null&&!"".equals(cache_cdn.trim())){
            if ("CDN".equals(cache_cdn)) {
                strBuff.append(" and cache_cdn=\'CDN\'");
            }else if("CACHE".equals(cache_cdn)){
                strBuff.append(" and cache_cdn=\'CACHE\'");
            }else{
                strBuff.append(" and cache_cdn in('ALL',NULL)");
            }
        }
        StringBuffer strBuff2=new StringBuffer("");
        switch (dimension){
            case "data_center":
                if(type ==3)
                {
                    strBuff2.append(" and visit_cnt > 100 ");
                    break;
                }
            case "service_type":
                if(type==3){
                    strBuff2.append(" and visit_cnt >100 ");
                    break;
                }
        }
        if(!strBuff2.equals("")){
            limitControl=strBuff.toString() +strBuff2.toString();
        }else{
            limitControl=strBuff.toString();
        }
        switch (dimension) {
            case "data_center":
                if(cache_cdn!=null){
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("dc_id", id, time, hour)+limitControl));
                }else{
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("dc_id", id, time, hour)));
                }
            case "ICP":
                if(cache_cdn!=null){
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("icp_name", id, time, hour)+limitControl));
                }else{
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("icp_name", id, time, hour)));
                }
            case "service_type":
                /*StringBuffer querySQL=new StringBuffer(sqlTimeFormat(time,hour)+limitControl);
                if(service_type!=null){
                    querySQL.append(" and service_type=\'"+service_type+"\'");
                }
                return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension,type),"*",querySQL.toString()));*/
                if(cache_cdn!=null){
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("service_type", id, time, hour)+limitControl));
                }else{
                    return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("service_type", id, time, hour)));
                }
            default:
                return null;
        }
    }

//    /**
//     * 查询数据详单
//     * @param dimension
//     * @param type
//     * @param id
//     * @param time
//     * @return
//     */
//    public List<Map<String, Object>> queryDataList(String dimension, int type, String id, String time, int hour){
//        Map<String,String> selectMap;
//        switch (dimension) {
//            case "data_center":
//                selectMap = new HashMap<String,String>(){{
//                    put("dc_id", "id");
//                    put("dc_name", "name");
//                    put("dc_city", "belong_to");
//                    put("ttime", "time");
//                }};
//                break;
//            case "ICP":
//                selectMap = new HashMap<String,String>(){{
//                    put("icp_id", "id");
//                    put("icp_address", "name");
//                    put("icp_name", "belong_to");
//                    put("ttime", "time");
//                }};
//                break;
//            default:
//                return null;
//        }
//        switch (dimension) {
//            case "data_center":
//                return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("dc_id", id, time, hour)));
//            case "ICP":
//                return jdbcTemplateVertica.queryForList(SQLUtil.getQuerySql(getTable(dimension, type), "*", sqlTimeFormat("icp_rank", id, time, hour)));
//            default:
//                return null;
//        }
//    }

    /**
     * 查询数据详单
     * @param dimension
     * @param type
     * @param id
     * @param time
     * @return
     */
    public int countData(String dimension, String cache_cdn,int type, String id, String time, int hour){
        String dimension_id ="";
        switch (dimension){
            case "data_center":
                dimension_id = "dc_id";
                break;
            case "ICP":
                dimension_id = "icp_name";
                break;
            case "service_type":
                dimension_id = "service_type";
                break;
        }
        String limitControl="";
        StringBuffer strBuff=new StringBuffer("");
        if(cache_cdn!=null&&!"".equals(cache_cdn.trim())){
            if ("CDN".equals(cache_cdn)) {
                strBuff.append(" and cache_cdn=\'CDN\'");
            }else if("CACHE".equals(cache_cdn)){
                strBuff.append(" and cache_cdn=\'CACHE\'");
            }else{
                strBuff.append(" and cache_cdn in('ALL',NULL)");
            }
        }
        limitControl =strBuff.toString();
        return Integer.parseInt(jdbcTemplateVertica.queryForMap(SQLUtil.getQuerySql(getTable(dimension, type), "count(1) as numbercount",
                sqlTimeFormat(dimension_id, id, time, hour)+limitControl)).get("numbercount").toString());
    }
    /**
     * 根据维度和id获取表名
     * @param dimension
     * @param type
     * @return
     */
    public String getTable(String dimension, int type){
        switch (dimension) {
            case "data_center":
                switch (type) {
                    case 1:
                        return "shcdn.r_snapshot_22000";
                    case 2:
                        return "shcdn.r_snapshot_23000";
                    case 3:
                        return "shcdn.r_snapshot_22200";
                }
            case "ICP":
                switch (type) {
                    case 1:
                        return "shcdn.r_snapshot_20100";
                    case 2:
                        return "shcdn.r_snapshot_23100";
                    case 3:
                        return "shcdn.r_snapshot_22100";
                }
            case "service_type":
                if(type==1) {
                    return "shcdn.r_snapshot_21002";
                }else if(type==2) {
                    return "shcdn.r_snapshot_23002";
                }else{
                    return "shcdn.r_snapshot_20202";
                }
        }
        return null;
    }
}
