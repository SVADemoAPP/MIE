package com.bis.model;

public class CpuModel {
	private long id;
	private int cpuParameter;
	private long lastTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCpuParameter() {
		return cpuParameter;
	}
	public void setCpuParameter(int cpuParameter) {
		this.cpuParameter = cpuParameter;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	
}
