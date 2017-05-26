package cn.com.ailbb.Dao;

import cn.com.ailbb.database.Dialect;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;
import cn.com.ailbb.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xzl on 2017/5/17.
 */
@Repository
public class DownLoadBindDaoImpl implements DownLoadBindDao {

    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    private String[] cityNameArr = {
            "广州", "深圳", "珠海", "中山", "佛山",
            "汕头", "东莞", "惠州", "潮州", "河源",
            "汕尾", "肇庆", "梅州", "茂名", "云浮",
            "湛江", "阳江", "揭阳", "清远", "江门", "韶关"
    };

    @Override
    public List getDownloadChartData(Map map) throws Exception {
        String cityName = MapUtils.getString(map, "cityName");
        String ICP = MapUtils.getString(map, "ICP");
        String tableName = MapUtils.getString(map, "tableName");
        tableName = tableName.substring(0, tableName.length() - 2);
        String queryType = MapUtils.getString(map, "queryType");
        StringBuffer sql = new StringBuffer();
        sql.append("select dc_name as \"name\",sum(bandwidth_rate) * 100 as \"value\",dc_id as \"id\" from ")
                .append("{:tableName}").append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart")).append("' ");
        if ("cityName".equals(queryType) &&DataUtil.checkStr(cityName)) {
            sql.append(" and (dc_city = '").append(cityName).append("市'")
                    .append(" or dc_city = '").append(cityName).append("')");
            tableName += "2000";
        }
        if ("ICP".equals(queryType) && DataUtil.checkStr(ICP)) {
            sql.append(" and icp_id = '").append(ICP).append("' ");
            tableName += "2100";
        }

        //???CDN/CACHE/ALL????????
        sql.append(" and cache_cdn in("+(MapUtils.getString(map, "cache_cdn")==null?"'ALL',NULL)":"'"+MapUtils.getString(map, "cache_cdn")+"')"));

        sql.append(" group by dc_name,id order by id");
        String sql_last = sql.toString().replace("{:tableName}", tableName);
        return jdbcTemplateVertica.queryForList(sql_last);
    }


    @Override
    public List getDownloadICPChartData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select dc_city as \"cityName\",icp_id as \"id\",icp_name as \"name\",sum(bandwidth) as \"value\" from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart")).append("' and icp_name is not null group by dc_city,icp_id,icp_name")
                .append(" order by icp_id,").append(getCityOrder(cityNameArr));
        return jdbcTemplateVertica.queryForList(sql.toString());
    }

    @Override
    public List getDownloadServerData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select server_address as \"name\",bandwidth as \"value\" from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart")).append("' and dc_id = ")
                .append(MapUtils.getString(map, "id")).append(" and bandwidth is not NULL and  server_address is not null order by bandwidth asc");
        return jdbcTemplateVertica.queryForList(sql.toString());
    }

    @Override
    public List getDownloadDomainData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select domain_name as \"name\",bandwidth as \"value\" from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart")).append("' and dc_id = ")
                .append(MapUtils.getString(map, "id")).append(" and bandwidth is not NULL and  domain_name is not null order by bandwidth asc");
        return jdbcTemplateVertica.queryForList(sql.toString());
    }

    @Override
    public List getDownloadServerICPData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select dc_city as \"cityName\",server_address as \"name\",sum(bandwidth) as \"value\" from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart"))
                .append("' and icp_name = '").append(MapUtils.getString(map, "name"))
                .append("' and server_address is not null group by dc_city,server_address")
                .append(" order by server_address,").append(getCityOrder(cityNameArr));
        return jdbcTemplateVertica.queryForList(sql.toString());
    }

    @Override
    public List getDownloadDomainICPData(Map map) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select dc_city as \"cityName\",domain_name as \"name\",sum(bandwidth) as \"value\" from ")
                .append(MapUtils.getString(map, "tableName")).append(" where ttime = '")
                .append(MapUtils.getString(map, "timeStart"))
                .append("' and icp_name = '").append(MapUtils.getString(map, "name"))
                .append("' and domain_name is not null group by dc_city,domain_name")
                .append(" order by domain_name,").append(getCityOrder(cityNameArr));
        return jdbcTemplateVertica.queryForList(sql.toString());
    }

    private String getCityOrder(String[] cityNameArr) {
        StringBuffer order = new StringBuffer();
        order.append("case");
        for (int i = 0, len = cityNameArr.length; i < len; i++) {
            order.append(" when dc_city like  '").append(cityNameArr[i]).append("' then ").append(i);
        }
        order.append(" else 99 end");
        return order.toString();
    }
}
