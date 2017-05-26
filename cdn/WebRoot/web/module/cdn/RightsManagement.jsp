<%--
  Created by IntelliJ IDEA.
  User: xzl
  Date: 2017/5/24
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限管理</title>
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
</head>

<style type="text/css">
    iframe {
        height: 100%;
        width: 100%;
        padding: 0;
        border-width: 0;
        background-color: white;
    }

    body {
        /*width: 1200px;*/
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
        overflow-y: hidden;
        overflow-x: hidden;
    }

    ul li {
        background-color: white;
    }
</style>
<body>
<div class="layui-tab" style="margin:0 auto;width: 1200px;overflow-x: visible;">
    <ul class="layui-tab-title">
        <li class="layui-this">用户管理</li>
        <li>用户组管理</li>
    </ul>
    <div class="layui-tab-content" style="padding: 0;height: 100%;" >
        <div class="layui-tab-item layui-show">
            <iframe src="./UserManagent.jsp"></iframe>
        </div>
        <div class="layui-tab-item layui-show">
            <iframe src="./GroupManagent.jsp"></iframe>
        </div>
    </div>
</div>
</body>
</html>
