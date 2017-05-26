package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.dao.PfcsnapshotDao;
import cn.com.ailbb.server.mapper.PfcsnapshotMapper;
import cn.com.ailbb.server.pojo.Pfcsnapshot;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
@Service(value="pfcsnapshotDao")
public class PfcsnapshotDaoImpl implements PfcsnapshotDao {
    @Resource
    private PfcsnapshotMapper pfcsnapshotMapper;

    @Override
    public Pfcsnapshot getPfcsnapshot(long id) {
        return pfcsnapshotMapper.getData(id);
    }

    @Override
    public Pfcsnapshot getPfcsnapshotUserDimensionId(String id) {
        return pfcsnapshotMapper.getDimensionIdData(id);
    }

    @Override
    public void deleteData(long id) {
        pfcsnapshotMapper.deleteData(id);
    }

    @Override
    public void insertData(long id, String dimension, String index, String cache_cdn,String time, String dimensionId) {
        pfcsnapshotMapper.insertData(id, dimension, index,time, dimensionId, cache_cdn);
    }
}
