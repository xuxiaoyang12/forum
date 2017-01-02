package com.kaishengit.web.admin;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/28.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().removeAttribute("curr_admin");
        forward("admin/adminLogin",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminName = req.getParameter("adminName");
        String password = req.getParameter("password");
        //获取登录ip
        String ip = req.getRemoteAddr();

        AdminService adminService = new AdminService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            Admin admin = adminService.adminLogin(adminName, password, ip);
            //管理员账户存入session
            req.getSession().setAttribute("curr_admin",admin);
            map.put("state","success");

        }catch(ServiceException e){
            map.put("state","error");
            map.put("message",e.getMessage());

        }

        renderJSON(map,resp);

    }
}
