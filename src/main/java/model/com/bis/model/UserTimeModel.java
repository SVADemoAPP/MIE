package com.bis.model;

public class UserTimeModel {
    private String userId;
    private String time;
    private double delayTime;
    private int id;
    private int storeId;
    private int mapId;
    private int shopId;
    private double deepTime;
    private int visitorNumber;
    
    public double getDeepTime() {
        return deepTime;
    }
    public void setDeepTime(double deepTime) {
        this.deepTime = deepTime;
    }
    public int getVisitorNumber() {
        return visitorNumber;
    }
    public void setVisitorNumber(int visitorNumber) {
        this.visitorNumber = visitorNumber;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public double getDelayTime() {
        return delayTime;
    }
    public void setDelayTime(double delayTime) {
        this.delayTime = delayTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public int getMapId() {
        return mapId;
    }
    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    
}
