/**   
 * @Title: MarketModel.java 
 * @Package com.bis.model 
 * @Description: 商场信息model
 * @author labelCS   
 * @date 2017年6月26日 下午5:42:11 
 * @version V1.0   
 */
package com.bis.model;

import java.util.Date;

/** 
 * @ClassName: MarketModel 
 * @Description: 商场信息model
 * @author labelCS 
 * @date 2017年6月26日 下午5:42:11 
 *  
 */
public class MarketModel
{
    /** 
     * @Fields name : 商场名称
     */ 
    private String name;
    
    /** 
     * @Fields id : id
     */ 
    private String id;
    
    /** 
     * @Fields updateTime : 更新时间
     */ 
    private Date updateTime;
    
    /** 
     * @Fields createTime : 创建时间
     */ 
    private Date createTime;

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime()
    {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    
}
