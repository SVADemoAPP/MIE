package com.bis.model;

/**
 * @ClassName: TicketModel
 * @Description: 摇一摇实体类
 * @author JunWang
 * @date 2017年7月10日 下午3:37:40
 * 
 */
public class MixingModel {
    /**
     * @Fields id :主键id
     */
    private String id;
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
     * @Fields storeId : 商场id
     */ 
    private int storeId;
     
    /** 
     * @Fields mapId : mapID
     */ 
    private String mapId;
    
    /** 
     * @Fields storeCode : 楼宇编码
     */ 
    private String storeCode;
    
    /** 
     * @Fields floorCode : 楼层编码
     */ 
    private String  floorCode;
    
    /** 
     * @Fields codeToMapId : 楼层楼宇编码组合 
     */ 
    private String codeToMapId;

    public String getCodeToMapId() {
        return codeToMapId;
    }

    public void setCodeToMapId(String codeToMapId) {
        this.codeToMapId = codeToMapId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }


    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

}
