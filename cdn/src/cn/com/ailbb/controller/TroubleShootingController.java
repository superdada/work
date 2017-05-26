package cn.com.ailbb.controller;

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.ailbb.service.TroubleShootingService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/17.
 */
@Controller
@RequestMapping("/trobleshoot.do")
public class TroubleShootingController {
    @Resource
    TroubleShootingService  troubleShootingService;
    @RequestMapping(params = "method=request1")
    public void getTroubleData(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);

        try {
            HttpHandler.send(response, new Result(true, troubleShootingService.getErrCodeRate(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
}
