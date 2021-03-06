package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.service.AnalysisReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.ailbb.util.RequestUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
/**
 * Created by xzl on 2017/5/16.
 */
@Controller
@RequestMapping("/analyreport.do")
public class AnalysisReportController {

    @Resource
    private AnalysisReportService analysisReportService;

    @RequestMapping(params = "method=request1")
    public void getAnalysisChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request2")
    public void getConditionEnum(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getConditionEnum(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request3")
    public void getConditionTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getConditionTime(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
    @RequestMapping(params = "method=request4")
    public void getSearchResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getSearchResult(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }


    @RequestMapping(params = "method=request5")
    public void getMultiSearchResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getMultiSearchResult(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }


}
