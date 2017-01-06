package com.kaishengit.mapper;

import com.kaishengit.pojo.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mxia on 2017/1/6.
 */
public interface AdminMapper {
    @Select("select * from t_admin where adminName=#{adminName}")
    Admin findByAdminName(String AdminName);
}
