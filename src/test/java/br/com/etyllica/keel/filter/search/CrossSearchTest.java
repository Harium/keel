package br.com.etyllica.keel.filter.search;

import br.com.etyllica.keel.feature.Cross;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import com.harium.etyl.commons.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CrossSearchTest {

    private CrossSearch filter;

    @Before
    public void setUp() {
        filter = new CrossSearch();

        ColorStrategy colorStrategy = new ColorStrategy(Color.BLACK);

        //Looking for Black Pixels
        filter.setPixelStrategy(colorStrategy);
    }

    @Test
    public void testCrossPattern() {
        Cross cross = new Cross();

        cross.setUpperLeft(Color.BLACK.getRGB());
        cross.setUp(Color.BLACK.getRGB());
        cross.setUpperRight(Color.BLACK.getRGB());

        cross.setLeft(Color.BLACK.getRGB());
        cross.setCenter(Color.WHITE.getRGB());
        cross.setRight(Color.WHITE.getRGB());

        cross.setLowerLeft(Color.WHITE.getRGB());
        cross.setDown(Color.WHITE.getRGB());
        cross.setLowerRight(Color.WHITE.getRGB());

        assertTrue(filter.validateCross(cross, "TTT TFF FFF"));

    }

    @Test
    public void testOptionalPattern() {

        Cross cross = new Cross();

        cross.setUpperLeft(Color.BLACK.getRGB());
        cross.setUp(Color.BLACK.getRGB());
        cross.setUpperRight(Color.BLACK.getRGB());

        cross.setLeft(Color.BLACK.getRGB());
        cross.setCenter(Color.WHITE.getRGB());
        cross.setRight(Color.WHITE.getRGB());

        cross.setLowerLeft(Color.WHITE.getRGB());
        cross.setDown(Color.WHITE.getRGB());
        cross.setLowerRight(Color.WHITE.getRGB());

        assertTrue(filter.validateCross(cross, "TTT TFF FF."));

    }

}
