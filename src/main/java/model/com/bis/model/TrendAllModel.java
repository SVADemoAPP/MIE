package com.bis.model;

public class TrendAllModel {

    private int mapId;
    private long time;
    private String userid;
    private double x;
    private double y;
    private String pointsArray;

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

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
