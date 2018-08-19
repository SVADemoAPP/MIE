package com.bis.model;

/**
 * @ClassName: LocationModel
 * @Description: 定位数据Model
 * @author JunWang
 * @date 2017年6月19日 上午9:48:10
 * 
 */
public class LocModel {
    private String userId;
    private double x;
    private double y;
    private String pointsArray;
    private int id; 
    
    /**
     * @return the pointsArray
     */
    public String getPointsArray() {
        return pointsArray;
    }
    /**
     * @param pointsArray the pointsArray to set
     */
    public void setPointsArray(String pointsArray) {
        this.pointsArray = pointsArray;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }
    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
    
    

}
