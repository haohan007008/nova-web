package com.howbuy.fp.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ConfContext {
	private static Logger log = Logger.getLogger(ConfContext.class);
	private static ConfContext context;
	
	private HashMap<String, String> scripts;
	private HashMap<String, String> variables;
	
	private ConfContext(){
		scripts = new HashMap<String, String>();
		variables = new HashMap<String, String>();
		init();
	}
	
	private void init(){
		SAXReader reader = new SAXReader();
		String path= new File(ConfContext.class.getResource("/").getPath()).getParentFile().getPath();
		try {
			Document document = reader.read(new File(path+"/classes/config.xml"));
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> Elements = root.elements();
			for (Element element : Elements) {
				@SuppressWarnings("unchecked")
				List<Element> childElements = element.elements();
				for (Element child : childElements) {
					if("script".equals(child.getName())){
						scripts.put(child.attributeValue("name"), child.getText().trim());
					}
					if("variable".equals(child.getName())){
						variables.put(child.attributeValue("name"), child.getText().trim());
					}
				}
			}
		} catch ( DocumentException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	public String getScript(String name){
		return scripts.get(name);
	}
	
	public String getVar(String name){
		return variables.get(name);
	}
	
	public static ConfContext getInstance(){
		if(context == null )
			context = new ConfContext();
		return context;
	}
	
	public static void main(String[] args) {
		ConfContext.getInstance();
		
	}

}
