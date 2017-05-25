package com.work.spark.xml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;

import static com.work.spark.xml.XmlParser.windowsDebugConf;

/**
 * Hive初始化，初建表前初始化，只执行一次
 * <p>@author lenovo
 * <p>@createAt 2017-01-07 20:03
 */
public class InitTableApp {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf(true)
                .setAppName("SparkXmlParser");

        windowsDebugConf(sparkConf);

        JavaSparkContext sc = new JavaSparkContext (sparkConf);

        final SQLContext sqlContext = new org.apache.spark.sql.hive.HiveContext(sc);

        TableUtil.initHiveTable(sqlContext);
    }
}
