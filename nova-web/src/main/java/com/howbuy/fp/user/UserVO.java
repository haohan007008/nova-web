package com.howbuy.fp.user;

public class UserVO {
	private int userId;
	private String userName;
	private String loginName;
	private String address ;
	private String telphone1;
	private String telphone2;
	private int deptId;
	
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelphone1() {
		return telphone1;
	}
	public void setTelphone1(String telphone1) {
		this.telphone1 = telphone1;
	}
	public String getTelphone2() {
		return telphone2;
	}
	public void setTelphone2(String telphone2) {
		this.telphone2 = telphone2;
	}
	
}
