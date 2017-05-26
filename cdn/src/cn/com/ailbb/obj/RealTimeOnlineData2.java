package cn.com.ailbb.obj;

import java.util.List;
import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/15.
 */
public class RealTimeOnlineData2 {
    private String time; // 2017-03-09
    private List<Map<String,Object>> data;

    public RealTimeOnlineData2(String time, List<Map<String,Object>> data) {
        this.setTime(time);
        this.setData(data);
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Map<String,Object>> getData() {
        return data;
    }

    public void setData(List<Map<String,Object>> data) {
        this.data = data;
    }
}
