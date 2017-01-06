package com.kaishengit.mapper;

import com.kaishengit.pojo.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * Created by Mxia on 2017/1/6.
 */
public interface ReplyMapper {
    @Insert("insert into t_reply (content,userid,topicid) values (#{content},#{userid},#{topicid})")
    void addReply(Reply reply);
    @Delete("delete from t_reply where id =#{id}")
    void del(Integer id);

    List<Reply> findListReplyByTopicId(Integer topicid);
}
