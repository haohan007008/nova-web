package com.howbuy.fp.utils;

import com.alibaba.fastjson.JSON;

/** 
 * @author LvMeng
 * @version 2017�?2�?10�? 上午10:15:59
 */
public class RespResult<T> {
	private boolean success = true;
	private int total;

	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	private String desc;
	private T obj;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	
	public String toString(){
		return JSON.toJSONString(this);
	}
	
	
}
