package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.mapper.GroupInfoMapper;
import cn.com.ailbb.server.pojo.GroupInfo;

import cn.com.ailbb.server.dao.GroupInfoDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xzl on 2017/5/20.
 */
@Service(value="groupInfoDao")
public class GroupInfoDaoImpl implements  GroupInfoDao {

    @Resource
    private GroupInfoMapper GroupInfoMapper;

    @Override
    public List<GroupInfo> getGroupinfo(){
       return GroupInfoMapper.getGroupinfo();
    }
    @Override
   public void insertGroupinfo(GroupInfo grougpinfo) throws DataAccessException {
        GroupInfoMapper.insertGroupinfo(grougpinfo.getUgroupid(),
                grougpinfo.getUgroupname(),grougpinfo.getUparentgroupid(),grougpinfo.getUsernum());
    }
    @Override
   public void updateGroupinfo( int  ugroupid,  String  usernum){
        GroupInfoMapper.updateGroupinfo(ugroupid,usernum);
    }
    @Override
    public void  updateGroupNameByid(int  ugroupid,  String  ugroupname) throws DataAccessException {
        GroupInfoMapper.updateGroupNameById(ugroupid,ugroupname);
    }
    @Override
   public void deleteGroupinfo(int  ugroupid)  throws DataAccessException {
        GroupInfoMapper.deleteGroupinfo(ugroupid);
    }

    @Override
    public int getGroupIdByName(String  name){
        return  GroupInfoMapper.getGroupIdByName(name);
    }

    @Override
    public String getGroupNameById(int  ugroupid){
        return  GroupInfoMapper.getGroupNameById(ugroupid);
    }

}
