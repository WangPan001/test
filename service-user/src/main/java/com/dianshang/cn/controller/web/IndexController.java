package com.dianshang.cn.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dianshang.cn.dao.UserMapper;
import com.dianshang.cn.model.entity.User;
import com.dianshang.cn.utils.AbstractRestResponseUtil;
import com.dianshang.cn.utils.DefaultRestApiResponse;
import com.dianshang.cn.utils.EncryptUtil;
import com.dianshang.cn.utils.DictionaryUtil.ReturnCodeEnum;

@Controller
public class IndexController {
	
	@Autowired
	private UserMapper userMapper;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		request.getSession().setAttribute("contextPath", path);
		return "login";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user){
		ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		//在认证提交前准备token
		user.setUserPassword(EncryptUtil.string2MD5(user.getUserPassword()));
		UsernamePasswordToken token = new UsernamePasswordToken(user.getTelphone(), user.getUserPassword());
		try {
			User u = userMapper.login(user);
			if(u == null) {
				restResponseUtil.setResCode(ReturnCodeEnum.CODE_FAIL.getValue());
				restResponseUtil.setMsg(ReturnCodeEnum.CODE_FAIL.getLabel());
			}else {
				//从securityUtils里边创建一个subject
				Subject subject = SecurityUtils.getSubject();
				//执行认证登录
				subject.login(token);
				//根据权限，指定返回数据
				String role = userMapper.getRole(user.getTelphone());
				if("user".equals(role)) {
					restResponseUtil.setMsg("欢迎登录");
				} else if("admin".equals(role)) {
					restResponseUtil.setMsg("欢迎来到管理员页面");
				} else {
					restResponseUtil.setMsg("权限错误");
				}
				restResponseUtil.setResCode(null);
			}
		} catch (AuthenticationException e) {
			restResponseUtil.setResCode(ReturnCodeEnum.CODE_FAIL.getValue());
			restResponseUtil.setMsg(ReturnCodeEnum.CODE_FAIL.getLabel());
			token.clear();
			log.error("登录异常",e);
		}
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
}
