package com.bis.model;

public class MarketOverviewModel {
    private int CurDailyVistorNum;
    private int AverageStayMinutes;
    private double CurDailySales;
    private double AverageSales;

    public int getCurDailyVistorNum() {
        return CurDailyVistorNum;
    }

    public void setCurDailyVistorNum(int CurDailyVistorNum) {
        this.CurDailyVistorNum = CurDailyVistorNum;
    }

    public int getAverageStayMinutes() {
        return AverageStayMinutes;
    }

    public void setCurDailySales(double CurDailySales) {
        this.CurDailySales = CurDailySales;
    }

    public double getCurDailySales() {
        return CurDailySales;
    }

    public void setAverageSales(double AverageSales) {
        this.AverageSales = AverageSales;
    }

    public double getAverageSales() {
        return AverageSales;
    }
}
