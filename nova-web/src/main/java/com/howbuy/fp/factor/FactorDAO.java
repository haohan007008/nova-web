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
public class FactorDAO {
	private static Logger log = Logger.getLogger(FactorDAO.class);
	
	@Autowired
	private SqlHelper sqlHelper;

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}
	
	
	/**
	 * getFactors
	 * 根据交易日期和基金代码查询系数
	 * @param tradeDate
	 * @param jjdm
	 * @param pageIdx
	 * @return 创建时间：2017年2月10日 上午10:13:42
	 */
	public PagedResult<Factor> getFactors(String tradeDate,String jjdm,int pageIdx){
		List<Object> parameters = new ArrayList<>();
		//List<Object> pWithPgIdx = new ArrayList<>();
		List<Factor> factors = new ArrayList<>();
		
		PagedResult<Factor> pageResult = new PagedResult<>();
		
		ConfContext context = this.sqlHelper.getSqlContext();
		
		String sql = context.getScript("getFactorsBypg");
		String sql_total = context.getScript("getFactorsCount");
		
		if(!Constants.isBlank(jjdm)){
			sql += " and secu_code = ? ";
			sql_total += " and secu_code = '"+jjdm+"'";
			parameters.add(jjdm);
			//pWithPgIdx.add(jjdm);
		}
		
		if(!Constants.isBlank(tradeDate)){
			//格式转换：2017-01-05 转换到 20170105
			tradeDate = Constants.parseDate(tradeDate, "yyyy-MM-dd", "yyyyMMdd");
			sql += " and trade_Date = ? ";
			sql_total += " and trade_Date = '"+tradeDate+"'";
			
			parameters.add(tradeDate);
			//pWithPgIdx.add(tradeDate);
		}
		
		sql = Constants.ParsePageStr(sql, "RN_");
		int pageSize = Integer.parseInt(context.getVar("data_page_size"));
		
		parameters.add(pageSize*(pageIdx-1)+1);
		parameters.add(pageSize*pageIdx);
		
		log.info(sql);
		
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql,parameters);
		
		if(hst !=null && hst.size() >0){
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> hashtable = (Hashtable<String, Object>) iterator.next();
				Factor factor = new Factor();
				factor.setAlpha(Constants.toDouble(hashtable.get("ALPHA")));
				factor.setBelta1(Constants.toDouble(hashtable.get("BELTA1")));
				factor.setBelta2(Constants.toDouble(hashtable.get("BELTA2")));
				factor.setBelta3(Constants.toDouble(hashtable.get("BELTA3")));
				factor.setBelta4(Constants.toDouble(hashtable.get("BELTA4")));
				
				factor.setChangeratio(Constants.toDouble(hashtable.get("CHANGERATIO")));
				factor.setCybr(Constants.toDouble(hashtable.get("CYBR")));
				factor.setHs300r(Constants.toDouble(hashtable.get("HS300R")));
				factor.setNetvalue(Constants.toDouble(hashtable.get("NETVALUE")));
				factor.setPort10r(Constants.toDouble(hashtable.get("PORT10R")));
				factor.setZxbr(Constants.toDouble(hashtable.get("ZXBR")));
				
				factor.setJjdm(hashtable.get("SECU_CODE").toString());
				//factor.setMarket(hashtable.get("SECU_MARKET").toString());
				factor.setTradeDate(hashtable.get("TRADE_DATE").toString());
				factor.setXgsj(hashtable.get("XGSJ").toString());
				factors.add(factor);
			}
		}
		
		pageResult.setDataList(factors);
		pageResult.setTotal(Integer.parseInt(this.sqlHelper.query4OneVal(sql_total,null)));
		
		return pageResult;
	}
	
	
	public Map<String, Object> getFactorDiff(String jjdm,int fetchSize){
		List<Object> parameters = new ArrayList<>();
		List<Object> tradeDate = new ArrayList<>();
		List<Object> alpha = new ArrayList<>();
		List<Object> belta1 = new ArrayList<>();
		List<Object> belta2 = new ArrayList<>();
		List<Object> belta3 = new ArrayList<>();
		List<Object> belta4 = new ArrayList<>();
		
		ConfContext context = this.sqlHelper.getSqlContext();
		String sql = context.getScript("getFactorsDiff");
		parameters.add(jjdm);
		parameters.add(fetchSize);
		List<Hashtable<String, Object>> hst = this.sqlHelper.executeQuery(sql,parameters);
		
		if(hst !=null && hst.size() >0){
			
			
			int idx = 0 ;
			for (Iterator iterator = hst.iterator(); iterator.hasNext();) {
				Hashtable<String, Object> hashtable = (Hashtable<String, Object>) iterator.next();
				tradeDate.add(Constants.toDouble(hashtable.get("TRADE_DATE").toString()));
				alpha.add(Constants.toDouble(hashtable.get("ALPHA").toString()));
				belta1.add(Constants.toDouble(hashtable.get("BELTA1").toString()));
				belta2.add(Constants.toDouble(hashtable.get("BELTA2").toString()));
				belta3.add(Constants.toDouble(hashtable.get("BELTA3").toString()));
				belta4.add(Constants.toDouble(hashtable.get("BELTA4").toString()));
				
				idx++;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("categories", tradeDate);
		map.put("alpha", alpha);
		map.put("belta1", belta1);
		map.put("belta2", belta2);
		map.put("belta3", belta3);
		map.put("belta4", belta4);
		
		return map;
	}
}	
