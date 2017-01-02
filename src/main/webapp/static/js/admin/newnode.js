/**
 * Created by Mxia on 2016/12/28.
 */
$(function(){
   $("#newnodeBtn").click(function(){
       $("#newnodeForm").submit();
   })

    $("#newnodeForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
            nodename:{
                required:true,
                remote:"/admin/validate/node"
            }

        },
        messages:{
            nodename:{
                required:"请输入节点",
                remote:"节点已被占用"
            }

        },
        submitHandler:function(){
            $.ajax({
                url:"/admin/newnode",
                type:"post",
                data:$("#newnodeForm").serialize(),
                beforeSend:function(){
                    $("#newnodeBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        swal({title:"保存成功"},function(){
                            window.location.href="/admin/node";
                        })
                    }else{
                        swal(date.message);
                    }

                },
                error:function(){
                    swal("服务器错误");
                },
                complete:function(){
                    $("#newnodeBtn").text("保存").removeAttr("disabled");

                }
            })



        }
    })
});