package com.bis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bis.model.MapMngModel;
import com.bis.model.MarketModel;

/**
 * @ClassName: MapsDao
 * @Description: 地图dao
 * @author JunWang
 * @date 2017年6月20日 上午10:22:22
 * 
 */
public interface MapMngDao {

    /**
     * @Title: doquery
     * @Description: 获取所有的地图信息
     * @return
     */
    public List<MapMngModel> doquery();
    
    
    public List<MapMngModel> doquerys(String storeId);
    
    public List<MapMngModel> doqueryss(String mapId);

    /**
     * @Title: doallquery
     * @Description: 获取所有的地图信息
     * @return
     */
    public List<MapMngModel> doallquery();

    public List<MapMngModel> getMapDataByStoreName(String name);
    
    public List<MapMngModel> getMapDataByStoreName1(String name);
    
    /**
     * @Title: getAllMapId
     * @Description: 获取所有mapId
     * @return
     */
    public List<String> getAllMapId();

    /**
     * @Title: getStoreIdByMapId
     * @Description: 根据mapId获取其对应的storeId
     * @return
     */
    public Integer getStoreIdByMapId(String mapId);

    /**
     * @Title: doquery
     * @Description: 获取所有的地图信息
     * @return
     */
    public List<MapMngModel> getMapDataByMapId(String mapId);

    /**
     * @Title: queryMapByStore
     * @Description: 获取指定商场的地图信息
     * @return
     */
    public List<MapMngModel> queryMapByStore(String id);

    /**
     * @Title: getMapNameByMapId
     * @Description: 根据mapId获取对应的楼层的地图名称
     * @param mapId
     * @return
     */
    public String getMapNameByMapId(String mapId);

    /**
     * @Title: getSvgNameByMapId
     * @Description: 根据mapId获取对应的楼层的svg地图名称
     * @param mapId
     * @return
     */
    public String getSvgNameByMapId(String mapId);

    /**
     * @Title: getNaviNameByMapId
     * @Description: 根据mapId获取对应的楼层的导航文件名称
     * @param mapId
     * @return
     */
    public String getNaviNameByMapId(String mapId);

    /**
     * @Title: getMapNameById
     * @Description: 根据id获取对应的楼层的地图名称
     * @param id
     * @return
     */
    public String getMapNameById(String id);

    /**
     * @Title: getSvgNameById
     * @Description: 根据id获取对应的楼层的svg地图名称
     * @param id
     * @return
     */
    public String getSvgNameById(String id);

    /**
     * @Title: getNaviNameById
     * @Description: 根据id获取对应的楼层的导航文件名称
     * @param id
     * @return
     */
    public String getNaviNameById(String id);

    /**
     * @Title: deleteMapByMapId
     * @Description: 删除mapId对应的地图信息
     * @param mapId
     * @return
     */
    public int deleteMapByMapId(String mapId);

    /**
     * @Title: updateMap
     * @Description: 更新地图信息
     * @param model
     * @return
     */
    public int updateMap(MapMngModel model);

    /**
     * @Title: saveMapInfo
     * @Description: 添加地图信息
     * @param model
     * @return
     */
    public int saveMapInfo(MapMngModel model);

    /**
     * @Title: checkMapIdIsExisted
     * @Description: 检查mapId是否已存在
     * @param mapId
     * @param placeId
     * @return
     */
    public int checkMapIdIsExisted(@Param("mapId") String mapId, @Param("id") String id);

    /**
     * @Title: getFloorByPlaceId
     * @Description: huoqu louceng
     * @param placeId
     * @return
     */
    public List<String> getFloorByPlaceId(@Param("storeId") String storeId);

    /**
     * @Title: getMapCarData
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param floorNo
     * @return
     */
    public MapMngModel getMapCarData(String mapId);

    /**
     * @Title: getParkingNumber
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param mapId
     * @return
     */
    public List<String> getParkingNumber(String mapId);
    
    public List<MarketModel> getStoreData(@Param("mapId")String mapId);
    
    public List<Map<String, Object>> getMapTrendByHour(@Param("mapId") String mapId,
            @Param("sign") int sign, @Param("time") String time);
    
    public List<Map<String, Object>> getMapTrendByDay(@Param("mapId") String mapId,
            @Param("sign") int sign, @Param("time") String time);
    
    public Integer getMapTrendByHourOther(@Param("mapId") String mapId,
            @Param("sign") int sign, @Param("time") String time);
    
    public Integer getMapTrendByDayOther(@Param("mapId") String mapId,
            @Param("sign") int sign, @Param("time") String time);
}
