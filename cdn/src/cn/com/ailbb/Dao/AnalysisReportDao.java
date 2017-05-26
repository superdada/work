package cn.com.ailbb.Dao;

import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * Created by xzl on 2017/5/16.
 */
public interface AnalysisReportDao {

    List<Map<String, Object>> getPerformanceData(Map map) throws Exception;

    List<Map<String, Object>> getConditionEnum(Map map) throws Exception;

    List<Map<String, Object>> getFlowChartData(Map map) throws Exception;

    String getTimeSelPart(int timeType);
}
