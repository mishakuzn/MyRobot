package com.company;


import java.awt.*;

public class DataPoint {

    private int radius;
    private int angle;

    public DataPoint(int r, int a)
    {
        radius = r;
        angle = a;
    }

    public void drawPoint(Graphics g)
    {
        //g.fillOval(10* (int)getX(), 100 * (int)getY(), 100, 100);
        g.fillOval((int)getX() + 500, -(int)getY() + 500, 10,10);
    }

    public int getRadius()
    {
        return radius;
    }

    public int getAngle()
    {
        return angle;
    }

    public double getX()
    {
        return  10*radius * Math.cos(angle * (Math.PI / 180));
    }

    public double getY()
    {
        return  10*radius * Math.sin(angle * (Math.PI / 180));
    }
}
