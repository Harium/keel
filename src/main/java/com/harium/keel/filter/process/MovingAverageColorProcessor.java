package com.harium.keel.filter.process;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.processor.PointFeatureProcessor;

import java.util.List;

public class MovingAverageColorProcessor extends PointFeatureProcessor<Color> {

    private float alpha = 0.9f;

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

        for (int j = 0; j < source.getHeight(); j++) {
            for (int i = 0; i < source.getWidth(); i++) {

                int rgb = source.getRGB(i, j);
                int r = ColorHelper.getRed(rgb);
                int g = ColorHelper.getGreen(rgb);
                int b = ColorHelper.getBlue(rgb);

                averageRed = average(averageRed, r);
                averageGreen = average(averageGreen, g);
                averageBlue = average(averageBlue, b);
            }
        }

        return new Color((int) averageRed, (int) averageGreen, (int) averageBlue);
    }

    public Color apply(ImageSource source, PointFeature component) {
        float averageRed = 0;
        float averageBlue = 0;
        float averageGreen = 0;

        List<Point2D> points = component.getPoints();

        for (Point2D point : points) {
            int i = (int) point.x;
            int j = (int) point.y;

            int rgb = source.getRGB(i, j);

            int r = ColorHelper.getRed(rgb);
            int g = ColorHelper.getGreen(rgb);
            int b = ColorHelper.getBlue(rgb);

            averageRed = average(averageRed, r);
            averageGreen = average(averageGreen, g);
            averageBlue = average(averageBlue, b);
        }

        return new Color((int) averageRed, (int) averageGreen, (int) averageBlue);
    }

    private float average(float oldAverage, float value) {
        return alpha * value + (1 - alpha) * oldAverage;
    }

}
