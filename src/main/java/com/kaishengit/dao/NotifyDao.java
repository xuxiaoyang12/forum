package com.kaishengit.dao;

import com.kaishengit.pojo.Notify;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by Mxia on 2016/12/29.
 */
public class NotifyDao {


    /**
     * 通过userid查询通知
     * @param userid
     * @return
     */
    public List<Notify> findByUserid(Integer userid) {
        String sql = "select * from  t_notify where userid = ?";
        return DbHelp.query(sql,new BeanListHandler<>(Notify.class),userid);

    }

    /**
     * 保存新的通知
     * @param notify
     */
    public void saveNewNofity(Notify notify) {
        String sql = "insert into t_notify(userid,content,state) values(?,?,?)";
        DbHelp.update(sql,notify.getUserid(),notify.getContent(),notify.getState());
    }

    public Notify findNotifyByid(String id) {
        String sql = "select * from t_notify where id = ?";
       return  DbHelp.query(sql,new BeanHandler<Notify>( Notify.class),id);
    }

    public void update(Notify notify) {
        String sql = "update  t_notify set readtime=? where id=?";
        DbHelp.update(sql,notify.getReadtime(),notify.getId());
    }
}
