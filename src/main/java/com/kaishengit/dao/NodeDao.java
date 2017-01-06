package com.kaishengit.dao;

import com.kaishengit.pojo.Node;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by jimi_jin on 2016-12-20.
 */
public class NodeDao {

    public List<Node> findAllNodes(){
        String sql = "select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<>(Node.class));
    }
    public Node findNodeById(Integer nodeid){
        String sql = "select * from t_node where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(Node.class),nodeid);
    }

    public void update(Node node) {
        String sql = "update t_node set topicnum = ?,nodename = ? where id = ?";
        DbHelp.update(sql,node.getTopicnum(),node.getNodename(),node.getId());

    }

    public Node findNodeByName(String nodename) {
        String sql = "select * from t_node where nodename = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodename);
    }

    public void del(Integer nodeid) {
        String sql = "delete from t_node where id = ?";
        DbHelp.update(sql,nodeid);
    }

    /**
     * 添加新节点
     * @param newnodename
     */
    public void saveNewnode(String newnodename) {
        String sql ="insert into t_node (nodename) value(?)";
        DbHelp.update(sql,newnodename);
    }
}
