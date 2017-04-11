package com.howbuy.fp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howbuy.fp.order.OrderDAO;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017��3��2�� ����8:01:18
 */
@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private OrderDAO orderDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public RespResult<UserVO> login(String loginname,String loginpwd){
		RespResult<UserVO> resp = new RespResult<UserVO>();
		UserVO userVO = userDAO.queryUser(loginname, loginpwd);
		
		if(userVO == null){
			resp.setSuccess(false);
			resp.setDesc("用户名或密码不正确！");
		}else {
			userVO.setRoles(userDAO.getRoles(userVO.getUserId()));
			userVO.setPerms(userDAO.getPerms(userVO.getUserId()));
			userVO.setMyOrderCount(orderDAO.getMyOperOrderCount(userVO.getUserId()));
			
			resp.setObj(userVO);
		}
		return resp;
	}
	
	public RespResult<String> updateUserInfo(UserVO user){
		
		RespResult<String> resp = userDAO.update(user);
		return resp;
	}
	
	public void log(int staffId,String action,String ip){
		userDAO.log(staffId, action, ip);
	}
}
