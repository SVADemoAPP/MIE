package com.bis.model;

public class MarketMonthDataModel implements Comparable<Object>{
	private String date;
	private int value;
	public String GetDate()
	{
		return this.date;
	}
	public void SetDate(String date)
	{
		this.date = date;
	}
	public int GetValue()
	{
		return this.value;
	}
	public void SetValue(int value)
	{
		this.value = value;
	}
	
	@Override
	public int compareTo(Object o)
	{
		MarketMonthDataModel sdto = (MarketMonthDataModel)o;
		String otherDate = sdto.GetDate();
		return this.date.compareTo(otherDate);
	}
}
