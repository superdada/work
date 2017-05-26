package cn.com.ailbb.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import cn.com.ailbb.server.Provider.MenuInfoSqlProvider;
import cn.com.ailbb.server.pojo.MenuInfo;
import java.util.*;
/**
 * Created by xzl on 2017/5/22.
 */
public interface MenuInfoMapper {

    @SelectProvider(type=MenuInfoSqlProvider.class,
            method="getMenuById")
    List<MenuInfo> getMenuList(List<Integer>menulist);
}
