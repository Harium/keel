package com.harium.keel.filter.process;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ProcessComponentFilter;
import com.harium.keel.core.ProcessFilter;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public class MovingAverageColorFilter implements ProcessFilter<Color>, ProcessComponentFilter<Color> {

    float alpha = 0.9f;

    public MovingAverageColorFilter() {
        super();
    }

    public Color process(ImageSource source) {
        return filter(source);
    }

    public Color filter(ImageSource source) {
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

    @Override
    public Color process(ImageSource source, PointFeature component) {
        return filter(source, component);
    }

    public Color filter(ImageSource source, PointFeature component) {
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
