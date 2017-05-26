package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.DataCenterStatus;

import java.util.List;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public interface DataCenterDao {
    DataCenterStatus getCenterStatus(String id, String dimension);

    List<DataCenterStatus> getAllCenterStatus();

    void updateDataCenterStatus(String id, String dimension, boolean monitorFlag, boolean alarmFlag);

    void insertDataCenter(String id, String dimension, boolean monitorFlag, boolean alarmFlag);
}
