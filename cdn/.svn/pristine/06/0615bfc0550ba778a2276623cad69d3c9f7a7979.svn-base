package cn.com.ailbb.manage;

import cn.com.ailbb.handler.AlarmHandler;
import cn.com.ailbb.server.dao.SystemDao;
import cn.com.ailbb.util.MailUtil;
import com.sun.deploy.ui.AppInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * 在项目启动时，如果要执行操作，可以在applicationContext.xml里面配置bean。 <bean class="com.ailbb.manage.InitManage"></bean>
 * 若要使用系统连接bean需要实现ApplicationListener<ContextRefreshedEvent> 接口
 * Created by WildMrZhang on 2016/12/26.
 */
@Component("DataSourceInitListener")
public class InitManage implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = Logger.getLogger(InitManage.class);

    @Resource
    SystemDao systemDao;
    @Autowired
    JdbcTemplate jdbcTemplateVertica;
    @Resource
    AlarmHandler alarmHandler;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        //防止重复执行。
        if(ev.getApplicationContext().getParent() == null){
            init();
        }
    }
    public void init(){
        try {
            logger.info("info：初始化资源开始加载...");
            SystemManage.initDao(systemDao, jdbcTemplateVertica); // 系统信息扫描
            MailUtil.init(); // 加载mail信息
            alarmHandler.startHandler(); // 开始告警循环
            logger.info("info：初始化资源已经加载...");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("info：初始化资源加载失败" + e.getMessage() + e.getCause());
        }
    }

}
