package com.kaishengit.mapper;

import com.kaishengit.pojo.Notify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Mxia on 2017/1/5.
 */
public interface NotifyMapper {
    @Select("select * from  t_notify where userid = #{userid}")
    List<Notify> findByUserid(Integer userid);
    @Select("select * from t_notify where id = #{id}")
    Notify findNotifyById(Integer id);
    @Update("update  t_notify set readtime=#{readtime} where id=#{id}")
    void update(Notify notify);
    @Insert("insert into t_notify(userid,content,state) values(#{userid},#{content},#{state})")
    void saveNewNotify(Notify notify);
}
