package com.harium.keel.modifier.trim;

import com.harium.keel.core.ComponentModifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Component;
import com.harium.etyl.linear.Point2D;

public class VerticalTrimModifier implements ComponentModifierStrategy, ComponentModifier<Component, Component> {

    int threshold = 10;

    public VerticalTrimModifier() {
        super();
    }

    public VerticalTrimModifier(int threshold) {
        super();
        this.threshold = threshold;
    }

    @Override
    public Component modify(Component component) {
        boolean[][] mask = component.generateMask();

        int columns = -1;

        for (int x = component.getX(); x < component.getW(); x++) {
            int count = 0;

            for (int y = component.getY(); y < component.getH(); y++) {
                if (mask[y][x]) {
                    count++;
                }
            }

            //Count pixels
            if (count < component.getH() / threshold) {
                //Trim first line
                columns++;
            } else {
                System.out.println("Count: " + count + " vs " + component.getH() / threshold);
                break;
            }
        }

        if (columns >= 0) {
            //Remove columns
            for (int i = component.getPoints().size() - 1; i >= 0; i--) {
                Point2D point = component.getPoints().get(i);
                if (point.getX() <= columns) {
                    component.getPoints().remove(i);
                }
            }
        }

        return component;
    }

    @Override
    public Component modifyComponent(Component component) {
        return modify(component);
    }
}
