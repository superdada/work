<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/15
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ page isELIgnored="false" %>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<html>
<head>
    <title>性能分析</title>


    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="js/libs/echarts.min.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="components/layui/lay/modules/laydate.js"></script>


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
                height: 100%;
                padding: 0;
                margin: 0;
                overflow: hidden;
            }

        </style>
    </head>
    <body style="overflow-y: hidden">
    <%@include file="commonCondition.jsp" %>
    <div id="scrollDiv" style="width: 100%;">
        <div id="chartDiv">
            <div id="chart1" class="charts"></div>
            <div id="chart2" class="charts"></div>
            <div id="chart3" class="charts"></div>
            <div id="chart4" class="charts"></div>
            <div id="chart5" class="charts"></div>
            <div id="chart6" class="charts"></div>
            <%-- <div id="chart4">
                 <div id="chart4_1" style="height: 100%;"></div>
                 <div id="chart4_2" style="height: 100%;"></div>
             </div>
             <div id="chart5" style="width:100%"></div>
             <div id="chart6" style="width:75%"></div>
             <div id="chart7" style="width:25%"></div>--%>
    </div>
</div>
</body>
</html>
<script src="js/performanceAnalysis.js"></script>
<script src="js/echartsOptions.js"></script>
<script src="js/base.js"></script>
