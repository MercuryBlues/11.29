<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/18
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
</head>
<body>
姓名<input type="text" name="name">
地址<input type="text" name="home">
<input type="button" value="提交"  name="search">

<table>
    <tbody>
    </tbody>
</table>

<script type="text/javascript">
    $(function () {
        $("input[name=search]").click(function () {
            var name = $("input[name=name]")
            var home= $("input[name=home]")
            var data={"name":name.val(),"home":home.val()}
            $.ajax({
                url:<%=request.getContextPath()%>"/search.action",
                data:JSON.stringify(data),
                dataType:"json",
                type:"post",
                contentType:"application/json",
                success:function (d) {
                    $("tbody").empty();
                    for(var i=0;i<d.length;i++){
                        $("tbody").append("<tr><td>>"+d[i].name+"</td><td>"+d[i].home+"</td></tr>")
                    }
                }
            })
        })
    })
</script>

</body>
</html>
