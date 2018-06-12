package com.bis.model;

/**
 * @ClassName: StatisticsModel
 * @Description: 统计Model
 * @author JunWang
 * @date 2017年6月16日 下午2:50:27
 * 
 */
public class StatisticsModel {

    /**
     * @Fields visitTime : 驻留时长
     */
    private String visitTime;

    /**
     * @Fields storeId : 商场id
     */
    private int storeId;

    /**
     * @Fields userCount : 总人数
     */
    private int userCount;

    /**
     * @Fields shopId : 店铺id
     */
    private int shopId;

    /**
     * @Fields time :时间
     */
    private String time;
    
    /** 
     * @Fields userId : 用户id
     */ 
    private String userId;

    /**
     * @Fields type : 类型0:按日，1：按月
     */
    private int type;
    
    private String mapId;
    

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

}
