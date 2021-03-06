package cn.com.ailbb.handler;

import cn.com.ailbb.controller.PerformanceSnapshotController;
import cn.com.ailbb.manage.SystemManage;
import cn.com.ailbb.server.dao.SystemDao;
import cn.com.ailbb.service.PerformanceSnapshotService;
import cn.com.ailbb.util.SQLUtil;
import cn.com.ailbb.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
@Service
public class AlarmHandler {
    public static int MinHandlerTime = 1000*60*60; // 最小请求数据库系统版本信息

    @Resource
    private PerformanceSnapshotService performanceSnapshotService;
    /**
     * 开启循环告警
     */
    public void startHandler(){
        new Thread(() -> {
            try {
                String[] dimensions = new String[]{"data_center", "ICP","service_type"};
                String[] indexs = new String[]{"healthy", "data_rate", "hit_rate"};
                String[] cache_cdn = new String[]{"ALL", "CDN", "CACHE"};
                for(int i=0; i<dimensions.length; i++) {
                    for (int j = 0; j < indexs.length; j++) {
                        for (int k = 0; k < cache_cdn.length; k++) {
                            Map params = new HashMap();
                            params.put("dimension", dimensions[i]);
                            params.put("index", indexs[j]);
                            params.put("cache_cdn", cache_cdn[k]);
                            //  performanceSnapshotService.dataHandler(performanceSnapshotService.queryData(dimensions[i], indexs[j], null, null), dimensions[i], indexs[j], true);
                            //tmp!!!WZY performanceSnapshotService.dataHandler(performanceSnapshotService.queryData(params), dimensions[i], indexs[j], true);
                        }
                    }
                }
                Thread.sleep(MinHandlerTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
