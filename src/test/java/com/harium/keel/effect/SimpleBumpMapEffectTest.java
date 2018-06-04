package com.harium.keel.effect;

import com.badlogic.gdx.math.Vector3;
import com.harium.keel.core.helper.ColorHelper;
import org.junit.Assert;
import org.junit.Test;

public class SimpleBumpMapEffectTest {

    @Test
    public void testVectorXToColor() {
        Vector3 vector = new Vector3(Vector3.X);
        int rgb = SimpleBumpMapEffect.vectorToColor(vector);

        Assert.assertEquals(255, ColorHelper.getRed(rgb));
        Assert.assertEquals(127, ColorHelper.getGreen(rgb));
        Assert.assertEquals(127, ColorHelper.getBlue(rgb));
    }

    @Test
    public void testVectorToColor() {
        Vector3 vector = new Vector3(-1,0,1);
        int rgb = SimpleBumpMapEffect.vectorToColor(vector);

        Assert.assertEquals(0, ColorHelper.getRed(rgb));
        Assert.assertEquals(127, ColorHelper.getGreen(rgb));
        Assert.assertEquals(255, ColorHelper.getBlue(rgb));
    }

}
