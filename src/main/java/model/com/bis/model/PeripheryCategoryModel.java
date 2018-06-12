package com.bis.model;

/** 
 * @ClassName: PeripheryCategoryModel 
 * @Description: 周边服务model
 * @author JunWang 
 * @date 2017年7月13日 下午3:06:48 
 *  
 */
public class PeripheryCategoryModel
{
    private String id;

    /** 
     * @Fields category : 类别 
     */ 
    private String category;
    
    /** 
     * @Fields shopName : 店铺名称
     */ 
    private String shopName;
    
    /** 
     * @Fields Address : 地址
     */ 
    private String shopAddress;
    
    /** 
     * @Fields distance : 距离
     */ 
    private String distance;
    
    /** 
     * @Fields peripheryCategory : 周边类别 
     */ 
    private String peripheryCategory;
    
    /** 
     * @Fields path : 路径
     */ 
    private String path;
    
    
    /** 
     * @Fields storeId : 商场id
     */ 
    private String storeId;
    
    /** 
     * @Fields storeName :商场名称
     */ 
    private String storeName;
    
    
    /** 
     * @Fields maxPath : 大地图
     */ 
    private String maxPath;
    
    /** 
     * @Fields phoneNumber : 电话
     */ 
    private  String phoneNumber;
    
    /** 
     * @Fields businessHours : 营业时间
     */ 
    private String businessHours;
    
    public String getMaxPath() {
        return maxPath;
    }

    public void setMaxPath(String maxPath) {
        this.maxPath = maxPath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPeripheryCategory() {
        return peripheryCategory;
    }

    public void setPeripheryCategory(String peripheryCategory) {
        this.peripheryCategory = peripheryCategory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    

}
