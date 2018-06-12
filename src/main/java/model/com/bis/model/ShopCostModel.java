package com.bis.model;

/**
 * @ClassName: ShopCostModel
 * @Description: 店铺消费
 * @author JunWang
 * @date 2017年6月16日 下午2:50:27
 * 
 */
public class ShopCostModel {

	/**
	 * @Fields spending :消费
	 */
	private String spending;

	/**
	 * @Fields shopId : 店铺id
	 */
	private int shopId;

	/**
	 * @Fields time :时间
	 */
	private String createTime;

	/**
	 * @Fields userId : 用户id
	 */
	private int userId;

	/**
	 * @Fields userName : 用户名称
	 */
	private String userName;
	
	/**
	 * @Fields visitTime : 驻留时长
	 */
	private String visitTime;
	

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getSpending() {
		return spending;
	}

	public void setSpending(String spending) {
		this.spending = spending;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
