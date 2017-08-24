package com.harium.keel.filter.search.flood;

import com.harium.keel.core.ComponentFilter;
import com.harium.keel.core.mask.DynamicArrayMask;
import com.harium.keel.core.mask.DynamicMask;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.color.ColorStrategy;
import com.harium.etyl.linear.Point2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FloodFillSearch extends ComponentFilter {

    protected int minNeighbors = 1;

    protected int maxNeighbors = 9;

    private Component lastComponent;

    protected Component boundary;

    protected DynamicMask mask;

    public FloodFillSearch(int w, int h) {
        super(w, h, new ColorStrategy());

        mask = new DynamicArrayMask(w, h);
    }

    public FloodFillSearch(int w, int h, int minNeighbors) {
        super(w, h, new ColorStrategy());

        mask = new DynamicArrayMask(w, h);

        this.minNeighbors = minNeighbors;
    }

    public FloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
        super(w, h, new ColorStrategy());

        this.minNeighbors = minNeighbors;

        this.maxNeighbors = maxNeighbors;
    }

    public Component filterFirst(ImageSource source, Component component) {
        List<Component> list = filter(source, component);

        if (!list.isEmpty()) {
            lastComponent = list.get(0);
        }

        return lastComponent;
    }

    public void setup(int w, int h, ImageSource source) {
        super.setup(w, h);
        updateMask(w, h, source);
    }

    protected void updateMask(int w, int h, ImageSource source) {
        if (mask.getW() != w || mask.getH() != h) {
            mask.init(w, h);
        }

        mask.update(source);
    }

    public List<Component> filter(final ImageSource source, final Component component) {
        this.setup(component.getW(), component.getH());

        boundary = component;

        int x = component.getX() + border;
        int y = component.getY() + border;

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        for (int j = y; j < y + height; j += step) {
            for (int i = x; i < x + width; i += step) {
                if (!component.isInside(i, j)) {
                    continue;
                }

                int rgb = source.getRGB(i, j);

                if (verifySinglePixel(i, j, rgb)) {

                    //Clear Queue
                    Queue<Point2D> queue = new LinkedList<Point2D>();
                    Component found = new Component();

                    Point2D firstPoint = new Point2D(i, j, rgb);

                    //Mark as touched
                    addPoint(found, firstPoint);
                    addNeighbors(queue, firstPoint);

                    //For each neighbor
                    while (!queue.isEmpty()) {

                        //Queue.pop();
                        Point2D p = queue.remove();

                        if (verifyNext(p, i, j, width, height, source)) {
                            addPoint(found, p);
                            addNeighbors(queue, p);
                        } else {
                            mask.setTouched(i, j);
                        }
                    }

                    if (this.validate(found)) {
                        result.add(componentModifierStrategy.modifyComponent(found));
                    }
                } else {
                    mask.setTouched(i, j);
                }
            }
        }

        return result;
    }

    protected boolean verifyNext(Point2D p, int x, int y, int width,
                                 int height, ImageSource source) {

        int px = (int) p.getX();
        int py = (int) p.getY();

        int rgb = source.getRGB(px, py);

        if ((px >= x) && (px < x + width &&
                (py >= y) && (py < y + height))) {

            if (verifyPixel(px, py, rgb, source)) {
                return true;
            }
        }
        return false;
    }

    protected void addPoint(Component component, Point2D p) {
        mask.setTouched((int) p.getX(), (int) p.getY());
        component.add(p);
    }

    protected void addNeighbors(Queue<Point2D> queue, Point2D p) {
        addNeighbor(queue, (int) p.getX() + step, (int) p.getY(), p.getColor());
        addNeighbor(queue, (int) p.getX() - step, (int) p.getY(), p.getColor());
        addNeighbor(queue, (int) p.getX(), (int) p.getY() + step, p.getColor());
        addNeighbor(queue, (int) p.getX(), (int) p.getY() - step, p.getColor());
    }

    //It also prevents same pixel be included in a better list of neighbors
    //May have to be changed to let multiple touch
    protected void addNeighbor(Queue<Point2D> queue, int px, int py, int color) {
        if (!inBoundary(px, py)) {
            return;
        }

        if (!mask.isTouched(px, py)) {
            queue.add(new Point2D(px, py, color));
        }
    }

    protected boolean verifyPixel(int px, int py, int rgb, ImageSource source) {

        if (verifySinglePixel(px, py, rgb)) {
            if (!verifyNeighbors(px, py, source)) {
                return false;
            }
            return true;
        }

        return false;

    }

    protected boolean verifySinglePixel(int px, int py, int rgb) {

        if (mask.isUnknown(px, py)) {
            if (pixelStrategy.validateColor(rgb, px, py)) {
                mask.setValid(px, py);
            } else {
                mask.setInvalid(px, py);
            }
        }

        return (!mask.isTouched(px, py) && mask.isValid(px, py));
    }

    protected boolean verifyNeighbors(int px, int py, ImageSource source) {

        int verified = 0;

        //TODO Swap i,j
        for (int j = py - step; j <= py + step; j += step) {
            for (int i = px - step; i <= px + step; i += step) {
                if (!inBoundary(i, j)) {
                    continue;
                }

                if (mask.isValid(i, j)) {
                    verified++;
                } else if (pixelStrategy.validateColor(source.getRGB(i, j), i, j)) {
                    verified++;
                }
            }
        }

        return verified >= minNeighbors && verified <= maxNeighbors;
    }

    public boolean inBoundary(int px, int py) {
        return boundary.intersects(px, py);
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
