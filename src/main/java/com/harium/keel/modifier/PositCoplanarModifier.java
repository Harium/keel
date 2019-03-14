package com.harium.keel.modifier;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.Modifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.helper.RotationAxis;
import com.harium.keel.modifier.posit.CoplanarPosit;
import com.harium.keel.modifier.posit.Pose;

import java.util.ArrayList;
import java.util.List;

public class PositCoplanarModifier implements ComponentModifierStrategy, Modifier<PointFeature, RotationAxis> {

    private int w, h;

    private RotationAxis axis;
    private CoplanarPosit posit;

    public PositCoplanarModifier(int w, int h) {
        this(w, h, w / 2);
    }

    public PositCoplanarModifier(int w, int h, double focalLength) {
        super();

        this.w = w;
        this.h = h;

        double rectangleSize = 1;
        posit = new CoplanarPosit(rectangleSize, focalLength);
    }

    @Override
    public PointFeature modifyComponent(PointFeature component) {
        axis = apply(component);
        return component;
    }

    public RotationAxis apply(PointFeature feature) {
        List<Point2D> points = feature.getPoints();
        List<Point2D> imagePoints = ajustPoints(points);

        Pose pose = posit.pose(imagePoints);

        RotationAxis axis = new RotationAxis(pose);

        return axis;
    }

    private List<Point2D> ajustPoints(List<Point2D> points) {
        List<Point2D> imagePoints = new ArrayList<>(4);
        imagePoints.addAll(points);

        Point2D a = points.get(0);
        imagePoints.get(0).setLocation(a.x - w / 2, h / 2 - a.y);

        Point2D b = points.get(1);
        imagePoints.get(1).setLocation(b.x - w / 2, h / 2 - b.y);

        Point2D c = points.get(2);
        imagePoints.get(2).setLocation(c.x - w / 2, h / 2 - c.y);

        Point2D d = points.get(3);
        imagePoints.get(3).setLocation(d.x - w / 2, h / 2 - d.y);

        return imagePoints;
    }

    public RotationAxis getAxis() {
        return axis;
    }

    public void setAxis(RotationAxis axis) {
        this.axis = axis;
    }

}