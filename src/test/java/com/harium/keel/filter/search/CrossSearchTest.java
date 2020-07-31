package com.harium.keel.filter.search;

import com.harium.keel.feature.Cross;
import com.harium.keel.filter.selection.RGBColorStrategy;
import com.harium.etyl.commons.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CrossSearchTest {

    private CrossSearch filter;

    @Before
    public void setUp() {
        filter = new CrossSearch();

        RGBColorStrategy colorStrategy = new RGBColorStrategy(Color.BLACK, 1);

        //Looking for Black Pixels
        filter.setSelectionStrategy(colorStrategy);
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
