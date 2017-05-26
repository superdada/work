package cn.com.ailbb.server.pojo;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class Pfcsnapshot {
    long id = 0;
    String dimension; // 维度
    String index; // 指标
    String time; // 时间
    String dimensionId; // 查询维度ID
    String cacheCdn;

    public Pfcsnapshot(){}

    public Pfcsnapshot(long id, String dimension, String index, String time, String dimensionId,String cache_cdn) {
        this.setId(id);
        this.setDimension(dimension);
        this.setIndex(index);
        this.setTime(time);
        this.setDimensionId(dimensionId);
        this.setCacheCdn(cache_cdn);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getCacheCdn() {
        return cacheCdn;
    }

    public void setCacheCdn(String cache_cdn) {
        this.cacheCdn = cache_cdn;
    }
}
