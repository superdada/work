package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.service.DownLoadBindService;
import cn.com.ailbb.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
@Controller
@RequestMapping("/downloadbind.do")
public class DownLoadBindController {

    @Resource
   DownLoadBindService downLoadBindService;


    @RequestMapping(params = "method=request1")
    public void getDownloadChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, downLoadBindService.getDownloadChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }


    @RequestMapping(params = "method=request2")
    public void getDownloadICPChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, downLoadBindService.getDownloadICPChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
    @RequestMapping(params = "method=request3")
    public void getSubDownloadChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, downLoadBindService.getSubDownloadChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request4")
    public void getSubDownloadICPChartData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, downLoadBindService.getSubDownloadICPChartData(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }


}
