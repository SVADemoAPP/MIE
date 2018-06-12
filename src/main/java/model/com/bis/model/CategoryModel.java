package com.bis.model;

import java.util.Date;

public class CategoryModel
{
    private String id;

    private String name;

    private Date updateTime;

    private Date createTime;
    
    private String x;
    
    private String y;
    
    private String deepTime;
    
    private String visitorNumber;

    public String getDeepTime() {
		return deepTime;
	}

	public void setDeepTime(String deepTime) {
		this.deepTime = deepTime;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getVisitorNumber() {
		return visitorNumber;
	}

	public void setVisitorNumber(String visitorNumber) {
		this.visitorNumber = visitorNumber;
	}

	public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

}
