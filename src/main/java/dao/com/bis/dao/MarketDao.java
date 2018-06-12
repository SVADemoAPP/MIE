package com.bis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bis.model.MarketModel;
import com.bis.model.NewUserModel;
import com.bis.model.ShopModel;
import com.bis.model.StatisticsModel;
public interface MarketDao {
    
    /** 
     * @Title: getAllStore 
     * @Description: 获取商场列表
     * @return 
     */
    public List<MarketModel> getAllStore();
    
    /**
     * 
     * @Title: getStoreName 
     * @Description: 获取所有商场名
     * @return
     */
    public List<String> getStoreName();
    
    /** 
     * @Title: saveInfo 
     * @Description: 添加商场信息
     * @param model
     * @return 
     */
    public int saveInfo(MarketModel model);
    
    public int checkName(@Param("storeName")String name);
    public int checkName1(@Param("storeName")String name,@Param("id")String id);
    /** 
     * @Title: updateInfo 
     * @Description: 更新商场信息
     * @param model
     * @return 
     */
    public int updateInfo(MarketModel model);
    
    /** 
     * @Title: deleteById 
     * @Description: 删除指定id的商场信息
     * @param id
     * @return 
     */
    public int deleteById(String id);
    
    public List<Map<String, Object>> getShopWeekData(@Param("tableName") String tableName,
            @Param("storeId") String storeId,@Param("beginTime")String beginTime,@Param("lastTime")String lastTime);
    
    public List<Map<String, Object>> getShopWeekData1(@Param("tableName") String tableName,
            @Param("storeId") String storeId,@Param("beginTime")String beginTime);
    
    public List<Map<String, Object>> getShopWeekData2(@Param("tableName") String tableName,
            @Param("storeId") String storeId,@Param("lastTime")String lastTime);
    
    public List<ShopModel> getShopDataById( @Param("storeId") String storeId);
    
    public List<NewUserModel> getAllNewDataByShopId( @Param("storeId") String storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    
    public List<String> getUserIdList(@Param("tableName") String tableName, @Param("storeId") String storeId,
            @Param("timeDate") String timeDate);
    
    public List<StatisticsModel> getUserIdListByMapId(@Param("mapId") String mapId,
            @Param("timeDate") String timeDate);
    
    public Float getDelaytimeSum(@Param("tableName") String tableName, @Param("storeId") String storeId,
            @Param("timeDate") String timeDate);
    public Float getDelaytimeSumByMapId(@Param("tableName") String tableName, @Param("mapId") String mapId,
            @Param("timeDate") String timeDate);
}
