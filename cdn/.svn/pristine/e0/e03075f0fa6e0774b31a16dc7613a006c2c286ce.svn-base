package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.pojo.Pfcsnapshot;
import cn.com.ailbb.server.pojo.System;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface PfcsnapshotMapper {

    @Select("SELECT * FROM cdn_SNAPSHOT WHERE ID = #{id}")
    Pfcsnapshot getData(@Param("id") long id);

    @Select("SELECT * FROM cdn_SNAPSHOT WHERE DIMENSION_ID = #{dimensionId}")
    Pfcsnapshot getDimensionIdData(@Param("dimensionId") String dimensionId);

    @Select("DELETE FROM cdn_SNAPSHOT WHERE ID = #{id}")
    void deleteData(@Param("id") long id);

    @Select("INSERT INTO cdn_SNAPSHOT VALUES(#{id},#{dimension},#{index},#{time},#{dimensionId},#{cache_cdn})")
    void insertData(@Param("id") long id, @Param("dimension") String dimension, @Param("index") String index, @Param("time") String time, @Param("dimensionId") String dimensionId,@Param("cache_cdn") String cache_cdn);
}
