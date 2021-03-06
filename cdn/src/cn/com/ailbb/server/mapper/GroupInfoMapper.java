package cn.com.ailbb.server.mapper;

import org.apache.ibatis.annotations.Param;
import cn.com.ailbb.server.pojo.GroupInfo;
import org.apache.ibatis.annotations.Select;
import java.util.*;

/**
 * Created by xzl on 2017/5/20.
 */
public interface GroupInfoMapper {
    @Select("select * from cdn_ugroupinfo")
    List<GroupInfo> getGroupinfo();

    @Select("select ugroupid from cdn_ugroupinfo where ugroupname = #{ugroupname}")
    int getGroupIdByName(@Param("ugroupname") String name);

    @Select("select ugroupname from cdn_ugroupinfo where ugroupid = #{ugroupid}")
    String getGroupNameById(@Param("ugroupid") int ugroupid);

    @Select("insert into cdn_ugroupinfo (ugroupid,ugroupname,uparentgroupid,usernum)" +
            "values ( #{ugroupid},#{ugroupname},#{uparentgroupid},#{usernum})")
    void insertGroupinfo(@Param("ugroupid") int  ugroupid,@Param("ugroupname") String  ugroupname,
                         @Param("uparentgroupid") int  uparentgroupid, @Param("usernum") int  usernum);

   @Select("update cdn_ugroupinfo set usernum= #{usernum}　WHERE ugroupid = #{ugroupid}")
    void updateGroupinfo(@Param("ugroupid") int  ugroupid,@Param("usernum") String  usernum);

    @Select("update cdn_ugroupinfo set ugroupname = #{ugroupname} where ugroupid = #{ugroupid}")
    void updateGroupNameById(@Param("ugroupid") int  ugroupid,@Param("ugroupname") String  ugroupname);

    @Select("delete from cdn_ugroupinfo where ugroupid = #{ugroupid}")
    void deleteGroupinfo(@Param("ugroupid") int  ugroupid);

}
