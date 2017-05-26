package cn.com.ailbb.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WildMrZhang on 2017/3/2.
 */
public class HttpUtil {
    public static String getIp(HttpServletRequest request) {
        return request.getRemoteHost();
    }
}
