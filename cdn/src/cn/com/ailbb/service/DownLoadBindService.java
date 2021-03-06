package cn.com.ailbb.service;

import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
public interface DownLoadBindService {

    Map<String, List> getDownloadChartData(Map map) throws Exception;
    Map<String, Object> getDownloadICPChartData(Map map) throws Exception;
    Map<String, List> getSubDownloadChartData(Map map) throws Exception;
    Map<String, Object>getDownLoadBindChartData(Map map)throws Exception;
    Map<String, Map<String, Object>> getSubDownloadICPChartData(Map map) throws Exception;
}
