<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page isELIgnored="false" %>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/> --%>
<html>
<head>
    <title>CDN日志分析系统</title>
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/components/layer/skin/layer.css">--%>
    <script type="text/javascript">
        var AppBase = "<%=AppBase%>";
        var base ="<%=basePath%>";
        var base2 ="<%=path%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录
    </script>
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="js/ajax.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="components/layui/lay/modules/laydate.js"></script>
    <script src="js/base.js"></script>

    <script src="js/libs/echarts.min.js"></script>
    <%--<script src="${ctx}/admin/app/unicom_cdn/js/errorAnalysisOption.js"></script>--%>
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
    <ul class="layui-tab-title" style="width: 100%">
        <li class="layui-this">性能分析</li>
        <li>错误分析</li>
        <li>问题排查</li>
        <li>流量与带宽</li>
        <li>下载带宽</li>
    </ul>
    <div class="layui-tab-content" style="padding: 0;height: 100%;" id="tabDiv">
        <div class="layui-tab-item layui-show" style="padding: 0px;height: 100%;">
            <iframe src="./performanceAnalysis.jsp"></iframe>
        </div>
        <div class="layui-tab-item layui-show" style="padding: 0;height: 100%;">
            <iframe src="./errorCodeAnalysis.jsp"></iframe>
        </div>
        <div class="layui-tab-item layui-show" style="padding: 0;height: 100%;">
            <iframe src="./troubleShooting.jsp"></iframe>
        </div>
        <div class="layui-tab-item layui-show" style="padding: 0;height: 100%;">
            <iframe src="./flowAndBandwidth.jsp"></iframe>
        </div>
        <div class="layui-tab-item layui-show" style="padding: 0;height: 100%;">
            <iframe src="./downloadBindWidth.jsp"></iframe>
        </div>
    </div>
</div>

</body>
</html>
<script>
    layui.use('element', function () {
    });
</script>