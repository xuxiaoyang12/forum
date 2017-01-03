<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员系统管理</title>
    <link href="/static/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavber.jsp"%>
<div>


</div>
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>新主题数</th>
            <th>新回复数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="topicReplynum">
        <tr>
            <td>
                ${topicReplynum.time}
            </td>
            <td>${topicReplynum.topicnum}</td>
            <td>${topicReplynum.replynum}</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;font-size: 22px"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<%--<script src="/static/js/user/notify.js"></script>--%>
<script>
    //分页框架
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}&nodeid=${param.nodeid}'
        });
    })
</script>
</body>
</html>

