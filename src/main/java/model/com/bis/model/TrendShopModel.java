package com.bis.model;

import java.util.Date;

/**
 * @ClassName: TrendShopModel
 * @Description: 根据shopId统计客流量来源的预处理类
 * @author gyr
 * @date 2017年7月26日 下午3:01:13
 * 
 */
public class TrendShopModel {
    private int id;
    private int shopId;
    private int fromShopId; // 来源shopId
    private int visitorCount;
    private int sign; // 整点时刻或日期天数
    private Date time;

    public TrendShopModel() {
    }

    public TrendShopModel(int shopId, int sign, Date time) {
        this.shopId = shopId;
        this.sign = sign;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getFromShopId() {
        return fromShopId;
    }

    public void setFromShopId(int fromShopId) {
        this.fromShopId = fromShopId;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    

}
