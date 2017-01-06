package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.exception.DataAccessException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.mapper.TopicReplyCountMapper;
import com.kaishengit.pojo.*;
import com.kaishengit.util.Page;
import com.kaishengit.util.SqlSessionFactoryUtils;
import com.kaishengit.util.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jimi_jin on 2016-12-20.
 */
public class TopicService {

    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    public List<Node> findAllNode(){
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            List<Node> nodeList = nodeMapper.findAllNode();
            return nodeList;
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 添加新的主题
     * @param title
     * @param content
     * @param nodeid
     * @param userId
     * @return
     */
    public Topic addNewTopic(String title, String content, Integer nodeid,Integer userId){
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            //封装topic对象
            Topic topic = new Topic();
            topic.setTitle(title);
            topic.setContent(content);
            topic.setNodeid(nodeid);
            topic.setUserid(userId);
            //暂时设置最后回复时间为当前时间
            topic.setLastreplytime(new Timestamp(new DateTime().getMillis()));

            TopicMapper topicMapper =sqlSession.getMapper(TopicMapper.class);
            topicMapper.save(topic);//使用myBatis能自动获取id

            //更新node表的topicnum
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            Node node = nodeMapper.findNodeById(nodeid);
            if (node != null){
                node.setTopicnum(node.getTopicnum() + 1);
                nodeMapper.update(node);
            } else{
                throw new ServiceException("节点不存在");
            }
            return topic ;
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 通过topici查询topic对象
     * @param topicId
     * @return
     */
    public Topic findTopicById(String topicId) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            if (StringUtils.isNumeric(topicId)){
                TopicMapper topicMapper =sqlSession.getMapper(TopicMapper.class);
                Topic topic = topicMapper.findById(Integer.valueOf(topicId));
                if (topic != null ){

                    //通过topic对象的userid、nodeid 获取user和node对象,并set到tipic对象中;
                    UserMapper userMapper = sqlSession .getMapper(UserMapper.class);
                    User user = userMapper.findById(topic.getUserid());


                    NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
                    Node node = nodeMapper.findNodeById(topic.getNodeid());


                    topic.setUser(user);
                    topic.setNode(node);

                    //更新topic表中的clicknum字段
                    topic.setClicknum(topic.getClicknum() + 1);
                    topicMapper.update(topic);

                    return topic;
                }else{
                    throw new ServiceException("该帖不存在或已被删除");
                }
            }else{
                throw  new ServiceException("参数错误");
            }
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 添加新回复
     * @param topicId
     * @param content
     * @param user
     */
    public void addTopicReply(String topicId, String content, User user) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            //新增回复到t_reply表
            Reply reply = new Reply();
            reply.setContent(content);
            reply.setUserid(user.getId());
            reply.setTopicid(Integer.valueOf(topicId));
            ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
            replyMapper.addReply(reply);

            //更新t_topic表中的replynum 和 lastreplytime字段
            TopicMapper topicMapper = sqlSession .getMapper(TopicMapper.class);
            Topic topic =topicMapper.findById(Integer.valueOf(topicId));
            if (topic != null){
                topic.setReplynum(topic.getReplynum() + 1);
                topic.setLastreplytime(new Timestamp(DateTime.now().getMillis()));
                topicMapper.update(topic);

                //给用户一条通知
                //如果通知是自己回复的则不通知
                if(!user.getId().equals(topic.getUserid())){
                    Notify notify = new Notify();
                    notify.setUserid(topic.getUserid());
                    notify.setContent("您发布的主题<a href='/topicDetail?topicid="+topic.getId()+"'>"+topic.getTitle()+"</a>有了新的回复");

                    NotifyMapper notifyMapper = sqlSession .getMapper(NotifyMapper.class);
                    notifyMapper.saveNewNotify(notify);
                }
            }else{
                throw new ServiceException("回复的主题不存在或被删除");
            }

        }finally{
            sqlSession.close();
        }

    }

    public List<Reply> findReplyListByTopicId(String topicId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{

            ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
            return replyMapper.findListReplyByTopicId(Integer.valueOf(topicId));

        }finally{
            sqlSession.close();
        }

    }

    //收藏帖子
    public void favTopic(User user, String topicid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            Fav fav = new Fav();
            fav.setTopicid(Integer.valueOf(topicid));
            fav.setUserid(user.getId());

            FavMapper favMapper = sqlSession.getMapper(FavMapper.class);
            favMapper.addFav(fav);

            //更新帖子中的收藏次数
            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
            Topic topic = topicMapper.findById(Integer.valueOf(topicid));

            topic.setFavnum(topic.getFavnum()+1);
            topicMapper.update(topic);

        }finally{
            sqlSession.close();
        }

    }

    /**
     * 取消收藏
     * @param user
     * @param topicid
     */
    public  void unfavTopic(User user, String topicid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            FavMapper favMapper = sqlSession.getMapper(FavMapper.class);
            favMapper.deleteFav(user.getId(),Integer.valueOf(topicid));

            //更新收藏数
            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
            Topic topic = topicMapper.findById(Integer.valueOf(topicid));

            topic.setFavnum(topic.getFavnum()-1);
            topicMapper.update(topic);
        }finally{
            sqlSession.close();
        }


    }

    /**
     * 判断用户是否收藏该帖
     * @param user
     * @param topicid
     * @return
     */
    public Fav findFavByUseridAndTopicid(User user, String topicid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            FavMapper favMapper = sqlSession.getMapper(FavMapper.class);
            return favMapper.findReplyByUseridAndTopicid(user.getId(),Integer.valueOf(topicid));

        }finally{
            sqlSession.close();
        }
    }

    public Page<Topic> findAllTopic(String nodeid,Integer pageNo){

        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            HashMap<String,Object> map = Maps.newHashMap();//创建一个map集合
            int count = 0;
            Map<String,Object> map1 = Maps.newHashMap();
            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);

            if(StringUtils.isEmpty(nodeid)){
                map1.put("nodeid","");
                count = topicMapper.count(map1);//如果没有节点数 查询主题总数
            }else{
                map1.put("nodeid",Integer.valueOf(nodeid));
                count = topicMapper.count(map1);
            }

            Page<Topic> topicPage = new Page<>(count,pageNo);
            map.put("nodeid",nodeid);
            map.put("start",topicPage.getStart());
            map.put("pageSize",topicPage.getPageSize());

            try {
                List<Topic> topicList= topicMapper.findAll(map);
                topicPage.setItems(topicList);
            }catch (DataAccessException e){
                logger.error(e.getMessage());
            }
            return topicPage;

        }finally{
            sqlSession.close();
        }

    }
    /**
     * 编辑主题
     * @param topicid
     * @param title
     * @param content
     * @param nodeid
     * @param user
     */
    public void editTopic(String topicid, String title, String content, String nodeid, User user) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            TopicMapper topicMapper =sqlSession.getMapper(TopicMapper.class);
            Topic topic = topicMapper.findById(Integer.valueOf(topicid));

            if(topic==null){
                throw new ServiceException("贴子不存在或已被删除");
            }else{
                //再次判断是否可编辑
                if(topic.getUserid().equals(user.getId())&&topic.isEdit()) {
                    topic.setTitle(title);
                    topic.setContent(content);
                    topic.setNodeid(Integer.valueOf(nodeid));

                    topicMapper.update(topic);
                }else{
                    throw new ServiceException("当前主题不可编辑");
                }
            }
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 通过nodeid查询数据
     * @param nodeid
     */
    public Node findNodeById(String nodeid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            //判断nodeid是否是数字
            if(StringUtils.isNumeric(nodeid)){
                //查询数据
                NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
                Node node = nodeMapper.findNodeById(Integer.valueOf(nodeid));

                if(node==null){
                    throw new ServiceException("节点不存在或已被删除");
                }else{
                    return node;
                }
            }else{
                throw new ServiceException("参数错误");
            }
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 更改节点
     * @param nodeid
     * @param nodename
     */
    public void updateNode(String nodeid, String nodename) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            if(StringUtils.isNumeric(nodeid)){
                NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
                Node node = nodeMapper.findNodeById(Integer.valueOf(nodeid));

                if(node==null){
                    throw new ServiceException("节点不存在或已被删除");
                }else{
                    node.setNodename(nodename);
                    nodeMapper.update(node);
                }

            }else{
                throw new ServiceException("参数异常");
            }
        }finally{
            sqlSession.close();
        }

    }

    public Node findNOdeByName(String nodename) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            return nodeMapper.findNodeByName(nodename);
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 删除节点通过id
     * @param nodeid
     */
    public void delNodeById(String nodeid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            //判断id是否为数字
            if(StringUtils.isNumeric(nodeid)){
                NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
                Node node = nodeMapper.findNodeById(Integer.valueOf(nodeid));

                //判断node是否为空
                if(node !=null){
                    //判断topicnum 是否为空
                    if( node.getTopicnum() == 0){
                        //删除
                        nodeMapper.findNodeById(Integer.valueOf(nodeid));
                    }else{
                        throw new ServiceException("该节点不能删除 里面包含主题");
                    }
                }else{
                    throw new ServiceException("节点不存在或已被删除");
                }
            }else{

                throw new ServiceException("参数错误");

            }
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 添加新节点
     * @param nodename
     */
    public void saveNewnode(String nodename) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            try {
                nodeMapper.saveNewNode(nodename);
            }catch(Exception e){
                throw new ServiceException("保存失败请稍后重试");
            }
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 查询 日期 主题数 回复数
     * @param pageNo
     * @return
     */
    public Page<TopicReplyCount> getTopicAndReplyNumByDayList(Integer pageNo) {

        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{

            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
            //查询日期分布数量
            int count = topicMapper.countTopicByDay();

            //获取page对象
            Page<TopicReplyCount> page = new Page<>(count,pageNo);

            //查询日期 主题 回复数
            TopicReplyCountMapper topicReplyCountMapper =sqlSession.getMapper(TopicReplyCountMapper.class);
            List<TopicReplyCount> CountList = topicReplyCountMapper.getTopicAndReplyNumList(page.getStart(),page.getPageSize());

            page.setItems(CountList);

            return page;
        }finally{
            sqlSession.close();
        }

    }

    /**
     * 添加感谢
     * @param user
     * @param topicid
     */
    public void thanksTopic(User user, String topicid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{

            //添加感谢
            Thanks thanks = new Thanks();
            thanks.setUserid(user.getId());
            thanks.setTopicid(Integer.valueOf(topicid));


            ThanksMapper thanksMapper = sqlSession.getMapper(ThanksMapper.class);
            thanksMapper.save(thanks);

            //更新帖子感谢数
            TopicMapper topicMapper =sqlSession.getMapper(TopicMapper.class);
            Topic topic = topicMapper.findById(Integer.valueOf(topicid));

            if(topic==null){
                throw new ServiceException("帖子已删除或不存在");
            }else{
                topic.setThankyounum(topic.getThankyounum()+1);
                topicMapper.update(topic);
            }

        }finally{
            sqlSession.close();
        }

    }

    /**
     * 取消感谢
     * @param user
     * @param topicid
     */
    public void unthanksTopic(User user, String topicid) {

        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            ThanksMapper thanksMapper = sqlSession.getMapper(ThanksMapper.class);
            thanksMapper.delThanks(Integer.valueOf(topicid),user.getId());

            //更新帖子
            TopicMapper topicMapper = sqlSession.getMapper(TopicMapper.class);
            Topic topic =topicMapper.findById(Integer.valueOf(topicid));

            if(topic==null){
                throw new ServiceException("帖子已删除或不存在");
            }else{
                topic.setThankyounum(topic.getThankyounum()-1);
                topicMapper.update(topic);
            }

        }finally{
            sqlSession.close();
        }


    }

    public Thanks findThankByUserIdAndTopidid(User user, String topicId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSession(true);
        try{
            ThanksMapper thanksMapper = sqlSession.getMapper(ThanksMapper.class);
            return thanksMapper.findByUseridAndTopicid(user.getId(),Integer.valueOf(topicId));

        }finally{
            sqlSession.close();
        }


    }
}
