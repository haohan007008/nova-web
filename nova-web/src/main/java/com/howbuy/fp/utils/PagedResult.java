package com.howbuy.fp.utils;

import java.util.List;

/** 
 * @author LvMeng
 * @version 2017年2月10日 上午10:15:59
 */
public class PagedResult<T> {
	private int total;
	private List<T> dataList;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
