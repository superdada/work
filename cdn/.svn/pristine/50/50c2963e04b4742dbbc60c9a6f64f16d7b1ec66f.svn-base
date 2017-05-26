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

    @Select("SELECT * FROM CONF_DATA_CENTER_STATUS")
    List<DataCenterStatus> getAllData();

    @Select("SELECT * FROM CONF_DATA_CENTER_STATUS WHERE ID = #{id} AND DIMENSION = #{dimension}")
    DataCenterStatus getData(@Param("id") String id, @Param("dimension") String dimension);

    @Select("UPDATE CONF_DATA_CENTER_STATUS SET MONITOR_FLAG = #{monitorFlag}, ALARM_FLAG = #{alarmFlag} WHERE ID = #{id} AND DIMENSION = #{dimension}")
    void updateData(@Param("id") String id, @Param("dimension") String dimension, @Param("monitorFlag") boolean monitorFlag, @Param("alarmFlag") boolean alarmFlag);

    @Select("INSERT INTO CONF_DATA_CENTER_STATUS VALUES(#{id},#{dimension},#{monitorFlag},#{alarmFlag})")
    void insertData(@Param("id") String id, @Param("dimension") String dimension, @Param("monitorFlag") boolean monitorFlag, @Param("alarmFlag") boolean alarmFlag);
}
