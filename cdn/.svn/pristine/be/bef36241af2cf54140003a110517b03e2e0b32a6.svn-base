package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.mapper.GroupInfoMapper;
import cn.com.ailbb.server.pojo.GroupInfo;

import cn.com.ailbb.server.dao.GroupInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xzl on 2017/5/20.
 */
@Service(value="groupInfoDao")
public class GroupInfoDaoImpl implements  GroupInfoDao {

    @Resource
    private GroupInfoMapper GroupInfoMapper;

   public void insertGroupinfo(GroupInfo grougpinfo){
        GroupInfoMapper.insertGroupinfo(grougpinfo.getUgroupid(),grougpinfo.getUgroupname(),grougpinfo.getUparentgroupid(),grougpinfo.getUsernum());
    }

   public void updateGroupinfo( int  ugroupid,  String  usernum){
        GroupInfoMapper.updateGroupinfo(ugroupid,usernum);
    }

   public void deleteGroupinfo(int  ugroupid){
        GroupInfoMapper.deleteGroupinfo(ugroupid);
    }
}
