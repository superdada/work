<%--
  Created by IntelliJ IDEA.
  User: zhongguohua
  Date: 2017/3/20
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp" %>
<html>
<head>
    <title>流量与带宽</title>

    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">

    <script src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="js/libs/echarts.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="components/layui/lay/modules/laydate.js"></script>
    <script src="components/layer/layer.min.js"></script>

    <script>
        var AppBase = "<%=AppBase%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录
        //选项框联动关系,单选框
        var conditionReflesh = {
            'cityName1': {conId: ""},
            'cityName2': {conId: ""},
            'dataCenter':{conId: ""},
            'ICP1':{conId:'domain1'},
            'domain1':{conId: ""},
            // 'serviceTypeCategory':{conId: ""},
            // 'serviceNameCategory':{conId: ""},
            'server1':{conId: ""},
        };
        //多选框的处理
        var conditionReflesh_multiple = {
        };
    </script>

    <style type="text/css">
        body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
            overflow-y: hidden;
        }

    </style>
</head>
<body>
<%@include file="commonCondition.jsp" %>
<div id="scrollDiv" style="width: 100%;">
    <div id="chartDiv">
        <div id="chart_flow" class="singleChart"></div>
        <div id="chart_flow2" class="charts"></div>
        <div id="chart_flow3" class="charts"></div>
    </div>
</div>
</body>
</html>
<script src="js/flowAndBandwidth.js"></script>
<script src="js/echartsOptions.js"></script>
<script src="js/base.js"></script>
