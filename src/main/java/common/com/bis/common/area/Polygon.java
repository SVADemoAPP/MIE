package com.bis.common.area;

import java.util.ArrayList;
import java.util.List;

public class Polygon
{
  private List<Line> orderedSides;
  private BoundBox boundBox;
  
  private Polygon(List<Line> orderedSides, BoundBox boundBox)
  {
    this.orderedSides = orderedSides;
    this.boundBox = boundBox;
  }
  
  private static void updateBoundBox(Point point, BoundBox boundBox)
  {
    if (point.getX().doubleValue() < boundBox.getxMin()) {
      boundBox.setxMin(point.getX().doubleValue());
    }
    if (point.getY().doubleValue() < boundBox.getyMin()) {
      boundBox.setyMin(point.getY().doubleValue());
    }
    if (point.getX().doubleValue() > boundBox.getxMax()) {
      boundBox.setxMax(point.getX().doubleValue());
    }
    if (point.getY().doubleValue() > boundBox.getyMax()) {
      boundBox.setyMax(point.getY().doubleValue());
    }
  }
  
  public static Polygon initPolygon(List<Point> points)
  {
    BoundBox bBox = new BoundBox();
    List<Line> oSides = new ArrayList<Line>();
    for (int i = 0; i < points.size(); i++)
    {
      if (i == points.size() - 1) {
        oSides.add(new Line((Point)points.get(i), (Point)points.get(0)));
      } else {
        oSides.add(new Line((Point)points.get(i), (Point)points.get(i + 1)));
      }
      updateBoundBox((Point)points.get(i), bBox);
    }
    return new Polygon(oSides, bBox);
  }
  
  public boolean contains(Point point)
  {
    if (isInBoundBox(point))
    {
      Line ray = createRay(point);
      int intersection = 0;
      for (Line side : getOrderedSides()) {
        if (intersect(ray, side)) {
          intersection++;
        }
      }
      if (intersection % 2 == 1) {
        return true;
      }
    }
    return false;
  }
  
  private Line createRay(Point point)
  {
    double epsilon = (getBoundBox().getxMax() - getBoundBox()
      .getxMin()) / 100.0D;
    
    Point outsidePoint = new Point(Double.valueOf(getBoundBox().getxMin() - epsilon), 
      Double.valueOf(getBoundBox().getyMin()));
    Line vector = new Line(outsidePoint, point);
    return vector;
  }
  
  private boolean isInBoundBox(Point point)
  {
    if ((point.getX().doubleValue() < getBoundBox().getxMin()) || 
      (point.getX().doubleValue() > getBoundBox().getxMax()) || 
      (point.getY().doubleValue() < getBoundBox().getyMin()) || 
      (point.getY().doubleValue() > getBoundBox().getyMax())) {
      return false;
    }
    return true;
  }
  
  private boolean intersect(Line ray, Line side)
  {
    Point intersectPoint;
    if ((!ray.isVertical()) && (!side.isVertical()))
    {
      if (ray.getSlope().equals(side.getSlope())) {
        return false;
      }
      double x = (side.getB() - ray.getB()) / (
        ray.getSlope().doubleValue() - side.getSlope().doubleValue());
      
      double y = side.getSlope().doubleValue() * x + side.getB();
      intersectPoint = new Point(Double.valueOf(x), Double.valueOf(y));
    }
    else
    {
      if ((ray.isVertical()) && (!side.isVertical()))
      {
        double x = ray.getStartPoint().getX().doubleValue();
        double y = side.getSlope().doubleValue() * x + side.getB();
        intersectPoint = new Point(Double.valueOf(x), Double.valueOf(y));
      }
      else
      {
        if ((!ray.isVertical()) && (side.isVertical()))
        {
          double x = side.getStartPoint().getX().doubleValue();
          double y = ray.getSlope().doubleValue() * x + ray.getB();
          intersectPoint = new Point(Double.valueOf(x), Double.valueOf(y));
        }
        else
        {
          return false;
        }
      }
    }
    if ((side.isContainsPoint(intersectPoint)) && 
      (ray.isContainsPoint(intersectPoint))) {
      return true;
    }
    return false;
  }
  
  public List<Line> getOrderedSides()
  {
    return this.orderedSides;
  }
  
  public void setOrderedSides(List<Line> orderedSides)
  {
    this.orderedSides = orderedSides;
  }
  
  public BoundBox getBoundBox()
  {
    return this.boundBox;
  }
  
  public void setBoundBox(BoundBox boundBox)
  {
    this.boundBox = boundBox;
  }
}
