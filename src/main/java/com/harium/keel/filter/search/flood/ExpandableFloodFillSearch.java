package com.harium.keel.filter.search.flood;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.model.ColorPoint;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.LinkedList;
import java.util.Queue;

public class ExpandableFloodFillSearch extends FloodFillSearch {

    public ExpandableFloodFillSearch(int w, int h) {
        super(w, h);
    }

    public ExpandableFloodFillSearch(int w, int h, int minNeighbors) {
        super(w, h, minNeighbors);
    }

    public ExpandableFloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
        super(w, h, minNeighbors, maxNeighbors);
    }

    @Override
    protected boolean inBoundary(int px, int py, Feature component) {
        return px > step || px <= getW() - step || py > step || py <= getH() - step;
    }

    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        int rgb = source.getRGB(x, y);

        if (verifySinglePixel(x, y, rgb)) {

            //Clear Queue
            Queue<ColorPoint> queue = new LinkedList<>();
            PointFeature found = new PointFeature();

            ColorPoint firstPoint = new ColorPoint(x, y, rgb);

            //Mark as touched
            addPoint(found, firstPoint);
            addNeighbors(queue, firstPoint, component);

            //For each neighbor
            while (!queue.isEmpty()) {

                //Queue.pop();
                ColorPoint p = queue.remove();

                if (verifyNext(p, x, y, getW(), getH(), source, component)) {
                    addPoint(found, p);
                    addNeighbors(queue, p, component);
                } else {
                    mask.setTouched(x, y);
                }
            }

            if (this.validate(found)) {
                results.add(componentModifierStrategy.modifyComponent(found));
            }
        } else {
            mask.setTouched(x, y);
        }
        return false;
    }

}
