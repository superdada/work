package com.work.spark;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;


public class ReadSaxHandler extends DefaultHandler {

    private MainRunner runner;

    // 因为xml文件可能很大，所以不能使用dom方式读取，只能使用sax的方式读取，简单说就是按字符的方式读取。
    // 当识别xml中一个标签的开头后，这个字段就有值了。表明正在读取的是哪个标签。
    private String preTag = null;

    // smr表头  存放获取Smr元素
    public List<String> smrHeader = new ArrayList<String>();

    // file header
    public ArrayList<String> fileHeader = new ArrayList<String>();
    // 对获取的元素做一个映射
    public Map<String, String> fileData = new HashMap<String, String>();

    // object属性名  获取object元素
    public ArrayList<String> objectHeader = new ArrayList<String>();

    // 数据集    创建数据集存放获取的元素值
    List<OneObjectData> objectDataList = new ArrayList<OneObjectData>();

    // 当前处理的object
    OneObjectData lastObject = null;

    // 是否已经建表，用来防止重复建表
    boolean tableCreated = false;
    //创建表名
    private String tableName;

    private int tableCount = 1;
    // 表唯一
    private String fileId = null;
    //识别表名
    private String tableNamePrefix = null;
    //缓存v值
    private String wholeContent = "";

    private StringBuilder smrWholeContent = new StringBuilder();

    String comment = null;

    String reportTime = null;
    //构造方法
    public ReadSaxHandler(MainRunner runner, String tableNamePrefix) {
        this.runner = runner;
        this.tableNamePrefix = tableNamePrefix;
    }
    //开始解析文件
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Begin parse document");
    }
    //结束解析文档
    @Override
    public void endDocument() throws SAXException {
        System.out.println("End parse document");
    }
    //保存节点内容，smr值和v值
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        if ("smr".equals(preTag)) {
            if (content != null) {
                smrWholeContent.append(content);
            } else {
                System.out.println("ERROR SMR!");
            }
        } else if ("v".equals(preTag)) {
            wholeContent = wholeContent + content;
        }
    }

    //开始解析节点
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //开始对各个节点进行判断是否值，并获取值
        if ("object".equals(qName)) {
            if (attributes != null) {
                OneObjectData oneObjectData = new OneObjectData();
                Map<String, String> objectAttrMap = new HashMap<String, String>(attributes.getLength());
                boolean needAddColumn = this.objectHeader.isEmpty();
                for (int i = 0; i < attributes.getLength(); i++) {
                    // getQName()是获取属性名称，
                    String objectHeaderName = attributes.getQName(i);
                    if ("id".equals(objectHeaderName)) {
                        objectHeaderName = "object_id";
                    }

                    if (needAddColumn) {
                        objectHeader.add(objectHeaderName);
                    }
                    objectAttrMap.put(objectHeaderName, attributes.getValue(i));
                }
                //调用方法 将数据映射进列表
                oneObjectData.setHeaderData(objectAttrMap);
                this.objectDataList.add(oneObjectData);
                lastObject = oneObjectData;
            }
        } else if ("measurement".equals(qName)) {
            // begin a table
            // 新建hive表
            this.tableName = null;
            this.tableCreated = false;
            if (attributes != null) {
                int size = attributes.getLength();
                for (int i = 0; i < size; i++) {
                    // getQName()是获取属性名称，

                        if ("mrName".equals(attributes.getQName(i))) {
                            this.tableName = attributes.getValue(i).replace('.','_');
                    }
                }
            }
        } else if ("eNB".equals(qName)) {
            if (attributes != null) {
                int size = attributes.getLength();
                for (int i = 0; i < size; i++) {
                    // getQName()是获取属性名称，
                    if ("id".equals(attributes.getQName(i))) {
                        this.fileId = attributes.getValue(i);
                        this.fileHeader.add("eNb_id");
                        this.fileData.put("eNb_id", attributes.getValue(i));
                    }
                }
            }
        } else if ("fileHeader".equals(qName)) {
            StringBuilder commentData = new StringBuilder();
            if (attributes != null) {
                int size = attributes.getLength();
                for (int i = 0; i < size; i++) {
                    // getQName()是获取属性名称，
                    if ("id".equals(attributes.getQName(i))) {
                        this.fileId = attributes.getValue(i);
                    } else if ("reportTime".equals(attributes.getQName(i))) {
                        if (StringUtils.isNotBlank(attributes.getValue(i))) {
                            this.reportTime = attributes.getValue(i).replace("-", "").replace(":", "").replace(".","");
                        }
                        continue;
                    }
                    this.fileHeader.add(attributes.getQName(i));
                    this.fileData.put(attributes.getQName(i), attributes.getValue(i));

                    if (i > 0) {
                        commentData.append(" ");
                    }
                    commentData.append(attributes.getQName(i));
                    commentData.append("=");
                    commentData.append(attributes.getValue(i));
                }
            }
            this.comment = commentData.toString();
        } else if ("v".equals(qName)) {
            this.wholeContent = "";
        } else if ("smr".equals(qName)) {
            smrWholeContent = new StringBuilder();
        }
        //把正在解析的节点赋值给pretag
        preTag = qName;
    }
    //结束解析结点
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("object".equals(qName)) {


        } else if ("measurement".equals(qName)) {
            // create table
            // 没有建表且有数据
            if (!tableCreated && !this.objectDataList.isEmpty()) {
                createTable();
                tableCreated = true;
            }

            // write data
            this.writeData();

            // clear cache
            this.clearCacheData();
            //读取V值并把v值写入列表
        } else if ("v".equals(qName)) {
            String content = this.wholeContent;
            if (content != null && content.length() > 0) {
                String[] dataArray = content.split(" ");
                List<String> dataLine = new ArrayList<String>();
                for (int i = 0; i < dataArray.length; i++) {
                    String dataItem = dataArray[i].trim();
                    if (dataItem.length() > 0) {
                        dataLine.add(dataItem);
                    }
                }

                if (dataLine.size() != smrHeader.size()) {
                    System.out.println("ERROR: v data size[" + dataLine.size() + "] != smr size[" + smrHeader.size() + "]");
                    System.out.println(content);
                    return;
                }

                lastObject.getvData().add(dataLine);
                if (lastObject.getvData().size() > 20000) {
                    // 防止占满内存
                    // 建表
                    if (!tableCreated) {
                        createTable();
                        tableCreated = true;
                    }
                    // 写入数据
                    this.writeData();
                }
            }
            //读取Smr值 并把smr写进列表
        } else if ("smr".equals(qName)) {
            String smrHeaderContent = smrWholeContent.toString();
            String[] columns = smrHeaderContent.split(" ");
            for (int i = 0; i < columns.length; i++) {
                String dataItem = columns[i].trim();
                if (dataItem.length() > 0) {
                    dataItem = dataItem.replace('.', '_');

                    smrHeader.add(dataItem);
                }
            }


        }
    }

    public void clearCacheData() {
        this.smrHeader.clear();
        this.objectDataList.clear();
        this.objectHeader.clear();
        this.tableName = null;
    }
    //创建副本，之前提取的数据的顺序可能对不上，先做一个副本进行排序，拍完序之后再写入，建表，
    public void createTable() {

        List<String> fileHeaderCopy = new ArrayList<String>();
        fileHeaderCopy.addAll(this.fileHeader);
        Collections.sort(fileHeaderCopy);

        List<String> objectHeaderCopy = new ArrayList<String>();
        objectHeaderCopy.addAll(this.objectHeader);
        Collections.sort(objectHeaderCopy);

        List<String> smrHeaderCopy = new ArrayList<String>();
        smrHeaderCopy.addAll(this.smrHeader);
        Collections.sort(smrHeaderCopy);

        List<String> columnList = new ArrayList<String>();
        columnList.addAll(fileHeaderCopy);
        columnList.addAll(objectHeaderCopy);
        columnList.addAll(smrHeaderCopy);

        String tableNameTemp = this.tableName;
        if (tableNameTemp == null) {
            String tableNameToHash = StringUtils.join(columnList, ",");
            tableNameTemp = this.tableNamePrefix + "_" + EncryptUtil.md5(tableNameToHash);
            this.tableName = tableNameTemp;
        }
//不会因为大小写的问题被分成两个表
        this.tableName = this.tableName.toLowerCase();
        tableNameTemp = this.tableName;

       this.runner.createTable(tableNameTemp, columnList, this.comment);
    }

    public void writeData() {
        //对获取的 数值进行排序，排完序之后再  写入数据
        if (this.objectDataList.isEmpty()) {
            return;
        }

        List<List<String>> dataList = new ArrayList<List<String>>();
        for (OneObjectData oneObjectData : this.objectDataList) {
            // convert list to map
            oneObjectData.convertDataToMap(this.smrHeader);
        }

        // 合并列
        List<String> fileHeaderCopy = new ArrayList<String>();
        fileHeaderCopy.addAll(this.fileHeader);
        Collections.sort(fileHeaderCopy);

        List<String> objectHeaderCopy = new ArrayList<String>();
        objectHeaderCopy.addAll(this.objectHeader);
        Collections.sort(objectHeaderCopy);

        List<String> smrHeaderCopy = new ArrayList<String>();
        smrHeaderCopy.addAll(this.smrHeader);
        Collections.sort(smrHeaderCopy);

        // generate data
        List<String> fileHeaderList = new ArrayList<String>();
        for (String fileHeaderColumn : fileHeaderCopy) {
            String headerData = this.fileData.get(fileHeaderColumn);
            // 处理- 号
            if (headerData != null) {
                headerData = headerData.replace('-', '_');
            }
            fileHeaderList.add(headerData);
        }

        for (OneObjectData oneObjectData : this.objectDataList) {
            List<String> objectHeaderList = new ArrayList<String>();
            for (String objectHeaderName : objectHeaderCopy) {
                String headerData = oneObjectData.getHeaderData().get(objectHeaderName);
                // 处理 - 号
                if (headerData != null) {
                    headerData = headerData.replace('-', '_');
                }
                objectHeaderList.add(headerData);
            }

            for (Map<String, String> vDataMap : oneObjectData.getvDataMap()) {
                List<String> oneLine = new ArrayList<String>();
                oneLine.addAll(fileHeaderList);
                oneLine.addAll(objectHeaderList);

                for (String smrColumn : smrHeaderCopy) {
                    String smrData = vDataMap.get(smrColumn);
                    if (smrData != null) {
                        smrData = smrData.replace('-', '_');
                    }
                    oneLine.add(smrData);
                }

                dataList.add(oneLine);
            }
        }

        this.runner.addDataToTable(this.tableName, dataList, this.reportTime);

        this.objectDataList.clear();
    }


}
