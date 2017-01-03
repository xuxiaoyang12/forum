<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/28
  Time: 17:41
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
    <form  id="updatenodeForm" action="">
        <legend>编辑节点</legend>
        <label>节点名称</label>
        <input type="hidden" name="id" value="${node.id}">
        <input type="text" name="nodename" value="${node.nodename}">
        <div class="form-actions">
            <button id="updatenodeBtn" type="button" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function(){

        $("#updatenodeBtn").click(function(){
            $("#updatenodeForm").submit();
        });

        $("#updatenodeForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required:true,
                    remote:"/admin/validate/node?nodeid=${node.id}"
                }
            },
            messages:{
                nodename:{
                    required:"请输入节点名称",
                    remote:"节点已存在！"
                }

            },
            submitHandler:function(){
                $.ajax({
                    url:"/admin/updatenode",
                    type:"post",
                    data:$("#updatenodeForm").serialize(),
                    beforeSend:function(){
                        $("#updatenodeBtn").text("保存中...").attr("disabled","disabled");
                    },
                    success:function(data){
                        if(data.state='success'){
                            //window.location.href="/admin/node";
                            swal({title:"修改成功"},function () {
                                window.location.href = "/admin/node";
                            });
                        }else{
                            swal(data.message)
                        }

                    },
                    error:function(){
                        swal("服务器错误");
                    },
                    complete:function(){
                        $("#updatenodeBtn").text("保存").removeAttr("disabled");
                    }


                });
            }
        })
    });



</script>
</body>
</html>

