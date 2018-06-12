package com.bis.model;

public class ShopSalesUserInfoModel {
	private String userName;
	private int salesMoney;
	private int salesCnt;
	//private int salesAverage;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSalesMoney() {
		return salesMoney;
	}
	public void setSalesMoney(int salesMoney) {
		this.salesMoney = salesMoney;
	}
	public int getSalesCnt() {
		return salesCnt;
	}
	public void setSalesCnt(int salesCnt) {
		this.salesCnt = salesCnt;
	}
	/*
	public int getSalesAverage() {
		return salesAverage;
	}
	public void setSalesAverage(int salesAverage) {
		this.salesAverage = salesAverage;
	}
	*/
	
	
}
