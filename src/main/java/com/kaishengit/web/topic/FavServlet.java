package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.pojo.Topic;
import com.kaishengit.pojo.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/27.
 */
@WebServlet("/topicFav")
public class FavServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String topicid = req.getParameter("topicid");
        User user = getCurrentUser(req);
        TopicService topicService = new TopicService();
        JsonResult result = new JsonResult();
        if(StringUtils.isNotEmpty(action)&&StringUtils.isNumeric(topicid)){
            if(action.equals("fav")){
                topicService.favTopic(user,topicid);
                result.setState(JsonResult.SUCCESS);
            }else if(action .equals("unfav")){
                topicService.unfavTopic(user,topicid);
                result.setState(JsonResult.SUCCESS);
            }

            Topic topic = topicService.findTopicById(topicid);
            result.setData(topic.getFavnum());//获取此贴收藏数量
        }else{
            result.setMessage("参数异常");
        }
        renderJSON(result,resp);

    }
}
