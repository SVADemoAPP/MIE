package com.bis.model;

import java.util.Date;

public class MapMngModel {
    private String scale;

    private String xo;

    private String yo;

    private String floor;

    private String coordinate;

    private float angle;

    private String path;
    
    private String svg;
    
    private String navi;
    
    private int imgWidth;

    private int imgHeight;

    private String mapId;

    private int storeId;

    private String storeName;

    private String id;

    private String updateTime;
    
    private Date createTime;
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private int mapType;
    
    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public String getSvg() {
        return svg;
    }

    public String getNavi() {
        return navi;
    }

    public void setNavi(String navi) {
        this.navi = navi;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }
    
    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    /**
     * @return the xo
     */
    public String getXo() {
        return xo;
    }

    /**
     * @param xo
     *            the xo to set
     */
    public void setXo(String xo) {
        this.xo = xo;
    }

    /**
     * @return the yo
     */
    public String getYo() {
        return yo;
    }

    /**
     * @param yo
     *            the yo to set
     */
    public void setYo(String yo) {
        this.yo = yo;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getStoreId()
    {
        return storeId;
    }

    public void setStoreId(int storeId)
    {
        this.storeId = storeId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

}
