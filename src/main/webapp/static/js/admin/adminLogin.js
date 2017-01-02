/**
 * Created by Mxia on 2016/12/28.
 */
$(function(){
    //    获取url中的后缀
    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    //点击事件 当点击登录按钮是提交表单
    $("#loginBtn").click(function(){
        $("#loginForm").submit();
    });
    $("#password").keydown(function(event){
        if(event.keyCode==13){
            $("#loginForm").submit();
        }
    })

    //登录表单验证规则
    $("#loginForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            adminName:{
                required:true
            },
            password:{
                required:true
            }
        },
        messages:{
           adminName:{
                required:"请输入账号"
            },
            password:{
                required:"请输入密码"
            }
        },
        //表单提交应该做得事情
        submitHandler:function(form){
            $.ajax({
                url:"/admin/login",
                type:"post",
                data:$(form).serialize(),
                //提交之前
                beforeSend:function(){
                    $("#loginBtn").text("登录中...").attr("disabled","disabled");
                },
                //提交成功
                success:function(data){
                    if(data.state == 'success') {
                        var url = getParameterByName("redirect");
                        if(url) {
                            var hash = location.hash;
                            if(hash){
                                window.location.href = url + hash;
                            } else {
                                window.location.href = url;
                            }
                        } else {
                            window.location.href = "/admin/home";
                        }
                    } else {
                        alert(data.message);
                    }
                },
                //提交失败
                error:function(){
                    alert("服务器错误");
                },
                //不管成功还是失败
                complete:function(){
                    $("#loginBtn").text("登录").removeAttr("disabled");
                }
            });
        }
    });



});
