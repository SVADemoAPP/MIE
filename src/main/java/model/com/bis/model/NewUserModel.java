package com.bis.model;

/**
 * @ClassName: NewUserModel
 * @Description:新增用户model
 * @author JunWang
 * @date 2017年7月19日 上午10:09:59
 * 
 */
public class NewUserModel {

    /**
     * @Fields id : 主键id
     */
    private int id;
    /**
     * @Fields newUser : 新增用户
     */
    private int newUser;
    /**
     * @Fields todayUser :今日用户
     */
    private int todayUser;
    /**
     * @Fields yesdayUser : 昨日用户
     */
    private int yesdayUser;
    /**
     * @Fields time : 时间
     */
    private String time;

    /**
     * @Fields shop :店铺id
     */
    private String shopId;

    /**
     * @Fields storeId ：商场id
     */
    private String storeId;
    
    private String mapId;
    

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getTodayUser() {
        return todayUser;
    }

    public void setTodayUser(int todayUser) {
        this.todayUser = todayUser;
    }

    public int getYesdayUser() {
        return yesdayUser;
    }

    public void setYesdayUser(int yesdayUser) {
        this.yesdayUser = yesdayUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
