package com.kaishengit.dao;

import com.kaishengit.pojo.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class UserDao {

   /* public void save(User user) {
        String sql = "INSERT INTO t_user(username, password, email, phone, state, avatar) VALUES (?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
    }*/


    /**
     * 通过用户名查询账户信息
     * @param username 用户名
     * @return 账户信息
     */
    public User findByUserName(String username) {
        String sql = "select * from t_user where username = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),email);
    }

    public void update(User user) {
        String sql = "update t_user set createTime=?, password=?,email=?,phone=?,state=?,avatar=? where id = ?";
        DbHelp.update(sql, user.getCreateTime(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
    }

    public User findById(Integer id) {
        String sql = "select * from t_user where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),id);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAllUser() {
        String sql = "select * from t_user";
        return DbHelp.query(sql,new BeanListHandler<User>(User.class));
    }
}
