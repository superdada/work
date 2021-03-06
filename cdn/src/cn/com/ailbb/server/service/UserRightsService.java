package cn.com.ailbb.server.service;

import cn.com.ailbb.obj.Result;
import cn.com.ailbb.server.model.Node;
import cn.com.ailbb.server.pojo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/21.
 */
public interface UserRightsService {

   Result doLogin(Map<String,Object> params);

    List<MenuInfo> getUserRightsAndMenu(int groupid);

    Result doLoginout();

    String getUserGroupInfo();

    String changedatatree(HashMap nodeList);

    List<Map<String,String>> getUserInfo(int groupid);

    Map  addUserInfo(Map map);

    Map editUserInfo(Map map);

    void deleteUserInfo(String username);

    String getMenuInfo(int groupid);

    String changedatatreeforrights(List<MenuInfo> menuinfo,List<GroupRights> groupinfo);

    int getGroupIdByName(String groupname);

    String  modifypasswd(Map map);

    String editMenuRights(Map map);

    String getOsInfo();

    String allocateUserGroup(Map map);

    String modifyOsInfo(Map map);

    String deleteOsInfo(Map map);

     OsInfo addOsInfo(Map map);

     String addGroupInfo(Map map);

     String editGroupInfo(Map map);

     String removeGroupInfo(Map map);
}
