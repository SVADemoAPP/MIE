package com.bis.model;

/**
 * @ClassName: LocationModel
 * @Description: 定位数据Model
 * @author JunWang
 * @date 2017年6月19日 上午9:48:10
 * 
 */
public class LocationModel {

    /**
     * @Fields idType : 订阅类型
     */
    private String idType;

    /**
     * @Fields timestamp : 本地时间戳
     */
    private long timestamp;

    /**
     * @Fields time_sva : sva时间戳
     */
    private long time_sva;

    /**
     * @Fields mapId : mapId
     */
    private String mapId;

    /**
     * @Fields dataType : 数据类型
     */
    private String dataType;

    /**
     * @Fields x : 定位x
     */
    private double x;

    /**
     * @Fields y : 定位y
     */
    private double y;

    /**
     * @Fields userID : 用户id
     */
    private String userID;

    private int id;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTime_sva() {
        return time_sva;
    }

    public void setTime_sva(long time_sva) {
        this.time_sva = time_sva;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
