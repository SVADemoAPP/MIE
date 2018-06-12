package com.bis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bis.model.AreaModel;
import com.bis.model.IvasSvaModel;
import com.bis.model.NewUserModel;
import com.bis.model.StatisticsModel;

/**
 * @ClassName: StatisticsDao
 * @Description: 统计dao
 * @author JunWang
 * @date 2017年6月16日 上午11:10:00
 * 
 */
public interface StatisticsDao {

    /**
     * @Title: createTable
     * @Description: 建表
     * @param tableName
     * @return
     */
    public int createTable(String tableName);

    /**
     * @Title: createShopTable
     * @Description: 创建shop表
     * @param tableName
     * @return
     */
    public int createShopTable(String tableName);

    /**
     * @Title: isTableExist
     * @Description: 判断表是否存在
     * @param tableName
     * @param schema
     * @return
     */
    public int isTableExist(@Param("tableName") String tableName, @Param("schema") String schema);

    /**
     * @Title: getStoreStatistics
     * @Description: 统计商场客流人数，驻留时长
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getStoreStatistics(@Param("tableName") String tableName);

    /**
     * @Title: doUpdate
     * @Description: sql执行
     * @param sql
     * @return
     */
    public int doUpdate(@Param("sql")String sql);

    /**
     * @Title: getUserDataByArea
     * @Description: 根据区域范围获取每人的驻留时长
     * @param areaModel
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getUserDataByArea(@Param("tableName") String tableName,
            @Param("area") AreaModel areaModel);

    /**
     * @Title: getAreaData
     * @Description: 获取所有的区域信息
     * @return
     */
    public List<AreaModel> getAreaData();

    /**
     * @Title: getStoreByMouth
     * @Description: 按月统计人数，驻留时长
     * @param time
     * @return
     */
    public List<Map<String, Object>> getStoreByMouth(@Param("time") String time);

    /**
     * @Title: getUserByMouth
     * @Description: 按月统计每个人的驻留时长
     * @param time
     * @return
     */
    public List<Map<String, Object>> getUserByMouth(@Param("time") String time, @Param("tableName") String tableName);

    /**
     * @Title: savePeopleRoute
     * @Description: 保存客户的店铺轨迹信息
     * @param data
     * @return
     */
    public int savePeopleRoute(List<StatisticsModel> data);
    
    /** 
     * @Title: getOrderedPeopleRoute 
     * @Description: 获取指定时间段内排序后的用户店铺轨迹
     * @param startTime
     * @param endTime
     * @return 
     */
    public List<StatisticsModel> getOrderedPeopleRoute(
            @Param("mapId")String mapId,
            @Param("startTime")String startTime, 
            @Param("endTime")String endTime);
    
    public int saveNewUser(NewUserModel model);
    
    public List<String> getStroeUserById(@Param("storeId")String storeId,@Param("tableName")String tableName);
    
    public int login(@Param("userName")String userName,@Param("password")String password);
    
    public void updateLogin(@Param("loginTime")String loginTime);
    
    public List<IvasSvaModel> getIvasList();
    
    public List<Map<String, Object>> getAllcountByMapId(@Param("tableName")String tableName);
    
    public List<String> getMapIdUser(@Param("tableName")String tableName,@Param("mapId")String mapId);
    public List<NewUserModel> getNewUserByMapId(@Param("mapId")String mapId,@Param("startTime")String startTime,@Param("endTime")String endTime);
}
