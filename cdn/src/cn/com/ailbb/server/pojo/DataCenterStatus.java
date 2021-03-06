package cn.com.ailbb.server.pojo;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class DataCenterStatus {
    private String id;
    private String dimension;
    private String datasource;
    private String type;
    private int directory_level;
    private boolean monitorFlag = false;
    private boolean alarmFlag = false;

    public DataCenterStatus(){}

    public DataCenterStatus(String id,String datasource, String type,String dimension,int directory_level, boolean monitorFlag, boolean alarmFlag) {
        this.setId(id);
        this.setDimension(dimension);
        this.setDatasource(datasource);
        this.setType(type);
        this.setDirectory_level(directory_level);
        this.setMonitorFlag(monitorFlag);
        this.setAlarmFlag(alarmFlag);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasource(){
        return datasource;
    }

    public void setDatasource(String datasource){this.datasource = datasource; }

    public String getType(){
        return type;
    }

    public void setType(String type){this.type = type;}

    public int getDirectory_level(){return directory_level;}

    public void setDirectory_level(int directory_level){this.directory_level = directory_level;}

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
