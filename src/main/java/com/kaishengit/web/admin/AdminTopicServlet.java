package com.kaishengit.web.admin;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.Node;
import com.kaishengit.pojo.Topic;
import com.kaishengit.service.AdminService;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mxia on 2016/12/28.
 */
@WebServlet("/admin/topic")
public class AdminTopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");//得到页码
        Integer pageNo = StringUtils.isNumeric(p) ? Integer.valueOf(p):1;

        TopicService topicService = new TopicService();
        Page<Topic> page = topicService.findAllTopic("",pageNo);
        List<Node> nodeList = topicService.findAllNode();
        System.out.println(nodeList);

        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);

        forward("/admin/adminTopic",req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid  =req.getParameter("id");
        //通过主题id 删除帖子
        AdminService adminService = new AdminService();
        try {
            adminService.delByTopicid(topicid);

            renderText("success" ,resp);

        }catch (ServiceException e){
            renderText(""+e.getMessage()+"",resp);
        }

    }
}
