package com.dianshang.cn.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dianshang.cn.dao.UserMapper;
import com.dianshang.cn.utils.AbstractRestResponseUtil;
import com.dianshang.cn.utils.DefaultRestApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(value="userManager", description="用户管理")
public class TestController {

	@Autowired
	private UserMapper userMapper;
	
	@ApiOperation(value="测试", notes="测试")
	@RequestMapping("/guest/enter")
	public ResponseEntity<?> login(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("欢迎进入，您的身份是游客");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	}
	
	@RequestMapping("/guest/getMessage")
	public ResponseEntity<?> submitLogin(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("您拥有获得该接口的信息的权限");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
	
	@RequestMapping("/user/getMessage")
	public ResponseEntity<?> userController(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("您拥有用户权限，获得该接口的信息");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
	
	@RequestMapping("/admin/getMessage")
	public ResponseEntity<?> admin(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("您拥有管理员权限，获得该接口的信息");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
	
	@RequestMapping("/notLogin")
	public ResponseEntity<?> notLogin(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("您尚未登录");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
	@RequestMapping("/notRole")
	public ResponseEntity<?> notRole(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("您没有权限");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
	@RequestMapping("/logout")
	public ResponseEntity<?> logout(){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg("成功注销");
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	} 
}
