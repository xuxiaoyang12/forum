package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mxia on 2017/1/4.
 */
@WebServlet("/thanksTopic")
public class ThanksTopicServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");

        //获取当前用户
        User user = getCurrentUser(req);
        //保存感谢
        TopicService topicService = new TopicService();
        Map<String , Object> map = Maps.newHashMap();
        if(StringUtils.isNumeric(topicid)&&StringUtils.isNotEmpty(action)){
            if("thanks".equals(action)){
                //加入感谢
                System.out.println("thanks");
                try {
                    topicService.thanksTopic(user, topicid);
                    map.put("state","success");
                }catch (ServiceException e){
                    map.put("message",e.getMessage());
                }
            }else if("unthanks".equals(action)){
                //取消感谢
                System.out.println("unthanks");
                try {
                    topicService.unthanksTopic(user, topicid);
                    map.put("state","success");
                }catch (ServiceException e){
                    map.put("message",e.getMessage());
                }

            }else{
                map.put("message","参数异常");
            }
            //获取感谢数量
            Topic topic = topicService.findTopicById(topicid);
            map.put("data",topic.getThankyounum());

        }

        renderJSON(map,resp);


    }
}
