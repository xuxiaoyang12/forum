package com.kaishengit.pojo;

import java.io.Serializable;

/**
 * Created by Mxia on 2016/12/29.
 */
public class TopicReplyCount implements Serializable {

    private String time;
    private String replynum;
    private String topicnum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReplynum() {
        return replynum;
    }

    public void setReplynum(String replynum) {
        this.replynum = replynum;
    }

    public String getTopicnum() {
        return topicnum;
    }

    public void setTopicnum(String topicnum) {
        this.topicnum = topicnum;
    }
}
