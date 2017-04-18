package com.howbuy.fp.production;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.order.OrderDAO;
import com.howbuy.fp.product.Product;
import com.howbuy.fp.product.ProductAction;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年4月7日 上午9:04:22
 */
@Service
public class ProductionService {
	
	private static Logger log = Logger.getLogger(ProductionService.class);
	
	@Autowired
	private ProductionDAO productionDAO;
	
	
	public void log(int orderId,int userId,String action,String remark){
		productionDAO.addorderlog(orderId, userId, action, remark);
	}
	
	public List<Product> unionList(Product product,
			HttpServletRequest request){
		
		List<Product> list = (List<Product>)request.getSession().getAttribute("mypro");
				
		if(list != null && list.size() > 0){
			boolean has = false;
			for (int i = 0; i < list.size(); i++) {
				Product prd = (Product)list.get(i);
				if(prd.getId() == product.getId()){
					has = true;
					list.get(i).unionItems(product.getItems());
					break;
				}
			}
			if(!has)
				list.add(product);
		}else{
			list = new ArrayList<>();
			list.add(product);
		};
		log.debug((JSON.toJSONString(list)));
		request.getSession().setAttribute("mypro",list);
		return list;
	}

	/**
	 * addProduction
	 *
	 * @return 创建时间：2017年4月15日 下午10:59:51
	 */
	public RespResult<String> addProduction(Production production) {
		
		return productionDAO.addProduction(production);
	}

	/**
	 * getProductions
	 *
	 * @param custName
	 * @param i
	 * @param limit
	 * @param staffId
	 * @return 创建时间：2017年4月15日 下午11:54:07
	 */
	public RespResult<List<Production>> getProductions(
			String custName, int i, int limit, int staffId) {
		RespResult<List<Production>> resp = new RespResult<List<Production>>();
		Production production = new Production();
		production.setManufacturer(custName);
		resp.setObj(productionDAO.getProductionList(production, i, limit, false));
		resp.setTotal(Integer.parseInt(productionDAO.getProductionListCount(production)));
		return resp;
	}

	/**
	 * getProductionsById
	 *
	 * @param pId
	 * @return 创建时间：2017年4月16日 上午12:24:56
	 */
	public RespResult<Production> getProductionsById(int pId) {
		RespResult<Production> resp = new RespResult<Production>();
		resp.setObj(productionDAO.getProductionById(pId));
		return resp;
	}

	/**
	 * auditorder
	 *
	 * @param production
	 * @return 创建时间：2017年4月16日 上午10:56:24
	 */
	public RespResult<String> auditorder(Production production) {
		RespResult<String> resp = new RespResult<String>();
		if("PASS".equals(production.getAction())){
			this.log(production.getId(), production.getCurOperatorId(),
					String.valueOf(production.getCurNode()), "经理审批通过！"+production.getRemark());
			production.setCurNode(9);
			resp = productionDAO.updateProductionState(production);
		}else if("REJECT".equals(production.getAction())){
			this.log(production.getId(), production.getCurOperatorId(),
					String.valueOf(production.getCurNode()), "经理审批未通过！"+production.getRemark());
			production.setCurNode(8);
			//production.setCurOperatorId();
			resp = productionDAO.updateProductionState(production);
		}
		return resp;
	}

	/**
	 * editProduction
	 *
	 * @param production
	 * @return 创建时间：2017年4月17日 下午4:17:46
	 */
	public RespResult<String> editProduction(Production production) {
		
		return productionDAO.editProduction(production);
	}
}
