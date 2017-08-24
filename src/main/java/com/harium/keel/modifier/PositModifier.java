package com.harium.keel.modifier;

import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Component;
import com.harium.keel.helper.RotationAxis;
import com.harium.etyl.linear.Point3D;
import org.opencv.criteria.CvTermCriteria;
import org.opencv.modules.calib3d.Posit;

import java.util.ArrayList;
import java.util.List;

public class PositModifier extends RotationAxis implements ComponentModifierStrategy {

    private CvTermCriteria criteria;

    private double focalLength;

    public PositModifier(double w, double h) {
        super();

        this.focalLength = w;

        criteria = new CvTermCriteria(30);

    }

    @Override
    public Component modifyComponent(Component component) {
        List<Point3D> objPoints = new ArrayList<Point3D>();

        // A Point equivalent
        objPoints.add(new Point3D(-0.5, 0, -0.5));

        // B Point equivalent
        objPoints.add(new Point3D(+0.5, 0, -0.5));

        // TODO Swap C with D
        // C Point equivalent
        objPoints.add(new Point3D(-0.5, 0, +0.5));

        // D Point equivalent
        objPoints.add(new Point3D(+0.5, 0, +0.5));

        Posit posit = new Posit();

        posit.icvPOSIT(objPoints, component.getPoints(), focalLength, criteria);

        computeRotationValues(posit.getRotation());

        return component;

    }

}
