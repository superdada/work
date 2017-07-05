package cn.com.ailbb.service;

import cn.com.ailbb.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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
        return dataProcess(troubleShootingDao.getErrCodeRate(map));
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
    //返回前数据处理
    public List<Map<String,Object>> dataProcess(List<Map<String, Object>> mapList){
        int dataSize = mapList.size();
        for (int i =0;i<dataSize;i++){
            Map<String,Object> map = mapList.get(i);
            // Float value = MapUtil.getFloat(map,"value");
            Double value= DataUtil.StringToDouble(DataUtil.ObjectToString(map.get("value")));
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(DataUtil.ObjectToString(entry.getKey()).indexOf("value")> 0){
                    String key = DataUtil.ObjectToString(entry.getKey());
                    String indexvalue = DataUtil.ObjectToString(entry.getValue());
                    if(indexvalue.indexOf("E")>0){
                        BigDecimal db = new BigDecimal(indexvalue);
                        indexvalue = db.toPlainString();
                    }
                    if(indexvalue.indexOf(".")>0){
                        if(indexvalue.length()>(indexvalue.indexOf(".")+1+3)){
                            indexvalue = indexvalue.substring(0,indexvalue.indexOf(".")+1+3);
                        }
                    }
                    map.put(key,indexvalue);
                }
                }
            }


        return mapList;
    }


}
