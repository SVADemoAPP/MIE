package com.bis.model;

import java.util.Date;

/**
 * @ClassName: TrendMapModel
 * @Description: 根据mapId统计客流量来源的预处理类
 * @author gyr
 * @date 2017年7月26日 下午3:01:13
 * 
 */
public class TrendMapModel {
    private int id;
    private String mapId;
    private String fromMapId; // 来源mapId
    private int visitorCount;
    private int sign; // 整点时刻或日期天数
    private Date time;

    public TrendMapModel() {
    }

    public TrendMapModel(String mapId, int sign, Date time) {
        this.mapId = mapId;
        this.sign = sign;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getFromMapId() {
        return fromMapId;
    }

    public void setFromMapId(String fromMapId) {
        this.fromMapId = fromMapId;
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
