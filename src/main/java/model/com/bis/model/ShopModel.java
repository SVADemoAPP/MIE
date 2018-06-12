package com.bis.model;

import java.util.Date;

/**
 * @ClassName: ShopModel
 * @Description: 店铺实体类
 * @author gyr
 * @date 2017年6月16日 上午11:29:42
 * 
 */
public class ShopModel {
    private String id;
    private String shopName;
    private String mapId;
    private String xSpot;
    private String ySpot;
    private String description;
    private String status;
    private String zoneId;
    private String x1Spot;
    private String y1Spot;
    private String categoryId;
    private String isVip;
    private String floorName;
    private String storeName;
    private String storeId;
    private String categoryName;
    private String allcount;
    private Date updateTime;
    private Date createTime;
    private String shopId;
    private String x;
    private String y;
    
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAllcount() {
        return allcount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setAllcount(String allcount) {
        this.allcount = allcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getxSpot() {
        return xSpot;
    }

    public void setxSpot(String xSpot) {
        this.xSpot = xSpot;
    }

    public String getySpot() {
        return ySpot;
    }

    public void setySpot(String ySpot) {
        this.ySpot = ySpot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getX1Spot() {
        return x1Spot;
    }

    public void setX1Spot(String x1Spot) {
        this.x1Spot = x1Spot;
    }

    public String getY1Spot() {
        return y1Spot;
    }

    public void setY1Spot(String y1Spot) {
        this.y1Spot = y1Spot;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}
