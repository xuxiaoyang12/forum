package com.kaishengit.web.admin;

import com.kaishengit.pojo.Node;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/28.
 */
@WebServlet("/admin/validate/node")
public class AdminValidatenodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String nodename = req.getParameter("nodename");
        nodename = StringUtils.isoToUtf8(nodename);//转码
        TopicService topicService = new TopicService();
        if(nodeid!=null) {
            Node node2 = topicService.findNodeById(nodeid);
            if(node2.getNodename().equals(nodename)){
                renderText("true",resp);
                return;
            }
        }


        Node node  = topicService.findNOdeByName(nodename);
        if(node==null){
            renderText("true",resp);
        }else{

            renderText("false" ,resp);
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
