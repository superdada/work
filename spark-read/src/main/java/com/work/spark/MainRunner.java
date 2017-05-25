package com.work.spark;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.Serializable;

public class MainRunner implements Serializable {

    // 使用JDBC连接hive
    // 使用JDBC连接hive
    private final String driverName = "org.apache.hive.jdbc.HiveDriver";

    // 连接参数
    private final String url = "jdbc:hive2://10.100.26.81:10001/default";
    // 连接hive用户名
    private final String username = "root";

    // 连接hive密码
    private final String password = "root";

    public static final char separator = 1; // \001

    public static final String EMPTY_DATA = String.valueOf((char) 9);

    // 默认文件路径。 可以通过第一个控制台参数传，如果不传，使用默认的名称O
    // private static String defaultFilePath
    // ="hdfs://MASTER01:8020/user/hdfs/xmldata/xml/TDD_LTE_MRE_HUAWEI_655429_20160629190000.xml";

    private static String defaultFilePath = "hdfs://MASTER01:8020/user/hdfs/xmldata/mr/";

    // 临时txt文件的路径
    private String tempTxtFilePath = "hdfs://MASTER01:8020/user/hdfs/tmpxml/txt";

    private String today_string = "";

    public static void main(String[] args) {

        // 可以通过第一个控制台参数传，如果不传，使用默认的名称

        MainRunner runner = new MainRunner();
        runner.run();
    }

    transient List<String> fileNames = new ArrayList<String>();

    void addFiles(FileSystem fs, Path path) throws FileNotFoundException,
            IOException {
        if (fs.isDirectory(path)) {
            System.out.println("file is directory");
            // 遍历文件夹下的文件
            // File[] fs = f.listFiles();
            RemoteIterator<LocatedFileStatus> iter = fs.listFiles(path, true);
            while (iter.hasNext()) {
                LocatedFileStatus fstatus = iter.next();
                addFiles(fs, fstatus.getPath());
                // dealOneFile(fstatus.getPath(), parser);
            }
        } else {
            System.out.println("file is file");
            fileNames.add(path.getName());
        }
    }

    public void run() {

        try {

            //SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            //this.today_string = format.format(new Date());

            SparkConf sparkConf = new SparkConf()
                    .setAppName("com.work.spark.MainRunner");
            JavaSparkContext sc = new JavaSparkContext(sparkConf);

            // 3.获取需要解析的文档，生成解析器,最后解析文档
            // 这可能是个路径，需要先判断
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path(defaultFilePath);
            // File f = new File(filePath);
            fileNames.clear();
            addFiles(fs, path);
            for (int i = 0; i < fileNames.size(); ++i)
                System.out.println(fileNames.get(i));
            System.out.println("total " + fileNames.size() + " files.");
            sc.parallelize(fileNames).repartition(10).foreachPartition(new VoidFunction<Iterator<String>>() {

                private static final long serialVersionUID = 1L;

                public void call(Iterator<String> it) throws Exception {
                    while (it.hasNext()) {
                        work(it.next());
                    }

                }


            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void work(String path) {
        try{
            // 1.实例化SAXParserFactory对象
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // 2.创建解析器
            SAXParser parser = factory.newSAXParser();
            dealOneFile(new Path(defaultFilePath + path), parser);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dealOneFile(Path path, SAXParser parser) {

        try {

            String fileName = path.getName();
            // 检查文件名字是否已经处理过
            if (fileName.contains("_DONE")) {
                System.out.println(fileName + " has dealed before.");
                return;
            }

            System.out.println("filePath:" + path.toString());
            System.out.println("fileName:" + fileName);

            String[] types = fileName.split("_");
            if (types.length == 6) {
                ReadSaxHandler dh = new ReadSaxHandler(this, types[2]);

                FileSystem fs = FileSystem.get(new Configuration());
                FSDataInputStream is = fs.open(path);
                parser.parse(is, dh);

                // 对于处理完的文件重命名，在扩展名签名增加_DONE

                String oldPath = path.toString();
                String newPath_s = oldPath.substring(0, oldPath.lastIndexOf('.')) + "_DONE" + oldPath.substring(oldPath.lastIndexOf('.'));

                Path newPath = new Path(newPath_s);    //新的路径
                boolean isRename = fs.rename(path, newPath);
                if (isRename) {
                    System.out.println(fileName + "文件处理完成,重新命名。");
                } else {
                    System.out.println("重命名失败！");
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



	/*
	 * public void dealOneFile(File f, SAXParser parser) {
	 *
	 * try {
	 *
	 * String fileName = f.getName(); System.out.println("filePath:"+
	 * f.getAbsoluteFile()); System.out.println("fileName:"+ fileName);
	 *
	 * String[] types = fileName.split("_"); ReadSaxHandler dh = new
	 * ReadSaxHandler(this, types[2]); parser.parse(f, dh);
	 *
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

    public void createTable(String tableName, List<String> columns,
                            String comment) {
        if (validateTableExist(tableName) == true) {
            return;
        }
        System.out.println("[" + tableName + "] already exist.");

        //
        Connection con = this.getConnection();
        if (con == null) {
            return;
        }
        try {

            Statement stmt = con.createStatement();
            StringBuilder createTableSql = new StringBuilder();
            createTableSql.append("CREATE TABLE IF NOT EXISTS ")
                    .append(tableName).append(" ( ");
            for (int i = 0; i < columns.size(); i++) {
                String columnName = columns.get(i);
                if (i > 0) {
                    createTableSql.append(", ");
                }
                createTableSql.append(columnName + " String");
            }
            createTableSql.append(" )");
            createTableSql.append(" partitioned by (reportTime String)");
            // createTableSql.append(" COMMENT '");
            // createTableSql.append(comment);
            // createTableSql.append("'");
            createTableSql.append(" ROW FORMAT DELIMITED");
            createTableSql.append(" FIELDS TERMINATED BY '\\001'");
            createTableSql.append(" LINES TERMINATED BY '\\n'");
            createTableSql.append(" STORED AS TEXTFILE");

            System.out.println(createTableSql.toString());

            stmt.execute(createTableSql.toString());
            System.out.println("Create table[" + tableName + "] success!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDataToTable(String tableName, List<List<String>> data,
                               String reportTime) {

        if (data == null || data.isEmpty()) {
            return;
        }

        try {
            // 写入临时的txt文件
            System.out.println("write data to file, data count:" + data.size());
            String fileName = tempTxtFilePath + File.separator + tableName
                    + ".txt";
            this.writeToTextFile(fileName, data);

            //写到hive中
          this.loadTextFileToHive(tableName, fileName, reportTime);

            //删除临时
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path(fileName);
            if (fs.isFile(path) && fs.exists(path)) {
                fs.delete(path,true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTextFileToHive(String tableName, String fileName,
                                   String reportTime) {

        // 连接到hive
        Connection con = this.getConnection();
        if (con == null) {
            return;
        }

        try {
            Statement stmt = con.createStatement();
            String queryString = "LOAD DATA  INPATH '" + fileName + "'"
                    + " INTO TABLE " + tableName;
            if (StringUtils.isBlank(reportTime)) {
                queryString += ";";
            } else {
                queryString += " PARTITION(reportTime='" + reportTime + "')";
            }
            stmt.executeQuery(queryString);
            System.out.println("Load Data into " + tableName + " successful");

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToTextFile(String fileName, List<List<String>> data) {

        try {

			/* 写入Txt文件 */
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            Path writename = new Path(fileName);
            FSDataOutputStream out =fs.create(writename);
            //out.writeUTF(fileName);

            //BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            StringBuilder oneLine = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i > 0) {
                    out.write("\n".getBytes());
                }
                List<String> oneLineList = data.get(i);
                for (int j = 0; j < oneLineList.size(); j++) {
                    if (j > 0) {
                        out.write(MainRunner.separator);
                        //oneLine.append(MainRunner.separator);
                    }
                    if ("NIL".equals(oneLineList.get(j))) {
                        out.write(MainRunner.EMPTY_DATA.getBytes());
                        //oneLine.append(MainRunner.EMPTY_DATA);
                    } else {
                        out.write(oneLineList.get(j).getBytes());
                        //oneLine.append(oneLineList.get(j));
                    }

                }
                //out.write(oneLine.toString());
                //oneLine = new StringBuilder();
            }

            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateTableExist(String tableName) {
        // 定义一个变量标示
        boolean flag = true;
        // 一个查询该表所有的语句。
        String sql = "SELECT * FROM " + tableName;
        // 获取连接
        Connection con = this.getConnection();
        if (con == null) {
            return false;
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            flag = false;
            // 该表不存在，则 会跳入catch中
            System.out.println("Table has already exist.");
        } finally {
            // 关闭所有连接
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public Connection getConnection() {

        Connection con = null;
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
