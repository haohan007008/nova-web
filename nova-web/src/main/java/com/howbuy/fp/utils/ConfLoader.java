package com.howbuy.fp.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ConfLoader {
	private static Logger log = Logger.getLogger(ConfLoader.class);
	private static ConfLoader loader;
	private HashMap<String, String> scripts;
	
	private ConfLoader(){
		scripts = new HashMap<String, String>();
		init();
	}
	
	private void init(){
		SAXReader reader = new SAXReader();
		String path= new File(C3P0Connection.class.getResource("/").getPath()).getParentFile().getPath();
		try {
			Document document = reader.read(new File(path+"/conf/config.xml"));
			Element root = document.getRootElement();
			List<Element> Elements = root.elements();
			for (Element element : Elements) {
				List<Element> childElements = element.elements();
				for (Element child : childElements) {
					if("script".equals(child.getName())){
						scripts.put(child.attributeValue("name"), child.getText().trim());
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
	
	public static ConfLoader getInstance(){
		if(loader == null )
			loader = new ConfLoader();
		return loader;
	}
	
	public static void main(String[] args) {
		ConfLoader.getInstance();
		
	}

}
