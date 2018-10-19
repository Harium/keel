package com.harium.keel.effect;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Test;

public class AddTest {

    @Test
    public void testApply() {
        MatrixSource overlay = new MatrixSource(2,1);
        overlay.setRGB(0,0, ColorHelper.getRGB(128,128,128));
        overlay.setRGB(1,0, ColorHelper.getRGB(255,255,255));

        MatrixSource source = new MatrixSource(2,1);
        source.setRGB(0,0, ColorHelper.getRGB(128,128,128));
        source.setRGB(1,0, ColorHelper.getRGB(0,0,0));

        Add effect = new Add(overlay);
        effect.apply(source);

        Assert.assertEquals(255, source.getR(0,0));
        Assert.assertEquals(255, source.getG(0,0));
        Assert.assertEquals(255, source.getB(0,0));

        Assert.assertEquals(255, source.getB(1,0));
        Assert.assertEquals(255, source.getB(1,0));
        Assert.assertEquals(255, source.getB(1,0));
    }

}
