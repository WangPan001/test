package com.dianshang.cn.dao;

import java.util.List;

import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.model.vo.UserVo;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User checkUser(User record);
    
    List<User> selectUserList(UserVo record);
    
    User login(User record);
    
    String getPassword(String userName);
    
    String getRole(String userName);
}