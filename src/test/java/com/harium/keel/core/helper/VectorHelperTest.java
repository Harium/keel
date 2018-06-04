package com.harium.keel.core.helper;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

public class VectorHelperTest {

    @Test
    public void testVectorXToColor() {
        Vector3 vector = new Vector3(Vector3.X);
        int rgb = VectorHelper.vectorToColor(vector);

        Assert.assertEquals(255, ColorHelper.getRed(rgb));
        Assert.assertEquals(127, ColorHelper.getGreen(rgb));
        Assert.assertEquals(127, ColorHelper.getBlue(rgb));
    }

    @Test
    public void testVectorToColor() {
        Vector3 vector = new Vector3(-1, 0, 1);
        int rgb = VectorHelper.vectorToColor(vector);

        Assert.assertEquals(0, ColorHelper.getRed(rgb));
        Assert.assertEquals(127, ColorHelper.getGreen(rgb));
        Assert.assertEquals(255, ColorHelper.getBlue(rgb));
    }

}
