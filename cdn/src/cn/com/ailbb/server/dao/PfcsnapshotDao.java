package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.Pfcsnapshot;

import java.util.List;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public interface PfcsnapshotDao {
    Pfcsnapshot getPfcsnapshot(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index);

    Pfcsnapshot getPfcsnapshotUserDimensionId(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index);

    void deleteData(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index);

    void insertData(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index);
}
