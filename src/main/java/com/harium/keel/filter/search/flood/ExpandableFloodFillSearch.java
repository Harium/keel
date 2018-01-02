package com.harium.keel.filter.search.flood;

import com.harium.etyl.linear.Point2D;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

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
    public boolean inBoundary(int px, int py) {
        return px > step || px <= getW() - step || py > step || py <= getH() - step;
    }

    public boolean filter(int x, int y, int width, int height, ImageSource source) {
        int rgb = source.getRGB(x, y);

        if (verifySinglePixel(x, y, rgb)) {

            //Clear Queue
            Queue<Point2D> queue = new LinkedList<Point2D>();
            Component found = new Component();

            Point2D firstPoint = new Point2D(x, y, rgb);

            //Mark as touched
            addPoint(found, firstPoint);
            addNeighbors(queue, firstPoint);

            //For each neighbor
            while (!queue.isEmpty()) {

                //Queue.pop();
                Point2D p = queue.remove();

                if (verifyNext(p, x, y, getW(), getH(), source)) {
                    addPoint(found, p);
                    addNeighbors(queue, p);
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
