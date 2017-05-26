package cn.com.ailbb.service;

import cn.com.ailbb.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import cn.com.ailbb.Dao.TroubleShootingDao;
import org.springframework.stereotype.Service;

/**
 * Created by xzl on 2017/5/17.
 */
@Service
public class TroubleShootingServiceImpl implements  TroubleShootingService{

    @Autowired
    private TroubleShootingDao troubleShootingDao;

    @Override
    public List<Map<String, Object>> getErrCodeRate(Map<String, String> map) {
        return dataProcessing(troubleShootingDao.getErrCodeRate(map));
    }

    //返回前数据处理
    public List<Map<String,Object>> dataProcessing(List<Map<String, Object>> mapList){
        int dataSize = mapList.size();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);//保留三位小数
        for (int i =0;i<dataSize;i++){
            Map<String,Object> map = mapList.get(i);
           // Float value = MapUtil.getFloat(map,"value");
           Double value= DataUtil.StringToDouble(DataUtil.ObjectToString(map.get("value")));
            map.put("value",nf.format(value));
        }
        return mapList;
    }
}
