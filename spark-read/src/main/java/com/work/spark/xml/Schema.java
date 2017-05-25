package com.work.spark.xml;

import org.apache.spark.sql.types.*;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-25 18:28
 * <p>@version 1.0
 */
public class Schema {

    public static final StructType mre_root = new StructType(new StructField[]{
            new StructField("fileHeader", new StructType(
                    new StructField[]{
                            new StructField("_fileFormatVersion", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_jobid", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_reportTime", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_startTime", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_endTime", DataTypes.StringType, true, Metadata.empty())
                    }
            ), true, Metadata.empty()),
            new StructField("eNB", new StructType(
                    new StructField[]{
                            new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_userLabel", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("measurement", new StructType(
                                    new StructField[]{
                                            new StructField("object", new StructType(
                                                    new StructField[]{
                                                            new StructField("_EventType", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_MmeCode", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_MmeGroupId", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_MmeUeS1apId", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_TimeStamp", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("v", DataTypes.createArrayType(
                                                                    DataTypes.StringType, true
                                                            ), true, Metadata.empty())
                                                    }
                                            ), true, Metadata.empty())

                                    }
                            ), true, Metadata.empty())
                    }
            ), true, Metadata.empty()),


    });




    public static final StructType mre_object = new StructType(
            new StructField[]{
                    new StructField("_EventType", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_MmeCode", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_MmeGroupId", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_MmeUeS1apId", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_TimeStamp", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("v", DataTypes.createArrayType(
                            DataTypes.StringType, true
                    ), true, Metadata.empty())
            }
    );


    public static final StructType mro_root = new StructType(new StructField[]{
            new StructField("fileHeader", new StructType(
                    new StructField[]{
                            new StructField("_fileFormatVersion", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_jobid", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_period",DataTypes.StringType,true,Metadata.empty()),
                            new StructField("_reportTime", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_startTime", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_endTime", DataTypes.StringType, true, Metadata.empty())
                    }
            ), true, Metadata.empty()),
            new StructField("eNB", new StructType(
                    new StructField[]{
                            new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("_userLabel", DataTypes.StringType, true, Metadata.empty()),
                            new StructField("measurement", new StructType(
                                    new StructField[]{
                                            new StructField("object", new StructType(
                                                    new StructField[]{
                                                            new StructField("_MmeCode", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_MmeGroupId", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_MmeUeS1apId", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_TimeStamp", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                                                            new StructField("v", DataTypes.createArrayType(
                                                                    DataTypes.StringType, true
                                                            ), true, Metadata.empty())
                                                    }
                                            ), true, Metadata.empty())

                                    }
                            ), true, Metadata.empty())
                    }
            ), true, Metadata.empty()),


    });


    public static final StructType mro_object = new StructType(
            new StructField[]{
                    new StructField("_MmeCode", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_MmeGroupId", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_MmeUeS1apId", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_TimeStamp", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("_id", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("v", DataTypes.createArrayType(
                            DataTypes.StringType, true
                    ), true, Metadata.empty())
            }
    );




/*    public static final StructType mre_header = new StructType(
            new StructField[]{
                    new StructField("fileFormatVersion", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("jobId", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("reportTime", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("startTime", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("endTime", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("enbId", DataTypes.StringType,true,Metadata.empty())
            }
    );*/


}
