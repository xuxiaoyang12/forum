package com.kaishengit.mapper;

import com.kaishengit.pojo.Topic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Mxia on 2017/1/5.
 */
public interface TopicMapper {

    @Select("select * from t_topic where id = #{id}")
    Topic findById(Integer id);
    @Update("update t_topic set title = #{title} ,content = #{content} ,clicknum = #{clicknum},favnum = #{favnum},thankyounum =#{thankyounum} ,replynum = #{replynum},lastreplytime = #{lastreplytime}, nodeid = #{nodeid},userid = #{userid} where id = #{id}")
    void update(Topic topic);
//    @Select("select count(*) from t_topic")
//    Integer count();
  //  @Select("select count(*) from t_topic where nodeid = #{nodeid}")

    @Delete("delete from t_topic where id = #{id}")
    void delTopicById(Integer id);

    @Select("select count(*) from (select count(*) from t_topic group by DATE_FORMAT(createtime,'%y-%m-%d'))as topicCount")
    Integer countTopicByDay();

    Integer count(Map<String, Object> map);
    void save(Topic topic);

    List<Topic> findAll(Map<String,Object> map);



}
