package com.bis.common.area;

public class Line
{
  private Point startPoint;
  private Point endPoint;
  private Double slope;
  private boolean vertical;
  private double b;
  
  public Line(Point startPoint, Point endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    if (!endPoint.getX().equals(startPoint.getX()))
    {
      this.vertical = false;
      this.slope = Double.valueOf((endPoint.getY().doubleValue() - startPoint.getY().doubleValue()) / (endPoint.getX().doubleValue() - startPoint.getX().doubleValue()));
      this.b = (getStartPoint().getY().doubleValue() - getSlope().doubleValue() * getStartPoint().getX().doubleValue());
    }
    else
    {
      this.vertical = true;
    }
    
  }
  
  public boolean isContainsPoint(Point point)
  {
    double biggerX = (this.startPoint.getX().doubleValue() > this.endPoint.getX().doubleValue() ? this.startPoint.getX() : this.endPoint.getX()).doubleValue();
    double biggerY = (this.startPoint.getY().doubleValue() > this.endPoint.getY().doubleValue() ? this.startPoint.getY() : this.endPoint.getY()).doubleValue();
    double smallerX = (this.startPoint.getX().doubleValue() < this.endPoint.getX().doubleValue() ? this.startPoint.getX() : this.endPoint.getX()).doubleValue();
    double smallerY = (this.startPoint.getY().doubleValue() < this.endPoint.getY().doubleValue() ? this.startPoint.getY() : this.endPoint.getY()).doubleValue();
    if ((point.getX().doubleValue() >= smallerX) && (point.getX().doubleValue() <= biggerX) && (point.getY().doubleValue() >= smallerY) && 
      (point.getY().doubleValue() <= biggerY)) {
      return true;
    }
    return false;
  }
  
  public Point getStartPoint()
  {
    return this.startPoint;
  }
  
  public void setStartPoint(Point startPoint)
  {
    this.startPoint = startPoint;
  }
  
  public Point getEndPoint()
  {
    return this.endPoint;
  }
  
  public void setEndPoint(Point endPoint)
  {
    this.endPoint = endPoint;
  }
  
  public Double getSlope()
  {
    return this.slope;
  }
  
  public void setSlope(Double slope)
  {
    this.slope = slope;
  }
  
  public boolean isVertical()
  {
    return this.vertical;
  }
  
  public void setVertical(boolean vertical)
  {
    this.vertical = vertical;
  }
  
  public double getB()
  {
    return this.b;
  }
  
  public void setB(double b)
  {
    this.b = b;
  }
  
  public String toString()
  {
    return 
      "Line [startPoint=" + this.startPoint + ", endPoint=" + this.endPoint + ", slope=" + this.slope + ", vertical=" + this.vertical + ", b=" + this.b + "]";
  }
}
