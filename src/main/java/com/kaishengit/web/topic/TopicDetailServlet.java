package com.kaishengit.web.topic;


import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.*;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jimi_jin on 2016-12-20.
 */
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicid");
        TopicService service = new TopicService();
        try {
            Topic topic = service.findTopicById(topicId);

            //获取该topicid对应的回复列表
            List<Reply> replyList = service.findReplyListByTopicId(topicId);
            req.setAttribute("replyList",replyList);
            req.setAttribute("topic",topic);
            User user = getCurrentUser(req);
            //判断用户是否收藏该主题
            if(user!=null&& StringUtils.isNumeric(topicId)){

                Fav fav= service.findFavByUseridAndTopicid(user,topicId);
                //判断是否感谢该主题
                Thanks thanks = service.findThankByUserIdAndTopidid(user,topicId);
                req.setAttribute("thanks" ,thanks);
                req.setAttribute("fav",fav);

            }


            forward("/topic/topicDetail",req,resp);
        }catch (ServiceException e){
            resp.sendError(404);
        }
    }
}
