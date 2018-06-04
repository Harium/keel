package com.harium.keel.effect;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
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

    @Test
    public void testApply() {
        int[][] image = new int[3][3];
        image[0][0] = 0;
        image[0][1] = Color.WHITE.getRGB();
        image[0][2] = 0;
        image[1][0] = 0;
        image[1][1] = 0;
        image[1][2] = 0;
        image[2][0] = 0;
        image[2][1] = 0;
        image[2][2] = 0;

        MatrixSource source = new MatrixSource(image);
        SimpleBumpMapEffect effect = new SimpleBumpMapEffect();
        ImageSource output = effect.apply(source);

        int rgb = output.getRGB(1,1);
        Assert.assertEquals(254, ColorHelper.getRed(rgb));
        Assert.assertEquals(127, ColorHelper.getGreen(rgb));
        Assert.assertEquals(127, ColorHelper.getBlue(rgb));
    }

}
