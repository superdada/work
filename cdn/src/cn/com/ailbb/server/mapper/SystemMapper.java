package cn.com.ailbb.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import cn.com.ailbb.server.pojo.System;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface SystemMapper {

    @Select("SELECT * FROM cdn_SYSTEM")
    System getInfo();

    @Select("UPDATE cdn_SYSTEM SET " + "VISITS = #{visits}")
    System upDateVisits( @Param("visits") long visits );

    @Select("UPDATE cdn_SYSTEM SET VERSION = #{version}")
    System upDateVersion( @Param("version") double version );

    @Select("UPDATE cdn_SYSTEM SET LANGUAGE_TYPE = #{languageType}")
    System upDateLanguageType( @Param("languageType") String languageType );

    @Select("UPDATE cdn_SYSTEM SET MIN_ACCESS_TIME = #{minAccessTime},")
    System upDateMinAccessTime( @Param("minAccessTime") int minAccessTime );

    @Select("UPDATE cdn_SYSTEM SET MIN_HANDLER_TIME = #{minHandlerTime} ")
    System upDateMinHandlerTime( @Param("minHandlerTime") int minHandlerTime );
}
