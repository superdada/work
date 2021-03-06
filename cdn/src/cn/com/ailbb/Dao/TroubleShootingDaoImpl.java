package cn.com.ailbb.Dao;


import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.com.ailbb.util.*;
/**
 * Created by xzl on 2017/5/17.
 */
@Repository
public class TroubleShootingDaoImpl  implements  TroubleShootingDao{

    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    private static String dbName = "shcdn.";//数据库名称

    @Override
    public List<Map<String,Object>> getErrCodeRate(Map<String, String> map) {
        String sql = getSelSql(map);
        return jdbcTemplateVertica.queryForList( sql);
    }


    //根据查询条件获取查询SQL
    public String getSelSql(Map<String, String> map) {
        String dimension = MapUtils.getString(map, "dimension", "");//维度
        String cache_cdn = MapUtils.getString(map, "cache_cdn", "");//类型
        String ICP2 = DataUtil.ObjectToString(map.get("ICP2"));//
        String server2 =DataUtil.ObjectToString(map.get("server2"));//
        String domain2 = DataUtil.ObjectToString(map.get("domain2"));//
        String ttime = DataUtil.ObjectToString(map.get("ttime"));//查询的时间点
        //String orderStr = DataUtil.getString(map, "orderStr", "ttime");//排序指标
        String orderStr = (map.get("orderStr")==null || map.get("orderStr").length() == 0 )?"ttime":map.get("orderStr").toString();
       // String limitStr = MapUtil.getString(map, "limit");限制的行数
        String limitStr = map.get("limit")!=null? DataUtil.ObjectToString(map.get("limit")):null;
        int limit = DataUtil.checkObj(limitStr) ? DataUtil.StringToInt(limitStr) : 150;
        String selql ="";
        if(orderStr.indexOf(",") > 0){
            selql +=  " select ";
            String[] sourceStrArray=orderStr.split(",");
            for(int i = 0 ;i<sourceStrArray.length;i++){
                selql +=sourceStrArray[i] + "*100 as \"value"+(i+1)+"\", ";
            }

        }else {
            selql +=  " select " + orderStr + "*100 as \"value\", ";
        }
         selql += "ttime AS \"ttime\",{:selectPart}" +
                " FROM {:tableName} WHERE  {:otherPart} ";
        if("domain".equals(dimension)){
            if (DataUtil.checkStr(ICP2)) {
                if(ICP2.indexOf(",") > 0){
                    String[] sourceStrArray=ICP2.split(",");
                    ICP2 ="";
                    for(int i = 0 ;i<sourceStrArray.length;i++){
                        ICP2 +=("'"+sourceStrArray[i]+"'");
                        if(i !=sourceStrArray.length -1 ){
                            ICP2 +=",";
                        }
                    }
                    selql += " AND icp_name in (" + ICP2 + ")";
                }else {
                    selql += " AND icp_name in ('" + ICP2 + "')";
                }
            }
            if (DataUtil.checkStr(domain2)) {
                if(domain2.indexOf(",") > 0){
                    String[] sourceStrArray=domain2.split(",");
                    domain2 ="";
                    for(int i = 0 ;i<sourceStrArray.length;i++){
                        domain2 +=("'"+sourceStrArray[i]+"'");
                        if(i !=sourceStrArray.length -1 ){
                            domain2 +=",";
                        }
                    }
                    selql += " AND domain_name in (" + domain2 + ")";
                }else {
                    selql += " AND domain_name in ('" + domain2 + "')";
                }
            }
        }else{
            if (DataUtil.checkStr(server2)) {
                if(server2.indexOf(",") > 0){
                    String[] sourceStrArray=server2.split(",");
                    server2="";
                    for(int i = 0 ;i<sourceStrArray.length;i++){
                        server2 +=("'"+sourceStrArray[i]+"'");
                        if(i !=sourceStrArray.length -1 ){
                            server2 +=",";
                        }
                    }
                    selql += " AND server_address in (" + server2 + ")";
                }else {
                    selql += " AND server_address in ('" + server2 + "')";
                }
            }
        }
        if (DataUtil.checkStr(ttime)) {
            selql += " AND ttime ='" + ttime + "'";
        }
        //selql += " AND " + orderStr + " is not null ";
        if(orderStr.indexOf(",") > 0){
            String[] sourceStrArray=orderStr.split(",");
            for(int i = 0 ;i<sourceStrArray.length;i++){
                selql +=(" AND " + sourceStrArray[i] + " is not null ");
            }
        }
        else{
            selql += " AND " + orderStr + " is not null ";
        }
        //添加CDN/CACHE/ALL过滤条件
        selql+=" and cache_cdn in("+(MapUtils.getString(map, "cache_cdn")==null?"'ALL',NULL)":"'"+MapUtils.getString(map, "cache_cdn")+"')");
        //order by
        if(orderStr.indexOf(",") > 0){
            selql += " order by ";
            String[] sourceStrArray=orderStr.split(",");
            for(int i = 0 ;i<sourceStrArray.length;i++){
                selql += (sourceStrArray[i]+" desc ");
                if(i !=sourceStrArray.length -1 ){
                    selql +=",";
                }
            }
            selql +=" limit " + limit + " ";
        }
        else{
            selql += " order by " + orderStr + " desc  limit " + limit + " ";//默认限制150条
        }
        Map<String, String> confMap = getSelectConf(map);
        selql = selql.replace("{:selectPart}", confMap.get("selectPart"));
        selql = selql.replace("{:tableName}", confMap.get("tableName"));
        selql = selql.replace("{:otherPart}", confMap.get("otherPart"));
        return selql;
    }

    //根据查询条件匹配表名,查询字段等信息
    public Map<String, String> getSelectConf(Map<String, String> map) {
        String timeType = map.get("timeType");//时间类型 2:小时,3:天,4:周,5:月
        String dimension = MapUtils.getString(map, "dimension", "");//维度
        String tableName = dbName + "r_report2_" + timeType;//表名
        String selectPart = "";//查询的其他条件
        String otherPart = "";//查询的其他限制
        if ("domain".equals(dimension)) { //域名维度
            tableName += "0020";
            selectPart += " icp_name AS \"icp_name\"," + " domain_name as \"y_name\", domain_name as \"id\" ";
            otherPart += " domain_name is not null and  icp_name is not null ";
        } else if ("server".equals(dimension)) { //服务器
            tableName += "0300";
            selectPart += " server_address as \"y_name\",server_address as \"id\" ";
            otherPart += "  server_address is not null ";
        }
        Map<String, String> confMap = new HashMap<String, String>();
        confMap.put("tableName", tableName);
        confMap.put("selectPart", selectPart);
        confMap.put("otherPart", otherPart);
        return confMap;
    }
}
