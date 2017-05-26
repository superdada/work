<%--
  Created by IntelliJ IDEA.
  User: xzl
  Date: 2017/5/22
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/web/lib/nav.jsp" %>
<% String data = request.getParameter("filePath");%>
<html>
<head>
    <title>CDN日志分析系统</title>
    <script>
        var AppBase = "<%=AppBase%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录
    </script>
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="js/cdn_index.js"></script>
    <style type="text/css">
        body {
            height: 100%;
            width: 100%;
            overflow-y: hidden;
            background-color: #f3f3f3;
        }
    </style>
</head>

<body>
<div id="scroll">
    <div class="header">
        <div class="my-header">
            <div class="" id="headerLogo"><img src="../login/images/logo-2.png" class="my-logo1"></div>
            <div>
                <span class="my-quit my-float-right" aria-hidden="true">
                    <a class="signOutImg" onclick="signOutEvent()" title="退出"></a>
                </span>
            </div>
        </div>
    </div>
</div>

<div class="layui-tab-content" style="padding: 0;height: 90%;margin: 1px auto">
    <div class="layui-tab-item layui-show">
        <iframe id="changeTab" style="height:100%;width: 100%;
            border-width:0;padding: 0"
        ></iframe>
    </div>
</div>
</body>
</html>
