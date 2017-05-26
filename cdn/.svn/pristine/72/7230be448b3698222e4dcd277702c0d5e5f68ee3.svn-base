package cn.com.ailbb.util;

import cn.com.ailbb.manage.SystemManage;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WildMrZhang on 2017/3/6.
 */
public class ExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class); // 日志记录
    private static int numCount = 0;
    private static String path = "/export/excel/"; // 相对路径
    private static String type_xls = ".xls";
    private static String type_xlsx = ".xlsx";

    /**
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param data 数据
     * @return 路径
     */
    public static String exportToExcel(String path, String fileName, List<Map<String,Object>> data, String type) throws Exception {
        return exportToExcel(path, fileName, null, data, type);
    }

    /**
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param data 数据
     * @param sheetName sheet名
     * @return 路径
     */
    public static String exportToExcel(String path, String fileName, String sheetName, List<Map<String,Object>> data, String type) throws Exception {
        List<Object> headers = new ArrayList<>();
        List<List<Object>> list = new ArrayList<>();
        for(Map<String,Object> map : data) {
            List<Object> li = new ArrayList<>();
            for(String key : map.keySet()) {
                if(headers.size() != map.keySet().size()) {
                    headers.add(key);
                }
                li.add(map.get(key));
            }
            list.add(li);
        }
        return exportToExcel(path, fileName, sheetName, headers, list, type);
    }

    /**
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param headers 导出title
     * @param data 数据
     * @param sheetName sheet名
     * @return 路径
     */
    public static String exportToExcel(String path, String fileName, String sheetName, List<Object> headers, List<List<Object>> data, String type) throws Exception {
        long t = System.currentTimeMillis();

        if(null == path) path = getPath() + TimeUtil.getNowFormatText();
        if(null == fileName) fileName = TimeUtil.getNowFormatText();
        if(null == type){
            type = type_xlsx;
        } else if(type.equalsIgnoreCase("xls") || type.equalsIgnoreCase(".xls")){
            type = type_xls;
        } else {
            type = type_xlsx;
        }

        path = (path+"/").replaceAll("//","/");
        fileName = fileName.toLowerCase().replace(type_xls,"").replace(type_xlsx,"") + type;
        String absolutePath = getAbsolutePath(path);

        File filePath = new File(absolutePath);
        File file = new File(absolutePath + fileName);
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            if(!filePath.exists()) filePath.mkdirs();

            Workbook wb;
            Sheet sheet;
            if(file.exists()) {
                fileInputStream = new FileInputStream(file);
                wb = type.equals(type_xlsx) ? new XSSFWorkbook(fileInputStream) : new HSSFWorkbook(fileInputStream);
            } else {
                wb = type.equals(type_xlsx) ? new XSSFWorkbook() : new HSSFWorkbook();
            }

            if(null == sheetName) {
                sheet = wb.createSheet();
            } else {
                sheet = wb.getSheet(sheetName);
                if(null == sheet)
                    sheet = wb.createSheet(sheetName);
            }
            numCount = 0;
            writeData(sheet, new ArrayList<List<Object>>(){{ add(headers);}}); // 写title
            writeData(sheet, data); // 写数据

            fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
        } finally {
            if(null != fileInputStream)
                fileInputStream.close();
            if(null != fileOutputStream)
                fileOutputStream.close();
        }

        logger.info(String.format("导出 [%s] 共 [%s] 条数据，耗时：[%s] 毫秒。", file.getName(), data.size(), (System.currentTimeMillis() - t)));
        return path + file.getName();
    }

    /**
     * 向sheet内写入数据
     * @param sheet
     * @param data
     * @return
     */
    private static void writeData(Sheet sheet, List<List<Object>> data){
        if(null != data)
            for (; numCount<data.size(); numCount++) {
                Row row = sheet.createRow(numCount);
                List<Object> d = data.get(numCount);
                for(int j=0; j<d.size(); j++) {
                    Object v = d.get(j);
                    if(null != v) {
                        int width = (short)(v.toString().getBytes().length * 256);

                        if (sheet.getColumnWidth(j) < width) // 宽度设置
                            sheet.setColumnWidth(j, width);
                    }
                    row.createCell(j).setCellValue(null == v ? "" : v.toString());
                }
            }

    }

    /**
     * 获取绝对路径
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        String r = SystemManage.AppType.equals("windows") ? File.separator+File.separator : File.separator;
        return (SystemManage.AppPath + path).replaceAll("/+", r).replaceAll("\\\\+", r);
    }

    public static String getPath() {
        return path + System.currentTimeMillis() + "/";
    }

    public static void setPath(String path) {
        ExcelUtil.path = path;
    }
}
