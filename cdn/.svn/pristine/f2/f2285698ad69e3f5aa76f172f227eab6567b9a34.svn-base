package cn.com.ailbb.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Properties;

/**
 * 发送邮件
 * Created by WildMrZhang on 2017/3/6.
 */
public class MailUtil {
    private static boolean isEnable; // 是否启用
    private static Properties props; // 环境变量信息
    private static String debug; // 开启debug调试
    private static String auth; // 发送服务器需要身份验证
    private static String host; // 设置邮件服务器主机名
    private static String protocol; // 发送邮件协议名称
    private static String formInternetAddress; // 发件人邮箱
    private static String username; // 邮件用户名
    private static String password; // 邮件密码
    private static String[] alarmReceiveInternetAddress; // 告警接收邮件人邮箱
    private static String properties = "/mail.properties"; // 文档名称

    private static Logger logger = Logger.getLogger(MailUtil.class); // log4j日志

    /**
     * 初始化信息
     * @return
     */
    public static boolean init() throws Exception {
        props =  new  Properties();
        props.load(new InputStreamReader((PropertiesUtil.getProperties(properties)), "UTF-8"));
        isEnable = Boolean.parseBoolean(props.getProperty("mail.enable").trim());
        debug = props.getProperty("mail.debug").trim();
        auth = props.getProperty("mail.smtp.auth").trim();
        host = props.getProperty("mail.host").trim();
        protocol = props.getProperty("mail.transport.protocol").trim();
        formInternetAddress = props.getProperty("mail.formInternetAddress").trim();
        username = props.getProperty("mail.username").trim();
        password = props.getProperty("mail.password").trim();
        alarmReceiveInternetAddress = props.getProperty("mail.alarmReceiveInternetAddress").trim().split(",");
        return true;
    }

    /**
     * 发送邮件
     * @param sendInternetAddress 邮件地址
     * @param title 标题
     * @param text 内容
     * @throws Exception
     */
    public static void send(String sendInternetAddress, String title, String text) throws Exception {
        if(!isEnable) return;

        // 设置环境信息
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 创建邮件对象
        Message msg = new MimeMessage(session);
        msg.setSubject(title);
        // 设置邮件内容
        msg.setText(text);
        // 设置发件人
        msg.setFrom(new InternetAddress(formInternetAddress));
        Transport transport = session.getTransport();
        // 连接邮件服务器
        transport.connect(username, password);
        // 发送邮件
        transport.sendMessage(msg, new Address[] {new InternetAddress(sendInternetAddress)});
        // 记录日志
        logger.info(String.format("向 [ %s ] 发送邮件，内容 [ %s ]", sendInternetAddress, msg));
        // 关闭连接
        transport.close();
    }

    public static Properties getProps() {
        return props;
    }

    public static void setProps(Properties props) {
        MailUtil.props = props;
    }

    public static String getDebug() {
        return debug;
    }

    public static void setDebug(String debug) {
        MailUtil.debug = debug;
    }

    public static String getAuth() {
        return auth;
    }

    public static void setAuth(String auth) {
        MailUtil.auth = auth;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        MailUtil.host = host;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static void setProtocol(String protocol) {
        MailUtil.protocol = protocol;
    }

    public static String getFormInternetAddress() {
        return formInternetAddress;
    }

    public static void setFormInternetAddress(String formInternetAddress) {
        MailUtil.formInternetAddress = formInternetAddress;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MailUtil.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MailUtil.password = password;
    }

    public static String[] getAlarmReceiveInternetAddress() {
        return alarmReceiveInternetAddress;
    }

    public static void setAlarmReceiveInternetAddress(String[] alarmReceiveInternetAddress) {
        MailUtil.alarmReceiveInternetAddress = alarmReceiveInternetAddress;
    }
}
