package com.kaishengit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * 获取数据连接 工具类
 * Created by Mxia on 2017/1/5.
 */
public class SqlSessionFactoryUtils {

    //私有并单例salSessionFactory
    private static SqlSessionFactory sqlSessionFactory = builderSqlSessionFactory();

    //创建sqlSessionFactory
    private static SqlSessionFactory builderSqlSessionFactory() {

        try {
            //读取配置文件
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取配置文件异常",e);
        }

    }
    //创建一个外部获取sqlSessionFactory对象的方法
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
    //创建一个外部获取SqlSessiond对象的方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }

    //创建一个对外自动提交事务的SqlSession方法
    public static SqlSession getSqlSession(Boolean isAutoCommit){
        return sqlSessionFactory.openSession(isAutoCommit);

    }


}
