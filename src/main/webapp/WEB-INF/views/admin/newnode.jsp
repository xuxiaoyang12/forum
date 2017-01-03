<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/28
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/static/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
</head>
<body>
<%@include file="../include/adminNavber.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form id="newnodeForm" action="">
        <legend>添加新节点</legend>
        <label>节点名称</label>
        <input type="text" name="nodename"  >
        <div class="form-actions">
            <button type="button" id="newnodeBtn" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script src="/static/js/admin/newnode.js"></script>
</body>
</html>

