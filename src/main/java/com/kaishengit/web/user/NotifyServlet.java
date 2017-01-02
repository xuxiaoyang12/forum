package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/29.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);//获取当前用户


        if(user==null){
            resp.sendRedirect("/login?redirect=/notify");
        }else {
            //通过用户获取此用户所有通知
            List<Notify> notifyList = userService.findNotifyListByUser(user);
            System.out.println(notifyList);
            req.setAttribute("notifyList", notifyList);
            forward("user/notify", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用轮询传给页面一个通知消息数据 数
        User user = getCurrentUser(req);
        Map<String ,Object> map = Maps.newHashMap();
        if(user==null){
            map.put("count",0);
        }else{
            //获取通知列表
            List<Notify> notifyList = userService.findNotifyListByUser(user);
            //查找里边中未读通知
            List<Notify> unReadList = new ArrayList<>();

            for(Notify notify : notifyList){
                if(notify.getReadtime()==null){
                    unReadList.add(notify);
                }
            }
            map.put("count",unReadList.size());
        }

        renderJSON(map,resp);







    }
}
