package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.Node;
import com.kaishengit.pojo.Topic;
import com.kaishengit.pojo.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/27.
 */
@WebServlet("/editTopic")
public class EditTopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.get("qiniu.bucket"),null,3600,stringMap);

        //获取nodelist到jsp页面

        if(StringUtils.isNumeric(topicid)) {
            TopicService topicService = new TopicService();
            Topic topic = topicService.findTopicById(topicid);
            User user =getCurrentUser(req);

            //再次验证编辑是否过期 并且 是否是当前用户
            if( user.getId().equals(topic.getUserid())&& topic.isEdit()) {
                List<Node> nodeList = topicService.findAllNode();
                req.setAttribute("nodeList", nodeList);
                req.setAttribute("topic", topic);
                req.setAttribute("token",token);
            }
            forward("topic/edittopic", req, resp);
        }else{
            resp.sendError(404);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String title = req.getParameter( "title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        User user = getCurrentUser(req);
        //更新数据
        TopicService topicService = new TopicService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            topicService.editTopic(topicid, title, content, nodeid, user);
            map.put("state","success");
            map.put("data",topicid);
        }catch(ServiceException e){
            map.put("state","error");
            map.put("message",e.getMessage());
        }
        renderJSON(map,resp);
    }
}
