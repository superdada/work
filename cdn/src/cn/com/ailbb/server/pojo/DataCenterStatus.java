package cn.com.ailbb.server.pojo;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class DataCenterStatus {
    private int id = 0;
    private String dimension;
    private boolean monitorFlag = false;
    private boolean alarmFlag = false;

    public DataCenterStatus(){}

    public DataCenterStatus(int id, String dimension, boolean monitorFlag, boolean alarmFlag) {
        this.setId(id);
        this.setDimension(dimension);
        this.setMonitorFlag(monitorFlag);
        this.setAlarmFlag(alarmFlag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMonitorFlag() {
        return monitorFlag;
    }

    public void setMonitorFlag(boolean monitorFlag) {
        this.monitorFlag = monitorFlag;
    }

    public boolean isAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(boolean alarmFlag) {
        this.alarmFlag = alarmFlag;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
