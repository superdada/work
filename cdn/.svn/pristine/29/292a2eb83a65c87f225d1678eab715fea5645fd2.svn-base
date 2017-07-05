package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.pojo.OsInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xzl on 2017/5/31.
 */
public interface OsInfoMapper {

    @Select("SELECT * FROM data2.cdn_osinfo")
    List<OsInfo> getOsinfo();

    @Select("UPDATE data2.cdn_osinfo SET osname = #{osname} where osid =  #{osid}")
    void updateOsnameById(@Param("osid") int osid,@Param("osname") String osname);

    @Select("UPDATE data2.cdn_osinfo SET osparentname = #{osparentname} where osparentid =  #{osparentid}")
    void updateOsParentnameById(@Param("osparentid") int osparentid,@Param("osparentname") String osparentname);

    @Select("delete from data2.cdn_osinfo where osid =  #{osid}")
    void  deleteOsnameById(@Param("osid") int osid);

    @Select("delete from data2.cdn_osinfo where osparentid =  #{osparentid}")
    void  deleteOsnameByparnentId(@Param("osparentid") int osparentid);


    @Select("select count(*) from data2.cdn_osinfo where osparentid =  #{osparentid}")
    int getsubnode(@Param("osparentid") int osparentid);

    @Select("INSERT INTO data2.cdn_osinfo VALUES(#{osid},#{osname},#{osparentid},#{osparentname})")
    void insertOsinfo(@Param("osid") int osid, @Param("osname") String osname,
                      @Param("osparentid") int osparentid,@Param("osparentname") String osparentname);
}
