package com.harium.keel.feature;

import com.harium.etyl.geometry.collision.CollisionDetector;

public class Feature extends ColorFeature implements FeatureArea {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Feature() {

    }

    public Feature(int width, int height) {
        this(0, 0, width, height);
    }

    public Feature(int x, int y, int width, int height) {
        super();
        setBounds(x, y, width, height);
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void merge(Feature component) {
        if (component.x < x) {
            this.x = component.x;
        }
        if (component.y < y) {
            this.y = component.y;
        }

        if (component.x + component.width > x + width) {
            width = component.x + component.width - x;
        }
        if (component.y + component.height > y + height) {
            height = component.y + component.height - y;
        }
    }

    public boolean collidePoint(int px, int py) {
        return CollisionDetector.collideRectPoint(x, y, width, height, px, py);
    }

    public boolean collide(Feature feature) {
        return CollisionDetector.collideRectRect(x, y, width, height,
                feature.x, feature.y, feature.width, feature.height);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.x = y;
    }

    @Override
    public boolean isInside(int px, int py) {
        return true;
    }

    public boolean contains(int px, int py) {
        return px >= x && px < x + width && py >= y && py < y + height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}