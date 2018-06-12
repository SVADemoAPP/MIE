package com.bis.model;

public class LeaveMessageModel {
    /**
     * @Fields id : 游客id
     */
    private String id;

    /**
     * @Fields msg : 留言内容
     */
    private String msg;

    /**
     * @Fields mapId : mapId
     */
    private String mapId;

    /**
     * @Fields image : 足迹图片
     */
    private byte[] image;

    /**
     * @Fields x : x
     */
    private String x;

    /**
     * @Fields y : y
     */
    private String y;

    private String path;

    private String uploadTime;

    private String shopId;
    
    private String floorName;
    
    

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

}
