package com.dianshang.cn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.model.entity.UserInfo;
import com.dianshang.cn.model.vo.UserVo;
import com.dianshang.cn.service.UserService;
import com.dianshang.cn.utils.AbstractRestResponseUtil;
import com.dianshang.cn.utils.DefaultRestApiResponse;
import com.dianshang.cn.utils.DictionaryUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="userManager", description="用户管理")
@RequestMapping("/userManager")
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user", method = {RequestMethod.POST})
	@ApiOperation(value="userManager", notes="用户注册")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		ResponseEntity<?> result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		try {
			int num = userService.insertUser(user);
			if(num > 0) {
				restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getLabel());
				restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getValue());
			}else {
				restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getLabel());
				restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getValue());
			}
			restResponseUtil.setObj(num);
		} catch (Exception e) {
			log.error("注册失败,e={}",e);
		}
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/users", method = {RequestMethod.POST})
	@ApiOperation(value="userManager", notes="用户列表")
	public ResponseEntity<?> queryUserList(@RequestBody UserVo userVo){
		ResponseEntity<?> result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		try {
			if(userVo.getPageNum() <= 0) {
				userVo.setPageNum(1);
			}
			if(userVo.getPageSize() <= 0) {
				userVo.setPageSize(10);
			}
			PageHelper.startPage(userVo.getPageNum(), userVo.getPageSize());
			PageInfo<User> page = userService.selectUserList(userVo);
			if(page != null) {
				restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getLabel());
				restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getValue());
			}else {
				restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getLabel());
				restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getValue());
			}
			restResponseUtil.setObj(page);
		} catch (Exception e) {
			log.error("查询用户列表失败,e={}",e);
		}
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ApiOperation(value = "userManager", notes="用户登录")
	public ResponseEntity<?> login(@RequestBody User user){
		ResponseEntity<?> result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		try {
			UserInfo userInfo = userService.login(user);
			restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getLabel());
			restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_SUCCESS.getValue());
			restResponseUtil.setObj(userInfo);
		} catch (Exception e) {
			restResponseUtil.setMsg(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getLabel());
			restResponseUtil.setResCode(DictionaryUtil.ReturnCodeEnum.CODE_FAIL.getValue());
			log.error("登录失败,e={}",e);
		}
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	}
}
