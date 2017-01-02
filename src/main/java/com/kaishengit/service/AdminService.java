package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Mxia on 2016/12/28.
 */
public class AdminService {


    private Admin admin = new Admin();
    private AdminDao adminDao = new AdminDao();
    private Topic topic = new Topic();
    private Logger logger = LoggerFactory.getLogger(AdminService.class);
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private Node node = new Node();
    private Reply reply = new Reply();
    private ReplyDao replyDao = new ReplyDao();
    /**
     * 登录管理员账户
     * @param adminName
     * @param password
     * @param ip
     */
    public Admin adminLogin(String adminName, String password, String ip) {
          Admin admin = adminDao.findByName(adminName);

        if(admin!=null&&admin.getPassword().equals(DigestUtils.md5Hex(Config.get("admin.password.salt")+password))){
            logger.debug("管理员{}登录了系统 ip{}",admin.getAdminName(),ip);
            return admin;

        }else{
            throw new ServiceException("账号或密码错误");
        }



    }

    /**
     * 通过topicid 删除主题
     * @param topicid
     */
    public void delByTopicid(String topicid) {
        //判断topicid是否是数字
        if(StringUtils.isNumeric(topicid)){
            //查询topicid 下是否有主题
            Topic topic = topicDao.findTopicById(topicid);
            if(topic==null){
                throw new ServiceException("帖子不存在或已被删除！");
            }else {
                //相应节点主题数更新
                Node node = nodeDao.findNodeById(topic.getNodeid());
                node.setTopicnum(node.getTopicnum()-1);
                nodeDao.update(node);
                //删除相应的回复
                List<Reply> replyList = replyDao.findListByTopicId(topicid);
               for(Reply reply : replyList){
                   replyDao.del(reply.getId());
               }
                //删除主题
                topicDao.delTopicById(topicid);
            }
        }else{
            throw new ServiceException("参数错误！");
        }
    }
}
