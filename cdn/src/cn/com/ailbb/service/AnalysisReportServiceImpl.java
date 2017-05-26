package cn.com.ailbb.service;
import cn.com.ailbb.Dao.AnalysisReportDao;
import cn.com.ailbb.util.DataUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
/**
 * Created by xzl on 2017/5/16.
 */
@Service
public class AnalysisReportServiceImpl  implements AnalysisReportService {

    @Autowired
    private AnalysisReportDao analysisReportDao;

    public List<List> getChartData(Map map) throws Exception {
        matchTableName(map);
        String[] timeSlot = MapUtils.getString(map, "timeSlot").split(",");
        String[] keyStr = {"http_200_rate", "http_206_rate", "http_301_rate", "http_302_rate",
                "http_306_rate", "http_other_rate", "低于_100k_rate", "高于_100k_rate",
                "高于_500k_rate", "高于_2000k_rate", "hit_dl_speed", "miss_dl_speed"};//应与sql中的别名对应
        List<Map<String, Object>> list = analysisReportDao.getPerformanceData(map);
        if (list.size() == 0) {
            return new ArrayList<List>();
        }
        return convertRowToCol(list, timeSlot, keyStr, true);
    }

    @Override
    public List getConditionEnum(Map map) throws Exception {
        return analysisReportDao.getConditionEnum(map);
    }

    public List getFlowChartData(Map map) throws Exception {
        matchTableName(map);
        String[] timeSlot = MapUtils.getString(map, "timeSlot").split(",");
        String[] keyStr = {"bandwidth"};
        List<Map<String, Object>> list = analysisReportDao.getFlowChartData(map);
        if (list.size() == 0) {
            return new ArrayList<List>();
        }
        return convertRowToCol(list, timeSlot, keyStr, false);
    }
    /**
     * 根据维度匹配表名
     * 和 根据数据库驱动匹配时间函数
     *
     * @param map
     */
    private void matchTableName(Map map) {
        String wdName = MapUtils.getString(map, "wdName");
        int timeType = MapUtils.getIntValue(map, "timeType");
        String tableName = "r_report_";
      //  String timeSelPart = "";
        switch (timeType) {
            case 60:
                tableName += "2";
               // timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "YYYY-MM-DD hh:mm");
                break;
            case 1440:
                tableName += "3";
             //   timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM-dd");
                break;
            case 10080:
                tableName += "4";
             //   timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM-dd");
                break;
            case 44640:
                tableName += "5";
              //  timeSelPart = DialectFactory.getDialect("Vertica").datetimeTostring("ttime", "yyyy-MM");
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
      //  map.put("timeSelPart", timeSelPart);
        map.put("tableName", "shcdn." + tableName + "00");
    }

    /**
     * 将每行的列值拼成一行数据。
     *
     * @param dataList 查询出的行数据集合
     * @param timeSlot 时间点集合
     * @param keyStr   列名集合
     * @return {r1:a1,a2,a3...}
     */
    private List<List> convertRowToCol(List<Map<String, Object>> dataList, String[] timeSlot,
                                       String[] keyStr, boolean isConvert) {
        int keySize = keyStr.length;
        List<List> listArr = new ArrayList<List>(keySize);
        for (int j = 0; j < keySize; j++) {
            listArr.add(new ArrayList());
        }
        int size = timeSlot.length;
        int listSize = dataList.size();
        for (int i = 0, k = 0; i < size; i++) {
            boolean flag = false;//防止变量k使 dataList.get(k) 溢出
            for (int j = 0; j < keySize; j++) {
                if (k >= listSize) {//处理 前面有数据，后面没数据的情况
                    listArr.get(j).add("0");
                    flag = false;
                    continue;
                }
                Map<String, Object> item = dataList.get(k);
                if (timeSlot[i].equals(DataUtil.ObjectToString(item.get("ttime")))) {
                    float value = MapUtils.getFloatValue(item, keyStr[j]);
                    if (isConvert) {
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
                        nf.setRoundingMode(RoundingMode.UP);
                        /*if (j < 10) {//处理占比数据 保留4位小数
                            nf.setMaximumFractionDigits(4);
//                            listArr.get(j).add(nf.format(value * 100));
                        } else {//处理速率数据  单位转换->保留两位小数
                            nf.setMaximumFractionDigits(2);
//                            listArr.get(j).add(nf.format(value / 1024));
                        }*/
                        nf.setMaximumFractionDigits(3);//都保留三位小数
                        listArr.get(j).add(nf.format(value));
                    } else {
                        listArr.get(j).add(value);
                    }
                    flag = true;
                } else {
                    listArr.get(j).add("0");
                    flag = false;
                }
            }
            if (flag)
                k++;
        }
        return listArr;
    }
}
