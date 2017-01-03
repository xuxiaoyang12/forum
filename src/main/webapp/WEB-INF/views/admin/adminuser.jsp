<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/29
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userVoList}" var="userVo">
        <tr>
            <td>${userVo.username}</td>
            <td>${userVo.createtime}</td>
            <td>${userVo.lastlogintime}</td>
            <td>${userVo.loginip}</td>
            <td>
              <c:choose>
                  <c:when test="${userVo.loginstate == '0' }">
                      <a href="javascript:;"  rel="${userVo.userid}"   class="updateState" >激活</a>
                  </c:when>
                  <c:otherwise>
                      <a href="javascript:;" rel="${userVo.userid}" class="updateState">${userVo.loginstate == '1'? '禁用':'恢复'}</a>
                  </c:otherwise>
              </c:choose>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pull-right">
        <ul>
            <li><a href="#">Prev</a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">Next</a></li>
        </ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function(){
        $(".updateState").click(function(){
            //获取用户状态
            var userid = $(this).attr("rel");
            var state = $(this).text();
            //异步请求
            $.ajax({
                url:"/admin/user",
                type:"post",
                data:{"state":state,"userid":userid},
                success:function(json){
                    if(json.state=='success'){
                        swal({title:"状态更改成功"},function () {
                            window.history.go(0);
                        })
                    }else{
                        swal(json.message);
                    }

                },
                error:function(){
                    swal("服务器错误 状态更改失败");
                }

            })





        })



    })


</script>
</body>
</html>

