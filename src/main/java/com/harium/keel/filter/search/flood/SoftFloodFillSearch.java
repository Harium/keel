package com.harium.keel.filter.search.flood;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.etyl.linear.Point2D;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
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
    public List<Component> filter(ImageSource source, Component component) {
        super.setup(component.getW(), component.getH(), source);

        this.boundary = component;

        int x = component.getX() + border;
        int y = component.getY() + border;

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        for (int j = y; j < y + height; j += step) {
            for (int i = x; i < x + width; i += step) {
                if (!component.isInside(i, j)) {
                    continue;
                }

                //Found some valid pixel
                int rgb = source.getRGB(i, j);

                if (verifySinglePixel(i, j, rgb, UNDEFINED_COLOR)) {

                    //Clear Queue
                    Queue<Point2D> queue = new LinkedList<Point2D>();
                    Component found = new Component();

                    Point2D firstPoint = new Point2D(i, j);

                    //Mark as touched
                    addPoint(found, firstPoint);
                    addNeighbors(queue, firstPoint, rgb);//Add reference to its color

                    //For each neighbor
                    while (!queue.isEmpty()) {

                        //Queue.pop();
                        Point2D p = queue.remove();

                        if (verifyNext(p, x, y, width, height, source)) {
                            addPoint(found, p);
                            addNeighbors(queue, p);
                        } else {
                            mask.setTouched(i, j);
                        }
                    }

                    if (this.validate(found)) {
                        result.add(componentModifierStrategy.modifyComponent(found));
                    }
                }

                mask.setTouched(i, j);
            }
        }

        return result;
    }

    protected boolean verifyNext(Point2D p, int x, int y, int width,
                                 int height, BufferedImage bimg) {

        int px = (int) p.getX();
        int py = (int) p.getY();
        int rgb = bimg.getRGB(px, py);

        //Get color from previous pixel
        int lastColor = p.getColor();

        if ((px >= x) && (px < x + width &&
                (py >= y) && (py < y + height))) {

            if (verifyPixel(px, py, rgb, lastColor, bimg)) {
                p.setColor(rgb);
                return true;
            }
        }
        return false;
    }

    protected void addNeighbors(Queue<Point2D> queue, Point2D p, int lastColor) {
        addNeighbor(queue, (int) p.getX() + step, (int) p.getY(), lastColor);
        addNeighbor(queue, (int) p.getX() - step, (int) p.getY(), lastColor);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() + step, lastColor);
        addNeighbor(queue, (int) p.getX(), (int) p.getY() - step, lastColor);
    }

    private boolean verifyPixel(int px, int py, int rgb, int lastRGB, BufferedImage bimg) {

        if (verifySinglePixel(px, py, rgb, lastRGB)) {
            if (minNeighbors > 0 && maxNeighbors > 0) {
                if (!verifyNeighbors(px, py, rgb, bimg)) {
                    return false;
                }
            }
            return true;
        }

        return false;

    }

    protected boolean verifySinglePixel(int px, int py, int rgb, int lastRGB) {
        if (mask.isUnknown(px, py)) {
            if (pixelStrategy.validateColor(rgb, px, py)) {
                mask.setValid(px, py);
            } else if (lastRGB != UNDEFINED_COLOR) {
                if (pixelStrategy.strongValidateColor(lastRGB, px, py, rgb)) {
                    mask.setValid(px, py);
                }
            } else {
                mask.setInvalid(px, py);
            }
        }

        return (!mask.isTouched(px, py) && mask.isValid(px, py));
    }

    private boolean verifyNeighbors(int px, int py, int rgb, BufferedImage bimg) {

        int verified = 0;
        int baseColor = rgb;

        for (int i = py - step; i <= py + step; i += step) {
            for (int j = px - step; j <= px + step; j += step) {

                int currentColor = bimg.getRGB(i, j);
                if (mask.isValid(j, i) || pixelStrategy.strongValidateColor(baseColor, j, i, currentColor)) {
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
