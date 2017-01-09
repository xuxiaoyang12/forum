<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="/static/img/detail.png">
    <title>Mixa论坛-${topic.title}</title>
    <link href="/static/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/styles/solarized-light.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
        .topic-body img {
            width: 200px;
        }
        pre {
            padding: 0px;
            border: 0px;
            background-color: transparent;
        }
        #ckepop{
            padding-left:20px;
        }
        .jiathis_style{
            padding-left:20px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active">${requestScope.topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="${requestScope.topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${requestScope.topic.title}</h3>
            <p class="topic-msg muted"><a href="">${requestScope.topic.user.username}</a> · <span id="topicTime">${requestScope.topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${requestScope.topic.content} </div>
        <div class="topic-toolbar">
            <ul class="unstyled inline pull-left">
                <c:choose>
                    <c:when test="${not empty fav}">
                        <li><a href="javascript:;" id="favtopic">取消收藏</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="javascript:;" id="favtopic">加入收藏</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty thanks}">
                        <li><a href="javascript:;" id="thanksTopic">已感谢</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="javascript:;" id="thanksTopic">感谢</a></li>
                    </c:otherwise>
                </c:choose>

                <c:if test="${requestScope.topic.userid==sessionScope.curr_user.id and requestScope.topic.edit }">
                    <li><a href="/editTopic?topicid=${topic.id}">编辑</a></li>
                </c:if>
            </ul>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.clicknum}次点击</li>
                <li > <span id="topicFav">${topic.favnum}</span> 人收藏</li>
                <li ><span id="thankTopic">${topic.thankyounum}</span> 人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <c:if test="${not empty replyList}">
            <div class="talk-item muted" style="font-size: 12px">
                ${fn:length(replyList)}个回复 | 直到<span id="lastreplytime">${topic.lastreplytime}</span>
            </div>
        </c:if>
        <c:forEach items="${replyList}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <a name="reply${vs.count}"></a>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px" class="reply">${reply.createtime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="javascript:;" rel="${vs.count}" class="replyLink" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </div>
    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;">
                <a name="reply"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/newReply" method="post" id="replyForm" style="padding: 15px;margin-bottom:0px;">
                    <input name="topicid" type="hidden" value="${topic.id}">
                    <textarea name="content" id="editor"></textarea>
                </form>
                <%--<!-- JiaThis Button BEGIN -->--%>
                <%--<div id="ckepop">--%>
                    <%--<span class="jiathis_txt">分享到：</span>--%>
                    <%--<a class="jiathis_button_tsina">新浪微博</a>--%>
                    <%--<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank">更多</a>--%>
                    <%--<a class="jiathis_counter_style"></a>--%>
                <%--</div>--%>
                <%--<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>--%>
                <%--<!-- JiaThis Button END -->--%>
                <!-- JiaThis Button BEGIN -->
                <div class="jiathis_style">
                    <span class="jiathis_txt">分享到：</span>
                    <%--<a class="jiathis_button_qzone"></a>--%>
                    <a class="jiathis_button_tsina"></a>
                    <a class="jiathis_button_tqq"></a>
                    <a class="jiathis_button_weixin"></a>
                    <a class="jiathis_button_renren"></a>
                    <a class="jiathis_button_xiaoyou"></a>
                    <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                    <a class="jiathis_counter_style"></a>
                </div>
                <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
                <!-- JiaThis Button END -->
                <div class="talk-item muted" style="text-align: right;font-size: 12px"><br/>
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button id="replyBtn" class="btn btn-primary">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="box" style="margin:20px 0px;">
                <div class="talk-item"> 请<a href="/login?redirect=topicDetail?topicid=${topic.id}#reply">登录</a>后再回复</div>
            </div>
        </c:otherwise>
    </c:choose>

</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/highlight.pack.js"></script>


<script>
    $(function(){
        //把时间转换成**前
        $("#topicTime").text(moment($("#topicTime").text()).fromNow());
        $("#lastreplytime").text(moment($("#lastreplytime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        //回复贴时间应为没有固定id 所以用一个函数来确定每一个的时间段
        $(".reply").text(function () {
            var time = $(this).text();
            return moment(time).fromNow();
        });


//        文本编辑器js
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
            //optional options
        });
        //点击提交回复表单
        $("#replyBtn").click(function(){
           $("#replyForm").submit();
        });



        //添加回复信息的锚标记
        $(".replyLink").click(function(){
            var count = $(this).attr("rel");
            var html = "<a href='#reply"+count+"'>#"+ count +"</a>";
            editor.setValue(html + editor.getValue());
            window.location.href="#reply";
        });

        //改变代码高亮风格
        hljs.initHighlightingOnLoad();


        //加入收藏
        $("#favtopic").click(function(){
            var $this = $(this);
            var action="";
            if($this.text()=='加入收藏'){
                action = "fav";
            }else{
                action = "unfav";
            }
            $.post("/topicFav",{"topicid":${topic.id},"action":action})
                .done(function(json){
                    if(json.state == "success"){
                        if(action == "fav"){
                            $this.text("取消收藏");
                        }else{
                            $this.text("加入收藏");
                        }
                        $("#topicFav").text(json.data)
                    }
                }).error(function(){
                    alert("收藏失败");
            })
        })

        //当点击感谢/已感谢时加入感谢或取消感谢
        $("#thanksTopic").click(function () {
            //获取文本
            var $this = $(this);
            var thanksText = $(this).text();
            var action ="";
            //判断文本是事感谢还是已感谢
            if(thanksText=="感谢"){
                //添加感谢
                action="thanks"
            }else{
                action="unthanks"
            }
            //异步post请求
            $.post("/thanksTopic",{"topicid":${topic.id},"action":action})
                .done(function(json){
                if(json.state=='success'){
                    if(action=="thanks"){
                        $this.text("已感谢");
                    }else{
                        $this.text("感谢");
                    }
                    $("#thankTopic").text(json.data)
                } else{
                    alert(json.message)
                }


        }).error(function(){
            alert("服务器错误，请稍后重试！")
        })
        });
    });

</script>

</body>
</html>
