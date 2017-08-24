package com.harium.keel.feature.hull;

import com.harium.etyl.linear.Point2D;
import com.harium.etyl.linear.Polygon;
import com.harium.keel.feature.Component;

import java.util.List;

public class HullComponent extends Component {

    private Polygon polygon;

    public HullComponent() {
        super();
        polygon = new Polygon();
    }

    @Override
    public void addLogic(Point2D point) {
        polygon.add(point.getX(), point.getY());
    }

    @Override
    public boolean isInside(int x, int y) {
        return polygon.contains(x, y);
    }

    @Override
    public boolean intersects(int x, int y) {
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
            points.addAll(polygon.asList());
        }

        return points;
    }

    public List<Point2D> asList() {
        return polygon.asList();
    }
}
