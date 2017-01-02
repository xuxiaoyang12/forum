package com.kaishengit.web.admin;

import com.google.common.collect.Maps;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
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
@WebServlet("/admin/del/node")
public class AdminDelNodeServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("id");

        TopicService topicService = new TopicService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            topicService.delNodeById(nodeid);
            map.put("state","success");
        }catch (ServiceException e ){
            map.put("state","error");
            map.put("message",e.getMessage());

        }
        renderJSON(map,resp);

    }
}
