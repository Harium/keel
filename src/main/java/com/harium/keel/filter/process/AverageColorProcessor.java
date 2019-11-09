package com.harium.keel.filter.process;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.processor.PointFeatureProcessor;

import java.util.List;

public class AverageColorProcessor extends PointFeatureProcessor<Color> {

    public Color apply(ImageSource source) {
        if (feature == null) {
            return applyAll(source);
        }

        return apply(source, feature);
    }

    private Color applyAll(ImageSource source) {
        float averageRed = 0;
        float averageBlue = 0;
        float averageGreen = 0;

        int pixelCount = 0;

        for (int j = 0; j < source.getHeight(); j++) {

            for (int i = 0; i < source.getWidth(); i++) {

                int rgb = source.getRGB(i, j);

                int r = ColorHelper.getRed(rgb);
                int g = ColorHelper.getGreen(rgb);
                int b = ColorHelper.getBlue(rgb);

                averageRed = average(averageRed, r, pixelCount);
                averageGreen = average(averageGreen, g, pixelCount);
                averageBlue = average(averageBlue, b, pixelCount);

                pixelCount++;
            }
        }

        return new Color((int) averageRed, (int) averageGreen, (int) averageBlue);
    }

    public Color apply(ImageSource source, PointFeature component) {
        float averageRed = 0;
        float averageBlue = 0;
        float averageGreen = 0;

        int pixelCount = 0;

        List<Point2D> points = component.getPoints();

        for (Point2D point : points) {
            int i = (int) point.x;
            int j = (int) point.y;

            int rgb = source.getRGB(i, j);

            int r = ColorHelper.getRed(rgb);
            int g = ColorHelper.getGreen(rgb);
            int b = ColorHelper.getBlue(rgb);

            averageRed = average(averageRed, r, pixelCount);
            averageGreen = average(averageGreen, g, pixelCount);
            averageBlue = average(averageBlue, b, pixelCount);

            pixelCount++;
        }

        return new Color((int) averageRed, (int) averageGreen, (int) averageBlue);
    }

    public float average(float oldAverage, float value, int n) {
        return (oldAverage * (n - 1) + value) / n;
    }

}
