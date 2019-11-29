<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/19
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<script src="layui/layui.all.js"></script>
<script>
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;
        layer.msg('Hello World');
    });
</script>
 姓名<input  type="text" name="loginName">
 密码<input type="text" name="password">
<button type="button" class="layui-btn layui-btn-radius">登陆</button>
</body>
</html>
