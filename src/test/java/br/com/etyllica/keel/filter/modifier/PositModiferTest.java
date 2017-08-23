package br.com.etyllica.keel.filter.modifier;

import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.modifier.PositModifier;
import com.harium.etyl.linear.Point2D;
import org.junit.Assert;
import org.junit.Before;
import org.opencv.OpenCv;

public class PositModiferTest {

    private PositModifier modifier;

    @Before
    public void setUp() {

        double focalLength = 760;

        modifier = new PositModifier(focalLength, 0);
    }


    public void testModifier() {
        Component imageComponent = new Component(200, 200);

        imageComponent.add(new Point2D(0, 0));

        modifier.modifyComponent(imageComponent);

        double axisX = modifier.getRotationX();

        double axisY = modifier.getRotationY();

        double axisZ = modifier.getRotationZ();

        double length = Math.sqrt(OpenCv.cvSqr(axisX) + OpenCv.cvSqr(axisY) + OpenCv.cvSqr(axisZ));

        Assert.assertEquals(1, length, 0.001);

        System.out.println("Rotation Angle: " + modifier.getAngle());

        System.out.println("Rotation X Axis: " + axisX);

        System.out.println("Rotation Y Axis: " + axisY);

        System.out.println("Rotation Z Axis: " + axisZ);

    }

}
