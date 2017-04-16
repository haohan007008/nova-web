package com.howbuy.fp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.howbuy.fp.production.Production;
import com.howbuy.fp.utils.BeanUtils;

/** 
 * @author LvMeng
 * @version 2017年4月5日 下午4:59:50
 */
public class BeanCopyUtils {
	@Test
	public void hashTable2object(){
		 Hashtable< String, Object> ht = new Hashtable<String , Object>();
		 Production prod = new Production();
		 ht.put("contractNo", "1111");
		 ht.put("amount",2000.89);
		 BeanUtils.copyPropertiesfromHashtable(prod,ht);
		 
		 System.out.println(prod.getContractNo());
		 System.out.println(prod.getAmount());
	}
	
}
