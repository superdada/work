package cn.com.ailbb.controller;

import cn.com.ailbb.enums.Msg;
import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.obj.Token;
import cn.com.ailbb.server.dao.MenuInfoDao;
import cn.com.ailbb.server.dao.UserDao;
import cn.com.ailbb.server.service.UserRightsService;
import cn.com.ailbb.util.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.ailbb.server.pojo.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WildMrZhang on 2016/12/26.
 */
// 该注解表示，此类是一个controller类。会被spring扫描到
@Controller
@RequestMapping("/login.do")
public class LoginController {

   @Resource
    private UserRightsService userRightsService;
    @Autowired
    private MenuInfoDao menuInfoDao;

    @RequestMapping(params = "method=request1")
    public void ajaxLogin(HttpServletRequest request, HttpServletResponse response) {

        //String username = request.getParameter("username");
       // String password = request.getParameter("password");
       // boolean rememberMe = Boolean.getBoolean(request.getParameter("rememberMe"));
       // String host = HttpUtil.getIp(request);
        Map<String,Object> params=new HashMap<String,Object>(){{
            put("username",request.getParameter("username"));
            put("password",request.getParameter("password"));
            put("rememberMe",request.getParameter("rememberMe"));
            put("host", HttpUtil.getIp(request));
        }
        };
        try {
           // ThreadContext.bind(SecurityUtils.getSubject());
          //  Result successresult=userRightsService.doLogin(params);
          //  Session session =SecurityUtils.getSubject().getSession();
          //  SecurityUtils.getSubject().getSession().setAttribute("user",successresult.getData());
            //HttpHandler.send(response, new Result(true, userRightsService.doLogin(params).getMsg(), null));
            HttpHandler.send(response,userRightsService.doLogin(params));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
    @RequestMapping(params = "method=request2")
    public void getMenu(HttpServletRequest request, HttpServletResponse response) {

           User userinfo= (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            int groupid= userinfo.getUgroupid();
        try {
           HttpHandler.send(response, new Result(true,userRightsService.getUserRightsAndMenu(groupid)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        try {
            HttpHandler.send(response, userRightsService.doLoginout());
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

}
