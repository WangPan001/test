package com.dianshang.cn.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianshang.cn.dao.UserMapper;
import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.model.entity.UserInfo;
import com.dianshang.cn.model.vo.UserVo;
import com.dianshang.cn.service.UserInfoService;
import com.dianshang.cn.service.UserService;
import com.dianshang.cn.utils.EncryptUtil;
import com.github.pagehelper.PageInfo;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public int insertUser(User user) {
		int num = 0;
		try {
			//校验账号是否已存在
			boolean isExist = checkUser(user);
			if(isExist) {
				user.setUserPassword(EncryptUtil.string2MD5(user.getUserPassword()));
				num = userMapper.insertSelective(user);
			}
		} catch (Exception e) {
			log.error("注册失败e={}",e);
		}
		return num;
	}
	
	public boolean checkUser(User user) {
		boolean isExist = false;
		try {
			User u = userMapper.checkUser(user);
			if(u == null || StringUtils.isBlank(u.getTelphone())) {
				isExist = true;
			}
		} catch (Exception e) {
			log.error("校验用户是否存在失败e={}",e);
		}
		return isExist;
	}

	@Override
	public PageInfo<User> selectUserList(UserVo record) {
		try {
			PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectUserList(record));
			return pageInfo;
		} catch (Exception e) {
			log.error("查询用户列表失败,e={}",e);
		}
		return null;
	}

	@Override
	public UserInfo login(User record) {
		try {
			record.setUserPassword(EncryptUtil.string2MD5(record.getUserPassword()));
			User u = userMapper.login(record);
			if(u != null) {
				UserInfo userInfo = userInfoService.queryUserInfo(u);
				return userInfo;
			}
		} catch (Exception e) {
			log.error("登录失败,e={}",e);
		}
		return null;
	}
}
