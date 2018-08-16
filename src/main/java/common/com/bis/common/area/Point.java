package com.bis.common.area;

public class Point
{
  public Double x;
  public Double y;
  
  public Point(Double x, Double y)
  {
    this.x = x;
    this.y = y;
  }
  
  public Double getX()
  {
    return this.x;
  }
  
  public void setX(Double x)
  {
    this.x = x;
  }
  
  public Double getY()
  {
    return this.y;
  }
  
  public void setY(Double y)
  {
    this.y = y;
  }
  
  public String toString()
  {
    return "Point [x=" + this.x + ", y=" + this.y + "]";
  }
}
