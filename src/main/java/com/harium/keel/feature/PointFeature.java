package com.harium.keel.feature;

import com.harium.etyl.commons.layer.GeometricLayer;
import com.harium.etyl.commons.layer.Layer;
import com.harium.etyl.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PointFeature extends Feature implements Comparable<PointFeature> {

    private Point2D center = null;

    protected List<Point2D> points = new ArrayList<Point2D>();

    protected int lowestX = Integer.MAX_VALUE;
    protected int lowestY = Integer.MAX_VALUE;

    protected int highestX = 0;
    protected int highestY = 0;

    protected double sumX;
    protected double sumY;

    public PointFeature() {
        //Inverted to adapt the size adding points
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
    }

    public PointFeature(int w, int h) {
        super();

        highestX = w;
        highestY = h;

        lowestX = 0;
        lowestY = 0;
    }

    public PointFeature(int x, int y, int w, int h) {
        super();

        lowestX = x;
        lowestY = y;

        highestX = w;
        highestY = h;
    }

    public PointFeature(List<Point2D> list) {
        this();
        addAll(list);
    }

    public boolean[][] generateMask() {

        int w = getW();
        int h = getH();

        if (w < 0) {
            w = -w;
        }

        if (h < 0) {
            h = -h;
        }

        boolean[][] mask = new boolean[h][w];

        for (Point2D point : points) {

            int x = (int) point.getX() - this.getLowestX();
            int y = (int) point.getY() - this.getLowestY();

            if (x < 0 || y < 0 || x >= w || y >= h)
                continue;

            mask[y][x] = true;
        }

        return mask;

    }

    public void add(int x, int y) {
        add(new Point2D(x, y));
    }

    public void add(Point2D p) {

        int px = (int) p.getX();
        int py = (int) p.getY();

        if (px > highestX) {
            highestX = px;
        }

        if (px < lowestX) {
            lowestX = px;
        }

        if (py > highestY) {
            highestY = py;

        }

        if (py < lowestY) {
            lowestY = py;
        }

        center = null;
        addLogic(p);
    }


    public void addAll(List<Point2D> list) {
        for (Point2D point : list) {
            add(point);
        }
    }

    protected void addLogic(Point2D p) {
        points.add(p);
        sumX += p.getX();
        sumY += p.getY();
    }

    public int getPointCount() {
        return points.size();
    }

    public void setBounds(int lowestX, int lowestY, int width, int height) {
        this.lowestX = lowestX;
        this.lowestY = lowestY;

        this.highestX = lowestX + width;
        this.highestY = lowestY + height;
    }

    public int getLowestX() {
        return lowestX;
    }

    public void setLowestX(int lowestX) {
        this.lowestX = lowestX;
    }

    public int getLowestY() {
        return lowestY;
    }

    public void setLowestY(int lowestY) {
        this.lowestY = lowestY;
    }

    public int getHighestX() {
        return highestX;
    }

    public void setHighestX(int highestX) {
        this.highestX = highestX;
    }

    public int getHighestY() {
        return highestY;
    }

    public void setHighestY(int highestY) {
        this.highestY = highestY;
    }

    public GeometricLayer getRectangle() {
        GeometricLayer rect = new GeometricLayer(lowestX, lowestY, highestX - lowestX, highestY - lowestY);
        return rect;
    }

    public double getDensity() {
        double area = getArea();

        if (area == 0) {
            return 1;
        }

        return ((double) points.size() * 100 / area);
    }

    public int getArea() {
        return getW() * getH();
    }

    public Point2D getCenter() {
        if (center != null) {
            return center;
        }

        center = new Point2D(sumX / points.size(), sumY / points.size());
        return center;
    }

    public Layer getLayer() {
        return new Layer(lowestX, lowestY, getW(), getH());
    }

    public List<Point2D> getPoints() {
        return points;
    }

    public void setPoints(List<Point2D> points) {
        this.points = points;
    }

    @Override
    public int compareTo(PointFeature component) {

        //return component.getPoints().size()*getW()-points.size()*getH();

        double dif = component.getDensity() * component.getH() - this.getDensity() * getW();

        if (dif > 0) {
            return 1;
        } else if (dif < 0) {
            return -1;
        } else {
            return 0;
        }

    }

    public void merge(PointFeature component) {
        for (Point2D point : component.getPoints()) {
            add(point);
        }
    }

    public boolean colidePoint(int px, int py) {

        if ((px < getX()) || (px > getX() + getW())) {
            return false;
        }

        if ((py < getY()) || (py > getY() + getH())) {
            return false;
        }

        return true;
    }

    public boolean colide(PointFeature component) {

        int bx = component.getX();
        int bw = component.getW();

        int by = component.getY();
        int bh = component.getH();

        if (bx + bw < getX()) return false;
        if (bx > getX() + getW()) return false;

        if (by + bh < getY()) return false;
        if (by > getY() + getH()) return false;

        return true;

    }

    public void setLocation(int x, int y) {
        this.lowestX = x;
        this.lowestY = y;
        this.highestX = x + 1;
        this.highestY = y + 1;
    }

    public int getX() {
        return lowestX;
    }

    public int getY() {
        return lowestY;
    }

    public int getW() {
        return highestX - lowestX;
    }

    public int getH() {
        return highestY - lowestY;
    }

    public void reset() {
        points.clear();

        highestX = 0;
        highestY = 0;

        lowestX = Integer.MAX_VALUE;
        lowestY = Integer.MAX_VALUE;

        sumX = 0;
        sumY = 0;
    }

    @Override
    public boolean isInside(int x, int y) {
        return true;
    }

    public static boolean contains(int x, int y, int cx, int cy, int cw, int ch) {
        return (x >= cx && x < cx + cw && y >= cy && y < cy + ch);
    }

    public boolean contains(int x, int y) {
        return contains(x, y, getX(), getY(), getW(), getH());
    }

}