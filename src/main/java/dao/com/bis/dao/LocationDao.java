/**   
 * @Title: LocationDao.java 
 * @Package com.sva.dao 
 * @Description: LocationDao接口类 
 * @author labelCS   
 * @date 2017年6月21日 下午3:28:34 
 * @version V1.0   
 */
package com.bis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bis.model.LocationModel;
import com.bis.model.ShopModel;
import com.bis.model.TrendMapModel;
import com.bis.model.TrendShopModel;

/**
 * @ClassName: LocationDao
 * @Description: LocationDao接口类
 * @author labelCS
 * @date 2017年6月21日 下午3:28:34
 * 
 */
public interface LocationDao {

    /**
     * @Title: queryAllUserId
     * @Description: 获取指定日期当天所有的用户id
     * @param tableName
     * @return
     */
    public List<String> queryAllUserId(@Param("tableName") String tableName);

    /**
     * @Title: queryByUserId
     * @Description: 获取指定日期当天、指定用户的位置信息（10s一条）
     * @param userId
     * @param tableName
     * @return
     */
    public List<LocationModel> queryByUserId(@Param("userId") String userId, @Param("tableName") String tableName);

    /**
     * @Title: queryHeatmap
     * @Description: 获取热力图数据
     * @param floorNo
     * @param time
     * @param tableName
     * @return
     */
    public List<LocationModel> queryHeatmap(@Param("mapId") String floorNo, @Param("time") long time,
            @Param("tableName") String tableName);

    public List<LocationModel> queryHeatmap5(@Param("floorNo") String floorNo, @Param("time") long time,
            @Param("tableName") String tableName);

    public List<LocationModel> queryHeatmap6(@Param("floorNo") String floorNo, @Param("tableName") String tableName);

    /**
     * locationPhone为非匿名订阅，提高数据查询效率
     * 
     * @param userId
     * @return
     */
    public List<LocationModel> queryLocationByUseId(String userId);

    public List<LocationModel> getUserId(@Param("floorNo") String floorid, @Param("time") String time,
            @Param("tableName") String tableName);

    public List<LocationModel> getMark(@Param("userId") String userId, @Param("time") String time,
            @Param("tableName") String tableName);

    public List<LocationModel> queryOverData(@Param("floorNo") int floorNo, @Param("time") String time,
            @Param("tableName") String tableName);

    /**
     * @Title: queryLocationForPosition @Description: 查询指定日期当天的顾客数据 @param time
     *         指定日期，形如YYYYMMDD @return Collection<LocationModel> @throws
     */
    public List<Map<String, Object>> queryLocationForPosition(@Param("floorNo") String floorNo,
            @Param("timeList") List<String> timeList);

    /**
     * @Title: queryScatterMapData @Description: 获取散点图数据 @param floorNo
     *         楼层号 @param time 时间 @return Collection<LocationModel> @throws
     */
    public List<Map<String, Object>> queryScatterMapData(@Param("floorNo") String floorNo, @Param("time") long time,
            @Param("tableName") String tableName);

    public int getNumberByMinute(@Param("tableName") String tableName, @Param("placeId") String placeId);

    public int getYesterdayNumber(@Param("tableName") String tableName, @Param("placeId") String placeId,
            @Param("time") long time);

    public int getNowPeople(@Param("tableName") String tableName, @Param("placeId") String placeId,
            @Param("time") String time);

    public int getOneHourData(@Param("mapId") String mapId, @Param("tableName") String tableName,
            @Param("time") long time);

    public int getOneDayData(@Param("mapId") String mapId, @Param("tableName") String tableName);

    public int getAllCount(@Param("mapId") String mapId, @Param("tableName") String tableName);

    public int getNowCount(@Param("times") long times, @Param("tableName") String tableName,
            @Param("shopData") ShopModel model);

    public int getNowCounts(@Param("times") long times, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public int getYesCount(@Param("beginTimes") long beginTimes, @Param("times") long times,
            @Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public List<String> getNowAllCount(@Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public List<String> getYesNowCount(@Param("tableName") String tableName, @Param("shopData") ShopModel model,
            @Param("times") long times);

    public int getAllTiaoshu(@Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public int getYesAllTiaoshu(@Param("tableName") String tableName, @Param("shopData") ShopModel model,
            @Param("times") long times);

    public int getMomentTotal(@Param("beginTimes") long beginTimes, @Param("times") long times,
            @Param("tableName") String tableName, @Param("mapId") String mapId);
    
    public int getMallTotal(@Param("beginTimes") long beginTimes, @Param("times") long times,
            @Param("tableName") String tableName, @Param("storeId") String storeId);
    
    public int getMallTotal1(@Param("beginTimes") long beginTimes,
            @Param("tableName") String tableName, @Param("storeId") String storeId);
    

    public List<String> getTodayUserIdList(@Param("tableName") String tableName, @Param("storeId") String storeId);
    
    public List<String> getTodayUserIdListByMapId(@Param("tableName") String tableName, @Param("mapId") String mapId);

    public Long getTodayDelayCount(@Param("tableName") String tableName, @Param("storeId") String storeId);
    
    public Long getTodayDelayCountByMapId(@Param("tableName") String tableName, @Param("mapId") String mapId);
    
    public List<LocationModel> getShopHeatMapByShopId(@Param("times") long times, @Param("tableName") String tableName,
            @Param("shopData") ShopModel model);

    public List<LocationModel> getTenminitShopData(@Param("times") long times, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public List<LocationModel> getMapHeatMapById(@Param("times") long times, @Param("tableName") String tableName,
            @Param("mapId") String mapId);
    
    public List<LocationModel> getMapHeatStore(@Param("times") long times, @Param("tableName") String tableName,
            @Param("storeId") String storeId);    
    
    public List<LocationModel> getMapHeatMapByIds(@Param("tableName") String tableName,
            @Param("mapId") String mapId);    

    public List<LocationModel> getMapPeriodHeatMapById(@Param("beginTimes") long beginTimes, @Param("times") long times,
            @Param("tableName") String tableName, @Param("shopData") ShopModel model);

    public List<LocationModel> getPeriodMapHeatMap(@Param("startTime") long startTime, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("mapId") String mapId);

    public List<LocationModel> getTenminitMallData(@Param("startTime") long startTime, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("storeId") String storeId);
    
    public List<LocationModel> getTenminitFloorData(@Param("startTime") long startTime, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("mapId") String mapId);

    public long getStoreMomentCount(@Param("startTime") long startTime, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("storeId") String storeId);
    
    public long getFloorMomentCount(@Param("startTime") long startTime, @Param("endTime") long endTime,
            @Param("tableName") String tableName, @Param("mapId") String mapId);    
    
    public List<String> queryAllUserIdByMapId(@Param("mapId") String mapId, @Param("tableName") String tableName);

    public LocationModel getOtherMapIdByMaxTime(@Param("mapId") String mapId, @Param("userId") String userId,
            @Param("tableName") String tableName);

    public void saveTrendMapByHour(TrendMapModel model);

    public void saveTrendMapByDay(TrendMapModel model);
    
    public void saveTrendShopByHour(TrendShopModel model);

    public void saveTrendShopByDay(TrendShopModel model);
    
    public List<String> queryAllUserIdByShopId(@Param("shopId") int shopId, @Param("tableName") String tableName);
    
    public LocationModel getOtherShopIdByMaxTime(@Param("shopId") int shopId, @Param("userId") String userId,
            @Param("tableName") String tableName);
    
    public int getCountByTimestamp(@Param("timestamp")long timestamp,@Param("tableName")String tableName);
}
