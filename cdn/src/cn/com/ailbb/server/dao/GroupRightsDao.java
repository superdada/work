package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.GroupRights;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.*;

/**
 * Created by xzl on 2017/5/21.
 */
public interface GroupRightsDao {
    List<GroupRights> getGroupRights(int ugroupid);

    void insertGroupRights(int  ugroupid,String  ugroupname, int menuid,  String  menuname);

    void deleteGroupRights( int  ugroupid, int menuid);

    void deleteGroupRightsById( int  ugroupid) throws DataAccessException;

    void  updateGroupnameById(int  ugroupid, String  ugroupname) throws DataAccessException;

}
