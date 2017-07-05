package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.pojo.GroupRights;
import cn.com.ailbb.server.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.*;

/**
 * Created by xzl on 2017/5/20.
 */
public interface GroupRightsMapper {
    @Select("SELECT * FROM cdn_ugrouprights WHERE ugroupid = #{ugroupid}")
   List<GroupRights> getGroupRights(@Param("ugroupid") int ugroupid);

    @Select("insert into cdn_ugrouprights(ugroupid,ugroupname,menuid,menuname)" +
            "values ( #{ugroupid},#{ugroupname},#{menuid},#{menuname})")
    void insertGroupRights(@Param("ugroupid") int  ugroupid, @Param("ugroupname") String  ugroupname,
                              @Param("menuid") int menuid, @Param("menuname") String  menuname);

    @Select("delete from cdn_ugrouprights WHERE ugroupid = #{ugroupid} and menuid =#{menuid} ")
    void deleteGroupRights(@Param("ugroupid") int  ugroupid, @Param("menuid") int menuid);

    @Select("delete from cdn_ugrouprights WHERE ugroupid = #{ugroupid}")
    void deleteGroupRightsById(@Param("ugroupid") int  ugroupid);

    @Select("update data2.cdn_ugrouprights set ugroupname = #{ugroupname} where ugroupid = #{ugroupid}")
    void updateGroupnameById(@Param("ugroupid") int  ugroupid,@Param("ugroupname") String  ugroupname);
}
