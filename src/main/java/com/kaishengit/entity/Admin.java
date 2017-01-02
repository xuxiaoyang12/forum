package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by Mxia on 2016/12/28.
 */
public class Admin {


    private Integer id;
    private String adminName;
    private String password;
    private Timestamp createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
