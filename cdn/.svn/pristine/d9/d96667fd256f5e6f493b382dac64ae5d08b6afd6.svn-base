package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.dao.MenuInfoDao;
import cn.com.ailbb.server.mapper.MenuInfoMapper;
import cn.com.ailbb.server.pojo.MenuInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xzl on 2017/5/22.
 */
@Service(value="MenuInfoDao")
public class MenuDaoInfoImpl implements MenuInfoDao {

    @Resource
    private MenuInfoMapper menuInfoMapper;

    public List<MenuInfo> getMenuList(List<Integer>menulist){
        return  menuInfoMapper.getMenuList(menulist);
    };
}
