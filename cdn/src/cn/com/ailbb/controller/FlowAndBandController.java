package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.service.AnalysisReportService;
import cn.com.ailbb.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
@Controller
@RequestMapping("/flowandband.do")
public class FlowAndBandController {

    @Resource
    private AnalysisReportService analysisReportService;

    @RequestMapping(params = "method=request1")
    public void getFlowChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, analysisReportService.getFlowChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
}
