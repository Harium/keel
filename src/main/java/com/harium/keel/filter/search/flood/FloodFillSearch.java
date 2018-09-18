package com.harium.keel.filter.search.flood;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.filter.ComponentFilter;
import com.harium.keel.core.mask.DynamicArrayMask;
import com.harium.keel.core.mask.DynamicMask;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.filter.color.RGBColorStrategy;

import java.util.LinkedList;
import java.util.Queue;

public class FloodFillSearch extends ComponentFilter {

    protected int minNeighbors = 1;
    protected int maxNeighbors = 9;

    protected DynamicMask mask;

    public FloodFillSearch(int w, int h) {
        super(w, h, new RGBColorStrategy());

        mask = new DynamicArrayMask(w, h);
    }

    public FloodFillSearch(int w, int h, int minNeighbors) {
        super(w, h, new RGBColorStrategy());

        mask = new DynamicArrayMask(w, h);

        this.minNeighbors = minNeighbors;
    }

    public FloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
        super(w, h, new RGBColorStrategy());

        this.minNeighbors = minNeighbors;
        this.maxNeighbors = maxNeighbors;
    }

    public void setup(ImageSource source, Feature feature) {
        super.setup(source, feature);

        int w = feature.getWidth();
        int h = feature.getHeight();
        updateMask(w, h, source);
    }

    protected void updateMask(int w, int h, ImageSource source) {
        if (mask.getW() != w || mask.getH() != h) {
            mask.init(w, h);
        }

        mask.update(source);
    }

    @Override
    public boolean filterFirst(int x, int y, int width, int height, ImageSource source, Feature component) {
        return filter(x, y, width, height, source, component);
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        int rgb = source.getRGB(x, y);

        if (verifySinglePixel(x, y, rgb)) {

            //Clear Queue
            Queue<Point2D> queue = new LinkedList<Point2D>();
            PointFeature found = new PointFeature();

            Point2D firstPoint = new Point2D(x, y, rgb);

            //Mark as touched
            addPoint(found, firstPoint);
            addNeighbors(queue, firstPoint, component);

            //For each neighbor
            while (!queue.isEmpty()) {

                //Queue.pop();
                Point2D p = queue.remove();

                if (verifyNext(p, x, y, width, height, source, component)) {
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

    protected boolean verifyNext(Point2D p, int x, int y, int width,
                                 int height, ImageSource source, Feature component) {

        int px = (int) p.getX();
        int py = (int) p.getY();

        int rgb = source.getRGB(px, py);

        if ((px >= x) && (px < x + width &&
                (py >= y) && (py < y + height))) {

            if (verifyPixel(px, py, rgb, source, component)) {
                return true;
            }
        }
        return false;
    }

    protected void addPoint(PointFeature component, Point2D p) {
        mask.setTouched((int) p.getX(), (int) p.getY());
        component.add(p);
    }

    protected void addNeighbors(Queue<Point2D> queue, Point2D p, Feature component) {
        addNeighbor(queue, (int) p.getX() + step, (int) p.getY(), p.getColor(), component);
        addNeighbor(queue, (int) p.getX() - step, (int) p.getY(), p.getColor(), component);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() + step, p.getColor(), component);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() - step, p.getColor(), component);
    }

    //It also prevents same pixel be included in a better list of neighbors
    //May have to be changed to let multiple touch
    protected void addNeighbor(Queue<Point2D> queue, int px, int py, int color, Feature component) {
        if (!inBoundary(px, py, component)) {
            return;
        }

        if (!mask.isTouched(px, py)) {
            queue.add(new Point2D(px, py, color));
        }
    }

    protected boolean verifyPixel(int px, int py, int rgb, ImageSource source, Feature component) {
        if (verifySinglePixel(px, py, rgb)) {
            if (!verifyNeighbors(px, py, source, component)) {
                return false;
            }
            return true;
        }

        return false;

    }

    protected boolean verifySinglePixel(int px, int py, int rgb) {

        if (mask.isUnknown(px, py)) {
            if (selectionStrategy.validateColor(rgb, px, py)) {
                mask.setValid(px, py);
            } else {
                mask.setInvalid(px, py);
            }
        }

        return (!mask.isTouched(px, py) && mask.isValid(px, py));
    }

    protected boolean verifyNeighbors(int px, int py, ImageSource source, Feature component) {
        int count = 0;

        for (int y = py - step; y <= py + step; y += step) {
            for (int x = px - step; x <= px + step; x += step) {
                if (!inBoundary(x, y, component)) {
                    continue;
                }

                if (mask.isValid(x, y)) {
                    count++;
                } else if (selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
                    count++;
                }
            }
        }

        return count >= minNeighbors && count <= maxNeighbors;
    }

    protected boolean inBoundary(int x, int y, Feature component) {
        return component.contains(x, y);
    }

    public int getMinNeighbors() {
        return minNeighbors;
    }

    public void setMinNeighbors(int minNeighbors) {
        this.minNeighbors = minNeighbors;
    }

    public int getMaxNeighbors() {
        return maxNeighbors;
    }

    public void setMaxNeighbors(int maxNeighbors) {
        this.maxNeighbors = maxNeighbors;
    }

}
