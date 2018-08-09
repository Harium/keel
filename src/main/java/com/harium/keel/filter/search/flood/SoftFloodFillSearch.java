package com.harium.keel.filter.search.flood;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.LinkedList;
import java.util.Queue;

public class SoftFloodFillSearch extends FloodFillSearch {

    protected int UNDEFINED_COLOR = -1;

    public SoftFloodFillSearch(int w, int h) {
        super(w, h);
    }

    public SoftFloodFillSearch(int w, int h, int minNeighbors) {
        super(w, h, minNeighbors);
    }

    public SoftFloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
        super(w, h, minNeighbors, maxNeighbors);
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        int rgb = source.getRGB(x, y);

        if (verifySinglePixel(x, y, rgb, UNDEFINED_COLOR)) {

            //Clear Queue
            Queue<Point2D> queue = new LinkedList<Point2D>();
            PointFeature found = new PointFeature();

            Point2D firstPoint = new Point2D(x, y);

            //Mark as touched
            addPoint(found, firstPoint);
            addNeighbors(queue, firstPoint, rgb, component);//Add reference to its color

            //For each neighbor
            while (!queue.isEmpty()) {

                //Queue.pop();
                Point2D p = queue.remove();

                if (verifyNext(p, x, y, component.getWidth(), component.getHeight(), source)) {
                    addPoint(found, p);
                    addNeighbors(queue, p, component);
                } else {
                    mask.setTouched(x, y);
                }
            }

            if (this.validate(found)) {
                results.add(componentModifierStrategy.modifyComponent(found));
            }
        }

        mask.setTouched(x, y);
        return false;
    }

    protected boolean verifyNext(Point2D p, int x, int y, int width,
                                 int height, ImageSource bimg) {

        int px = (int) p.getX();
        int py = (int) p.getY();
        int rgb = bimg.getRGB(px, py);

        //Get color from previous pixel
        int lastColor = p.getColor();

        if ((px >= x) && (px < x + width - border &&
                (py >= y) && (py < y + height - border))) {

            if (verifyPixel(px, py, rgb, lastColor, bimg)) {
                p.setColor(rgb);
                return true;
            }
        }
        return false;
    }

    protected void addNeighbors(Queue<Point2D> queue, Point2D p, int lastColor, Feature component) {
        addNeighbor(queue, (int) p.getX() + step, (int) p.getY(), lastColor, component);
        addNeighbor(queue, (int) p.getX() - step, (int) p.getY(), lastColor, component);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() + step, lastColor, component);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() - step, lastColor, component);
    }

    private boolean verifyPixel(int px, int py, int rgb, int lastRGB, ImageSource source) {
        if (verifySinglePixel(px, py, rgb, lastRGB)) {
            if (minNeighbors > 0 && maxNeighbors > 0) {
                if (!verifyNeighbors(px, py, rgb, source)) {
                    return false;
                }
            }
            return true;
        }

        return false;

    }

    protected boolean verifySinglePixel(int px, int py, int rgb, int lastRGB) {
        if (mask.isUnknown(px, py)) {
            if (selectionStrategy.validateColor(rgb, px, py)) {
                mask.setValid(px, py);
            } else if (lastRGB != UNDEFINED_COLOR) {
                if (selectionStrategy.softValidateColor(lastRGB, px, py, rgb)) {
                    mask.setValid(px, py);
                }
            } else {
                mask.setInvalid(px, py);
            }
        }

        return (!mask.isTouched(px, py) && mask.isValid(px, py));
    }

    private boolean verifyNeighbors(int px, int py, int rgb, ImageSource bimg) {

        int verified = 0;
        int baseColor = rgb;

        for (int y = py - step; y <= py + step; y += step) {
            for (int x = px - step; x <= px + step; x += step) {

                int currentColor = bimg.getRGB(x, y);
                if (mask.isValid(x, y) || selectionStrategy.softValidateColor(baseColor, x, y, currentColor)) {
                    verified++;
                    if (verified >= minNeighbors) {
                        return true;
                    }
                }
                baseColor = currentColor;
            }
        }

        return verified >= minNeighbors && verified <= maxNeighbors;
    }

}
