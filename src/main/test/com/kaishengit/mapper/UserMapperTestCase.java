package com.kaishengit.mapper;

import com.kaishengit.pojo.User;
import com.kaishengit.util.SqlSessionFactoryUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Mxia on 2017/1/5.
 */
public class UserMapperTestCase {

//    private SqlSession sqlSession;
//    @Before
//    public void  setup(){
//        sqlSession = SqlSessionFactoryUtils.getSqlSession();
//    }
//    @After
//    public void close(){
//        sqlSession.close();
//    }

    @Test
    public void findById(){

//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        User user = userMapper.findById(1);
//        System.out.println(user);
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
