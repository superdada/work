package cn.com.ailbb.server.service;

import cn.com.ailbb.enums.Msg;
import cn.com.ailbb.handler.HttpHandler;
import cn.com.ailbb.obj.Result;
import cn.com.ailbb.obj.Token;
import cn.com.ailbb.server.dao.UserDao;
import cn.com.ailbb.server.dao.MenuInfoDao;
import cn.com.ailbb.server.pojo.MenuInfo;
import cn.com.ailbb.server.dao.GroupRightsDao;
import cn.com.ailbb.server.pojo.GroupRights;
import cn.com.ailbb.server.service.UserRightsService;
import cn.com.ailbb.server.pojo.User;
import cn.com.ailbb.util.HttpUtil;
import cn.com.ailbb.util.MD5Util;
import cn.com.ailbb.util.TimeUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import cn.com.ailbb.util.*;

/**
 * Created by xzl on 2017/5/21.
 */
@Service
public class UserRightsServiceImpl implements UserRightsService {

    private Logger logger = Logger.getLogger(UserRightsService.class); // log4j日志

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupRightsDao groupRightsDao;

    @Autowired
    private MenuInfoDao menuInfoDao;

    /**
     * 登陆
     * @param request
     * @param response
     * @return Result
     */
   @Override
   public Result doLogin(Map<String,Object> params) {


        String username = DataUtil.ObjectToString(params.get("username"));
        String password =DataUtil.ObjectToString(params.get("password"));



        User user = userDao.getUserById(username);

        if(null == username) {
            return new Result(false,  Msg.getCode(101));
        } else if(null == password) {
            return new Result(false, Msg.getCode(102));
        } else if(null == user) {
            return new Result(false, Msg.getCode(103));
        } else if(!user.getPassword().equals(MD5Util.encoded(password))) {
            return new Result(false, Msg.getCode(104));
        } else {
            boolean rememberMe = Boolean.getBoolean(DataUtil.ObjectToString(params.get("rememberMe")));
            String host =DataUtil.ObjectToString(params.get("host"));

            user.setBeforeLoginTime(user.getLastLoginTime())
                    .setLastLoginTime(TimeUtil.DateToTimestamp())
                    .setBeforeLoginHost(user.getLastLoginHost())
                    .setLastLoginHost(host)
                    .setRememberMe(rememberMe)
                    .setStatus(2);

            userDao.updateUser(user);
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            SecurityUtils.getSubject().login(new Token(user, username, password, rememberMe, host));

            logger.info(Msg.getCode(105) + host + user);
            //获取用户的权限和菜单
          // List<MenuInfo> menuContent =getUserRightsAndMenu(user.getUgroupid());
            Result result = new Result(true, Msg.getCode(105), "/web/module/cdn/cdn_index.jsp");
            return result;
        }
    }
    @Override
    public Result doLoginout(){
        Subject subject = SecurityUtils.getSubject();
        if(null != subject) {
            subject.logout();
            // logger.info(Msg.getCode(106));
            return  new Result(true, Msg.getCode(106));
            // HttpHandler.send(response, new Result(true, Msg.getCode(106)));
        } else {
            // HttpHandler.send(response, new Result(false, Msg.getCode(107)));
            return  new Result(true, Msg.getCode(107));
        }
    }
    @Override
    public List<MenuInfo> getUserRightsAndMenu(int groupid){

        //获取用户所在的用户组权限
        List<GroupRights> menurights = groupRightsDao.getGroupRights(groupid);
        List<Integer> menulist=new ArrayList<Integer>();
        for(GroupRights gr : menurights){
            menulist.add(gr.getMenuid());
        }
        //根据获得的菜单列表，去菜单表里获取菜单的依赖关系
        List<MenuInfo> menuContent= menuInfoDao.getMenuList(menulist);
      return menuContent;
    }



}
