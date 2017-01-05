package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.DataAccessException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jimi_jin on 2016-12-20.
 */
public class TopicService {
    private  TopicDao topicDao = new TopicDao();
    private  UserDao userDao = new UserDao();
    private  NodeDao nodeDao = new NodeDao();
    private  ReplyDao replyDao = new ReplyDao();
    private  FavDao favDao = new FavDao();
    private  Notify notify = new Notify();
    private  NotifyDao notifyDao = new NotifyDao();
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    public List<Node> findAllNode(){
        List<Node> nodeList = nodeDao.findAllNodes();
        return nodeList;
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
        //封装topic对象
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userId);
        //暂时设置最后回复时间为当前时间
        topic.setLastreplytime(new Timestamp(new DateTime().getMillis()));
        Integer topicId = topicDao.save(topic);
        topic.setId(topicId);

        //更新node表的topicnum
        Node node = nodeDao.findNodeById(nodeid);
        if (node != null){
            node.setTopicnum(node.getTopicnum() + 1);
            nodeDao.update(node);
        } else{
            throw new ServiceException("节点不存在");
        }
        return topic ;
    }

    /**
     * 通过topici查询topic对象
     * @param topicId
     * @return
     */
    public Topic findTopicById(String topicId) {
        if (StringUtils.isNumeric(topicId)){
            Topic topic = topicDao.findTopicById(topicId);
            if (topic != null ){

                //通过topic对象的userid、nodeid 获取user和node对象,并set到tipic对象中;
                User user = userDao.findById(topic.getUserid());
                Node node = nodeDao.findNodeById(topic.getNodeid());
                //Config.get("qiniu.domain")+
                //user.setAvatar(user.getAvatar());

                topic.setUser(user);
                topic.setNode(node);

                //更新topic表中的clicknum字段
                topic.setClicknum(topic.getClicknum() + 1);
                topicDao.update(topic);

                return topic;
            }else{
                throw new ServiceException("该帖不存在或已被删除");
            }
        }else{
            throw  new ServiceException("参数错误");
        }
    }

    /**
     * 添加新回复
     * @param topicId
     * @param content
     * @param user
     */
    public void addTopicReply(String topicId, String content, User user) {
        //新增回复到t_reply表
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setUserid(user.getId());
        reply.setTopicid(Integer.valueOf(topicId));
        replyDao.addReply(reply);

        //更新t_topic表中的replynum 和 lastreplytime字段
        Topic topic = topicDao.findTopicById(topicId);
        if (topic != null){
            topic.setReplynum(topic.getReplynum() + 1);
            topic.setLastreplytime(new Timestamp(DateTime.now().getMillis()));
            topicDao.update(topic);

            //给用户一条通知\
            //如果通知是自己回复的则不通知
            if(!user.getId().equals(topic.getUserid())){
                Notify notify = new Notify();
                notify.setUserid(topic.getUserid());
                notify.setContent("您发布的主题<a href='/topicDetail?topicid="+topic.getId()+"'>"+topic.getTitle()+"</a>有了新的回复");

                notifyDao.saveNewNofity(notify);
            }
        }else{
            throw new ServiceException("回复的主题不存在或被删除");
        }

    }

    public List<Reply> findReplyListByTopicId(String topicId) {
        return replyDao.findListByTopicId(topicId);
    }

    //收藏帖子
    public void favTopic(User user, String topicid) {
        Fav fav = new Fav();
        fav.setTopicid(Integer.valueOf(topicid));
        fav.setUserid(user.getId());


        favDao.addFav(fav);


        //更新帖子中的收藏次数
        Topic topic= topicDao.findTopicById(topicid);
        topic.setFavnum(topic.getFavnum()+1);
        topicDao.update(topic);
    }

    /**
     * 取消收藏
     * @param user
     * @param topicid
     */
    public  void unfavTopic(User user, String topicid) {
        favDao.deleteFav(user.getId(),topicid);

        //更新收藏数
        Topic topic = topicDao.findTopicById(topicid);
        topic.setFavnum(topic.getFavnum()-1);
        topicDao.update(topic);
    }

    /**
     * 判断用户是否收藏该帖
     * @param user
     * @param topicId
     * @return
     */
    public Fav findFavByUseridAndTopicid(User user, String topicId) {
        return favDao.findFavByUseridAndTopicid(user.getId(),topicId);
    }

    public Page<Topic> findAllTopic(String nodeid,Integer pageNo){

        HashMap<String,Object> map = Maps.newHashMap();//创建一个map集合
        int count = 0;
        if(StringUtils.isEmpty(nodeid)){
            count = topicDao.count();//如果没有节点数 查询主题总数
        }else{
            count = topicDao.count(nodeid);
        }


        Page<Topic> topicPage = new Page<>(count,pageNo);
        map.put("nodeid",nodeid);
        map.put("start",topicPage.getStart());
        map.put("pageSize",topicPage.getPageSize());

        try {
            List<Topic> topicList = topicDao.findAll(map);
            topicPage.setItems(topicList);
        }catch (DataAccessException e){
            logger.error(e.getMessage());
        }
        return topicPage;
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
        Topic topic = topicDao.findTopicById(topicid);
        if(topic==null){
            throw new ServiceException("贴子不存在或已被删除");
        }else{
            //再次判断是否可编辑
            if(topic.getUserid().equals(user.getId())&&topic.isEdit()) {
                topic.setTitle(title);
                topic.setContent(content);
                topic.setNodeid(Integer.valueOf(nodeid));

                topicDao.update(topic);
            }else{
                throw new ServiceException("当前主题不可编辑");
            }
        }
    }

    /**
     * 通过nodeid查询数据
     * @param nodeid
     */
    public Node findNodeById(String nodeid) {
        //判断nodeid是否是数字
        if(StringUtils.isNumeric(nodeid)){
            //查询数据
            Node node = nodeDao.findNodeById(Integer.valueOf(nodeid));
            if(node==null){
                throw new ServiceException("节点不存在或已被删除");
            }else{
                return node;
            }
        }else{
            throw new ServiceException("参数错误");
        }
    }

    /**
     * 更改节点
     * @param nodeid
     * @param nodename
     */
    public void updateNode(String nodeid, String nodename) {
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findNodeById(Integer.valueOf(nodeid));
            if(node==null){
                throw new ServiceException("节点不存在或已被删除");
            }else{
                node.setNodename(nodename);
                nodeDao.update(node);
            }
        }else{
            throw new ServiceException("参数异常");
        }
    }

    public Node findNOdeByName(String nodename) {
        return nodeDao.findNodeByName(nodename);
    }

    /**
     * 删除节点通过id
     * @param nodeid
     */
    public void delNodeById(String nodeid) {
        //判断id是否为数字
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findNodeById(Integer.valueOf(nodeid));
            //判断node是否为空
            if(node !=null){
                //判断topicnum 是否为空
               if( node.getTopicnum() == 0){
                   //删除
                   nodeDao.del(Integer.valueOf(nodeid));
               }else{
                   throw new ServiceException("该节点不能删除 里面包含主题");
               }
            }else{
                throw new ServiceException("节点不存在或已被删除");
            }
        }else{

            throw new ServiceException("参数错误");

        }
    }

    /**
     * 添加新节点
     * @param newnodename
     */
    public void saveNewnode(String newnodename) {
        try {
            nodeDao.saveNewnode(newnodename);
        }catch(Exception e){
            throw new ServiceException("保存失败请稍后重试");
        }
    }

    /**
     * 查询 日期 主题数 回复数
     * @param pageNo
     * @return
     */
    public Page<TopicReplyCount> getTopicAndReplyNumByDayList(Integer pageNo) {
        int count = topicDao.countTopicByDay();//查询日期分布数量

        //获取page对象
        Page<TopicReplyCount> page = new Page<>(count,pageNo);
        //查询日期 主题 回复数
        List<TopicReplyCount> CountList = topicDao.getTopicAndReplyNumList(page.getStart(),page.getPageSize());

        page.setItems(CountList);
        return page;

    }

    /**
     * 添加感谢
     * @param user
     * @param topicid
     */
    public void thanksTopic(User user, String topicid) {
        //添加感谢
        Thanks thanks = new Thanks();
        thanks.setUserid(user.getId());
        thanks.setTopicid(Integer.valueOf(topicid));

        ThanksDao thanksDao =new ThanksDao();
        thanksDao.save(thanks);

        //更新帖子感谢数
        Topic topic = topicDao.findTopicById(topicid);
        if(topic==null){
            throw new ServiceException("帖子已删除或不存在");
        }else{
            topic.setThankyounum(topic.getThankyounum()+1);
            topicDao.update(topic);
        }

    }

    /**
     * 取消感谢
     * @param user
     * @param topicid
     */
    public void unthanksTopic(User user, String topicid) {
        ThanksDao thanksDao =new ThanksDao();
        thanksDao.delThanks(user,Integer.valueOf(topicid));

        //更新帖子
        Topic topic = topicDao.findTopicById(topicid);
        if(topic==null){
            throw new ServiceException("帖子已删除或不存在");
        }else{
            topic.setThankyounum(topic.getThankyounum()-1);
            topicDao.update(topic);
        }
    }

    public Thanks findThankByUserIdAndTopidid(User user, String topicId) {
        ThanksDao thanksDao = new ThanksDao();
        return thanksDao.findByIdAndTopicid(user.getId(),topicId);
    }
}
