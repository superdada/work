<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp" %>
<html>
<head>
    <title>故障排查</title>
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <script type="text/javascript">
        var AppBase = "<%=AppBase%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录
        var conditionReflesh = {

        };
        //多选框的处理
        var conditionReflesh_multiple = {
            'ICP2':{conId:'domain2'},
            'domain2':{conId: ""},
            'server2':{conId: ""}
        };
    </script>
    <script src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="js/ajax.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="components/layui/lay/modules/laydate.js"></script>
    <script src="js/base.js"></script>

    <script src="js/libs/echarts.min.js"></script>
    <script src="js/getConfigOption.js"></script>
    <style type="text/css">
        body {
            height: 100%;
            padding: 0;
            margin: 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<%@include file="commonCondition.jsp" %>
<div id="scrollDiv" style="width: 100%;height: 100%;">
    <div id="chartIndex">
        <div id="statusCode" class="chart_content"></div>
        <div id="hitRate" class="chart_content"></div>
        <div id="delay" class="chart_content"></div>
    </div>
</div>
</body>
</html>
<script src="js/troubleShooting.js"></script>

