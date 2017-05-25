package com.work.spark.xml;

import com.work.spark.xml.bean.MreDetailBean;
import com.work.spark.xml.bean.MreHeaderBean;
import com.work.spark.xml.bean.MroDetailBean;
import com.work.spark.xml.bean.MroHeaderBean;
import org.apache.commons.math3.ode.UnknownParameterException;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-25 18:28
 * <p>@version 1.0
 */
public class XmlParser {

    private static Logger logger = Logger.getLogger(XmlParser.class);


    public static final char separator = 1;   //  \001

    public static final String sep = " ";

    public static final String EMPTY_DATA = String.valueOf((char) 9);



    /**
     * 本地调试
     *
     * @param conf
     */
    public static void windowsDebugConf(SparkConf conf) {
        String sparkExcuteUri = System.getenv("SPARK_EXECUTOR_URI");
        if (sparkExcuteUri != null) {
            conf.set("spark.executor.uri", sparkExcuteUri);
        }

        String os = (String) System.getProperties().get("os.name");
        if (os.contains("Windows")) {
            System.setProperty("hadoop.home.dir", "E:\\hadoop\\hadoop-2.6.5\\");
        }
    }

    /**
     * 打印一个xml文件的schema
     * @param sqlContext
     * @param xmlFilePath
     * @param rowTag
     */
    public static void printXmlSchema(SQLContext sqlContext, String xmlFilePath, String rowTag) {
        DataFrame df = sqlContext.read()
                .format("com.databricks.spark.xml")
                .option("rowTag", rowTag)
                .load(xmlFilePath);

        System.out.println(df.schema());
    }

    /**
     * xml文件解析
     * @param sqlContext
     * @param xmlFilePath
     * @return
     */
    protected static DataFrame xmlParser(SQLContext sqlContext, String xmlFilePath,String rowTag ) {

        DataFrame df = null;

        StructType xmlSchema = null;


        if("bulkPmMrDataFile".equals(rowTag)){
            if(xmlFilePath.contains("LTE_MRE")){
                xmlSchema = Schema.mre_root;

            }else if(xmlFilePath.contains("LTE_MRO")){
                xmlSchema = Schema.mro_root;
            }

        }else if("object".equals(rowTag)){
            if(xmlFilePath.contains("LTE_MRE")){
                xmlSchema = Schema.mre_object;

            }else if(xmlFilePath.contains("LTE_MRO")){
                xmlSchema = Schema.mro_object;
            }
        }


        if(xmlSchema != null){
            df = sqlContext.read()
                    .format("com.databricks.spark.xml")
                    .option("rowTag", rowTag)
                    .schema(xmlSchema)
                    .load(xmlFilePath);

        }

        return df;

    }


    /**
     * 处理MRE根节点
     * @param sqlContext
     * @param xmlFilePath
     * @return
     */
    private static DataFrame parseMreXmlHeaderToDF(SQLContext sqlContext,String xmlFilePath){

        List<MreHeaderBean> mreHeaderBeanList = new ArrayList<MreHeaderBean>();


        logger.info("----------解析MRE ->  bulkPmMrDataFile----------");
        DataFrame df = xmlParser(sqlContext, xmlFilePath, "bulkPmMrDataFile");



        Row[] fileHeaderRow = df.select("fileHeader").collect();
        Row[] enb = df.select("eNB").collect();

        String fileFormatVersion  = (String)((Row) fileHeaderRow[0].get(0)).getString(0);
        String jobId = (String)((Row) fileHeaderRow[0].get(0)).getString(1);
        String reportTime = (String)((Row) fileHeaderRow[0].get(0)).getString(2);
        String startTime = (String)((Row) fileHeaderRow[0].get(0)).getString(3);
        String endTime = (String)((Row) fileHeaderRow[0].get(0)).getString(4);

        String enbId = ((Row) enb[0].get(0)).getString(0);


        mreHeaderBeanList.add(new MreHeaderBean(fileFormatVersion,jobId,reportTime,startTime,endTime,enbId));

        return sqlContext.createDataFrame(mreHeaderBeanList, MreHeaderBean.class);

    }


    /**
     * 处理MRO根节点
     * @param sqlContext
     * @param xmlFilePath
     * @return
     */
    private static DataFrame parseMroXmlHeaderToDF(SQLContext sqlContext,String xmlFilePath){

        List<MroHeaderBean> mroHeaderBeanList = new ArrayList<MroHeaderBean>();

        logger.info("----------解析MRO -> bulkPmMrDataFile----------");
        DataFrame df = xmlParser(sqlContext, xmlFilePath, "bulkPmMrDataFile");



        Row[] fileHeaderRow = df.select("fileHeader").collect();
        Row[] enb = df.select("eNB").collect();

        String fileFormatVersion  = (String)((Row) fileHeaderRow[0].get(0)).getString(0);
        String jobId = (String)((Row) fileHeaderRow[0].get(0)).getString(1);
        String period = (String)((Row) fileHeaderRow[0].get(0)).getString(2);
        String reportTime = (String)((Row) fileHeaderRow[0].get(0)).getString(3);
        String startTime = (String)((Row) fileHeaderRow[0].get(0)).getString(4);
        String endTime = (String)((Row) fileHeaderRow[0].get(0)).getString(5);

        String enbId = ((Row) enb[0].get(0)).getString(0);


        mroHeaderBeanList.add(new MroHeaderBean(fileFormatVersion,jobId,period,reportTime,startTime,endTime,enbId));

        return sqlContext.createDataFrame(mroHeaderBeanList, MroHeaderBean.class);
    }

    /**
     * 处理MRE object子节点
     * @param sqlContext
     * @param xmlFilePath
     * @return
     * @throws CloneNotSupportedException
     */
    private static DataFrame parseMreXmlDetailToDF(SQLContext sqlContext,String xmlFilePath) {

        logger.info("----------解析MRE -> object----------");
        DataFrame df = xmlParser(sqlContext, xmlFilePath, "object");
        Row[] rows = df.collect();

        List<MreDetailBean> mreDetailBeanList = new ArrayList<MreDetailBean>();

        for (Row row : rows) {
            MreDetailBean m = new MreDetailBean();
            m.setObjectEventType(row.getString(0));
            m.setObjectMmeCode(row.getString(1));
            m.setObjectMmeGroupId(row.getString(2));
            m.setObjectMmeUeS1apId(row.getString(3));
            m.setObjectTimeStamp(row.getString(4));
            m.setObjectId(row.getString(5));
            List<String> list = row.getList(6);

            for(String s:list){
                String[] ss = s.split(sep);

                MreDetailBean m1 = null;
                try {
                    m1 = (MreDetailBean) m.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                m1.setvLteScRSRP(trimNil(ss[0]));
                m1.setvLteScRSRQ(trimNil(ss[1]));
                m1.setvLteScEarfcn(trimNil(ss[2]));
                m1.setvLteScPci(trimNil(ss[3]));
                m1.setvLteScCgi(trimNil(ss[4]));
                m1.setvLteNcRSRP(trimNil(ss[5]));
                m1.setvLteNcRSRQ(trimNil(ss[6]));
                m1.setvLteNcEarfcn(trimNil(ss[7]));
                m1.setvLteNcPci(trimNil(ss[8]));
                m1.setvUtraCellParameterId(trimNil(ss[9]));
                m1.setvUtraCpichRSCP(trimNil(ss[10]));
                m1.setvUtraCpichEcNo(trimNil(ss[11]));
                m1.setvGsmNcellNcc(trimNil(ss[12]));
                m1.setvGsmNcellBcc(trimNil(ss[13]));
                m1.setvGsmNcellBcch(trimNil(ss[14]));
                m1.setvGsmNcellCarrierRSSI(trimNil(ss[15]));

                mreDetailBeanList.add(m1);
            }

        }


        return sqlContext.createDataFrame(mreDetailBeanList, MreDetailBean.class);

    }

    /**
     * 处理MRO object子节点
     * @param sqlContext
     * @param xmlFilePath
     * @return
     * @throws CloneNotSupportedException
     */
    private static DataFrame parseMroXmlDetailToDF(SQLContext sqlContext,String xmlFilePath) {
        logger.info("-------------解析MRO -> object------------");
        DataFrame df = xmlParser(sqlContext, xmlFilePath, "object");
        Row[] rows = df.collect();

        List<MroDetailBean> mroDetailBeanList = new ArrayList<MroDetailBean>();

        for (Row row : rows) {
            MroDetailBean m = new MroDetailBean();
            m.setObjectMmeCode(row.getString(0));
            m.setObjectMmeGroupId(row.getString(1));
            m.setObjectMmeUeS1apId(row.getString(2));
            m.setObjectTimeStamp(row.getString(3));
            m.setObjectId(row.getString(4));
            List<String> list = row.getList(5);

            for(String s:list){
                String[] ss = s.split(sep);

                MroDetailBean m1 = null;
                try {
                    m1 = (MroDetailBean) m.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                m1.setvLteScRSRP(trimNil(ss[0]));
                m1.setLteNcRSRP(trimNil(ss[1]));
                m1.setvLteScRSRQ(trimNil(ss[2]));
                m1.setvLteNcRSRQ(trimNil(ss[3]));
                m1.setvLteScTadv(trimNil(ss[4]));
                m1.setvLteScPHR(trimNil(ss[5]));
                m1.setvLteScRIP(trimNil(ss[6]));
                m1.setvLteScPlrULQci1(trimNil(ss[7]));
                m1.setvLteScPlrULQci2(trimNil(ss[8]));
                m1.setvLteScPlrULQci3(trimNil(ss[9]));
                m1.setvLteScPlrULQci4(trimNil(ss[10]));
                m1.setvLteScPlrULQci5(trimNil(ss[11]));
                m1.setvLteScPlrULQci6(trimNil(ss[12]));
                m1.setvLteScPlrULQci7(trimNil(ss[13]));
                m1.setvLteScPlrULQci8(trimNil(ss[14]));
                m1.setvLteScPlrULQci9(trimNil(ss[15]));
                m1.setvLteScPlrDLQci1(trimNil(ss[16]));
                m1.setvLteScPlrDLQci2(trimNil(ss[17]));
                m1.setvLteScPlrDLQci3(trimNil(ss[18]));
                m1.setvLteScPlrDLQci4(trimNil(ss[19]));
                m1.setvLteScPlrDLQci5(trimNil(ss[20]));
                m1.setvLteScPlrDLQci6(trimNil(ss[21]));
                m1.setvLteScPlrDLQci7(trimNil(ss[22]));
                m1.setvLteScPlrDLQci8(trimNil(ss[23]));
                m1.setvLteScPlrDLQci9(trimNil(ss[24]));
                m1.setvLteScSinrUL(trimNil(ss[25]));
                m1.setvLteScEarfcn(trimNil(ss[26]));
                m1.setvLteScPci(trimNil(ss[27]));
                m1.setvLteScCgi(trimNil(ss[28]));
                m1.setvLteNcEarfcn(trimNil(ss[29]));
                m1.setvLteNcPci(trimNil(ss[30]));
                m1.setvGsmNcellBcch(trimNil(ss[31]));
                m1.setvGsmNcellCarrierRSSI(trimNil(ss[32]));
                m1.setvGsmNcellNcc(trimNil(ss[33]));
                m1.setvGsmNcellBcc(trimNil(ss[34]));
                m1.setvUtraCpichRSCP(trimNil(ss[35]));
                m1.setvUtraCpichEcNo(trimNil(ss[36]));
                m1.setvUtraCellParameterId(trimNil(ss[37]));
                m1.setvLteScAOA(trimNil(ss[38]));
                m1.setvLteScUeRxTxTD(trimNil(ss[39]));
                m1.setvLteSceEuRxTxTD(trimNil(ss[40]));
                m1.setvLteRSTD(trimNil(ss[41]));
                m1.setvLteTEuGNSS(trimNil(ss[42]));
                m1.setvLteTUeGNSS(trimNil(ss[43]));
                m1.setvLteFddNcRSRP(trimNil(ss[44]));
                m1.setvLteFddNcRSRQ(trimNil(ss[45]));
                m1.setvLteFddNcEarfcn(trimNil(ss[46]));
                m1.setvLteFddNcPci(trimNil(ss[47]));
                m1.setvLteTddNcRSRP(trimNil(ss[48]));
                m1.setvLteTddNcRSRQ(trimNil(ss[49]));
                m1.setvLteTddNcEarfcn(trimNil(ss[50]));
                m1.setvLteTddNcPci(trimNil(ss[51]));
                m1.setvUtraCarrierRSSI(trimNil(ss[52]));
                mroDetailBeanList.add(m1);
            }

        }


        return sqlContext.createDataFrame(mroDetailBeanList, MroDetailBean.class);
    }


    /**
     *
     * @param sqlContext
     * @param xmlFilePath
     * @return  输出DataFrame
     */
    public static DataFrame parseXmlToDF(SQLContext sqlContext,String xmlFilePath){

        if(xmlFilePath.contains("LTE_MRE")){
            DataFrame fileHeaderDf = parseMreXmlHeaderToDF(sqlContext, xmlFilePath);
            DataFrame detailDf = parseMreXmlDetailToDF(sqlContext, xmlFilePath);
            return fileHeaderDf.join(detailDf);
        }else if(xmlFilePath.contains("LTE_MRO")){
            DataFrame fileHeaderDf = parseMroXmlHeaderToDF(sqlContext, xmlFilePath);
            DataFrame detailDf = parseMroXmlDetailToDF(sqlContext, xmlFilePath);
            return fileHeaderDf.join(detailDf);
        }

        return null;
    }


    /**
     *
     * @param sqlContext
     * @param xmlFilePath
     * @return
     */
    public static DataFrame parseXmlToDF(SQLContext sqlContext,Path xmlFilePath){

        String path = xmlFilePath.toString();
        return parseXmlToDF(sqlContext,path);
    }

    /**
     * 直接解析XML文件写入Hive
     * @param sqlContext
     * @param xmlFilePath
     */
    public static void parseXmlIntoHive(SQLContext sqlContext,Path xmlFilePath) {
        String path = xmlFilePath.toString();
        if(path.contains("LTE_MRE")){
            DataFrame fileHeaderDf = parseMreXmlHeaderToDF(sqlContext, path);
            DataFrame detailDf = parseMreXmlDetailToDF(sqlContext, path);
            DataFrame df = fileHeaderDf.join(detailDf);
            df.insertInto("MRE",false);
        }else if(path.contains("LTE_MRO")){
            DataFrame fileHeaderDf = parseMroXmlHeaderToDF(sqlContext, path);
            DataFrame detailDf = parseMroXmlDetailToDF(sqlContext, path);
            DataFrame df = fileHeaderDf.join(detailDf);
            df.insertInto("MRO",false);
        }
    }

    /**
     * 将数据写入指定的分区表
     * @param sqlContext
     * @param xmlFilePath
     * @param hivePartition
     */
    public static void parseXmlIntoHivePartition(SQLContext sqlContext,Path xmlFilePath,String hivePartition) {
        String path = xmlFilePath.toString();
        if(path.contains("LTE_MRE")){
            try{
                DataFrame fileHeaderDf = parseMreXmlHeaderToDF(sqlContext, path);
                DataFrame detailDf = parseMreXmlDetailToDF(sqlContext, path);
                DataFrame df = fileHeaderDf.join(detailDf);
                df.registerTempTable("table1");
                sqlContext.sql(String.format("insert into MRE partition(pt='%s') select\n" +
                        "\tenbId,\n" +
                        "\tendTime,\n" +
                        "\tfileFormatVersion,\n" +
                        "\tjobId,\n" +
                        "\treportTime,\n" +
                        "\tstartTime,\n" +
                        "\tobjectEventType,\n" +
                        "\tobjectId ,\n" +
                        "\tobjectMmeCode,\n" +
                        "\tobjectMmeGroupId,\n" +
                        "\tobjectMmeUeS1apId,\n" +
                        "\tobjectTimeStamp ,\n" +
                        "\tvGsmNcellBcc,\n" +
                        "\tvGsmNcellBcch,\n" +
                        "\tvGsmNcellCarrierRSSI,\n" +
                        "\tvGsmNcellNcc,\n" +
                        "\tvLteNcEarfcn,\n" +
                        "\tvLteNcPci,\n" +
                        "\tvLteNcRSRP,\n" +
                        "\tvLteNcRSRQ,\n" +
                        "\tvLteScCgi,\n" +
                        "\tvLteScEarfcn,\n" +
                        "\tvLteScPci,\n" +
                        "\tvLteScRSRP,\n" +
                        "\tvLteScRSRQ,\n" +
                        "\tvUtraCellParameterId,\n" +
                        "\tvUtraCpichEcNo,\n" +
                        "\tvUtraCpichRSCP \n" +
                        "from table1", hivePartition));
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(String.format("PraseError，File%s is abnormal", xmlFilePath));
            }

        }else if(path.contains("LTE_MRO")){
            try{
                DataFrame fileHeaderDf = parseMroXmlHeaderToDF(sqlContext, path);
                DataFrame detailDf = parseMroXmlDetailToDF(sqlContext, path);
                DataFrame df = fileHeaderDf.join(detailDf);
                df.registerTempTable("table2");
                sqlContext.sql(String.format("insert into MRO partition(pt='%s') select\n" +
                        "\tenbId\n" +
                        "\t,endTime               \n" +
                        "\t,fileFormatVersion\n" +
                        "\t,jobId\n" +
                        "\t,period\n" +
                        "\t,reportTime          \n" +
                        "\t,startTime             \n" +
                        "\t,lteNcRSRP\n" +
                        "\t,objectId\n" +
                        "\t,objectMmeCode\n" +
                        "\t,objectMmeGroupId\n" +
                        "\t,objectMmeUeS1apId\n" +
                        "\t,objectTimeStamp       \n" +
                        "\t,vGsmNcellBcc\n" +
                        "\t,vGsmNcellBcch\n" +
                        "\t,vGsmNcellCarrierRSSI\n" +
                        "\t,vGsmNcellNcc\n" +
                        "\t,vLteFddNcEarfcn\n" +
                        "\t,vLteFddNcPci\n" +
                        "\t,vLteFddNcRSRP\n" +
                        "\t,vLteFddNcRSRQ\n" +
                        "\t,vLteNcEarfcn\n" +
                        "\t,vLteNcPci\n" +
                        "\t,vLteNcRSRQ\n" +
                        "\t,vLteRSTD\n" +
                        "\t,vLteScAOA\n" +
                        "\t,vLteScCgi\n" +
                        "\t,vLteScEarfcn\n" +
                        "\t,vLteScPHR\n" +
                        "\t,vLteScPci\n" +
                        "\t,vLteScPlrDLQci1\n" +
                        "\t,vLteScPlrDLQci2\n" +
                        "\t,vLteScPlrDLQci3\n" +
                        "\t,vLteScPlrDLQci4\n" +
                        "\t,vLteScPlrDLQci5\n" +
                        "\t,vLteScPlrDLQci6\n" +
                        "\t,vLteScPlrDLQci7\n" +
                        "\t,vLteScPlrDLQci8\n" +
                        "\t,vLteScPlrDLQci9\n" +
                        "\t,vLteScPlrULQci1\n" +
                        "\t,vLteScPlrULQci2\n" +
                        "\t,vLteScPlrULQci3\n" +
                        "\t,vLteScPlrULQci4\n" +
                        "\t,vLteScPlrULQci5\n" +
                        "\t,vLteScPlrULQci6\n" +
                        "\t,vLteScPlrULQci7\n" +
                        "\t,vLteScPlrULQci8\n" +
                        "\t,vLteScPlrULQci9\n" +
                        "\t,vLteScRIP\n" +
                        "\t,vLteScRSRP\n" +
                        "\t,vLteScRSRQ\n" +
                        "\t,vLteScSinrUL\n" +
                        "\t,vLteScTadv\n" +
                        "\t,vLteScUeRxTxTD\n" +
                        "\t,vLteSceEuRxTxTD\n" +
                        "\t,vLteTEuGNSS\n" +
                        "\t,vLteTUeGNSS\n" +
                        "\t,vLteTddNcEarfcn\n" +
                        "\t,vLteTddNcPci\n" +
                        "\t,vLteTddNcRSRP\n" +
                        "\t,vLteTddNcRSRQ\n" +
                        "\t,vUtraCarrierRSSI\n" +
                        "\t,vUtraCellParameterId\n" +
                        "\t,vUtraCpichEcNo\n" +
                        "\t,vUtraCpichRSCP\n" +
                        "from table2", hivePartition));
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(String.format("PraseError，File%s is abnormal", xmlFilePath));
            }

        }
    }

    /**
     * 写入分区表，分区默认按照文件名日期处理
     * @param sqlContext
     * @param xmlFilePath
     */
    public static void parseXmlIntoHivePartition(SQLContext sqlContext,Path xmlFilePath) {
        parseXmlIntoHivePartition(sqlContext,xmlFilePath,HadoopUtils.getDateFromFilename(xmlFilePath));
    }


    /**
     * 把数据中值为 Nil 替换为 null
     * @param s
     * @return
     */
    public static String trimNil(String s){
        if("NIL".equals(s)){
            return null;
        }
        return s;
    }






}
