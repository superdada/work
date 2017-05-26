<%--
  Created by IntelliJ IDEA.
  User: xzl
  Date: 2017/5/24
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="components/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="css/mycss/cdn.css">
    <script type="text/javascript" src="js/libs/jquery-1.11.1.min.js"></script>
    <script src="components/layer/layer.min.js"></script>
    <script src="components/layui/layui.js"></script>
    <script src="js/UserManagent.js"></script>
</head>
<body>
   <div id ="leftpannel" >
       <ul id="tree-nav">
           <li class="title_li">组织机构</li>
       </ul>

   </div>
   <div id ="rightpanel" >
       <div class="userlist">用户列表</div>
       <div class="userfunction">
           <ul >
               <li><a>新增</a> <span>|</span></li>
               <li><a>编辑</a> <span>|</span></li>
               <li><a>重置密码</a> <span>|</span></li>
               <li><a>分配用户组</a> <span>|</span></li>
               <li><a>删除</a> <span>|</span></li>
           </ul>
       </div>
   </div>
</body>
</html>
