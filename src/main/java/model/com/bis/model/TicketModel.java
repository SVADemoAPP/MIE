package com.bis.model;

/**
 * @ClassName: TicketModel
 * @Description: 摇一摇实体类
 * @author JunWang
 * @date 2017年7月10日 下午3:37:40
 * 
 */
public class TicketModel {
    /**
     * @Fields id :主键id
     */
    private String id;
    /**
     * @Fields shopName :店铺id
     */
    private String shopName;
    /**
     * @Fields floorName : 楼层Id
     */
    private String floorName;
    /**
     * @Fields storeName : 商场id
     */
    private String storeName;
    /**
     * @Fields creatTime : 创建时间
     */
    private String creatTime;
    /**
     * @Fields tickeName : 奖券路径
     */
    private String ticketName;
    /**
     * @Fields chanceName : 概率
     */
    private String chanceName;

    /** 
     * @Fields imageWidth : 奖券宽度
     */ 
    private int imageWidth;

    /** 
     * @Fields imageHigth : 奖券高度 
     */ 
    private int imageHigth;
    
    /** 
     * @Fields storeId : 商场id
     */ 
    private int storeId;
    
    /** 
     * @Fields shopId : 店铺id
     */ 
    private int shopId;
    
    /** 
     * @Fields mapId : mapID
     */ 
    private String mapId;
    

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHigth() {
        return imageHigth;
    }

    public void setImageHigth(int imageHigth) {
        this.imageHigth = imageHigth;
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

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getChanceName() {
        return chanceName;
    }

    public void setChanceName(String chanceName) {
        this.chanceName = chanceName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

}
