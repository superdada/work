package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.dao.DataCenterDao;
import cn.com.ailbb.server.dao.SystemDao;
import cn.com.ailbb.server.mapper.DataCenterStatusMapper;
import cn.com.ailbb.server.mapper.SystemMapper;
import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.System;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
@Service(value="dataCenterDao")
public class DataCenterDaoImpl implements DataCenterDao {
    @Resource
    private DataCenterStatusMapper dataCenterStatusMapper;

    @Override
    public DataCenterStatus getCenterStatus(String id, String dimension) {
        return dataCenterStatusMapper.getData(id, dimension);
    }

    @Override
    public List<DataCenterStatus> getAllCenterStatus() {
        return dataCenterStatusMapper.getAllData();
    }

    @Override
    public void updateDataCenterStatus(String id, String dimension, boolean monitorFlag, boolean alarmFlag) {
        dataCenterStatusMapper.updateData(id, dimension, monitorFlag, alarmFlag);
    }

    @Override
    public void insertDataCenter(String id, String dimension, boolean monitorFlag, boolean alarmFlag) {
        dataCenterStatusMapper.insertData(id, dimension, monitorFlag, alarmFlag);
    }

}
