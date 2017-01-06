package com.kaishengit.dao;

import com.kaishengit.pojo.Fav;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Mxia on 2016/12/27.
 */
public class FavDao {


    /**
     * 添加收藏
     * @param fav
     */
    public void addFav(Fav fav) {
        String sql = "insert into t_fav(userid,topicid) values(?,?)";
        DbHelp.update(sql,fav.getUserid(),fav.getTopicid());



    }

    /**
     * 删除收藏
     * @param userid
     * @param topicid
     */
    public void deleteFav(Integer userid, String topicid) {
        String sql = "delete from t_fav where userid = ? and topicid = ?";//联合主键删除
        DbHelp.update(sql,userid,Integer.valueOf(topicid));


    }

    /**
     *
     * @param id
     * @param topicId
     * @return
     */
    public Fav findFavByUseridAndTopicid(Integer id, String topicId) {
        String sql = "select * from t_fav where userid=? and topicId=?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),id,Integer.valueOf(topicId));
    }
}
