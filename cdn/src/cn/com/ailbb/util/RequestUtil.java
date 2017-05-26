package cn.com.ailbb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**Request����Ĺ�����
 * modify on Jul 2, 2013 11:22:43 AM 
 * @author tangyj
 */
public class RequestUtil {

	/**�첽���󷵻�
	 * @param encoding �����ʽ
	 * @param data data
	 * @param response HttpServletResponse
	 */
	public static void responseOut(String encoding, String data,
			HttpServletResponse response) {
		response.setContentType("text/html; charset=" + encoding);
		try {
			PrintWriter pw = response.getWriter();
			pw.print(data);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**��ȡrequest���������в����������õ�map��
	 * @param request HttpServletRequest
	 * @return ��request�����������װ��map������URL����ͨ��form���ύ�Ĳ�����
	 */
	public static Map getMapByRequest(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String paraValue = request.getParameter(paraName);
			if (paraValue != null && !"".equals(paraValue)) {
				map.put(paraName, paraValue.trim());
			}
		}
		if (DataUtil.checkObj(request.getAttribute("sUrl"))) {
			map.put("sURLs", request.getAttribute("sUrl").toString());
		}
		return map;
	}

}