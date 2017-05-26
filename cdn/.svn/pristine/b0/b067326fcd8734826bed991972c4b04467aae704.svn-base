package cn.com.ailbb.service;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.ailbb.Dao.DownLoadBindDao;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by xzl on 2017/5/17.
 */
@Service
public class DownLoadBindServiceImpl implements  DownLoadBindService {

    @Autowired
    DownLoadBindDao downLoadBindDao;

    private String[] cityNameArr = {
            "广州", "深圳", "珠海", "中山", "佛山",
            "汕头", "东莞", "惠州", "潮州", "河源",
            "汕尾", "肇庆", "梅州", "茂名", "云浮",
            "湛江", "阳江", "揭阳", "清远", "江门", "韶关"
    };

    @Override
    public Map<String, List> getDownloadChartData(Map map) throws Exception {
        matchTableName(map);
        List<Map<String, String>> list = downLoadBindDao.getDownloadChartData(map);
        Map<String, List> out = new HashMap<String, List>();
        List tempList = new ArrayList();
        List<String> wdList = new ArrayList<String>();
        NumberFormat nf = NumberFormat.getNumberInstance();

        nf.setRoundingMode(RoundingMode.UP);
        nf.setMaximumFractionDigits(3);
        for (Map item : list) {
            wdList.add(MapUtils.getString(item, "name"));
            item.put("value", nf.format(MapUtils.getFloatValue(item, "value")));
            tempList.add(item);
        }
        out.put("legendData", wdList);
        out.put("datas", tempList);
        return out;
    }

    @Override
    public Map<String, Object> getDownloadICPChartData(Map map) throws Exception {
        matchTableName(map);
        List dataList = downLoadBindDao.getDownloadICPChartData(map);
        Map<String, Object> res = convertToStackChart(dataList, -1);
        return res;
    }


    @Override
    public Map<String, List> getSubDownloadChartData(Map map) throws Exception {
        DecimalFormat df = new DecimalFormat("0.###");
        map.put("wdName", "服务器");
        matchTableName(map);
        List<Map> serverList =  downLoadBindDao.getDownloadServerData(map);
        map.put("wdName", "域名");
        matchTableName(map);
        List<Map> domainList =  downLoadBindDao.getDownloadDomainData(map);
        List<List<String>> tempList = new ArrayList<List<String>>(4);
        for (int i = 0; i < 4; i++) {
            tempList.add(new ArrayList<String>());
        }
//        int serverSize = serverList.size(), domainSize = domainList.size();
        for (Map item : serverList) {
            tempList.get(0).add(MapUtils.getString(item, "name"));
            tempList.get(1).add(df.format(MapUtils.getFloatValue(item, "value", 0)));
        }
        for (Map item : domainList) {
            tempList.get(2).add(MapUtils.getString(item, "name"));
            tempList.get(3).add(df.format(MapUtils.getFloatValue(item, "value", 0)));
        }
        Map<String, List> resuleMap = new HashMap<String, List>();
        resuleMap.put("serverName", tempList.get(0));
        resuleMap.put("serverValue", tempList.get(1));
        resuleMap.put("domainName", tempList.get(2));
        resuleMap.put("domainValue", tempList.get(3));
        return resuleMap;
    }

    @Override
    public Map<String, Map<String, Object>> getSubDownloadICPChartData(Map map) throws Exception {
        map.put("wdName", "地市_服务器");
        matchTableName(map);
        List<Map> serverList = downLoadBindDao.getDownloadServerICPData(map);
        map.put("wdName", "地市_域名");
        matchTableName(map);
        List<Map> domainList = downLoadBindDao.getDownloadDomainICPData(map);
        Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
        resultMap.put("serverData", convertToStackChart(serverList, 100));
        resultMap.put("domainData", convertToStackChart(domainList, 100));
        return resultMap;
    }

    private Map<String, Object> convertToStackChart(List<Map> dataList, int limit) {
        List<String> wdList = new ArrayList<String>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<Map> valueList = new ArrayList<Map>();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        Map<String, Object> sortMap = new HashMap<String, Object>();
        for (int i = 0, len = dataList.size(); i < len; i++) {
            Map item = dataList.get(i);
            if (i == 0) {
                wdList.add(MapUtils.getString(item, "name"));
                valueMap.put(MapUtils.getString(item, "cityName"), MapUtils.getFloatValue(item, "value", 0));
            } else {
                Map prevItem = dataList.get(i - 1);
                if (MapUtils.getString(item, "name").equals(MapUtils.getString(prevItem, "name"))) {
                    valueMap.put(MapUtils.getString(item, "cityName"), MapUtils.getFloatValue(item, "value", 0));
                } else {
                    wdList.add(MapUtils.getString(item, "name"));
                    valueList.add(valueMap);
                    valueMap = new HashMap<String, Object>();
                    valueMap.put(MapUtils.getString(item, "cityName"), MapUtils.getFloatValue(item, "value", 0));
                }
            }
        }
        valueList.add(valueMap);
        //对处理的数据进行排序-start
        for (int i = 0, len = wdList.size(); i < len; i++) {
            sortMap.put(wdList.get(i), valueList.get(i));
        }
        List<Map.Entry<String, Object>> infoIds =
                new ArrayList<Map.Entry<String, Object>>(sortMap.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                Map<String, Object> m1 = (Map<String, Object>) o1.getValue();
                Map<String, Object> m2 = (Map<String, Object>) o2.getValue();
                Iterator it1 = m1.entrySet().iterator();
                Iterator it2 = m2.entrySet().iterator();
                float sum1 = 0, sum2 = 0;
                while (it1.hasNext()) {
                    sum1 += (Float) ((Map.Entry) it1.next()).getValue();
                }
                while (it2.hasNext()) {
                    sum2 += (Float) ((Map.Entry) it2.next()).getValue();
                }
                int res = 0;
                if (sum1 - sum2 > 0) {
                    res = 1;
                } else if (sum1 - sum2 < 0) {
                    res = -1;
                }
                return res;
            }
        });
        wdList = new ArrayList<String>();
        valueList = new ArrayList<Map>();
        for (Map.Entry<String, Object> item : infoIds) {
            if (limit == 0) {
                break;
            }
            limit--;
            wdList.add(item.getKey());
            valueList.add((Map) item.getValue());

        }
        //对处理的数据进行排序-end
        dataMap.put("wdName", wdList);
        Map<String, List<String>> cityValue = completeCityData(valueList);
        dataMap.put("valueList", cityValue);
        return dataMap;
    }


    private Map<String, List<String>> completeCityData(List<Map> list) {
        List<String> valueList;
        DecimalFormat df = new DecimalFormat("0.###");
        Map<String, List<String>> resMap = new HashMap<String, List<String>>();
        for (String cityName : cityNameArr) {
            valueList = new ArrayList<String>();
            String city = cityName + "市";
            for (Map item : list) {
                valueList.add(df.format(MapUtils.getFloatValue(item, city, 0)));
            }
            resMap.put(city, valueList);
        }
        return resMap;
    }

    private void matchTableName(Map map) {
        String wdName = MapUtils.getString(map, "wdName");
        int timeType = MapUtils.getIntValue(map, "timeType");
        String tableName = "r_report_";
       // String timeSelPart = "";
        switch (timeType) {
            case 60:
                tableName += "2";
            ///   timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "YYYY-MM-DD hh:mm");
                break;
            case 1440:
                tableName += "3";
            //    timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM-dd");
                break;
            case 10080:
                tableName += "4";
            //    timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM-dd");
                break;
            case 44640:
                tableName += "5";
            //    timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM");
                break;
        }
        if ("全网".equals(wdName)) {
            tableName += "00";
        } else if ("地市".equals(wdName)) {
            tableName += "10";
        } else if ("数据中心".equals(wdName)) {
            tableName += "20";
        } else if ("ICP".equals(wdName)) {
            tableName += "01";
        } else if ("服务器".equals(wdName)) {
            tableName += "30";
        } else if ("域名".equals(wdName)) {
            tableName += "22";
        } else if ("ICP下载带宽".equals(wdName)) {
            tableName += "11";
        } else if ("地市_域名".equals(wdName)) {
            tableName += "12";
        } else if ("地市_服务器".equals(wdName)) {
            tableName += "31";
        }
       // map.put("timeSelPart", timeSelPart);
        map.put("tableName", "shcdn." + tableName + "00");
    }
}
