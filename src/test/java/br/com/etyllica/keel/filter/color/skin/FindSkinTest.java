package br.com.etyllica.keel.filter.color.skin;

import br.com.etyllica.keel.core.helper.ColorHelper;
import org.junit.Assert;
import org.junit.Test;

public class FindSkinTest {

    @Test
    public void testFindDarkerSkin() {
        Assert.assertTrue(checkColor(ColorHelper.getRGB(96, 92, 81)));
        //Assert.assertTrue(checkColor(ColorHelper.getRGB(129, 130, 122)));
        //Assert.assertTrue(checkColor(ColorHelper.getRGB(129, 130, 122)));
        //Assert.assertTrue(checkColor(ColorHelper.getRGB(117, 119, 108)));
        Assert.assertTrue(checkColor(ColorHelper.getRGB(102, 95, 77)));
        Assert.assertTrue(checkColor(ColorHelper.getRGB(74, 71, 64)));
        //Assert.assertTrue(checkColor(ColorHelper.getRGB(66, 65, 60)));

        Assert.assertTrue(checkColor(ColorHelper.getRGB(90, 60, 52)));
        Assert.assertTrue(checkColor(ColorHelper.getRGB(119, 67, 53)));
        Assert.assertTrue(checkColor(ColorHelper.getRGB(122, 76, 61)));
    }

    private boolean checkColor(int rgb) {
        return SkinColorCRStrategy.isSkin(rgb);
    }

}
