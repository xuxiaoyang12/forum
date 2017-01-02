package com.kaishengit.web.admin;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.StringUtils;
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
@WebServlet("/admin/updatenode")
public class AdminUpdatenodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        TopicService topicService = new TopicService();
        try {
            Node node = topicService.findNodeById(nodeid);
            req.setAttribute("node",node);
            forward("admin/updatenode",req,resp);
        }catch (ServletException e){
            e.getMessage();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodename = req.getParameter("nodename");
        String nodeid = req.getParameter("id");
        System.out.print("---------------");
        //更改节点
        TopicService topicService = new TopicService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            topicService.updateNode(nodeid, nodename);
            map.put("state","success");

        }catch(ServiceException e ){
            map.put("state","error");
            map.put("message",e.getMessage());
        }

        renderJSON(map,resp);
    }
}
