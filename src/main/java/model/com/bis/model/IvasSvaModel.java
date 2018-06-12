package com.bis.model;

public class IvasSvaModel {

    /**
     * @Fields id : 主键id
     */
    private int id;

    /**
     * @Fields getTokenUrl : 获取token地址
     */
    private String getTokenUrl;

    /**
     * @Fields subscriptionUrl :订阅地址
     */
    private String subscriptionUrl;

    /**
     * @Fields appName : app名称
     */
    private String appName;

    /**
     * @Fields appPassWord : app密码
     */
    private String appPassWord;

    
    private String myKey;
    
    private String mySecret;

    public String getMyKey() {
        return myKey;
    }

    public void setMyKey(String myKey) {
        this.myKey = myKey;
    }

    public String getMySecret() {
        return mySecret;
    }

    public void setMySecret(String mySecret) {
        this.mySecret = mySecret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getSubscriptionUrl() {
        return subscriptionUrl;
    }

    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl = subscriptionUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPassWord() {
        return appPassWord;
    }

    public void setAppPassWord(String appPassWord) {
        this.appPassWord = appPassWord;
    }


}
