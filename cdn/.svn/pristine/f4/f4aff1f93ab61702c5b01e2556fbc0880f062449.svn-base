package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.dao.OsInfoDao;
import cn.com.ailbb.server.mapper.OsInfoMapper;
import cn.com.ailbb.server.pojo.OsInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xzl on 2017/5/31.
 */
@Service(value="OsInfoDao")
public class OsInfoDaoImpl implements OsInfoDao {

    @Resource
    private OsInfoMapper osInfoMapper;

    @Override
   public List<OsInfo> getOsinfo(){
         return osInfoMapper.getOsinfo();
   }
    @Override
    public  void updateOsnameById(int osid,String osname) throws DataAccessException {
        osInfoMapper.updateOsnameById(osid,osname);
    }

    public  void  updateOsParentnameById(int osparentid,String osparentname) throws DataAccessException {
        osInfoMapper.updateOsParentnameById(osparentid,osparentname);
    }
    @Override
    public  void deleteOsInfo(int osid){
        osInfoMapper.deleteOsnameById(osid);
    }

    @Override
    public  void  deleteOsnameByparnentId(int osparentid){
        osInfoMapper.deleteOsnameByparnentId(osparentid);
    }
    @Override
    public int getsubnode(int osparentid){
        return  osInfoMapper.getsubnode(osparentid);
    }
    @Override
    public void insertinfo(OsInfo osinfo){
        osInfoMapper.insertOsinfo(osinfo.getOsid(),osinfo.getOsname(),osinfo.getOsparentid(),osinfo.getOsparentname());
    }
}
