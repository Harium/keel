package com.harium.keel.filter.modifier;

import com.harium.keel.feature.hull.HullFeature;
import com.harium.keel.modifier.hull.PathCompressionModifier;
import com.harium.etyl.geometry.Point2D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PathCompressionModifierTest {

    private PathCompressionModifier modifier;

    private HullFeature heavyTriangle;

    private HullFeature heavyLine;

    @Before
    public void setUp() {
        modifier = new PathCompressionModifier();

        heavyLine = new HullFeature();
        heavyLine.addLogic(new Point2D(10, 0));
        heavyLine.addLogic(new Point2D(5, 0));
        heavyLine.addLogic(new Point2D(0, 0));

        heavyTriangle = new HullFeature();
        heavyTriangle.addLogic(new Point2D(10, 0));
        heavyTriangle.addLogic(new Point2D(5, 0));
        heavyTriangle.addLogic(new Point2D(0, 0));
        heavyTriangle.addLogic(new Point2D(0, 5));
        heavyTriangle.addLogic(new Point2D(0, 10));
        heavyTriangle.addLogic(new Point2D(10, 0)); //Closed Triangle
    }

    @Test
    public void testModifyLine() {
        List<Point2D> points = modifier.apply(heavyLine);
        Assert.assertEquals(2, points.size());
    }

    @Test
    public void testModifyClosedTriangle() {
        List<Point2D> points = modifier.apply(heavyTriangle);

        Assert.assertEquals(4, points.size());
        Assert.assertEquals(new Point2D(10, 0), points.get(0));
        Assert.assertEquals(new Point2D(0, 0), points.get(1));
        Assert.assertEquals(new Point2D(0, 10), points.get(2));
        Assert.assertEquals(new Point2D(10, 0), points.get(3));
    }

}
