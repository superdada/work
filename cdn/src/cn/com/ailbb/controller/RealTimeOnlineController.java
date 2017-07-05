package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.RealTimeOnlineData1;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.server.dao.DataCenterDao;
import cn.com.ailbb.server.dao.PfcsnapshotDao;
import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.Pfcsnapshot;
import cn.com.ailbb.service.PerformanceSnapshotService;
import cn.com.ailbb.service.RealTimeOnlineService;
import cn.com.ailbb.util.ExcelUtil;
import cn.com.ailbb.util.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by WildMrZhang on 2017/03/06.
 */
// 该注解表示，此类是一个controller类。会被spring扫描到
@Controller
@RequestMapping("/realtimeonline.do")
public class RealTimeOnlineController {
    private Logger logger = Logger.getLogger(RealTimeOnlineController.class); // log4j日志

    @Resource
    private RealTimeOnlineService realTimeOnlineService;
    /**
     * 报表数据请求
     * @param request
     * @param response
     *
     */
    @RequestMapping(params = "method=request1")
    public void request1(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> params=new HashMap<String,String>(){{
            put("cache_cdn",request.getParameter("cache_cdn")==null?null:request.getParameter("cache_cdn").toUpperCase());
        }};
        try {
            HttpHandler.send(response, new Result(true, realTimeOnlineService.handler1(params)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 报表数据请求
     * @param request
     * @param response

    m2_param : {
        index : 'healthy',//枚举值 : 'healthy', 'hit_rate', 'data_rate'
        IPtype : 'data_center',//枚举值 : 'data_center', 'user'
    }
     */
    @RequestMapping(params = "method=request2")
    public void request2(HttpServletRequest request, HttpServletResponse response) {
        try {
//            String index = request.getParameter("index");
//            String IPtype = request.getParameter("IPtype");
//            String cache_cdn=request.getParameter("cache_cdn")!=null?request.getParameter("cache_cdn").toUpperCase():null;
            Map<String,String> params=new HashMap<String,String>(){{
                put("index",request.getParameter("index"));
                put("IPtype",request.getParameter("IPtype"));
                put("cache_cdn",request.getParameter("cache_cdn")!=null?request.getParameter("cache_cdn").toUpperCase():null);
            }};
            HttpHandler.send(response, new Result(true, realTimeOnlineService.handler2(params)));
//            HttpHandler.send(response, new Result(true, realTimeOnlineService.handler2(index,IPtype)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    /**
     * 报表数据请求
     * @param request
     * @param response
     *
    m3_param : {
        index : 'healthy', //枚举值 : 'healthy', 'hit_rate', 'data_rate'
        timeType:'3hours',//枚举值 : '3hours', 'today', '7days', '30days', 'custom'
        startTime:'',//当timeType为'custom'时取值，格式待定
        endTime:'',//当timeType为'custom'时取值，格式待定
    }
     */
    @RequestMapping(params = "method=request3")
    public void request3(HttpServletRequest request, HttpServletResponse response) {
        try {
            String index = request.getParameter("index");
            String timeType = request.getParameter("timeType");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String cache_cdn=request.getParameter("cache_cdn")== null?null:request.getParameter("cache_cdn").toUpperCase();

            Map<String,String> params=new HashMap<String,String>(){{
                put("index",index);
                put("timeType",timeType);
                put("startTime",startTime);
                put("endTime",endTime);
                put("cache_cdn",cache_cdn);
            }};
            HttpHandler.send(response, new Result(true, realTimeOnlineService.handler3(params)));
//            HttpHandler.send(response, new Result(true, realTimeOnlineService.handler3(index, timeType, startTime, endTime)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

//    @RequestMapping(params = "method=request4")
//    public void request4(HttpServletRequest request, HttpServletResponse response){
//        try{
//            String index=request.getParameter("index");
//            String cache_cdn=request.getParameter("cache_cdn")!=null?request.getParameter("cache_cdn").toUpperCase():null;
//            String IPtype=request.getParameter("IPtype");
//            Map<String,String> paramsMap=new HashMap<String,String>(){{
//                            if(null!=request.getParameter("index")){
//                    put("index",request.getParameter("index"));
//                }
//                if(null!=request.getParameter("startTime")&&!"".equals(request.getParameter("startTime").trim())){
//                    put("startTime",request.getParameter("startTime"));
//                }
//                if(null!=request.getParameter("endTime")&&!"".equals(request.getParameter("endTime").trim())){
//                    put("endTime",request.getParameter("endTime"));
//                }
//                if(null!=request.getParameter("IPtype")&&!"".equals(request.getParameter("IPtype").trim())){
//                    put("startTime",request.getParameter("IPtype"));
//                }
//                if(null!=request.getParameter("IPtype")&&!"".equals(request.getParameter("IPtype").trim())){
//                    put("startTime",request.getParameter("IPtype"));
//                }
//                put("cache_cdn",request.getParameter("cache_cdn")!=null?request.getParameter("cache_cdn").toUpperCase():null);
//            }};
//
////            HttpHandler.send(response,new Result(true,realTimeOnlineService.hadndle4(paramsMap)));
//        }catch(Exception e){
//            e.printStackTrace();;
//            HttpHandler.send(response,new Result(false,e.getCause()+e.getMessage()));
//        }
//    }

}
