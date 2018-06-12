package com.bis.model;

public class SvaModel {
	private String id;

	private String ip;

	private String name;

	private String username;

	private String password;


	private String storeId;

	private int status;
	
	private String managerEmail;

	/**
	 * 纠错状态码
	 */
	private String statusCode;



	/**
	 * @Fields type 订阅类型
	 */
	private int type; // 0非匿名化，1匿名化，2指定用户


	/**
	 * @Fields idType id类型
	 */
	private String idType;

	private String tokenPort;

	private String brokerPort;

	private String token;

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenPort() {
		return tokenPort;
	}

	public void setTokenPort(String tokenPort) {
		this.tokenPort = tokenPort;
	}

	public String getBrokerPort() {
		return brokerPort;
	}

	public void setBrokerPort(String brokerPort) {
		this.brokerPort = brokerPort;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/***
	 * 比较SVA配置信息是否改变，比较内容为ID、IP、appName、password
	 * 
	 * @param sva
	 * @return
	 */
	public boolean isChange(SvaModel sva) {
		if (this.id.equals(sva.getId()) && this.ip.equals(sva.getIp()) && this.username.equals(sva.getUsername())
				&& this.password.equals(sva.getPassword())) {
			return false;
		} else {
			return true;
		}
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getTypeStr() {
		String apiStr = "";
		switch (this.getType()) {
		case 0:
			apiStr = "locationstream";
			break;
		case 1:
			apiStr = "locationstreamanonymous";
			break;
		case 2:
			apiStr = "locationstream";
			break;
		}
		return apiStr;
	}

}
