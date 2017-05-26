package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.Pfcsnapshot;

import java.util.List;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public interface PfcsnapshotDao {
    Pfcsnapshot getPfcsnapshot(long id);

    Pfcsnapshot getPfcsnapshotUserDimensionId(String id);

    void deleteData(long id);

    void insertData(long id, String dimension, String index,String cache_cdn, String time, String dimensionId);
}
