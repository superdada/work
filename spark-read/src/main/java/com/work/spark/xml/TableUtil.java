package com.work.spark.xml;

import org.apache.spark.sql.SQLContext;
import org.apache.log4j.Logger;

/**
 * <p>@author lenovo
 * <p>@createAt 2017-01-03 9:49
 * <p>@version 1.0
 */
public class TableUtil {
    private static Logger logger = Logger.getLogger(TableUtil.class);


    private static final String dropMRE = "DROP TABLE IF EXISTS MRE";
    private static final String createMRE = "CREATE TABLE MRE (\n" +
            "\tenbId string,\n" +
            "\tendTime string,\n" +
            "\tfileFormatVersion string,\n" +
            "\tjobId string,\n" +
            "\treportTime string,\n" +
            "\tstartTime string,\n" +
            "\tobjectEventType string,\n" +
            "\tobjectId  string,\n" +
            "\tobjectMmeCode string,\n" +
            "\tobjectMmeGroupId string,\n" +
            "\tobjectMmeUeS1apId string,\n" +
            "\tobjectTimeStamp  string,\n" +
            "\tvGsmNcellBcc bigint,\n" +
            "\tvGsmNcellBcch bigint,\n" +
            "\tvGsmNcellCarrierRSSI bigint,\n" +
            "\tvGsmNcellNcc bigint,\n" +
            "\tvLteNcEarfcn bigint,\n" +
            "\tvLteNcPci bigint,\n" +
            "\tvLteNcRSRP bigint,\n" +
            "\tvLteNcRSRQ bigint,\n" +
            "\tvLteScCgi bigint,\n" +
            "\tvLteScEarfcn bigint,\n" +
            "\tvLteScPci bigint,\n" +
            "\tvLteScRSRP bigint,\n" +
            "\tvLteScRSRQ bigint,\n" +
            "\tvUtraCellParameterId bigint,\n" +
            "\tvUtraCpichEcNo bigint,\n" +
            "\tvUtraCpichRSCP bigint\n" +
            ") PARTITIONED BY (pt string)\n" +
            "ROW FORMAT DELIMITED\n" +
            "FIELDS TERMINATED BY '\\t'\n" +
            "LINES TERMINATED BY '\\n'\n" +
            "STORED AS textfile";


    private static final String dropMRO = "DROP TABLE IF EXISTS MRO";
    private static final String createMRO = "CREATE TABLE MRO (\n" +
            "\tenbId string\n" +
            "\t,endTime string               \n" +
            "\t,fileFormatVersion string\n" +
            "\t,jobId string\n" +
            "\t,period string\n" +
            "\t,reportTime string          \n" +
            "\t,startTime string             \n" +
            "\t,lteNcRSRP string\n" +
            "\t,objectId string\n" +
            "\t,objectMmeCode string\n" +
            "\t,objectMmeGroupId string\n" +
            "\t,objectMmeUeS1apId string\n" +
            "\t,objectTimeStamp string       \n" +
            "\t,vGsmNcellBcc bigint\n" +
            "\t,vGsmNcellBcch bigint\n" +
            "\t,vGsmNcellCarrierRSSI bigint\n" +
            "\t,vGsmNcellNcc bigint\n" +
            "\t,vLteFddNcEarfcn bigint\n" +
            "\t,vLteFddNcPci bigint\n" +
            "\t,vLteFddNcRSRP bigint\n" +
            "\t,vLteFddNcRSRQ bigint\n" +
            "\t,vLteNcEarfcn bigint\n" +
            "\t,vLteNcPci bigint\n" +
            "\t,vLteNcRSRQ bigint\n" +
            "\t,vLteRSTD bigint\n" +
            "\t,vLteScAOA bigint\n" +
            "\t,vLteScCgi bigint\n" +
            "\t,vLteScEarfcn bigint\n" +
            "\t,vLteScPHR bigint\n" +
            "\t,vLteScPci bigint\n" +
            "\t,vLteScPlrDLQci1 bigint\n" +
            "\t,vLteScPlrDLQci2 bigint\n" +
            "\t,vLteScPlrDLQci3 bigint\n" +
            "\t,vLteScPlrDLQci4 bigint\n" +
            "\t,vLteScPlrDLQci5 bigint\n" +
            "\t,vLteScPlrDLQci6 bigint\n" +
            "\t,vLteScPlrDLQci7 bigint\n" +
            "\t,vLteScPlrDLQci8 bigint\n" +
            "\t,vLteScPlrDLQci9 bigint\n" +
            "\t,vLteScPlrULQci1 bigint\n" +
            "\t,vLteScPlrULQci2 bigint\n" +
            "\t,vLteScPlrULQci3 bigint\n" +
            "\t,vLteScPlrULQci4 bigint\n" +
            "\t,vLteScPlrULQci5 bigint\n" +
            "\t,vLteScPlrULQci6 bigint\n" +
            "\t,vLteScPlrULQci7 bigint\n" +
            "\t,vLteScPlrULQci8 bigint\n" +
            "\t,vLteScPlrULQci9 bigint\n" +
            "\t,vLteScRIP bigint\n" +
            "\t,vLteScRSRP bigint\n" +
            "\t,vLteScRSRQ bigint\n" +
            "\t,vLteScSinrUL bigint\n" +
            "\t,vLteScTadv bigint\n" +
            "\t,vLteScUeRxTxTD bigint\n" +
            "\t,vLteSceEuRxTxTD bigint\n" +
            "\t,vLteTEuGNSS bigint\n" +
            "\t,vLteTUeGNSS bigint\n" +
            "\t,vLteTddNcEarfcn bigint\n" +
            "\t,vLteTddNcPci bigint\n" +
            "\t,vLteTddNcRSRP bigint\n" +
            "\t,vLteTddNcRSRQ bigint\n" +
            "\t,vUtraCarrierRSSI bigint\n" +
            "\t,vUtraCellParameterId bigint\n" +
            "\t,vUtraCpichEcNo bigint\n" +
            "\t,vUtraCpichRSCP bigint\n" +
            ") PARTITIONED BY (pt string)\n" +
            "ROW FORMAT DELIMITED\n" +
            "FIELDS TERMINATED BY '\\t'\n" +
            "LINES TERMINATED BY '\\n'\n" +
            "STORED AS textfile";

    public static void initHiveTable(SQLContext sqlContext){


        logger.info("---------删除MRE表----------");
        sqlContext.sql(dropMRE);
        logger.info("---------创建MRE表----------");
        sqlContext.sql(createMRE);
        logger.info("---------删除MRO表----------");
        sqlContext.sql(dropMRO);
        logger.info("---------创建MRO表----------");
        sqlContext.sql(createMRO);
    }

}
