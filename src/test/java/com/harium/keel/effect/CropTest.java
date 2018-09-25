package com.harium.keel.effect;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Test;

public class CropTest {

    @Test
    public void testApply() {
        int[][] image = new int[3][3];
        image[0][0] = 0;
        image[0][1] = 0;
        image[0][2] = 0;
        image[1][0] = 0;
        image[1][1] = Color.WHITE.getRGB();
        image[1][2] = 0;
        image[2][0] = 0;
        image[2][1] = 0;
        image[2][2] = 0;

        MatrixSource source = new MatrixSource(image);
        Crop effect = new Crop().height(1).width(1);
        ImageSource output = effect.apply(source);

        int rgb = output.getRGB(0, 0);
        Assert.assertEquals(255, ColorHelper.getRed(rgb));
        Assert.assertEquals(255, ColorHelper.getGreen(rgb));
        Assert.assertEquals(255, ColorHelper.getBlue(rgb));
    }

}
