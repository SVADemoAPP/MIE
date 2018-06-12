package com.bis.web.auth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.dao.PeripheryServiceDao;
import net.sf.json.JSONObject; 

@Service
public class PeripheryService {
	
	@Autowired
	private PeripheryServiceDao dao;
	
	private static final Logger LOG = Logger.getLogger(PeripheryService.class);
	
	public void readPeripheralService(){
		try {
			SAXReader reader = new SAXReader();
	    	String path = getClass().getResource("/").getPath();
		    Document document = reader.read(new File(path + "peripheralService.xml"));
		    Element root = document.getRootElement();
		    List<Element> childElements = root.elements();
		    List<JSONObject> list = new ArrayList<>();
		    for (Element child : childElements) {
		    	List<Element> elementList = child.elements();
	            JSONObject jsonObject=new JSONObject();
	            jsonObject.put("id","");
	            jsonObject.put("category","");
	            jsonObject.put("shopName","");
	            jsonObject.put("shopAddress","");
	            jsonObject.put("distance","");
	            jsonObject.put("peripheryCategory","");
	            jsonObject.put("path","");
	            jsonObject.put("maxPath","");
	            jsonObject.put("phoneNumber","");
	            jsonObject.put("businessHours","");
	            jsonObject.put("storeId","");
		    	for (Element ele : elementList) {
	                jsonObject.put(ele.getName(), ele.getText());
	            }
		    	list.add(jsonObject);
	          
			}
		    dao.saveData(list);
		     
		} catch (Exception e) {
			LOG.error(e);
			// TODO: handle exception
		}
	}

}
