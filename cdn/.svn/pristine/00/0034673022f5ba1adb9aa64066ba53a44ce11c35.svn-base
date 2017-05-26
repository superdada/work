package cn.com.ailbb.Dao;

import cn.com.ailbb.util.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
@Repository
public class ErrorAnalysisDaoImpl implements ErrorAnalysisDao {

    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    private static String dbName = "shcdn.";//数据库名称

    /**
     * 细化查询错误码 占比
     */
    @Override
    public Map<String, List<Map<String,Object>>> getErrCodeValueRate(Map<String, String> map) {
        Map<String, List<Map<String,Object>>> reMap = new HashMap<String, List<Map<String, Object>>>();
        Map<String, String> confMap = getSelectConf(map);
        String needGroup = map.get("needGroup");//是否需要进行汇聚,需要则先进行Y轴条数限制
        if ("true".equals(needGroup)) {
            List<Map<String, Object>> yNameMap = getYNameByLimit(map, confMap);
            reMap.put("yNameMap", yNameMap);
            String yNames = getYName(yNameMap);
            map.put("yNames",yNames);
        }
        String sql = getSelSql(map, confMap);
        //获取错误码占比
        List<Map<String, Object>> errorCodeRateList = jdbcTemplateVertica.queryForList(sql);
        reMap.put("errorCodeRate", errorCodeRateList);
        return reMap;
    }

    public String getYName(List<Map<String, Object>> yNameMap) {
        int ListSize = yNameMap.size();
        String reYName="";
        for (int i = 0; i < ListSize; i++) {
            reYName +=(DataUtil.ObjectToString(yNameMap.get(i).get("y_name"))+"','");
        }
        reYName = reYName.substring(0,reYName.length()-3);
        return reYName;
    }

    //根据查询条件和匹配信息拼接查询SQL
    public String getSelSql(Map<String, String> map, Map<String, String> confMap) {
        String dc_city = map.get("dc_city");//地市
        String dc_id = map.get("dc_id");//数据中心编号
        String dc_name = map.get("dc_name");//数据中心名称
        String icp_id = map.get("icp_id");//ICP编号
        String icp_name = map.get("icp_name");//ICP名称
        String ttime = map.get("ttime");//查询的时间点
        String y_name = map.get("yNames");//查询的时间点
        String needGroup = map.get("needGroup");//是否需要进行汇聚处理
        //String pageNoStr = MapUtil.getString(map, "pageNo");
       // String limitStr = MapUtil.getString(map, "limit");
        String limitStr = (map.get("limit")!=null? map.get("limit").toString():null);
        String isNeedCode = MapUtils.getString(map, "isNeedCode", "0");//是否需要错误码,即是否细化查询,1:是 0：否
        String isNeedLimit = MapUtils.getString(map, "isNeedLimit", "true");//是否需要进行数目限制
        int limit = DataUtil.checkObj(limitStr) ? DataUtil.StringToInt(limitStr) : 150;
        String selSql;
        if("true".equals(needGroup)){
            selSql = " select SUM(err_code_rate*100) as \"err_code_rate\",";
        }else{
            selSql = " select err_code_rate*100 as \"err_code_rate\",";
        }
        if ("1".equals(isNeedCode)) {
            selSql += " err_code as \"err_code\",";
        }
        selSql += "{:selectPart} FROM {:tableName} WHERE err_code_rate != 0 AND err_code_rate IS NOT NULL {:otherPart} ";
        if (DataUtil.checkStr(dc_city)) {
            selSql += " and (dc_city = '" + dc_city + "' or dc_city = '" + dc_city + "市')";
        }
        if (DataUtil.checkStr(dc_id)) {
            selSql += " AND dc_id ='" + dc_id + "'";
        }
        if (DataUtil.checkStr(dc_name)) {
            selSql += " AND dc_name ='" + dc_name + "'";
        }
        if (DataUtil.checkStr(icp_id)) {
            selSql += " AND icp_id ='" + icp_id + "'";
        }
        if (DataUtil.checkStr(icp_name)) {
            selSql += " AND icp_name ='" + icp_name + "'";
        }
        if (DataUtil.checkStr(ttime)) {
            selSql += " AND ttime ='" + ttime + "'";
        }
        if (DataUtil.checkStr(y_name)) {
            selSql += " AND {:y_name} in('" + y_name + "')";
        }
        if("true".equals(needGroup)){
            selSql += "{:groupStr}";
        }else{
            selSql += " order by err_code_rate desc ";
        }
        if ("true".equals(isNeedLimit)) {
            selSql += " limit " + limit + " ";
        }
        selSql = selSql.replace("{:selectPart}", confMap.get("selectPart"));
        selSql = selSql.replace("{:tableName}", confMap.get("tableName"));
        selSql = selSql.replace("{:otherPart}", confMap.get("otherPart"));
        selSql = selSql.replace("{:y_name}", confMap.get("y_name"));
        selSql = selSql.replace("{:groupStr}", confMap.get("groupStr"));
        return selSql;
    }


    //根据查询条件匹配表名,查询字段等信息
    public Map<String, String> getSelectConf(Map<String, String> map) {
        String timeType = map.get("timeType");//时间类型 2:小时,3:天,4:周,5:月
        String dimension = MapUtils.getString(map, "dimension", "");//维度
        String isNeedCode = MapUtils.getString(map, "isNeedCode", "0");//是否需要错误码,即是否细化查询,1:是 0：否
        String tableName = dbName + "r_report_" + timeType;//表名
        String selectPart = "";//查询的其他条件
        String otherPart = "";//查询的其他限制
        String orderStr = "";//排序字段
        String groupStr = "";//汇聚字段
        String y_name = "";//汇聚字段
        if ("dcCity".equals(dimension)) { //数据中心位置,即地市维度
            tableName += "100";
            selectPart += " dc_city AS \"y_name\",dc_city AS \"id\" ";
            otherPart += " AND  dc_city is not null ";
            orderStr = " order by dc_city,err_code_rate ";
        } else if ("dcName".equals(dimension)) { //数据中心维度
            tableName += "200";
            selectPart += " dc_name AS \"y_name\",dc_id AS \"id\" ";
            otherPart += " AND  dc_id is not null AND dc_name is not null ";
            orderStr = " order by dc_id,err_code_rate ";
        } else if ("icp".equals(dimension)) { //ICP维度
            tableName += "010";
            selectPart += " icp_name AS \"y_name\",icp_id AS \"id\" ";
            otherPart += " AND  icp_id is not null AND icp_address is not null ";
            orderStr = " order by icp_id,err_code_rate ";
        } else if ("dcDomain".equals(dimension)) { //数据中心+域名 维度
            tableName += "220";
            selectPart += " domain_name AS \"y_name\",domain_name AS \"id\" ";
            otherPart += " AND  domain_name is not null ";
            orderStr = " order by domain_name,err_code_rate ";
            groupStr += " group by err_code ,domain_name ";
            y_name = "domain_name";
        } else if ("dcServer".equals(dimension)) { //数据中心+服务器 维度
            tableName += "300";
            selectPart += " server_address AS \"y_name\",server_address AS \"id\" ";
            otherPart += " AND  server_address is not null AND dc_id IS NOT NULL ";
            orderStr = " order by server_address,err_code_rate ";
            groupStr += " group by err_code ,server_address ";
            y_name = "server_address";
        } else if ("icpDc".equals(dimension)) { //ICP+数据中心 维度
            tableName += "210";
            selectPart += " dc_name AS \"y_name\",dc_id AS \"id\" ";
            otherPart += " AND  dc_id is not null AND dc_name is not null ";
            orderStr = " order by dc_id,err_code_rate ";
            groupStr += " group by err_code ,dc_name,dc_id ";
            y_name = "dc_name";
        } else if ("icpServer".equals(dimension)) { //ICP+服务器 维度
            tableName += "310";
            selectPart += " server_address AS \"y_name\",server_address AS \"id\" ";
            otherPart += " AND  icp_address is not null AND server_address IS NOT NULL ";
            orderStr = " order by server_address,err_code_rate ";
            groupStr += "group by err_code ,server_address ";
            y_name = "server_address";
        }
        if ("0".equals(isNeedCode)) {
            orderStr = " order by err_code_rate ";
        }

        //添加cache_cdn过滤条件
        otherPart+=" and cache_cdn in("+(MapUtils.getString(map, "cache_cdn")==null?"'ALL',NULL)":"'"+MapUtils.getString(map, "cache_cdn")+"')");

        Map<String, String> confMap = new HashMap<String, String>();
        confMap.put("tableName", tableName + isNeedCode);
        confMap.put("selectPart", selectPart);
        confMap.put("otherPart", otherPart);
        confMap.put("orderStr", orderStr);
        confMap.put("groupStr", groupStr);
        confMap.put("y_name", y_name);
        return confMap;
    }

    /**
     * 限制查询Y轴的枚举数据
     */
    public List<Map<String, Object>> getYNameByLimit(Map<String, String> map, Map<String, String> confMap) {
        String dc_id = map.get("dc_id");//数据中心编号
        String dc_name = map.get("dc_name");//数据中心名称
        String icp_id = map.get("icp_id");//ICP编号
        String icp_name = map.get("icp_name");//ICP名称
        String ttime = map.get("ttime");//查询的时间点
       // String limitStr = MapUtil.getString(map, "limit");
        String limitStr = (map.get("limit")!=null? map.get("limit").toString():null);
        int limit = DataUtil.checkObj(limitStr) ? DataUtil.StringToInt(limitStr) : 150;
        String selsql = " select y_name as \"y_name\" FROM (";
        selsql += " SELECT {:selectPart},sum(err_code_rate*100) as \"sum_rate\" FROM {:tableName} WHERE " +
                " err_code_rate != 0 AND err_code_rate IS NOT NULL {:otherPart} ";
        if (DataUtil.checkStr(dc_id)) {
            selsql += " AND dc_id ='" + dc_id + "'";
        }
        if (DataUtil.checkStr(dc_name)) {
            selsql += " AND dc_name ='" + dc_name + "'";
        }
        if (DataUtil.checkStr(icp_id)) {
            selsql += " AND icp_id ='" + icp_id + "'";
        }
        if (DataUtil.checkStr(icp_name)) {
            selsql += " AND icp_name ='" + icp_name + "'";
        }
        if (DataUtil.checkStr(ttime)) {
            selsql += " AND ttime ='" + ttime + "'";
        }
        selsql += "  {:groupStr} ) A order by A.sum_rate desc limit " + limit + " ";
        selsql = selsql.replace("{:selectPart}", confMap.get("selectPart"));
        selsql = selsql.replace("{:tableName}", confMap.get("tableName"));
        selsql = selsql.replace("{:otherPart}", confMap.get("otherPart"));
        selsql = selsql.replace("{:groupStr}", confMap.get("groupStr"));
        return jdbcTemplateVertica.queryForList(selsql);
    }


}
