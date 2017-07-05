package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.mapper.GroupRightsMapper;
import cn.com.ailbb.server.dao.GroupRightsDao;
import cn.com.ailbb.server.pojo.GroupRights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;

/**
 * Created by xzl on 2017/5/21.
 */
@Service(value="groupRightsDao")
public class GroupRightsDaoImpl implements GroupRightsDao {

    @Resource
    private GroupRightsMapper GroupRightsMapper;

    public List<GroupRights> getGroupRights(int ugroupid){
        return  GroupRightsMapper.getGroupRights(ugroupid);
    }

    public void insertGroupRights(int  ugroupid,String  ugroupname, int menuid,  String  menuname){
        GroupRightsMapper.insertGroupRights(ugroupid,ugroupname,menuid,menuname);
    }

   public void deleteGroupRights( int  ugroupid, int menuid){
        GroupRightsMapper.deleteGroupRights(ugroupid,menuid);
    }

    public void deleteGroupRightsById( int  ugroupid) throws DataAccessException{
        GroupRightsMapper.deleteGroupRightsById(ugroupid);
    }

    public void  updateGroupnameById(int  ugroupid, String  ugroupname) throws DataAccessException {

        GroupRightsMapper.updateGroupnameById(ugroupid,ugroupname);

    }

}
