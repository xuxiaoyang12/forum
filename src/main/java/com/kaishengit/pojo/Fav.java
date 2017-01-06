package com.kaishengit.pojo;

import java.io.Serializable;

/**
 * Created by Mxia on 2016/12/27.
 */
public class Fav implements Serializable {

    private Integer userid;
    private Integer topicid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTopicid() {
        return topicid;
    }

    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
    }
}
