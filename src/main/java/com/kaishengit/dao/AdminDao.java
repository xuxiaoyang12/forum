package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Mxia on 2016/12/28.
 */
public class AdminDao {


    /**
     *通过账户名查数据
     * @param adminName
     * @return
     */
    public Admin findByName(String adminName) {
        String sql = "select * from t_admin where adminName=?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class) ,adminName);


    }
}
