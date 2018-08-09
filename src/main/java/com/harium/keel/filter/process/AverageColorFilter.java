package com.harium.keel.filter.process;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public class AverageColorFilter implements ProcessFilter<Color>, ProcessComponentFilter<Color> {

    public AverageColorFilter() {
        super();
    }

    public Color process(ImageSource source) {
        return filter(source);
    }

    public static Color filter(ImageSource source) {
        int averageRed = 0;
        int averageBlue = 0;
        int averageGreen = 0;

        int pixelCount = 0;

        for (int j = 0; j < source.getHeight(); j++) {

            for (int i = 0; i < source.getWidth(); i++) {

                int rgb = source.getRGB(i, j);

                averageRed += ColorHelper.getRed(rgb);

                averageBlue += ColorHelper.getBlue(rgb);

                averageGreen += ColorHelper.getGreen(rgb);

                pixelCount++;
            }
        }

        averageRed /= pixelCount;
        averageBlue /= pixelCount;
        averageGreen /= pixelCount;

        return new Color(averageRed, averageGreen, averageBlue);
    }

    @Override
    public Color process(ImageSource source, PointFeature component) {
        return filter(source, component);
    }

    public static Color filter(ImageSource source, PointFeature component) {

        int averageRed = 0;
        int averageBlue = 0;
        int averageGreen = 0;

        int pixelCount = 0;

        List<Point2D> points = component.getPoints();

        for (Point2D point : points) {

            int i = (int) point.getX();

            int j = (int) point.getY();

            int rgb = source.getRGB(i, j);

            averageRed += ColorHelper.getRed(rgb);

            averageBlue += ColorHelper.getBlue(rgb);

            averageGreen += ColorHelper.getGreen(rgb);

            pixelCount++;

        }

        averageRed /= pixelCount;

        averageBlue /= pixelCount;

        averageGreen /= pixelCount;

        return new Color(averageRed, averageGreen, averageBlue);

    }

}
