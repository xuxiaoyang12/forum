package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.AdminMapper;
import com.kaishengit.mapper.NodeMapper;
import com.kaishengit.mapper.ReplyMapper;
import com.kaishengit.mapper.TopicMapper;
import com.kaishengit.pojo.Admin;
import com.kaishengit.pojo.Node;
import com.kaishengit.pojo.Reply;
import com.kaishengit.pojo.Topic;
import com.kaishengit.util.Config;
import com.kaishengit.util.SqlSessionFactoryUtils;
import com.kaishengit.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Mxia on 2016/12/28.
 */
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminService.class);

    /**
     * 登录管理员账户
     * @param adminName
     * @param password
     * @param ip
     */
    public Admin adminLogin(String adminName, String password, String ip) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);
            Admin admin = adminMapper.findByAdminName(adminName);

            if(admin!=null&&admin.getPassword().equals(DigestUtils.md5Hex(Config.get("admin.password.salt")+password))){
                logger.debug("管理员{}登录了系统 ip{}",admin.getAdminName(),ip);
                return admin;
            }else{
                throw new ServiceException("账号或密码错误");
            }
        }finally{
            sqlSession.close();
        }
    }

    /**
     * 通过topicid 删除主题
     * @param topicid
     */
    public void delByTopicid(String topicid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{

            //判断topicid是否是数字
            if(StringUtils.isNumeric(topicid)){
                //查询topicid 下是否有主题
                TopicMapper topicMapper = sqlSession .getMapper(TopicMapper.class);
                Topic topic =topicMapper.findById(Integer.valueOf(topicid));

                if(topic==null){
                    throw new ServiceException("帖子不存在或已被删除！");
                }else {
                    //相应节点主题数更新
                    NodeMapper nodeMapper =sqlSession .getMapper(NodeMapper.class);
                    Node node = nodeMapper.findNodeById(topic.getNodeid());

                    node.setTopicnum(node.getTopicnum()-1);
                    nodeMapper.update(node);

                    //删除相应的回复
                    ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
                    List<Reply> replyList = replyMapper.findListReplyByTopicId(Integer.valueOf(topicid));

                    for(Reply reply : replyList){
                        replyMapper.del(reply.getId());
                    }

                    //删除主题
                    topicMapper.delTopicById(Integer.valueOf(topicid));
                }
            }else{
                throw new ServiceException("参数错误！");
            }
        }finally{
            sqlSession.close();
        }
    }
}
