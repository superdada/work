package cn.com.ailbb.manage;

import cn.com.ailbb.server.dao.SystemDao;
import cn.com.ailbb.util.HttpUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * Created by WildMrZhang on 2017/2/20.
 */
public class SystemManage implements ServletContextListener {
    public static double Version = 0; // 版本
    public static long Visits = 0; // 访问次数
    public static String LanguageType; // 系统语言
    public static int MinAccessTime = 1000*10;// 最小记录访问间隔10秒
    public static int MinHandlerTime = 1000*60; // 最小请求数据库系统版本信息
    public static String AppPath; // 系统所处路径
    public static String AppType; // 系统类型
    public static String AppName; // 系统类型

    private static Map<String, Long> accessMap = new HashMap<>();
    private static SystemDao systemDao;
    private static JdbcTemplate jdbcTemplate;

    public SystemManage(){}

    public synchronized static SystemDao getSystemDao() {
        return systemDao;
    }

    public synchronized static SystemDao setSystemDao(SystemDao systemDao) {
        return SystemManage.systemDao = systemDao;
    }

    public synchronized static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public synchronized static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        SystemManage.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 统计访问人数
     * @return
     */
    public synchronized static long countVisits(HttpServletRequest request){
        String ip = HttpUtil.getIp(request);
        long time = new Date().getTime();

        // 如果同一个IP在最大于访问时间内，或者不存在，则将访问人数增加1
        if(null == accessMap.get(ip) || time - accessMap.get(ip) > MinAccessTime) {
            accessMap.put(ip, time);
            getSystemDao().upDateVisits(Visits += 1);
        }

        return Visits;
    }

    /**
     * 开启定时扫描系统版本信息
     */
    public static void startHandler(){
        new Thread(() -> {
            SystemDao sys= getSystemDao();
            Version = sys.getSystemInfo().getVersion();
            LanguageType = sys.getSystemInfo().getLanguageType();
            Visits = sys.getSystemInfo().getVisits();
            MinAccessTime = sys.getSystemInfo().getMinAccessTime();
            MinHandlerTime  = sys.getSystemInfo().getMinHandlerTime();
            
            try {
                Thread.sleep(MinHandlerTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 初始化数据
     * @param systemDao
     * @param jdbcTemplateVertica
     */
    public static void initDao(SystemDao systemDao, JdbcTemplate jdbcTemplateVertica){
        setSystemDao(systemDao);
        setJdbcTemplate(jdbcTemplateVertica);
        startHandler();
    }

    /**
     * 初始化数据
     * @param AppPath
     * @param AppName
     */
    public static void initProperty(String AppPath, String AppName){
        SystemManage.AppPath = AppPath;
        SystemManage.AppName = AppName;
        SystemManage.AppType = System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows" : "linux";
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //设置项目根目录绝对路径
        String path = event.getServletContext().getRealPath("/");

        if(path.lastIndexOf(path) + 1 == path.length())
            path = path.substring(0,path.length()-1);

        SystemManage.initProperty(path, event.getServletContext().getContextPath());
    }

    /**
     * 初始化系统熟悉
     * @param event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
