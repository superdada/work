<%--
  Created by IntelliJ IDEA.
  User: sir.ZhangWei
  Date: 2017/3/9
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp"%>
<%
    String dimension = request.getParameter("dimension");
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title><%=null == dimension || dimension.equals("data_center") ? "数据中心" : "ICP"%> - 分享页面</title>

    <meta charset="UTF-8">

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <!--bootstrap适应mobile声明-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--END-->


    <script>
        /**
         * @type: // 请求配置（无配置默认请求所有库文件）
         * @动态库(reqLibraries)有：['jQuery', 'ext', 'd3', 'eCharts', 'highCharts']
         * @插件(reqPlugins)有：['bootstrap', 'highCharts3d']
         *   requireConfig = {
             *      navScript: string,  // 动态库的nav文件
             *      libBaseURL: string, // 动态库根路径 （默认通过nav去寻找）
             *      reqLibraries: Array, // 需要请求的动态库
             *      reqPlugins: Array, // 需要请求的插件
             *      libVersions: {}, // 多个版本切换 （请保持目录结构一致）
             *		versionURL: win.versionURL, // 系统版本库地址
             *		useMask: boolean, // 是否启动时显示遮罩，默认为true
             *      style: { // ext多种样式切换
             *          extStyle || 'triton' // aria/classic/crisp/gray/neptune/triton
             *      }
             *   }
         **/
        var AppBase = '<%=AppBase%>';
        var baseURL = AppBase+"/web/module/cdn/fenxiang"; // 模块根目录

        var requireConfig = {
            userMask: false,
            reqLibraries: [
                'ext',
                'jQuery',
                baseURL+'/view.js' // 加载自定义文件，路径为根目录
            ],
            reqPlugins: ['bootstrap']
        };

        var request_id = <%=request.getParameter("id")%>;
        var dimension = '<%=dimension%>';
        var index = '<%=request.getParameter("index")%>';
        var cache_cdn = <%=request.getParameter("cache_cdn")%>;
    </script>

</head>

<body>
    <!--链接文件导航-->
    <script data-main="web/lib/nav.min" src="web/lib/RequireJS/require-2.3/require.min.js"></script>
</body>
</html>