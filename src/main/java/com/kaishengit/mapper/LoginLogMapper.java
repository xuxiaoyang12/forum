package com.kaishengit.mapper;

import com.kaishengit.pojo.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mxia on 2017/1/5.
 */
public interface LoginLogMapper {

    @Insert("insert into t_login_log (ip,userId) values(#{ip},#{userId})")
    void save(LoginLog loginLog);

    @Select("SELECT * FROM t_login_log WHERE userid = #{userid} ORDER BY loginTime DESC LIMIT 0,1")
    LoginLog findLastTimeAndIp(Integer userid);
}
