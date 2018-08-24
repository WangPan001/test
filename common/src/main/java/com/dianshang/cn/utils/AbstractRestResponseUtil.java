package com.dianshang.cn.utils;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractRestResponseUtil<T> {
	
	private String resCode;
	
	private String msg;
	
	private T obj;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
	public String AbstractRestResponseStr(AbstractRestResponseUtil str) {
		return JSONObject.toJSONString(str);
	}
}
