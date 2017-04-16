package com.howbuy.fp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.esotericsoftware.reflectasm.MethodAccess;

/** 
 * @author LvMeng
 * @version 2017年4月5日 下午5:08:14
 */
public class BeanUtils {
	 private static Map<Class,MethodAccess> methodMap = new HashMap<Class,MethodAccess>();
	 private static Map<String,Integer> methodIndexMap = new HashMap<String,Integer>();  
     private static Map<Class,List<String>> fieldMap = new HashMap<Class,List<String>>(); 
     
     public static void copyPropertiesfromHashtable(Object desc ,Hashtable<String, Object> orgi){
    	 MethodAccess descMethodAccess = methodMap.get(desc.getClass());  
         if(descMethodAccess == null){  
        	 descMethodAccess = cache(desc);  
         }
         List<String> fieldList = fieldMap.get(desc.getClass());
         for (String field : fieldList) {  
             String getKey = StringUtils.uncapitalize(field);  
             String setkey = desc.getClass().getName() + "." + "set" + field;  
             Integer setIndex = methodIndexMap.get(setkey);  
             
             if(setIndex != null && orgi.get(getKey) != null){
                 descMethodAccess.invoke(desc, setIndex.intValue(), orgi.get(getKey));
             }  
         }  
     }
     
     public static void copyProperties(Object desc,Object orgi){  
         MethodAccess descMethodAccess = methodMap.get(desc.getClass());  
         if(descMethodAccess == null){  
             descMethodAccess = cache(desc);  
         }  
         MethodAccess orgiMethodAccess = methodMap.get(orgi.getClass());  
         if(orgiMethodAccess == null){  
             orgiMethodAccess = cache(orgi);  
         }  
           
         List<String> fieldList = fieldMap.get(orgi.getClass());  
         for (String field : fieldList) {  
             String getKey = orgi.getClass().getName() + "." + "get" + field;  
             String setkey = desc.getClass().getName() + "." + "set" + field;  
             Integer setIndex = methodIndexMap.get(setkey);  
             if(setIndex != null){  
                 int getIndex = methodIndexMap.get(getKey);  
                 descMethodAccess.invoke(desc, setIndex.intValue(), orgiMethodAccess.invoke(orgi, getIndex));  
             }  
         }  
           
           
     }  
     
     private static MethodAccess cache(Object orgi){  
         synchronized (orgi.getClass()) {  
             MethodAccess methodAccess = MethodAccess.get(orgi.getClass());   
             Field[] fields = orgi.getClass().getDeclaredFields();  
             List<String> fieldList = new ArrayList<String>(fields.length);  
             for (Field field : fields) {  
                 if(Modifier.isPrivate(field.getModifiers()) && ! Modifier.isStatic(field.getModifiers())){  
                     //非公共私有变量  
                     String fieldName = StringUtils.capitalize(field.getName());  
                     int getIndex = methodAccess.getIndex("get"+fieldName);  
                     int setIndex = methodAccess.getIndex("set"+fieldName);  
                     methodIndexMap.put(orgi.getClass().getName()+"."+"get"+fieldName, getIndex);  
                     methodIndexMap.put(orgi.getClass().getName()+"."+"set"+fieldName, setIndex);  
                     fieldList.add(fieldName);  
                 }  
             }  
             fieldMap.put(orgi.getClass(),fieldList);  
             methodMap.put(orgi.getClass(), methodAccess);  
             return methodAccess;  
         }  
     }  
}
