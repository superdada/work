package cn.com.ailbb.handler;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by WildMrZhang on 2017/1/20.
 */
public class HttpHandler {
    /**
     * 封装发送json消息
     * @param response
     * @param object
     */
    public static void send(HttpServletResponse response, Object object){
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(JSONObject.fromObject(object));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
