package com.harium.keel.feature.hull;

import com.badlogic.gdx.math.Vector2;
import com.harium.etyl.geometry.Point2D;
import com.harium.etyl.geometry.Polygon;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public class HullFeature extends PointFeature {

    private Polygon polygon;

    public HullFeature() {
        super();
        polygon = new Polygon();
    }

    @Override
    public void addLogic(Point2D point) {
        polygon.add((float)point.getX(), (float)point.getY());
    }

    @Override
    public boolean isInside(int x, int y) {
        return polygon.contains(x, y);
    }

    @Override
    public boolean contains(int x, int y) {
        return isInside(x, y);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int getPointCount() {
        return polygon.size();
    }

    @Override
    public List<Point2D> getPoints() {
        if (points.isEmpty()) {
            for(Vector2 p:polygon.asList()) {
                points.add(new Point2D(p.x, p.y));
            }
        }

        return points;
    }

    public List<Vector2> asList() {
        return polygon.asList();
    }
}
