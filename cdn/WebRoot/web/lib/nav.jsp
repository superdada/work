<%--
  Created by IntelliJ IDEA.
  User: WildMrZhang
  Date: 2017/1/19
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%
    String AppBase = application.getContextPath();
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
