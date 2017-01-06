package com.kaishengit.mapper;

import com.kaishengit.pojo.Node;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Mxia on 2017/1/5.
 */
public interface NodeMapper {
    @Select("select * from t_node where id = #{id}")
    Node findNodeById(Integer id);
    @Select("select * from t_node")
    List<Node> findAllNode();
    @Update("update t_node set topicnum = #{topicnum},nodename = #{nodename} where id = #{id}")
    void update(Node node);
    @Select("select * from t_node where nodename = #{nodename}")
    Node findNodeByName(String nodename);
    @Delete("delete from t_node where id = #{id}")
    void delNodeById(Integer id);
    @Insert("insert into t_node (nodename) value(#{nodename})")
    void saveNewNode(String nodename);
}
