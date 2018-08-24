package com.dianshang.cn.service;

import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.model.entity.UserInfo;
import com.dianshang.cn.model.vo.UserVo;
import com.github.pagehelper.PageInfo;

public interface UserService {

	/**
	 * 
	 * 方法描述：   注册会员
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: insertUser 
	 * @date 2018年8月9日 下午10:02:26
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param user
	 * @return
	 */
	int insertUser(User user);
	
	/**
	 * 
	 * 方法描述：   查询用户列表
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: selectUserList 
	 * @date 2018年8月10日 下午3:30:11
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param record
	 * @return
	 */
	PageInfo<User> selectUserList(UserVo record);
	
	/**
	 * 
	 * 方法描述：   用户登录
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: login 
	 * @date 2018年8月10日 下午4:58:23
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param record
	 * @return
	 */
	UserInfo login(User record);
}
