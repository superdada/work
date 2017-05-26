package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.ItemData;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.server.dao.DataCenterDao;
import cn.com.ailbb.server.dao.PfcsnapshotDao;
import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.Pfcsnapshot;
import cn.com.ailbb.service.PerformanceSnapshotService;
import cn.com.ailbb.util.ExcelUtil;
import cn.com.ailbb.util.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by WildMrZhang on 2017/03/06.
 */
// 该注解表示，此类是一个controller类。会被spring扫描到
@Controller
@RequestMapping("/pfcsnapshot.do")
public class PerformanceSnapshotController {
    private Logger logger = Logger.getLogger(PerformanceSnapshotController.class); // log4j日志

    @Resource
    private DataCenterDao dataCenterDao;
    @Resource
    private PerformanceSnapshotService performanceSnapshotService;
    @Resource
    private PfcsnapshotDao pfcsnapshotDao;

    /**
     * 报表数据请求
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=request1")
    public void request1(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dimension = request.getParameter("dimension"); // 维度
            String index = request.getParameter("index"); // 指标
            Map<String,Object> params=new HashMap<String,Object>(){{
                put("index",index);
                put("dimension",dimension);
                put("cache_cdn",request.getParameter("cache_cdn")==null||"ALL".equals(request.getParameter("cache_cdn").toUpperCase())?"ALL":request.getParameter("cache_cdn").toUpperCase());
            }};

            HttpHandler.send(response, new Result(true,
                    performanceSnapshotService.dataHandler(performanceSnapshotService.queryData(params), dimension, index, false)));
//            HttpHandler.send(response, new Result(true,
//                    performanceSnapshotService.dataHandler(performanceSnapshotService.queryData(dimension, index, null, null), dimension, index, false)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 报表弹出框数据请求
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=request2")
    public void request2(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String[]> params=new HashMap(request.getParameterMap());
        String[] hours={"1"};
        params.put("hour",hours);
//        HttpHandler.send(response, new Result(true, performanceSnapshotService.queryDataList(dimension, type, id, TimeUtil.DateToMinDateText(new Date(TimeUtil.MinTextToDate(time).getTime() + 1*60*60*1000)), 1)));
        HttpHandler.send(response, new Result(true, performanceSnapshotService.queryDataList(params)));
    }

    /**
     * 告警监控请求
     * 发送邮件至指定邮箱
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=request3")
    public void request3(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            String dimension = request.getParameter("dimension");
            boolean alarmFlag = Boolean.valueOf(request.getParameter("alarm_flag"));
            boolean monitorFlag = Boolean.valueOf(request.getParameter("monitor_flag"));

            DataCenterStatus dataCenterStatus = dataCenterDao.getCenterStatus(id, dimension);
            if(null == dataCenterStatus) {
                dataCenterDao.insertDataCenter(id, dimension, monitorFlag, alarmFlag);
            } else {
                dataCenterStatus.setMonitorFlag(alarmFlag);
                dataCenterStatus.setMonitorFlag(monitorFlag);
                dataCenterDao.updateDataCenterStatus(id, dimension, monitorFlag, alarmFlag);
            }
            HttpHandler.send(response, new Result(true, "修改数据成功"));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 导出请求
     * 将数据导出为Excel
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=request4")
    public void request4(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Map<String,String[]> params=request.getParameterMap();
            Map<String,String[]> params= new HashMap<String,String[]>();
            params.put("hour",new String[]{"24"} );
            params.put("dimension",new String[]{request.getParameter("dimension")});
            params.put("time",new String[]{request.getParameter("time")});
            params.put("id",new String[]{request.getParameter("id")});
            params.put("fileName",new String[]{request.getParameter("fileName")});
            params.put("cache_cdn",new String[]{request.getParameter("cache_cdn")});
            String dimension = request.getParameter("dimension");
            String id = request.getParameter("id");
            String time = request.getParameter("time");
           // String type = request.getParameter("type");

            String path = ExcelUtil.getPath();
            String fileName = TimeUtil.getNowFormatText();
            String url = null;
            for(int t : new int[]{1,2,3}) {
//                url = ExcelUtil.exportToExcel(path, fileName,
//                        performanceSnapshotService.getTable(dimension, t), performanceSnapshotService.queryDataList(dimension, t, id, time, 24), type);
                params.put("type",new String[]{String.valueOf(t)});
                List<Map<String,Object>> data =  performanceSnapshotService.queryDataList(params);
                url = ExcelUtil.exportToExcel(path, fileName,
                        performanceSnapshotService.getTable(dimension, t), data, null);
            }
            HttpHandler.send(response, new Result(true, "成功", url.substring(1,url.length())));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 分享连接请求
     * @param request
     * @param response
     * 生成一个免登陆链接
     */
    @RequestMapping(params = "method=request5")
    public void request5(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id"); // 维度Id
            String dimension = request.getParameter("dimension"); // 维度
            String index = request.getParameter("index"); // 指标
            String time = request.getParameter("time"); // 时间
            String cache_cdn = request.getParameter("cache_cdn");
            boolean isCanCance = false; // 默认不能取消分享

            if(null == id) {
                HttpHandler.send(response, new Result(false, "没有ID，不能分享！"));
            } else {
                Pfcsnapshot pfcsnapshot = pfcsnapshotDao.getPfcsnapshotUserDimensionId(id);
                if(null == pfcsnapshot || !isCanCance) {
                    long randomId = System.currentTimeMillis();
                    pfcsnapshotDao.insertData(randomId, dimension, index, cache_cdn,time, id);
                    HttpHandler.send(response, new Result(true, "分享成功！", String.format("web/module/cdn/fenxiang/index.jsp?dimension=%s&index=%s&id=%s&cache_cdn=%s", dimension, index, randomId,cache_cdn)));;
                } else {
                    pfcsnapshotDao.deleteData(pfcsnapshot.getId());
                    HttpHandler.send(response, new Result(true, "取消分享成功！"));;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 导出请求
     * 将数据导出为Excel
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=request6")
    public void request6(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dimension = request.getParameter("dimension");
            String id = request.getParameter("id");
            String time = request.getParameter("time");
            String cache_cdn = request.getParameter("cache_cdn");
            int count = 0;

            for(int t : new int[]{1,2,3})
                count +=  performanceSnapshotService.countData(dimension,cache_cdn, t, id, time, 24);

            HttpHandler.send(response, new Result(true, "提示", String.format("共有 [ %s ] 条数据，大约需要：[ %s ] 秒，是否继续下载？", count, Math.round(count * 0.004) + 3)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 分享连接请求
     * @param request
     * @param response
     * 生成一个免登陆链接
     */
    @RequestMapping(params = "method=request7")
    public void request7(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id"); // 分享Id
        try {
            if(null == id) {
                HttpHandler.send(response, new Result(false, "未获取到该Id：" + id));
            } else {
                long request_id = Long.parseLong(id);
                Pfcsnapshot pfcsnapshot = pfcsnapshotDao.getPfcsnapshot(request_id);
                if(null == pfcsnapshot) {
                    HttpHandler.send(response, new Result(false, "未获取到该Id：" + id));
                } else {
                    Map params=new HashMap<>();
                    params.put("dimension",pfcsnapshot.getDimension());
                    params.put("index",pfcsnapshot.getIndex());
                    params.put("time",pfcsnapshot.getTime());
                    params.put("dimension_id",pfcsnapshot.getDimensionId());
                    params.put("cache_cdn",pfcsnapshot.getCacheCdn());

                    HttpHandler.send(response, new Result(true,
                            performanceSnapshotService.transData(performanceSnapshotService.queryData(params))));
//                    HttpHandler.send(response, new Result(true,
//                            performanceSnapshotService.transData(performanceSnapshotService.queryData(pfcsnapshot.getDimension(), pfcsnapshot.getIndex(), pfcsnapshot.getTime(), pfcsnapshot.getDimensionId()))));
                }
            }
        } catch (Exception e) {
            HttpHandler.send(response, new Result(false, "获取分享失败！：" + id));
        }
    }

}
