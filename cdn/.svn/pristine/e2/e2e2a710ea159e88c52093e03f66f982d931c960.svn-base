package cn.com.ailbb.server.dao;


import cn.com.ailbb.server.pojo.OsInfo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by xzl on 2017/5/31.
 */
public interface OsInfoDao {

    List<OsInfo> getOsinfo();

    void updateOsnameById(int osid,String osname) throws DataAccessException;

    void  updateOsParentnameById(int osparentid,String osparentname);

    void deleteOsInfo(int osid);

    void  deleteOsnameByparnentId(int osparentid);

    int getsubnode(int osparentid);

    void insertinfo(OsInfo osinfo);
}
