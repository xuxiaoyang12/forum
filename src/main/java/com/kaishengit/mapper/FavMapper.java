package com.kaishengit.mapper;

import com.kaishengit.pojo.Fav;
import com.kaishengit.pojo.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mxia on 2017/1/5.
 */
public interface FavMapper {
    @Insert("insert into t_fav(userid,topicid) values(#{userid},#{topicid})")
    void addFav(Fav fav);
    @Delete("delete from t_fav where userid = #{param1} and topicid = #{param2}")
    void deleteFav(Integer userid,Integer topicid);
    @Select("select * from t_fav where userid=#{param1} and topicId=#{param2}")
    Fav findReplyByUseridAndTopicid(Integer userid,Integer topicid);
}
