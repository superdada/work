package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.MenuInfo;

import java.util.List;

/**
 * Created by xzl on 2017/5/22.
 */
public interface MenuInfoDao {

    List<MenuInfo> getMenuList(List<Integer>menulist);
    List<MenuInfo> getAllMenuList();
}
