package com.kaishengit.web;

import com.kaishengit.pojo.Node;
import com.kaishengit.pojo.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");//获取节点id
        String p = req.getParameter("p");//页码
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        //如果nodeid不是数字则跳出此方法
        if(!StringUtils.isEmpty(nodeid)&&!StringUtils.isNumeric(nodeid)){
            forward("index",req,resp);
            return;
        }
        //查询所有节点
        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNode();

        Page<Topic> page =topicService.findAllTopic(nodeid,pageNo);
        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);
        forward("index",req,resp);
    }
}
