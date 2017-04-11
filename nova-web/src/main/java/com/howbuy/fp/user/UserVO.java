package com.howbuy.fp.user;

import java.util.List;

public class UserVO {
	private int userId;
	private String userPwd;
	private String oldPwd;
	
	private String userName;
	private String loginName;
	private String address ;
	private String telphone1;
	private String telphone2;
	private String deptName;
	private int deptId;
	private int loginCount = 0;
	private String lastLoginDate;
	private String lastLoginIP;
	private List<RoleVO> roles;
	private List<PermVO> perms;
	private int myOrderCount=0;
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public int getMyOrderCount() {
		return myOrderCount;
	}
	public void setMyOrderCount(int myOrderCount) {
		this.myOrderCount = myOrderCount;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<RoleVO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}
	public List<PermVO> getPerms() {
		return perms;
	}
	public void setPerms(List<PermVO> perms) {
		this.perms = perms;
	}
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
