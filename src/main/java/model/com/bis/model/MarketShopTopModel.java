package com.bis.model;

public class MarketShopTopModel implements Comparable<Object>{
	private String shopName;
	private int value;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public int compareTo(Object o)
	{
		MarketShopTopModel sdto = (MarketShopTopModel)o;
		int otherValue = sdto.getValue();
		
		return this.value < otherValue ? -1:1;
	}
}
