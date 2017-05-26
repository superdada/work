package cn.com.ailbb.service;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by xzl on 2017/5/16.
 */

public interface AnalysisReportService {

    List<List> getChartData(Map map)  throws Exception;

    List getConditionEnum(Map map) throws Exception;

    List getFlowChartData(Map map) throws Exception;

}