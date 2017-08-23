package br.com.etyllica.keel.filter.color;

import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.search.LeftToRightSearch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LeftColorFilterTest {

    private BufferedImage image;

    private LeftToRightSearch filter;

    private final int IMAGE_WIDTH = 640;

    private final int IMAGE_HEIGHT = 480;

    private final int RGB = Color.YELLOW.getRGB();

    @Before
    public void setUp() {

        image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.createGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        filter = new LeftToRightSearch();

        ColorStrategy colorStrategy = new ColorStrategy(RGB);

        colorStrategy.setTolerance(10);

        filter.setPixelStrategy(colorStrategy);

        filter.setBorder(1);

    }

    @Test
    public void testFilterFirst() {

        image.setRGB(20, 30, RGB);

        Component point = filter.filterFirst(new BufferedImageSource(image), new Component(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));

        Assert.assertEquals(20, point.getX(), 0);

        Assert.assertEquals(30, point.getY(), 0);

    }

    @Test
    public void testFilterFirstWithBorder() {

        image.setRGB(2, 30, RGB);

        image.setRGB(20, 30, RGB);

        filter.setBorder(3);

        Component point = filter.filterFirst(new BufferedImageSource(image), new Component(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));

        Assert.assertEquals(20, point.getX(), 0);

        Assert.assertEquals(30, point.getY(), 0);

    }

}
