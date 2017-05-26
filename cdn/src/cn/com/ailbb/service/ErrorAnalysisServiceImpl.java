package cn.com.ailbb.service;

import cn.com.ailbb.util.DataUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.ailbb.Dao.ErrorAnalysisDao;

import java.text.NumberFormat;
import java.util.*;

/**
 * Created by xzl on 2017/5/17.
 */
@Service
public class ErrorAnalysisServiceImpl implements ErrorAnalysisService {

    @Autowired
    private ErrorAnalysisDao errorAnalysisDao;


    @Override
    public Map<String, Object> getErrCodeValueRate(Map<String, String> map) {
        Map<String,List<Map<String, Object>>> reMap = errorAnalysisDao.getErrCodeValueRate(map);
        //时间段内的Y轴枚举
        //List<Map<String, String>> yNameMap = reMap.get("yNameMap");
        //时间段内出现的错误码占比
        List<Map<String, Object>> errorCodeRateList = reMap.get("errorCodeRate");
        Map<String,Object> result = DateAssortment(errorCodeRateList);
        return result;
    }

    /**
     * 数据分拣(排序,补数)
     * errorCodeRateList 错误码占比具体数据
     */
    public Map<String,Object> DateAssortment(List<Map<String,Object>> errorCodeRateList){
        Map<String,Object> reMap = new HashMap<String, Object>();
        int codeRateListSize = errorCodeRateList.size();
        Map<String,Float> sumCodeRateMap = new HashMap<String, Float>();//Y轴类目 对应错误码汇聚值
        Map<String,List<Map<String,   Object>>> codeMap = new HashMap<String, List<Map<String,   Object>>>();//错误码对应结果集
        for(int i=0;i<codeRateListSize;i++){
            Map<String,Object> codeRateMap = errorCodeRateList.get(i);
            String y_name = DataUtil.ObjectToString(codeRateMap.get("y_name"));//错误码//对应的Y轴 类目名称
            String err_code = DataUtil.ObjectToString(codeRateMap.get("err_code"));//错误码
            float err_code_rate = MapUtils.getFloatValue(codeRateMap, "err_code_rate");//错误码占比
            Float sumCodeRate = sumCodeRateMap.get(y_name);
            if(DataUtil.checkObj(sumCodeRate)){
                sumCodeRateMap.put(y_name,sumCodeRate+err_code_rate);
            }else{
                sumCodeRateMap.put(y_name,err_code_rate);
            }
            List<Map<String, Object>> codeList = codeMap.get(err_code);
            if(!DataUtil.checkObj(codeList)){
                codeList = new ArrayList<Map<String, Object>>();
            }
            codeList.add(codeRateMap);
            codeMap.put(err_code,codeList);
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);//保留三位小数
        List keyList = (List) sortByValue(sumCodeRateMap,false).get("keyList");//排序后的Y轴类目
        int KeyListSize = keyList.size();
        Map<String,List<Float>> codeRateByCode = new HashMap<String, List<Float>>();//存放    错误码-值
        for(Map.Entry<String,List<Map<String, Object>>> entry : codeMap.entrySet()){
            String key = entry.getKey();//错误码
            List<Map<String, Object>> value = entry.getValue();
            int valueSize = value.size();
            Map<String, Object> dataMap;
            List<Float> listData = new ArrayList<Float>();
            for(int j=0;j<KeyListSize;j++){
                String yName = (String)keyList.get(j);//Y轴类目
                if(yName == null){
                    break;
                }
                float code_rate = 0;
                for(int i=0;i<valueSize;i++){
                    dataMap = value.get(i);
                    String y_name = DataUtil.ObjectToString(dataMap.get("y_name"));
                    if(yName.equals(y_name)){
                        code_rate = MapUtils.getFloatValue(dataMap, "err_code_rate");//错误码占比
                        code_rate = Float.parseFloat(nf.format(code_rate));
                        break;
                    }
                }
                listData.add(code_rate);
            }
            codeRateByCode.put(key,listData);
        }
        reMap.put("yAxis",keyList);
        reMap.put("series",codeRateByCode);
        return reMap;
    }


    /**
     * 按照value对key进行排序
     * reverse : false小到大,true大到小
     */
    private Map<String,Object> sortByValue(Map map, final boolean reverse) {
        Map<String,Object> reMap = new HashMap<String, Object>();
        List list = new LinkedList(map.entrySet());
        Collections .sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                if (reverse) {
                    return -((Comparable) ((Map .Entry)o1).getValue())
                            .compareTo(((Map .Entry)o2).getValue());
                }
                return ((Comparable) ((Map .Entry)o1).getValue())
                        .compareTo(((Map .Entry)o2).getValue());
            }
        });
        Map result = new LinkedHashMap();
        List keyList = new ArrayList();
        List valueList = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        reMap.put("map",result);
        reMap.put("keyList",keyList);
        reMap.put("valueList",valueList);
        return reMap;
    }
}
