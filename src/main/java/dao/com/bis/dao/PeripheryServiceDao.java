package com.bis.dao;

import java.util.List;

import com.bis.model.PeripheryCategoryModel;

import net.sf.json.JSONObject;

public interface PeripheryServiceDao {
	
	public int updateData(PeripheryCategoryModel model);
	
	public int saveData(List<JSONObject> list);
}
