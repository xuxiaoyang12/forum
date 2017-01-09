<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/29
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon"href="/static/img/notify.png">
    <title>   通知中心</title>
    <link href="/static/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-bell"></i> 通知中心</span>
        </div>
        <button id="markBtn" style="margin-left: 8px;" disabled class="btn btn-mini">标记为已读</button>
        <table class="table">
            <thead>
            <tr>
                <th width="30"><input type="checkbox" id="ckFather"></th>
                <th width="200">发布日期</th>
                <th>内容</th>
            </tr>
            </thead>
            <tbody>
                <c:if test="${empty notifyList}">
                    <tr>
                        <td colspan="2">
                            暂无通知！
                        </td>
                    </tr>
                </c:if>
                <c:forEach items="${notifyList }" var="notify">
                    <c:choose>
                        <c:when test="${empty notify.readtime}">
                            <tr>
                                <td><input value="${notify.id}" type="checkbox" class="ckSon"></td>
                                <td>${notify.createtime}</td>
                                <td>${notify.content }</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>

                                <td></td>
                                <td class="muted" style="text-decoration: line-through">${notify.createtime}</td>
                                <td class="muted" style="text-decoration: line-through">${notify.content }</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </tbody>
        </table>





    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){
        $("#ckFather").click(function(){
            var sons = $(".ckSon");
            for(var i = 0; i < sons.length; i++){
                sons[i].checked = $(this)[0].checked;
            }
            if($(this)[0].checked){
                $("#markBtn").removeAttr("disabled");
            }else {
                $("#markBtn").attr("disabled","disabled");
            }
        });
        $(".ckSon").click(function () {
            var num = 0;
            var sons = $(".ckSon");

            for(var i = 0; i < sons.length; i++){
                if(sons[i].checked){
                    num++;
                }
            }
           if(num==sons.length){
                $("#ckFather")[0].checked=true;
           }else{
               $("#ckFather")[0].checked=false;
           }
            if(num != 0){
                $("#markBtn").removeAttr("disabled");
            }else {
                $("#markBtn").attr("disabled","disabled");
            }
        })
        $("#markBtn").click(function(){
            var id = [];
            var sons = $(".ckSon");
            for(var i = 0 ; i < sons.length; i++){
                if(sons[i].checked){
                    id.push(sons[i].value);
                }
            }
            alert(id);
            $.post("/notify/read",{"ids":id.join(",")},function (data) {
                if(data=='success'){
//                    成功的话刷新页面
                    window.history.go(0);
                }
            })
        })
    })

</script>
</body>
</html>