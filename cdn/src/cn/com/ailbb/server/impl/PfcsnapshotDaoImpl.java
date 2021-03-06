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
    public Pfcsnapshot getPfcsnapshot(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index) {
        return pfcsnapshotMapper.getData(id, datasource, dimension, cache_cdn, directory_level,time,index);
    }

    @Override
    public Pfcsnapshot getPfcsnapshotUserDimensionId(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index) {
        return pfcsnapshotMapper.getDimensionIdData( id, datasource, dimension, cache_cdn, directory_level,time,index);
    }

    @Override
    public void deleteData(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index) {
        pfcsnapshotMapper.deleteData(id, datasource, dimension, cache_cdn, directory_level,time,index);
    }

    @Override
    public void insertData(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index) {
        pfcsnapshotMapper.insertData(id, datasource, dimension, cache_cdn, directory_level,time,index);
    }
}
