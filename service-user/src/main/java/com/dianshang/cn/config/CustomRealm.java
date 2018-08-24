package com.dianshang.cn.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianshang.cn.dao.UserMapper;

public class CustomRealm extends AuthorizingRealm{

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 
	 * 方法描述：   获取授权信息
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: doGetAuthorizationInfo 
	 * @date 2018年8月14日 上午11:24:33
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("============权限认证=================");
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取该用户角色
		String role = userMapper.getRole(username);
		Set<String> set = new HashSet<>();
		//需要将role封装到set作为info.setRoles()的参数
		set.add(role);
		//设置该用户拥有的角色
		info.setRoles(set);
		return info;
	}

	/**
	 * 
	 * 方法描述：   获取身份验证信息
	 * shrio中，最终是通过realm来获取应用程序中的用户、角色及权限信息的
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: doGetAuthorizationInfo 
	 * @date 2018年8月14日 上午11:13:25
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("===============身份认证方法==============");
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		//从数据库获取对应的用户名与密码
		String password = userMapper.getPassword(passwordToken.getUsername());
		if(StringUtils.isBlank(password)) {
			throw new AuthenticationException("用户名不正确");
		}else if(!password.equals(new String((char[])passwordToken.getCredentials()))){
			throw new AuthenticationException("密码不正确");
		}
		//password = new Md5Hash(password, passwordToken.getUsername()).toHex();
		return new SimpleAuthenticationInfo(passwordToken.getPrincipal(), password, getName());
	}

}
