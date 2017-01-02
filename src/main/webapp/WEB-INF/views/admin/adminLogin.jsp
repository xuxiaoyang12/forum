<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<div class="header-bar">
    <div class="container">
        <a href="#" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
    </div>
</div>
<!--header-bar end-->

<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i>管理员登录</span>
        </div>

        <form action="" id="loginForm" class="form-horizontal">
            <c:if test="${not empty message}">
                <div class="alert alert-primary">
                    ${requestScope.message};
                </div>
            </c:if>
            <div class="control-group">
                <label class="control-label">管理员账号</label>
                <div class="controls">
                    <input type="text" name="adminName">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="form-actions">
                <button id="loginBtn" class="btn btn-primary">登录</button>
            </div>
        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/admin/adminLogin.js"></script>
</body>
</html>