package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.util.PathHelper;
import com.harium.keel.core.source.IntArraySource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class ColorFilterTest {

    private static final int WIDTH = 640, HEIGHT = 480;

    @Test
    public void testFindFeatures() throws IOException {
        Color orangeBall = new Color(240, 177, 14);
        ColorFilter filter = new ColorFilter(WIDTH, HEIGHT, orangeBall);
        filter.setBorder(3);

        String path = PathHelper.currentDirectory();
        path += "assets/images/table_tennis/";
        BufferedImage converted = loadImage(path + "orange.jpg");

        int[] dataBuffInt = converted.getRGB(0, 0, WIDTH, HEIGHT, null, 0, WIDTH);
        IntArraySource source = new IntArraySource(WIDTH, HEIGHT, dataBuffInt);

        Feature bounds = new Feature(0, 0, WIDTH, HEIGHT);
        List<PointFeature> features = filter.filter(source, bounds);

        assertFalse(features.isEmpty());
    }

    private BufferedImage loadImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new FileInputStream(new File(path)));
        BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        converted.getGraphics().drawImage(image, 0, 0, null);
        return converted;
    }

}
