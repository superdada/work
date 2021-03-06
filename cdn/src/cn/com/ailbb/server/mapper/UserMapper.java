package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.Provider.MenuInfoSqlProvider;
import cn.com.ailbb.server.Provider.UserInfoSqlProvider;
import cn.com.ailbb.server.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import  java.sql.Timestamp;
import java.util.*;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface UserMapper {

    @Select("SELECT * FROM cdn_userinfo WHERE USERNAME = #{userName}")
    User getUser(@Param("userName") String userName);

    @Select("DELETE FROM cdn_userinfo WHERE USERNAME = #{userName}")
    User deleteUser(@Param("userName") String userName);

    @Select("UPDATE cdn_userinfo SET password = #{password} where userName =#{userName} ")
    void modifyUserPasswd(@Param("password") String password,@Param("userName") String userName);

    @Select("UPDATE cdn_userinfo SET " +
            "STATUS = #{status}, " +
            "REMEMBER_ME = #{rememberMe}, " +
            "BEFORE_LOGIN_TIME = #{beforeLoginTime}, " +
            "LAST_LOGIN_TIME = #{lastLoginTime}, " +
            "BEFORE_LOGIN_HOST = #{beforeLoginHost}, " +
            "LAST_LOGIN_HOST = #{lastLoginHost} " +
            "WHERE USERNAME = #{userName}")
    void updateUser(@Param("userName") String userName,
                    @Param("status") int status,
                    @Param("rememberMe") boolean rememberMe,
                    @Param("beforeLoginTime") Timestamp beforeLoginTime,
                    @Param("lastLoginTime") Timestamp lastLoginTime,
                    @Param("beforeLoginHost") String beforeLoginHost,
                    @Param("lastLoginHost") String lastLoginHost);


    @UpdateProvider(type=UserInfoSqlProvider.class,
            method="updatePartUser")
    void updatePartUser(Map<String,Object> map);


  @Select("insert into cdn_userinfo(username,password,sex,name,ugroupid,osid," +
          "status,tel,level,REMEMBER_ME,CREATE_TIME,BEFORE_LOGIN_TIME,LAST_LOGIN_TIME,BEFORE_LOGIN_HOST,LAST_LOGIN_HOST) " +
          "values ( #{userName}, #{password},#{sex},#{name},#{ugroupid},#{osid},#{status},#{tel},#{level},#{rememberMe},#{createTime}," +
          "#{beforeLoginTime},#{lastLoginTime},#{beforeLoginHost},#{lastLoginHost})")
    void insertUser(@Param("userName") String userName,
                    @Param("password") String password,
                    @Param("sex") int sex,
                    @Param("name") String name,
                    @Param("ugroupid") int ugroupid,
                    @Param("osid") int osid,
                    @Param("status") int status,
                    @Param("tel") String tel,
                    @Param("level") int level,
                    @Param("rememberMe") boolean rememberMe,
                    @Param("createTime") Timestamp createTime,
                    @Param("beforeLoginTime") Timestamp beforeLoginTime,
                    @Param("lastLoginTime") Timestamp lastLoginTime,
                    @Param("beforeLoginHost") String beforeLoginHost,
                    @Param("lastLoginHost") String lastLoginHost);

    @Select("SELECT * FROM cdn_userinfo WHERE osid = #{osid}")
    List<User> getUserByOsId( @Param("osid") int Osid);
}


