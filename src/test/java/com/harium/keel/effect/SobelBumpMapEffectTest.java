package com.harium.keel.effect;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.effect.bump.SobelBumpMap;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Test;

public class SobelBumpMapEffectTest {

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
        SobelBumpMap effect = new SobelBumpMap();
        ImageSource output = effect.apply(source);

        int rgb = output.getRGB(1, 1);
        Assert.assertEquals(127, ColorHelper.getRed(rgb));
        Assert.assertEquals(13, ColorHelper.getGreen(rgb));
        Assert.assertEquals(184, ColorHelper.getBlue(rgb));
    }

}
