package com.kaishengit.mapper;

import com.kaishengit.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Mxia on 2017/1/5.
 */
@CacheNamespace
public interface UserMapper {

    User findById(Integer id);
    @Insert("INSERT INTO t_user(username, password, email, phone, state, avatar) VALUES (#{username},#{password},#{email},#{phone},#{state},#{avatar})")
    void saveNewUser(User user);
    @Select("select * from t_user where username = #{username}")
    User findByUsernaem(String username);
    @Select("select * from t_user where email = #{email}")
    User findByEmail(String email);
    @Update("update t_user set createTime=#{createTime}, password=#{password},email=#{email},phone=#{phone},state=#{state},avatar=#{avatar} where id = #{id}")
    void update(User user);
    @Select("select * from t_user")
    List<User> findAllUser();



}
