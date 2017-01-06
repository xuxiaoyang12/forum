package com.kaishengit.mapper;

import com.kaishengit.pojo.Thanks;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mxia on 2017/1/6.
 */
public interface ThanksMapper {

    @Insert("insert into t_thanks(userid,topicid) values(#{userid},#{topicid})")
    void save(Thanks thanks);
    @Delete("delete from t_thanks where topicid = #{param1} and userid=#{param2}")
    void delThanks(Integer topicid,Integer userid);
    @Select("select * from t_thanks where userid = #{param1} and topicid = #{param2}")
    Thanks findByUseridAndTopicid(Integer userid,Integer topicid);
}
