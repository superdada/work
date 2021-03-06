package cn.com.ailbb.server.pojo;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class Pfcsnapshot {
    String id;
    String dimension; // 维度
    String index; // 指标
    String time; // 时间
    String cacheCdn;
    String datasource;
    String directory_level;

    public Pfcsnapshot(){}

    public Pfcsnapshot(String id,String datasource,String dimension,String cache_cdn,String directory_level,String time,String index) {
        this.setId(id);
        this.setDimension(dimension);
        this.setIndex(index);
        this.setTime(time);
        this.setCacheCdn(cache_cdn);
        this.setDatasource(datasource);
        this.setDirectory_level(directory_level);
    }

    public String getDatasource(){return datasource;}
    public void setDatasource(String datasource){this.datasource = datasource;}

    public String getDirectory_level(){return directory_level;}
    public void setDirectory_level(String directory_level){this.directory_level = directory_level;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCacheCdn() {
        return cacheCdn;
    }

    public void setCacheCdn(String cache_cdn) {
        this.cacheCdn = cache_cdn;
    }
}
