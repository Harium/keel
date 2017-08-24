package com.harium.keel.feature;


import com.harium.etyl.commons.layer.GeometricLayer;
import com.harium.etyl.commons.layer.Layer;
import com.harium.etyl.linear.Point2D;

import java.awt.*;

public class MaskComponent implements Comparable<MaskComponent> {

    protected boolean[][] mask;

    protected int maskWidth = 640;

    protected int maskHeight = 480;

    protected int lowestX = maskWidth - 1;

    protected int lowestY = maskHeight - 1;

    protected int highestX = 0;

    protected int highestY = 0;

    private int count = 0;

    public MaskComponent() {
        super();
    }

    public MaskComponent(int w, int h) {
        super();

        this.maskWidth = w;
        this.maskHeight = h;

        reset();
    }

    public MaskComponent(int x, int y, int w, int h) {
        super();

        this.maskWidth = w;
        this.maskHeight = h;

        lowestX = x;
        lowestY = y;

        highestX = w;
        highestY = h;

        mask = new boolean[w][h];
    }

    public void add(Point2D point) {
        add((int) point.getX(), (int) point.getY());
    }

    public void add(int px, int py) {

        if (px > highestX) {

            highestX = px;

        } else if (px < lowestX) {

            lowestX = px;
        }

        if (py > highestY) {

            highestY = py;

        } else if (py < lowestY) {

            lowestY = py;
        }

        mask[px][py] = true;

        count++;

    }

    protected void addLogic(Point2D p) {

    }

    public int getPointCount() {
        return count;
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

    public Polygon getBoundingBox() {

        Polygon p = new Polygon();

        p.addPoint(lowestX, lowestY);
        p.addPoint(highestX, lowestY);
        p.addPoint(highestX, highestY);
        p.addPoint(lowestX, highestY);

        return p;
    }

    public GeometricLayer getRectangle() {

        GeometricLayer rect = new GeometricLayer(lowestX, lowestY, highestX - lowestX, highestY - lowestY);

        return rect;
    }

    public double getDensity() {
        int area = getArea();

        if (area == 0) {
            return 1;
        }

        return (double) (count * 100 / area);

    }

    public int getArea() {
        return getW() * getH();
    }

    public Point2D getCenter() {

        double countX = 0;

        double countY = 0;

        for (int j = 0; j < maskHeight; j++) {

            for (int i = 0; i < maskWidth; i++) {

                if (mask[i][j]) {

                    countX += i;

                    countY += j;

                }

            }
        }

        Point2D center = new Point2D(countX / count, countY / count);

        return center;
    }

    public Layer getLayer() {

        return new Layer(lowestX, lowestY, getW(), getH());
    }

    @Override
    public int compareTo(MaskComponent component) {

        // TODO Auto-generated method stub
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

    public void merge(MaskComponent component) {

        if ((component.getW() != this.getW()) || (component.getW() != this.getW()))
            return;


        for (int j = 0; j < component.maskHeight; j++) {

            for (int i = 0; i < component.maskWidth; i++) {

                if (component.hasPoint(i, j)) {
                    add(i, j);
                }

            }

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

    public boolean colide(MaskComponent component) {

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

    public void mergePolygon(Polygon p) {

        for (int i = 0; i < p.npoints; i++) {

            Point2D point = new Point2D(p.xpoints[i], p.ypoints[i]);

            add(point);
        }
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

    public int getMaskWidth() {
        return maskWidth;
    }

    public int getMaskHeight() {
        return maskHeight;
    }

    public boolean hasPoint(int px, int py) {
        return mask[px][py];
    }

    public void reset() {

        mask = new boolean[maskWidth][maskHeight];

        highestX = 0;
        highestY = 0;

        lowestX = maskWidth - 1;
        lowestY = maskHeight - 1;

    }

}

