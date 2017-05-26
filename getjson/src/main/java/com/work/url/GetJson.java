package com.work.url;


import org.apache.log4j.Logger;

import com.google.gson.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2017/5/23.
 */
public class GetJson {
    private String monthString;
    private GetConf getConf;
    private Map<String,String> cityCodeMap;
    private Map<String,String> provinceCodeMap;
    private StringBuilder resultString;
    private String createTableSQL;
    private HandleHive handleHive;
    private HDFSIO hdfsio;

    private static Logger logger = Logger.getLogger(GetJson.class);

    public GetJson(String monthString){
        getConf = new GetConf();
        getConf.loads();

        this.monthString=monthString;
        resultString = new StringBuilder();

        //读取配置文件，获得所有的地市code以及对应的省份code
        cityCodeMap = getMapCode(getConf.cityCodeFile);
        provinceCodeMap = getMapCode(getConf.provinceCodeFile);

        //读取配置文件，获取创建表的SQL语句
        createTableSQL = getFileContent(getConf.createTablesFile);

        //获取传入月份city_code.txt中地市的请求结果，结果存放在resultString中
        getResult();

        //使用JDBC接口在hive中执行SQL语句，以便创建表和分区，为了防止同一月份重新导入的情况，每次创建分区之前要删除分区
        executeSQL("hive");

        //将resultString写入对应月份的分析下，从而完成数据入hive
        writeToHDFS();

        //写入后，在impala刷新元数据，以便更新
        executeSQL("impala");
    }

    private void writeToHDFS(){
        hdfsio = new HDFSIO();
        hdfsio.WriteFile(getConf.hdfs+getLocation(),resultString.toString(),getConf.hadoopUserName);
    }

    private void executeSQL(String sqltype){
        if(sqltype == "hive"){
            handleHive = new HandleHive(getConf.hivedriverName,getConf.hiveconnectionString,getConf.hadoopUserName,"");
            handleHive.executeSQL(createTableSQL);
            handleHive.executeSQL(getConf.dropPartition.replace("YYYYMM",monthString));
            handleHive.executeSQL(getConf.addPartition.replace("YYYYMM",monthString));
            handleHive.closeConnection();
        }
        else if(sqltype == "impala"){
            handleHive = new HandleHive(getConf.impaladriverName,getConf.impalaconnectionString,"","");
            handleHive.executeSQL("invalidate metadata");
            handleHive.closeConnection();
            System.out.println("Impala invalidate metadata");
        }
    }

    //根据createTable.sql中create table语句中含有的location语句，
    // 获取hive表的存储路径，并根据传入的月份信息获取对应月份的分区路径，
    // 以便后续将本次月份的请求结果存放到此路径下
    private String getLocation(){
        String location = null;
        String index = "location ";
        location = createTableSQL.substring(createTableSQL.indexOf(index)+index.length()).replace("'","").replace("\n","")+"/month="+monthString+"/data.txt";
        return  location;
    }

    //完成对所有city_code.txt中地市的请求，并获得所有的结果存放到resultString中，后续将resultString存放到hive表中对应月份的分区路径下
    private void getResult(){
        if(cityCodeMap == null)
            return;

        for(Map.Entry<String,String> city : cityCodeMap.entrySet()){
            String cityCode=city.getKey();
            String provinceCode=cityCode.substring(0,3);
            String urlString=String.format(getConf.url,monthString,cityCode);
            handlejson(provinceCode,cityCode,urlGet(urlString));
            //System.out.println(urlGet(urlString));
        }
    }

    //根据url的返回值和对应请求省份地市的编码，解析出结果
    private void handlejson(String provinceCode,String cityCode,String jsonString){
        if(jsonString == null)
            return;

        JsonParser parser = new JsonParser();
        try {
            JsonObject json = (JsonObject)parser.parse(jsonString);
            int code = json.get("code").getAsInt();
            if (code!=1){
                logger.info("cityCode:"+cityCode+" result code is "+code);
                return;
            }

            JsonObject data = json.get("data").getAsJsonObject();
            System.out.println(data.toString());
            String jsonline = data.toString().replace("{\"China_Unicom\":{","");
            jsonline = jsonline.replace("\"China_Mobile\":{","");
            jsonline = jsonline.replace("\"China_Telecom\":{","");
            jsonline = jsonline.replace("\"cu_competitive\":{","");
            jsonline = jsonline.replace("\"ABCD\":","");
            jsonline = jsonline.replace("\"ABC\":","");
            jsonline = jsonline.replace("\"AB\":","");
            jsonline = jsonline.replace("\"D\":","");
            jsonline = jsonline.replace("\"C\":","");
            jsonline = jsonline.replace("\"B\":","");
            jsonline = jsonline.replace("\"A\":","");
            jsonline = jsonline.replace("}","");

            String[] colums = jsonline.split(",");
            ArrayList<String> lines = new ArrayList<String>();
            String line;
            String types[] = getConf.types.split(",");
            for(int i=0;i<types.length;i++){
                line=44640+","+monthString+","+provinceCode+","+cityCode+","+types[i]+","+colums[i]+","+colums[i+types.length]+","+colums[i+types.length*2]+","+colums[i+types.length*3]+"\n";
                resultString.append(line);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //根据URL获得返回值
    private String urlGet(String url){
        if(url == null)
            return null;

        StringBuilder result = new StringBuilder();
        try{
            String encoding = "UTF-8";
            URL urlhandle = new URL(url);
            URLConnection urlConnection = urlhandle.openConnection();
            InputStreamReader urlReader = new InputStreamReader(urlConnection.getInputStream(),encoding);
            BufferedReader urlBuffer = new BufferedReader(urlReader);
            String lineText=null;
            while((lineText = urlBuffer.readLine()) != null){
                result.append(lineText);
            }
            urlReader.close();
        }catch (Exception e){
            logger.error("connect webservice error!");
            e.printStackTrace();
            return null;
        }
        return result.toString();
    }

    private Map getMapCode(String filePath){
        Map<String,String> map = new HashMap<String, String>();

        String content = getFileContent(filePath);

        if (content == null)
            return null;

        String[] lines = content.split("\n");
        for (String line:lines) {
            String[] strings = line.split("\t");
            if(strings.length<2){
                logger.error("wrong format");
                return null;
            }
            map.put(strings[0],strings[1]);
        }
        return map;
    }

    //根据路径信息获得文件内容
    private String getFileContent(String filePath){
        StringBuilder content =new StringBuilder();
        try {
            String encoding = "UTF-8";
            //采用读取文件的方法读取内容
            /*File file = new File(filePath);
            if(!file.exists() || !file.isFile()){
                logger.error("city_code.txt does not exist");
                return null;
            }

            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
            BufferedReader bufferedReader =new BufferedReader(read);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                content.append(lineText+"\n");
            }
            read.close();*/
            InputStream is = this.getClass().getResourceAsStream(filePath);
            InputStreamReader read = new InputStreamReader(is,encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineText;
            while ((lineText = bufferedReader.readLine()) != null) {
                content.append(lineText+"\n");
            }
        }catch (Exception e){
            logger.error("error reading "+filePath);
            e.printStackTrace();
            return null;
        }

        return content.toString();
    }
}
