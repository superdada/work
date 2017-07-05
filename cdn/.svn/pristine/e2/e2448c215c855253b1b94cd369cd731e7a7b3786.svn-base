<%--
  Created by IntelliJ IDEA.
  User: xzl
  Date: 2017/5/24
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/web/lib/nav.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <!--<link rel="stylesheet" href="ztree/css/demo.css" type="text/css"> -->
    <link rel="stylesheet" href="ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="css/mycss/userrights.css">
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="ztree/js/jquery.ztree.core.min.js"></script>
    <script type="text/javascript" src="ztree/js/jquery.ztree.exedit.min.js"></script>
    <script src="js/libs/bootstrap.min.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="js/UserManagent.js"></script>

    <script type="text/javascript">
        var AppBase = "<%=AppBase%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录
    </script>
</head>
<style type="text/css">
    iframe {
        height: 100%;
        width: 100%;
        padding: 0;
        border-width: 0;
    }
    html,body {
        /*width: 1200px;*/
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
        overflow-y: hidden;
        overflow-x: hidden;
        background-color: #f3f3f3;
    }
</style>
<body>
   <div id="userpanel">
   <div id ="leftpannel" >
       <div class="title_li">组织机构</div>
       <ul id="tree-nav" class="ztree">
       </ul>
   </div>
   <div id ="rightpanel" >
       <div class="userlist">用户列表</div>
       <div class="userfunction">
           <ul>
               <li><a onclick="popupbox('adduser')">新增</a> <span>|</span></li>
               <li><a onclick="popupbox('edituser')">编辑</a> <span>|</span></li>
               <li><a onclick="popupbox('modifypaswd')">重置密码</a> <span>|</span></li>
               <li><a onclick="popupbox('allocategroup')">分配用户组</a> <span>|</span></li>
               <li><a  onclick="popupbox('delete')">删除</a> <span>|</span></li>
           </ul>
       </div>
       <table id="usertable" class="layui-table" style="margin:0;">
           <thead>
           <tr>
               <th>用户名</th>
               <th>用户真实姓名</th>
               <th>性别</th>
               <th>电话号码</th>
               <th>所属用户组</th>
               <th>创建时间</th>
               <th>最后一次登录时间</th>
           </tr>
           </thead>
           <tbody id="userinfo">
           </tbody>
       </table>
       <div class="my-page-control-box">
           <div class="my-page-goto">
               <div class="input-group">
                   <input id="my-page-num" type="text" class="form-control" placeholder="页数">
                    <span class="input-group-btn">
                        <button onclick ="changePage('jump')"  class="btn btn-default" type="button">跳转</button>
                    </span>
               </div>
           </div>
           <div onclick="changePage('next')" class="my-page-controller"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></div>
           <p class="my-page-text"></p>
           <div onclick ="changePage('Previous')" class="my-page-controller"><span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span></div>
       </div>
   </div>
   </div>
</body>
</html>
