package com.howbuy.fp.factor;


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

import com.howbuy.fp.utils.ConfContext;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.PagedResult;
import com.howbuy.fp.utils.SqlHelper;


/** 
 * @author LvMeng
 * @version 2017骞�2鏈�8鏃� 涓嬪崍4:51:17
 */
@Service
public class FundDAO {
	private static Logger log = Logger.getLogger(FundDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	/**
	 * getFundListTop10
	 * 获取基金名字和代码，用于基金下拉框智能提醒
	 * @param jjdm
	 * @return 创建时间：2017年2月15日 下午1:29:44
	 */
	public List<Fund> getFundListTop10(String jjdm){
		List<Object> parameters = new ArrayList<>();
		List<Fund> funds = new ArrayList<>();
		ConfContext context = this.sqlHelper.getSqlContext();
		
		String sql = context.getScript("getFundListTop10");
		parameters.add(jjdm+"%");
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql,parameters);
		
		if(hst !=null && hst.size() >0){
			
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> hashtable = (Hashtable<String, Object>) iterator.next();
				Fund fund = new Fund();
				fund.setJjdm(hashtable.get("JJDM").toString());
				fund.setJjlb(hashtable.get("TYPENAME").toString());
				fund.setJjmc(hashtable.get("JJMC").toString());
				funds.add(fund);
			}
		}
		
		return funds;
	}
}	
