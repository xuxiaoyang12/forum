package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class LoginLogDao {
    public void save(LoginLog log) {
        String sql = "insert into t_login_log (ip,userId) values(?,?)";
        DbHelp.update(sql,log.getIp(),log.getUserId());
    }

    /**
     * 通过userid查询用户最后登录时间和ip
     * @param userid
     * @return
     */
    public LoginLog findLasttimeAndIp(Integer userid) {
        String  sql = "SELECT * FROM t_login_log WHERE userid = ? ORDER BY loginTime DESC LIMIT 0,1";
        return DbHelp.query(sql,new BeanHandler<LoginLog>(LoginLog.class),userid);
    }
}
