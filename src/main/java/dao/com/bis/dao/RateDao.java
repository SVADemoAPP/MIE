package com.bis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bis.model.ShopModel;

public interface RateDao {
	public int selectCountByShop(@Param("tableName") String tableName,@Param("shop") ShopModel shop);
	
	public Integer selectXByCategoryId(@Param("id") int id);
	
	public Integer selectYByCategoryId(@Param("id") int id);
	
	public int selectAllCountByShop(@Param("tableName") String tableName,@Param("shop") ShopModel shop);
	
	public int selectAllCount();
	 
	public List<Integer> selectAllShopId();
	
    public List<Integer> selectShopIdByStoreId(@Param("storeId")String storeId);	
	
	public List<Integer> selectShopIdByMapId(@Param("mapId")String mapId);
	
	public double selectVisitorById(@Param("id") int id);
	
	public String selectShopNameById(@Param("id") int id);
	
	public int selectCountDelayTime(@Param("tableName") String tableName,@Param("shop") ShopModel shop,@Param("nowDay") String nowDay);
	
	public int selectCountDelayTime1(@Param("tableName") String tableName,@Param("shop") ShopModel shop,@Param("nowDay") String nowDay);
	
	public int selectCount(@Param("tableName") String tableName,@Param("id") int id,@Param("nowDay") String nowDay);
	
	public ShopModel getShopInfoById(@Param("id")int id);
	
	public List<ShopModel> getShopInfoByMapId(@Param("mapId")String mapId);
	
	public List<ShopModel> getShopInfoByStore(@Param("storeId")String storeId);
	
	public int getShopCountByShopId(@Param("tableName")String tableName,@Param("shop")ShopModel shop);
	
	public int getShopCountByShopIdRound(@Param("tableName")String tableName,@Param("shop")ShopModel shop);
	
}