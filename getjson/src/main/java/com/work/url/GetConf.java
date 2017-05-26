package com.work.url;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wang on 2017/5/25.
 */
public class GetConf {
    public static String cityCodeFile;
    public static String provinceCodeFile;
    public static String createTablesFile;
    public static String url;
    public static String hdfs;
    public static String hiveconnectionString;
    public static String dropPartition;
    public static String addPartition;
    public static String types;
    public static String hadoopUserName;
    public static String hivedriverName;
    public static String impaladriverName;
    public static String impalaconnectionString;

    private static Logger logger = Logger.getLogger(GetConf.class);

    synchronized static public void loads(){
        try{
            Properties properties = new Properties();
            /*File file =new File("data.properties");
            FileInputStream fis = new FileInputStream(file);*/
            InputStream fis = GetConf.class.getResourceAsStream("/data.properties");
            properties.load(fis);

            Set<Object> keySet = properties.keySet();

            for (Object object : keySet) {
                System.out.println(object.toString()+":"+properties.getProperty(object.toString()));
            }
            cityCodeFile = properties.getProperty("cityCodeFile");
            provinceCodeFile = properties.getProperty("provinceCodeFile");
            createTablesFile = properties.getProperty("createTablesFile");
            url = properties.getProperty("url");
            hdfs = properties.getProperty("hdfs");
            hiveconnectionString = properties.getProperty("hiveconnectionString");
            dropPartition = properties.getProperty("dropPartition");
            addPartition = properties.getProperty("addPartition");
            types = properties.getProperty("types");
            hadoopUserName = properties.getProperty("hadoopUserName");
            hivedriverName = properties.getProperty("hivedriverName");
            impaladriverName = properties.getProperty("impaladriverName");
            impalaconnectionString = properties.getProperty("impalaconnectionString");
        }catch(IOException e){
            logger.error("read ./etc/data.properties error");
            e.printStackTrace();
        }

    }
}
