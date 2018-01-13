package com.harium.keel.modifier.trim;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.PointFeature;
import com.harium.etyl.linear.Point2D;

public class VerticalTrimModifier implements ComponentModifierStrategy, Modifier<PointFeature, PointFeature> {

    int threshold = 10;

    public VerticalTrimModifier() {
        super();
    }

    public VerticalTrimModifier(int threshold) {
        super();
        this.threshold = threshold;
    }

    @Override
    public PointFeature modify(PointFeature feature) {
        boolean[][] mask = feature.generateMask();

        int columns = -1;

        for (int x = feature.getX(); x < feature.getW(); x++) {
            int count = 0;

            for (int y = feature.getY(); y < feature.getH(); y++) {
                if (mask[y][x]) {
                    count++;
                }
            }

            //Count pixels
            if (count < feature.getH() / threshold) {
                //Trim first line
                columns++;
            } else {
                System.out.println("Count: " + count + " vs " + feature.getH() / threshold);
                break;
            }
        }

        if (columns >= 0) {
            //Remove columns
            for (int i = feature.getPoints().size() - 1; i >= 0; i--) {
                Point2D point = feature.getPoints().get(i);
                if (point.getX() <= columns) {
                    feature.getPoints().remove(i);
                }
            }
        }

        return feature;
    }

    @Override
    public PointFeature modifyComponent(PointFeature component) {
        return modify(component);
    }
}
