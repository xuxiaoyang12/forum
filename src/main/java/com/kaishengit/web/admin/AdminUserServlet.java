package com.kaishengit.web.admin;

import com.google.common.collect.Maps;
import com.kaishengit.Vo.UserVo;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/28.
 */
@WebServlet("/admin/user")
public class AdminUserServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //查询用户数据和ip,最后登录时间
        UserService userService = new UserService();
       List<UserVo> userVoList =  userService.findUserList();
        req.setAttribute("userVoList",userVoList);
        forward("admin/adminuser",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  state = req.getParameter("state");
        String userid = req.getParameter("userid");

        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            userService.updateState(state, userid);
            map.put("state","success");
        }catch (ServiceException e ){
            map.put("state","error");
            map.put("message",e.getMessage());
        }
        renderJSON(map,resp);
    }
}
