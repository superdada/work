<%--
  Created by IntelliJ IDEA.
  User: WildMrZhang
  Date: 2017/1/19
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp"%>
<%
    response.setHeader("Access-Control-Allow-Origin", "*");
%>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">
        <meta charset="utf-8">
        <title>magic lake 登录</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link type="text/css" rel="stylesheet" href="web/module/login/css/login.css">
        <script type="text/javascript" charset="utf-8" src="web/module/login/js/jquery-1.11.1.min.js"></script>
    </head>

    <body style="background-image:url(web/module/login/images/bg.png)">
        <div id="my-login-box">
            <form id="my-form" method="post" action="<%=AppBase%>/login.do?method=request1" target="hidden_frame" novalidate>
                <div class="my-logo">
                    <img src="web/module/login/images/logo-2.png" style="width:200px;margin-left:35px">
                </div>
                <div class="my-login-info">
                    <label class="my-login-text">用户：</label>
                    <input type="text"  id="my-username" name="username" value="admin" class="my-input"  required>
                </div>
                <div class="my-login-info">
                    <label class="my-login-text">密码：</label>
                    <input type="password"  id="my-password" name="password" value="123456" class="my-input " required>
                </div>
                <div id="my-login">
                    <input type="submit" class="my-btn"  value="登录">
                </div>
                <div id="error-msg" style="color: wheat; margin-top: 10px; line-height: 25px;"></div>
            </form>
        </div>

        <iframe name="hidden_frame"id="hidden_frame" style="display:none"></iframe>
        <script>
            var AppBase = "<%=AppBase%>";
            $("#hidden_frame").load(function(){
                try {
                    var data = $.parseJSON($(window.frames['hidden_frame'].document.body).html());
                    if(data != null)
                        if(!data.success){
                            $('#error-msg').html(data.msg);
                        } else {
                           location.href = AppBase+data.data;
                           // alert(AppBase+data.data);
                        }
                } catch (e) {
                    $('#error-msg').html(" 失败（0）：登陆异常！<br/> Error（0）：Login Fail!");
                }
            });
        </script>
    </body>
</html>