package com.howbuy.fp.user;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.PagedResult;
import com.howbuy.fp.utils.SqlHelper;


/** 
 * @author LvMeng
 * @version 2017骞�2鏈�8鏃� 涓嬪崍4:51:17
 */
@Service
public class UserDAO {
	private static Logger log = Logger.getLogger(UserDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	public UserVO queryUser(String name,String pwd){
		
		List<Object> parameters = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("userlogin");
		log.debug("execute sql:" + sql );
		parameters.add(name);
		parameters.add(pwd);
		log.debug("parameters:" + JSON.toJSONString(parameters));
		
		Hashtable<String, Object> hst = this.sqlHelper.query4OneObject(sql, parameters);
		if(hst!=null){
			UserVO userVO = new UserVO();
			userVO.setUserId(Integer.parseInt(hst.get("Id").toString()));
			userVO.setUserName(hst.get("name").toString());
			userVO.setDeptId(Integer.parseInt(hst.get("deptId").toString()));
			userVO.setTelphone1(hst.get("telphone1").toString());
			userVO.setTelphone2(hst.get("telphone2").toString());
			userVO.setLoginName(hst.get("loginName").toString());
			return userVO;
		}else {
			return null;
		}
		
	}
	
	
}	