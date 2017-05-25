package com.work.spark.xml;

import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.SQLContext;

import java.util.List;

import static com.work.spark.xml.XmlParser.*;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-27 22:15
 * <p>@version 1.0
 */
public class AppMain {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf(true)
                .setAppName("SparkXmlParser");


        windowsDebugConf(sparkConf);


        JavaSparkContext sc = new JavaSparkContext (sparkConf);

        final SQLContext sqlContext = new org.apache.spark.sql.hive.HiveContext(sc);
//        SQLContext sqlContext = new SQLContext(sc);


        // 2. 读取HDFS上某个目录下的xml文件
        List<Path> paths = HadoopUtils.listAllFiles("/user/hdfs/xmldata/new");
        //System.out.println(paths);
        for(Path path:paths) {
            System.out.println(String.format("BeginDeal %s", path));
            XmlParser.parseXmlIntoHivePartition(sqlContext,path);
            System.out.println(String.format("Done %s", path));
        }
        sc.stop();

 /*       sc.parallelize(paths).foreach(new VoidFunction<Path>() {
            public void call(Path path) throws Exception {
                XmlParser.parseXmlIntoHivePartition(sqlContext,path);
            }
        });*/

        /**3. 传入XmlParser，解析得到DataFrame
        for(Path path:paths){
            String s = path.toString();
            DataFrame df = XmlParser.parseXmlToDF(sqlContext,s);
            // 4. 写入Hive表
            if(s.contains("TDD_LTE_MRE")){
                df.insertInto("MRE");
            }else if(s.contains("TDD_LTE_MRO")){
                df.insertInto("MRO");
            }*/
        }



//        String xml_mre = "C:\\Users\\lenovo\\Desktop\\work\\hive\\TDD_LTE_MRE_HUAWEI_655429_20160629190000.xml";
//        String xml_mro = "C:\\Users\\lenovo\\Desktop\\work\\hive\\TDD_LTE_MRO_HUAWEI_655429_20160629190000.xml";

//        printSchema(sqlContext,xml_mro,"bulkPmMrDataFile");

//        DataFrame mre_m = xmlParser(sqlContext, xml_mre, "object");
//        DataFrame mre_m = xmlParser(sqlContext, xml_mre, "fileHeader");
//        mre_m.show(1,false);

//        DataFrame df_detail = xmlParser(sqlContext, xml_mre, "object");
//        df_detail.show(false);


//        DataFrame df = parseXmlToDF(sqlContext,xml_mre);
//
//
//        df.show(false);



//        DataFrame df_header = xmlParserHeaderToDF(sqlContext, xml_mre);
//        df_header.show(false);
//        sc.stop();a

    }

