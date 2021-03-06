package cn.com.ailbb.obj;

import cn.com.ailbb.util.TimeUtil;

import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/15.
 * 返回最新数据，地市维度
 */
public class RealTimeOnlineData1 {
    private String health_rate;
    private String hit_rate;
    private String avg_dl_speed;
    private String delay;
    private String bandwidth;
    private String visit_cnt;
    private String time;
    private String cache_cdn;

    public RealTimeOnlineData1(){
    }

    public RealTimeOnlineData1(String health_rate, String hit_rate, String avg_dl_speed, String delay,String bandwidth,String visit_cnt ,String time,String cache_cdn){
        this.setHealth_rate(health_rate);
        this.setHit_rate(hit_rate);
        this.setAvg_dl_speed(avg_dl_speed);
        this.setDelay(delay);
        this.setBandwidth(bandwidth);
        this.setVisit_cnt(visit_cnt);
        this.setTime(time);
        this.setCache_cdn(cache_cdn);
    }

    public String getHealth_rate() {
        return health_rate;
    }

    public void setHealth_rate(String health_rate) {
        this.health_rate = health_rate;
    }

    public String getHit_rate() {
        return hit_rate;
    }

    public void setHit_rate(String hit_rate) {
        this.hit_rate = hit_rate;
    }

    public String getAvg_dl_speed() {
        return avg_dl_speed;
    }

    public void setAvg_dl_speed(String avg_dl_speed) {
        this.avg_dl_speed = avg_dl_speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getVisit_cnt() {
        return visit_cnt;
    }

    public void setVisit_cnt(String visit_cnt) {
        this.visit_cnt = visit_cnt;
    }

    public String getCache_cdn() {
        return cache_cdn;
    }

    public void setCache_cdn(String cache_cdn) {
        this.cache_cdn = cache_cdn;
    }
}
