package cn.com.ailbb.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by WildMrZhang on 2017/3/7.
 */
public class PropertiesUtil {
    public static InputStream getProperties(String name){
        return new PropertiesUtil().getClass().getResourceAsStream(name);
    }
}
