package com.bis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.bis.model.MapMngModel;
import com.bis.model.NewUserModel;
import com.bis.model.ShopCostModel;
import com.bis.model.ShopModel;
import com.bis.model.UserModel;

/**
 * @ClassName: ShopDao
 * @Description: 店铺dao
 * @author gyr
 * @date 2017年6月16日 下午2:46:50
 * 
 */
public interface ShopDao {

    public List<String> getTicketByLocation(@Param("mapId") String mapId, @Param("x") double x, @Param("y") double y);

    /**
     * 
     * @Title: doquery
     * @Description: 通过店铺名查找店铺
     * @param shopName
     * @return
     */
    public ShopModel doquery(String shopName);

    /**
     * 
     * @Title: queryShopByStore
     * @Description: 获取指定商场，类别的店铺信息
     * @param id
     * @param categoryId
     * @return
     */
    public List<ShopModel> queryShopByStore(@Param("id") String id, @Param("categoryId") String categoryId);

    /**
     * 
     * @Title: likequery
     * @Description: 模糊查询店铺名
     * @param key
     * @return
     */
    public List<String> likequery(String key);

    /**
     * @Title: queryAllShop
     * @Description: 查询所有店铺信息
     * @return
     */
    public List<ShopModel> queryAllShop();

    /**
     * @Title: getAllShop
     * @Description: 查询所有店铺信息
     * @return
     */
    public List<ShopModel> getAllShop();

    /**
     * @Title: getAllShopId
     * @Description: 查询所有shipId
     * @return
     */
    public List<Integer> getAllShopId();

    /**
     * @Title: getShopIdByshopName
     * @Description: 根据店铺名称获取店铺id
     * @param shopName
     * @return
     */
    public int getShopIdByshopName(@Param("shopName") String shopName);

    /**
     * @Title: getShopDataByShopId
     * @Description: 按日查询店铺驻留时长，总人数
     * @param tableName
     * @param shopId
     * @return
     */
    public List<Map<String, Object>> getShopDataByShopId(@Param("tableName") String tableName,
            @Param("shopId") int shopId);

    public List<Map<String, Object>> getShopWeekData(@Param("tableName") String tableName,
            @Param("shopId") String shopId, @Param("beginTime") String beginTime, @Param("lastTime") String lastTime);

    public List<Map<String, Object>> getShopWeekData1(@Param("tableName") String tableName,
            @Param("shopId") String shopId, @Param("beginTime") String beginTime);

    public List<Map<String, Object>> getShopWeekData2(@Param("tableName") String tableName,
            @Param("shopId") String shopId, @Param("lastTime") String lastTime);

    /**
     * @Title: getShopDataByShopId1
     * @Description: 按月查询店铺驻留时长，总人数
     * @param tableName
     * @param shopId
     * @return
     */
    public List<Map<String, Object>> getShopDataByShopId1(@Param("tableName") String tableName,
            @Param("shopId") int shopId);

    /**
     * @Title: getShopCostTp10
     * @Description:查询店铺消费tp10
     * @param shopId
     *            店铺id
     * @return
     */
    public List<ShopCostModel> getShopCostTp10(@Param("shopId") int shopId);

    /**
     * @Title: getShopCostTp10
     * @Description:查询店铺驻留tp10
     * @param shopId
     *            店铺id
     * @return
     */
    public List<ShopCostModel> getShopVisitimeTp10(@Param("tableName") String tableName, @Param("shopId") int shopId);

    /**
     * @Title: getGenderByShopId
     * @Description:获取性别比例职业比例
     * @param shopId
     * @return
     */
    public List<Map<String, Object>> getGenderByShopId(@Param("shopId") int shopId);

    /**
     * @Title: getprofessionByShopId
     * @Description:获取职业比例
     * @param shopId
     * @return
     */
    public List<UserModel> getprofessionByShopId(@Param("shopId") int shopId);

    /**
     * @Title: saveShopInfo
     * @Description: 添加店铺信息
     * @param model
     * @return
     */
    public int saveShopInfo(ShopModel model);

    /**
     * @Title: updateShopInfo
     * @Description: 更新店铺信息
     * @param model
     * @return
     */
    public int updateShopInfo(ShopModel model);

    /**
     * @Title: deleteShopById
     * @Description: 删除店铺信息
     * @param id
     * @return
     */
    public int deleteShopById(String id);

    /**
     * @Title: getShopInfoByMapId
     * @Description: 获取指定楼层的店铺信息
     * @param mapId
     * @return
     */
    public List<ShopModel> getShopInfoByMapId(String mapId);

    public List<ShopModel> getShopInfoByMapId1(String mapId);

    /**
     * @Title: checkByName
     * @Description: 判断店铺名称是否已存在
     * @param name
     * @param id
     * @return
     */
    public int checkByName(@Param("name") String name, @Param("id") String id, @Param("storeId") String storeId);

    public List<ShopModel> getShopDataById(@Param("shopId") String shopId);

    /**
     * @Title: getAllShopData
     * @Description: 获取所有的店铺信息
     * @param shopId
     * @return
     */
    public List<ShopModel> getAllShopData();

    public List<NewUserModel> getAllNewDataByShopId(@Param("shopId") String shopId,
            @Param("startTime") String startTime, @Param("endTime") String endTime);

    public List<Map<String, Object>> getShopTrendByHour(@Param("shopId") int shopId, @Param("sign") int sign,
            @Param("time") String time);

    public List<Map<String, Object>> getShopTrendByDay(@Param("shopId") int shopId, @Param("sign") int sign,
            @Param("time") String time);

    public Integer getShopTrendByHourOther(@Param("shopId") int shopId, @Param("sign") int sign,
            @Param("time") String time);

    public Integer getShopTrendByDayOther(@Param("shopId") int shopId, @Param("sign") int sign,
            @Param("time") String time);
}
