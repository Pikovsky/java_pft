package ru.stqa.pft.sandbox;

public class Point {
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  double x;
  double y;

  public static double distance(Point p1, Point p2){
    double deltaX = p1.x - p2.x;
    double deltaY = p1.y - p2.y;
    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }


}
