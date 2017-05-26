package cn.com.ailbb.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WildMrZhang on 2017/3/15.
 */
public class RealTimeOnlineData3 {
    private List<String> x = new ArrayList<>();
    private List<String> y = new ArrayList<>();

    public List<String> getX() {
        return x;
    }

    public void addX(String x) {
        this.x.add(x);
    }

    public void setX(List<String> x) {
        this.x = x;
    }

    public List<String> getY() {
        return y;
    }

    public void setY(List<String> y) {
        this.y = y;
    }

    public void addY(Object y) {
        if(null != y)
            this.y.add(y.toString());
        else
            this.y.add(null);
    }

}
