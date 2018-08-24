package com.dianshang.cn.controller;

import org.apache.shiro.authc.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dianshang.cn.utils.AbstractRestResponseUtil;
import com.dianshang.cn.utils.DefaultRestApiResponse;

@RestControllerAdvice
public class ExceptionController {

	// 捕捉 CustomRealm 抛出的异常
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> handleShiroException(Exception ex) {
    	ResponseEntity result = null;
		AbstractRestResponseUtil restResponseUtil = new DefaultRestApiResponse();
		restResponseUtil.setMsg(ex.getMessage());
		restResponseUtil.setResCode(null);
		result = new ResponseEntity<AbstractRestResponseUtil>(restResponseUtil, HttpStatus.OK);
		return result;
    }
}
