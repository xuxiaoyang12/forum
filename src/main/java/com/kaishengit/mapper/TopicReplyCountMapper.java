package com.kaishengit.mapper;

import com.kaishengit.pojo.TopicReplyCount;

import java.util.List;

/**
 * Created by Mxia on 2017/1/6.
 */
public interface TopicReplyCountMapper {

    List<TopicReplyCount> getTopicAndReplyNumList(Integer pageStart , Integer pagesize);
}
