package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.entity.TopicReplyCount;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import com.kaishengit.util.StringUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jimi_jin on 2016-12-20.
 */
public class TopicDao {

    public Integer save(Topic topic){
        String sql = "insert into t_topic (title,content,nodeid,userid) values(?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid());

    }

    public Topic findTopicById(String topicId) {
        String sql = "select * from t_topic where id = ?";
        return  DbHelp.query(sql,new BeanHandler<>(Topic.class),Integer.valueOf(topicId));
    }

    public void update(Topic topic) {
        String sql ="update t_topic set title = ? ,content = ? ,clicknum = ?,favnum = ?,thankyounum = ?,replynum = ?,lastreplytime = ?, nodeid = ?,userid = ? where id = ?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClicknum(),topic.getFavnum(),topic.getThankyounum(),topic.getReplynum(),topic.getLastreplytime(),topic.getNodeid(),topic.getUserid(),topic.getId());
    }

    public List findAllTopic() {

        String sql = "SELECT tp.content, tp.replynum, tn.nodename  FROM t_topic AS tp ,t_node AS tn WHERE tp.nodeid=tn.id";
      return null; // DbHelp.query(sql,new BeanListHandler<>())
    }

    public int count() {
        String sql= "select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }


    public int count(String nodeid) {
        String sql = "select count(*) from t_topic where nodeid = ?";
        return DbHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();
    }


    public List<Topic> findAll(HashMap<String, Object> map) {
        String sql = "SELECT tu.username,tu.avatar,tt.* FROM t_topic tt,t_user tu WHERE tt.userid = tu.id ";
        String nodeid = map.get("nodeid") == null ?null:String.valueOf(map.get("nodeid"));
        String where = "";
        List<Object> array = new ArrayList<>();
        if (StringUtils.isNotEmpty(nodeid)){
            where += "AND nodeid = ? ";
            array.add(nodeid);
        }
        where += "ORDER BY tt.lastreplytime DESC LIMIT ?,?";
        array.add(map.get("start"));
        array.add(map.get("pageSize"));
        sql += where;

        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs,Topic.class);
                User user = new User();
                user.setId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                //Config.get("qiniu.domain") +
                user.setAvatar(rs.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },array.toArray());
    }

    /**
     * 通过id删除主题
     * @param topicid
     */
    public void delTopicById(String topicid) {
        String sql = "delete from t_topic where id = ?";
        DbHelp.update(sql,Integer.valueOf(topicid));
    }

    /**
     * 查询日期分布数量
     * @return
     */
    public int countTopicByDay() {
        String sql = "select count(*) from (select count(*) from t_topic group by DATE_FORMAT(createtime,'%y-%m-%d'))as topicCount";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();


    }

    /**
     * 获取指定页的数据
     * @param start
     * @param pageSize
     * @return
     */
    public List<TopicReplyCount> getTopicAndReplyNumList(Integer start, Integer pageSize) {
        String sql = "SELECT COUNT(*) topicnum,DATE_FORMAT(createtime,'%y-%m-%d') 'time',\n" +
                "(SELECT COUNT(*) FROM t_reply WHERE DATE_FORMAT(createtime,'%y-%m-%d')= DATE_FORMAT(t_topic.createtime,'%y-%m-%d')) 'replynum'\n" +
                "FROM t_topic \n" +
                "GROUP BY (DATE_FORMAT(createtime,'%y-%m-%d'))\n" +
                "ORDER BY (DATE_FORMAT(createtime,'%y-%m-%d')) DESC \n" +
                "LIMIT ?,?;";
        return DbHelp.query(sql,new BeanListHandler<TopicReplyCount>(TopicReplyCount.class),start,pageSize);


    }
}
