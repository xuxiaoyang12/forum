package com.kaishengit.web.admin;

import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/29.
 */
@WebServlet("/admin/logout")
public class AdminLogoutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //管理员安全退出
        req.getSession().removeAttribute("curr_admin");
        req.setAttribute("message","您已安全退出");
        forward("admin/adminLogin",req,resp);
    }
}
