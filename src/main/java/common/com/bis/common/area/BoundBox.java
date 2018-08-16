package com.bis.common.area;

public class BoundBox
{
  private double xMax = Double.MIN_VALUE;
  private double xMin = Double.MAX_VALUE;
  private double yMax = Double.MIN_VALUE;
  private double yMin = Double.MAX_VALUE;
  
  public double getxMax()
  {
    return this.xMax;
  }
  
  public void setxMax(double xMax)
  {
    this.xMax = xMax;
  }
  
  public double getxMin()
  {
    return this.xMin;
  }
  
  public void setxMin(double xMin)
  {
    this.xMin = xMin;
  }
  
  public double getyMax()
  {
    return this.yMax;
  }
  
  public void setyMax(double yMax)
  {
    this.yMax = yMax;
  }
  
  public double getyMin()
  {
    return this.yMin;
  }
  
  public void setyMin(double yMin)
  {
    this.yMin = yMin;
  }
}
