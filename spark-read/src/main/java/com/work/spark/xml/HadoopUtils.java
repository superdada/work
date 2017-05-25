package com.work.spark.xml;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * <p>@author lenovo
 * <p>@createAt 2017-01-06 16:02
 */
public class HadoopUtils {

    private static String hostName = "10.100.26.81";  // 连接主机hostname
    private static Integer port = 8020;  // 连接端口

    private static String getAbsoluteUri(String basePath){
        return getAbsoluteUri(hostName,port,basePath);
    }

    /**
     * 获取HDFS的绝对路径
     * @param hostName
     * @param port
     * @param basePath
     * @return
     */
    private static String getAbsoluteUri(String hostName,Integer port,String basePath){
        return String.format("hdfs://%s:%d%s", hostName,port,basePath);
    }

    /**
     * 列出某个目录下的所有文件
     * @param basePath
     * @return
     */
    public static List<Path> listAllFiles(String basePath)  {
        String absolutePath = getAbsoluteUri(basePath);
        Configuration conf = new Configuration();
        FileSystem fs = null;
        FileStatus[] fileStatuses = null;
        Path path = new Path(absolutePath);
        try {
            fs = FileSystem.get(URI.create(absolutePath),conf);
            if(fs.isDirectory(path)){
                fileStatuses = fs.listStatus(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Path[] listPath = FileUtil.stat2Paths(fileStatuses);
        List<Path> paths = Arrays.asList(listPath);
        return paths;
    }



    /**
     * 解析xml文件名得到日期
     * @param path
     * @return
     */
    public static String getDateFromFilename(Path path){
        String s = path.getName().split("_")[5];
        return s.substring(0,8);
    }






}
