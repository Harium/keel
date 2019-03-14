package com.harium.keel.modifier;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.Modifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.PointFeature;

public class EnvelopeModifier implements ComponentModifierStrategy, Modifier<PointFeature, PointFeature> {

    protected double distance = 0;

    protected int points = 0;

    protected double angle = 0;

    public EnvelopeModifier() {
        super();
    }

    public PointFeature modifyComponent(PointFeature component) {

        PointFeature box = apply(component);

        Point2D a = box.getPoints().get(0);
        Point2D b = box.getPoints().get(1);
        Point2D c = box.getPoints().get(2);
        Point2D d = box.getPoints().get(3);

        Point2D ac = new Point2D((a.x + c.x) / 2, (a.y + c.y) / 2);
        Point2D bd = new Point2D((b.x + d.x) / 2, (b.y + d.y) / 2);

        Point2D rect = new Point2D(bd.x, ac.y);
        double dac = bd.distance(rect);
        double hip = bd.distance(ac);

        angle = Math.toDegrees(Math.asin(dac / hip));

        if (a.distance(c) > a.distance(b)) {
            angle -= 90;
        }

        points = component.getPoints().size();

        if (a.distance(d) > a.distance(c)) {
            distance = a.distance(d);
        } else {
            distance = a.distance(c);
        }

        return box;
    }

    public PointFeature apply(PointFeature feature) {

        //System.out.println("Degenerating "+component.getPoints().size()+" points into 4.");

        Point2D a = feature.getPoints().get(0); //Lower X
        Point2D b = feature.getPoints().get(0); //Lower equal Y
        Point2D c = feature.getPoints().get(0); //Higher Y
        Point2D d = feature.getPoints().get(0); //Higher equal X

        for (Point2D point : feature.getPoints()) {

            if (point.x < a.x) {
                a = point;
            } else if (point.y >= c.y) {

                if (point.y > c.y || point.x < c.x) {
                    c = point;
                }

            }

            if (point.y <= b.y) {
                b = point;
            } else if (point.x >= d.x) {
                d = point;
            }

        }

        PointFeature box = new PointFeature(feature.getW(), feature.getH());

        box.add(a);
        box.add(b);
        box.add(c);
        box.add(d);

        return box;

    }

    public int getPoints() {
        return points;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }

}
