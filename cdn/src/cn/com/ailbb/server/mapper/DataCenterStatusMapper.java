package cn.com.ailbb.server.mapper;

import cn.com.ailbb.server.pojo.DataCenterStatus;
import cn.com.ailbb.server.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface DataCenterStatusMapper {

    @Select("SELECT * FROM conf_data_center_status_v2")
    List<DataCenterStatus> getAllData();

    @Select("SELECT * FROM conf_data_center_status_v2 WHERE ID = #{id} AND datasource = #{datasource} AND type = #{type} AND DIMENSION = #{dimension} AND directory_level = #{directory_level}")
    DataCenterStatus getData(@Param("id") String id,@Param("datasource") String datasource,@Param("type")  String type,@Param("dimension") String dimension,@Param("directory_level") int directory_level);

    @Select("UPDATE conf_data_center_status_v2 SET MONITOR_FLAG = #{monitorFlag}, ALARM_FLAG = #{alarmFlag} WHERE ID = #{id} AND datasource = #{datasource} AND type = #{type} AND DIMENSION = #{dimension} AND directory_level = #{directory_level}")
    void updateData(@Param("id") String id, @Param("datasource") String datasource, @Param("type") String type, @Param("dimension") String dimension,
                    @Param("directory_level") int directory_level, @Param("monitorFlag") boolean monitorFlag, @Param("alarmFlag") boolean alarmFlag);

    @Select("INSERT INTO conf_data_center_status_v2 VALUES(#{id},#{datasource},#{type},#{dimension},#{directory_level},#{monitorFlag},#{alarmFlag})")
    void insertData(@Param("id") String id, @Param("datasource") String datasource, @Param("type") String type, @Param("dimension") String dimension,
                    @Param("directory_level") int directory_level, @Param("monitorFlag") boolean monitorFlag, @Param("alarmFlag") boolean alarmFlag);
}
