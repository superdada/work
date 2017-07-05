package cn.com.ailbb.controller;

/**
 * Created by xzl on 2017/5/25.
 */

import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.util.DataUtil;
import cn.com.ailbb.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.ailbb.server.service.UserRightsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/usergroupinfo.do")
public class UserGroupController {

    @Resource
   UserRightsService userRightsService;

    @RequestMapping(params = "method=request1")
    public void getUserGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
        userRightsService.getUserGroupInfo();
        try {
            HttpHandler.send(response, new Result(true, (Object)(userRightsService.getUserGroupInfo())));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request2")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int osid =  DataUtil.StringToInt(request.getParameter("id"));
        try {
            HttpHandler.send(response, new Result(true, (userRightsService.getUserInfo(osid))));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request3")
    public void addUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, (userRightsService.addUserInfo(map))));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request4")
    public void editUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, (userRightsService.editUserInfo(map))));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request5")
    public void deleteUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = request.getParameter("username");
        int osid = DataUtil.StringToInt(request.getParameter("osid"));
        userRightsService.deleteUserInfo(username);
        try {
            HttpHandler.send(response, new Result(true, (userRightsService.getUserInfo(osid))));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request6")
    public void getMenuInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
       // String ugroupname = request.getParameter("name");
        //int groupid =userRightsService.getGroupIdByName(ugroupname);
        int groupid = DataUtil.StringToInt(request.getParameter("id"));
        try {
            HttpHandler.send(response, new Result(true,(Object)(userRightsService.getMenuInfo(groupid))));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request7")
    public void editUserPassswd(HttpServletRequest request, HttpServletResponse response) throws Exception{

        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true,userRightsService.modifypasswd(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
    @RequestMapping(params = "method=request8")
    public void editMenuRights(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true,userRightsService.editMenuRights(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request9")
    public void getOsinfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            HttpHandler.send(response, new Result(true, (Object)userRightsService.getOsInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request10")
    public void allocateUserGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.allocateUserGroup(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request11")
    public void modifyOsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.modifyOsInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request12")
    public void deleteOsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.deleteOsInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request13")
    public void addOsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.addOsInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request14")
    public void addGroupInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.addGroupInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request15")
    public void editGroupInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.editGroupInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }

    @RequestMapping(params = "method=request16")
    public void removeGroupInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map map = RequestUtil.getMapByRequest(request);
        try {
            HttpHandler.send(response, new Result(true, userRightsService.removeGroupInfo(map)));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHandler.send(response, new Result(false, e.getCause() + e.getMessage()));
        }
    }
}
