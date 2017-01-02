/**
 * Created by Mxia on 2016/12/31.
 */
$(function(){
   //使用轮询查找未读通知总数
    var loadNotify = function(){
        $.post("/notify",function(json) {
            if (json.count > 0) {
                 $("#countNotify").text(json.count);
            }
        });
    }
    //每次登陆后执行
    loadNotify();
    //每隔3分钟查找一次
    setInterval(loadNotify,180000);


});