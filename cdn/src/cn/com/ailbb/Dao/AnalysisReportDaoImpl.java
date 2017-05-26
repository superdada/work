package cn.com.ailbb.Dao;

import cn.com.ailbb.database.Dialect;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import cn.com.ailbb.util.DataUtil;
import org.springframework.stereotype.Repository;

/**
 * Created by xzl on 2017/5/16.
 */
@Repository
public class AnalysisReportDaoImpl implements AnalysisReportDao {

    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    @Autowired
    private Dialect dialect; //如果多个数据库方言，哪个实现类注入

    public List<Map<String, Object>> getPerformanceData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(getTimeSelPart(MapUtils.getIntValue(map, "timeType"))).append(" as ttime")
                .append(",http_200_rate*100 as \"http_200_rate\",http_206_rate*100 as \"http_206_rate\",http_301_rate*100 as \"http_301_rate\",http_302_rate*100 as \"http_302_rate\",")
                .append("http_306_rate*100 as \"http_306_rate\",http_other_rate*100 as \"http_other_rate\",slow_100k_rate*100 as \"低于_100k_rate\",up_100k_rate*100 as \"高于_100k_rate\",")
                .append("up_500k_rate*100 as \"高于_500k_rate\",up_2000k_rate*100 as \"高于_2000k_rate\",hit_dl_speed / 1024 as \"hit_dl_speed\",miss_dl_speed / 1024 as \"miss_dl_speed\"")
                .append(" from ").append(MapUtils.getString(map, "tableName"))
                .append(" where ttime >= '").append(MapUtils.getString(map, "timeStart"))
                .append("' and ttime <= '").append(MapUtils.getString(map, "timeEnd")).append("'")
                .append(" and cache_cdn in("+(MapUtils.getString(map, "cache_cdn")==null?"'ALL',NULL)":"'"+MapUtils.getString(map, "cache_cdn")+"')"))
                .append(getWherePartByWD(map)).append(" order by ttime");
        String sql_last = sql.toString();
        return jdbcTemplateVertica.queryForList(sql_last);
    }


    @Override
    public List getConditionEnum(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        String id = MapUtils.getString(map, "conditionId");
        String cityName = MapUtils.getString(map, "cityName");
        String dataCenterId = MapUtils.getString(map, "dataCenter");
        String searchValue = MapUtils.getString(map, "searchValue", "");
        if ("dataCenter".equals(id)) {
            sql.append("select dc_name as \"text\",dc_id as \"id\" from shcdn.c_conf_datacenter")
                    .append(" where (dc_city = '").append(cityName).append("市'")
                    .append(" or dc_city = '").append(cityName).append("')")
                    .append(" group by dc_name,dc_id  order by dc_id asc");
        } else if ("server".equals(id)) {
            sql.append("select equipment_ip as \"id\",equipment_ip as \"text\" from shcdn.c_conf_datacenter")
                    .append(" where (dc_city = '").append(cityName).append("市'")
                    .append(" or dc_city = '").append(cityName).append("')")
                    .append(" and dc_id = ").append(dataCenterId)
                    .append(" and equipment_ip like '%").append(searchValue).append("%'")
                    .append(" group by equipment_ip limit 20");
        } else if ("ICP".equals(id)) {
            sql.append("select icp_name as \"text\",icp_id as \"id\" from shcdn.c_conf_icp")
                    .append(" where icp_name like '%").append(searchValue).append("%'")
                    .append(" group by icp_name,icp_id limit 20");
        }
        return jdbcTemplateVertica.queryForList(sql.toString());
    }


    @Override
    public List<Map<String, Object>> getFlowChartData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(getTimeSelPart(MapUtils.getIntValue(map, "timeType"))).append("as \"ttime\",bandwidth from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime >= '")
                .append(MapUtils.getString(map, "timeStart")).append("' and ttime <= '")
                .append(MapUtils.getString(map, "timeEnd")).append("'")
                .append(" and cache_cdn in("+(MapUtils.getString(map, "cache_cdn")==null?"'ALL',NULL)":"'"+MapUtils.getString(map, "cache_cdn")+"')"))
                .append(getWherePartByWD(map)).append(" order by ttime");
        String sql_last = sql.toString();
        return jdbcTemplateVertica.queryForList(sql_last);
    }

    /**
     * 维度获取查询条件
     *
     * @param map
     * @return
     */
    private String getWherePartByWD(Map map) {
        String wdName = MapUtils.getString(map, "wdName");
        String cityName = MapUtils.getString(map, "cityName");
        String dataCenter = MapUtils.getString(map, "dataCenter");
        String ICP = MapUtils.getString(map, "ICP");
        String server = MapUtils.getString(map, "server");
        String wherePart = "";
        if ("地市".equals(wdName)) {
            if (DataUtil.checkStr(cityName))
                wherePart += " and (dc_city = '" + cityName + "' or dc_city = '" + cityName + "市')";
        } else if ("数据中心".equals(wdName)) {
            if (DataUtil.checkStr(cityName))
                wherePart += " and (dc_city = '" + cityName + "' or dc_city = '" + cityName + "市')";
            if (DataUtil.checkStr(dataCenter))
                wherePart += " and dc_id = '" + dataCenter + "'";
        } else if ("ICP".equals(wdName)) {
            if (DataUtil.checkStr(ICP))
                wherePart += " and icp_id = '" + ICP + "'";
        } else if ("服务器".equals(wdName)) {
            if (DataUtil.checkStr(cityName))
                wherePart += " and (dc_city = '" + cityName + "' or dc_city = '" + cityName + "市')";
            if (DataUtil.checkStr(dataCenter))
                wherePart += " and dc_id = '" + dataCenter + "'";
            if (DataUtil.checkStr(server))
                wherePart += " and server_address = '" + server + "'";
        }
        return wherePart;
    }
   public String getTimeSelPart(int timeType){
        String timeSelPart = "";
       switch (timeType) {
           case 60:
               timeSelPart = dialect.datetimeTostring("ttime", "YYYY-MM-DD hh:mm");
               break;
           case 1440:
               timeSelPart = dialect.datetimeTostring("ttime", "yyyy-MM-dd");
               break;
           case 10080:
               timeSelPart = dialect.datetimeTostring("ttime", "yyyy-MM-dd");
               break;
           case 44640:
               timeSelPart = dialect.datetimeTostring("ttime", "yyyy-MM");
               break;
       }
       return timeSelPart;
   }

}
