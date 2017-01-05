package com.kaishengit.dao;

import com.kaishengit.entity.Thanks;
import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Mxia on 2017/1/4.
 */
public class ThanksDao {

    public void save(Thanks thanks) {
        String sql = "insert into t_thanks(userid,topicid) values(?,?)";
        DbHelp.update(sql,thanks.getUserid(),thanks.getTopicid());
    }

    public void delThanks(User user ,Integer topicid) {
        String sql = "delete from t_thanks where topicid = ? and userid=?";
        DbHelp.update(sql,topicid,user.getId());
    }

    public Thanks findByIdAndTopicid(Integer id, String topicId) {
        String sql = "select * from t_thanks where userid = ? and topicid = ?";
        return DbHelp.query(sql,new BeanHandler<Thanks>(Thanks.class), id,topicId);
    }
}
