package com.dianshang.cn.service;

import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.model.entity.UserInfo;

public interface UserInfoService {

	/**
	 * 
	 * 方法描述：   查询用户详细信息
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: queryUserInfo 
	 * @date 2018年8月13日 上午9:42:34
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param user
	 * @return
	 */
	UserInfo queryUserInfo(User user);
}
