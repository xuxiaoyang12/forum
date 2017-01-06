package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/31.
 */
@WebServlet("/notify/read")
public class NotifyReadServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        //User user = getCurrentUser(req);
        UserService userService = new UserService();
        userService.readNotify(ids);
        renderText("success",resp);
    }
}
