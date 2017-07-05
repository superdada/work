package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.pojo.Pfcsnapshot;
import cn.com.ailbb.server.pojo.System;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface PfcsnapshotMapper {

    @Select("SELECT * FROM cdn_SNAPSHOT_v2 WHERE ID = #{id} and DIMENSION = #{dimension} and TIME = #{time}" +
            "and CACHE_CDN = #{cache_cdn} and DATASOURCE = #{datasource} and directory_level = #{directory_level} and `INDEX` = #{index}")
    Pfcsnapshot getData(@Param("id") String id,@Param("datasource") String datasource,@Param("dimension") String dimension,
                        @Param("cache_cdn") String cache_cdn, @Param("directory_level") String directory_level,@Param("time") String time,
                        @Param("index") String index);

    @Select("SELECT * FROM cdn_SNAPSHOT_v2 WHERE ID = #{id} and DIMENSION = #{dimension} and TIME = #{time}" +
            "and CACHE_CDN = #{cache_cdn} and DATASOURCE = #{datasource} and directory_level = #{directory_level} and `INDEX` = #{index}")
    Pfcsnapshot getDimensionIdData(@Param("id") String id,@Param("datasource") String datasource,@Param("dimension") String dimension,
                                   @Param("cache_cdn") String cache_cdn, @Param("directory_level") String directory_level,@Param("time") String time,
                                   @Param("index") String index);

    @Select("DELETE FROM cdn_SNAPSHOT_v2 WHERE ID = #{id} and DIMENSION = #{dimension} and TIME = #{time}" +
            "and CACHE_CDN = #{cache_cdn} and DATASOURCE = #{datasource} and directory_level = #{directory_level} and `INDEX` = #{index}")
    void deleteData(@Param("id") String id,@Param("datasource") String datasource,@Param("dimension") String dimension,
                    @Param("cache_cdn") String cache_cdn, @Param("directory_level") String directory_level,@Param("time") String time,
                    @Param("index") String index);

    @Select("INSERT INTO cdn_SNAPSHOT_v2 VALUES(#{id},#{datasource},#{dimension},#{cache_cdn},#{directory_level},#{time},#{index})")
    void insertData(@Param("id") String id,@Param("datasource") String datasource,@Param("dimension") String dimension,
                    @Param("cache_cdn") String cache_cdn, @Param("directory_level") String directory_level,@Param("time") String time,
                    @Param("index") String index);
}
