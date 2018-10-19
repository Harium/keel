package com.harium.keel.effect.watershed;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

import java.util.Arrays;
import java.util.Vector;

/**
 * @author tejopa / http://popscan.blogspot.com
 * @date 2014-04-20
 */
public class Watershed implements Effect {

    int width;
    int height;

    int floodPoints = 16;
    int windowWidth = 256; // (8-256) Width Range
    int connectedPixels = 4; // or 8

    public Watershed() {
        super();
    }

    public Watershed(int floodPoints, int windowWidth, int connectedPixels) {
        super();
        this.floodPoints = floodPoints;
        this.windowWidth = windowWidth;
        this.connectedPixels = connectedPixels;
    }

    public Watershed floodPoints(int floodPoints) {
        this.floodPoints = floodPoints;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        /*
        // frame for real time view for the process
        frame = new JFrame();
        frame.setSize(image.getWidth(),image.getHeight());
        frame.setVisible(true);
        */

        width = input.getWidth();
        height = input.getHeight();
        // height map is the gray color image
        int[] map = EffectHelper.getGrayData(input);
        // LUT is the lookup table for the processed pixels
        int[] lut = new int[EffectHelper.getSize(input)];
        // fill LUT with ones
        Arrays.fill(lut, 1);
        Vector<FloodPoint> minimums = new Vector<FloodPoint>();
        // loop all the pixels of the image until
        // a) all the required minimums have been found
        // OR
        // b) there are no more unprocessed pixels left
        int foundMinimums = 0;
        while (foundMinimums < floodPoints) {
            int minimumValue = 256;
            int minimumPosition = -1;
            for (int i = 0; i < lut.length; i++) {
                if ((lut[i] == 1) && (map[i] < minimumValue)) {
                    minimumPosition = i;
                    minimumValue = map[i];
                }
            }
            // check if minimum was found
            if (minimumPosition != -1) {
                // add minimum to found minimum vector
                int x = minimumPosition % width;
                int y = minimumPosition / width;
                int grey = map[x + width * y] & 0xff;
                int label = foundMinimums;
                minimums.add(new FloodPoint(x, y,
                        label, grey));
                // remove pixels around so that the next minimum
                // must be at least windowWidth/2 distance from
                // this minimum (using square, could be circle...)
                int half = windowWidth / 2;
                fill(x - half, y - half, x + half, y + half, lut, 0);
                lut[minimumPosition] = 0;
                foundMinimums++;
            } else {
                // stop while loop
                System.out.println("Out of pixels. Found "
                        + minimums.size()
                        + " minimums of requested "
                        + floodPoints + ".");
                break;
            }
        }
        /*
        // create image with minimums only
        for (int i=0;i<minimums.size();i++) {
            FloodPoint p = minimums.elementAt(i);
            Graphics g = image.getGraphics();
            g.setColor(Color.red);
            g.drawRect(p.x, p.y, 2, 2);
        }
        saveImage("minimums.png", image);
        */

        // start flooding from minimums
        lut = flood(map, minimums, connectedPixels);

        for (int i = 0; i < lut.length; i++) {
            EffectHelper.setGray(i, lut[i], input);
        }

        return input;
    }

    private int[] flood(int[] map, Vector<FloodPoint> minimums,
                        int connectedPixels) {
        SortedVector queue = new SortedVector();
        //BufferedImage result = new BufferedImage(width, height,
        //        BufferedImage.TYPE_INT_RGB);
        int[] lut = new int[width * height];
        int[] inqueue = new int[width * height];
        // not processed = -1, processed >= 0
        Arrays.fill(lut, -1);
        Arrays.fill(inqueue, 0);
        // Initialize queue with each found minimum
        for (int i = 0; i < minimums.size(); i++) {
            FloodPoint p = minimums.elementAt(i);
            int label = p.label;
            // insert starting pixels around minimums
            addPoint(queue, inqueue, map, p.x, p.y - 1, label);
            addPoint(queue, inqueue, map, p.x + 1, p.y, label);
            addPoint(queue, inqueue, map, p.x, p.y + 1, label);
            addPoint(queue, inqueue, map, p.x - 1, p.y, label);
            if (connectedPixels == 8) {
                addPoint(queue, inqueue, map, p.x - 1, p.y - 1, label);
                addPoint(queue, inqueue, map, p.x + 1, p.y - 1, label);
                addPoint(queue, inqueue, map, p.x + 1, p.y + 1, label);
                addPoint(queue, inqueue, map, p.x - 1, p.y + 1, label);
            }
            int pos = p.x + p.y * width;
            lut[pos] = label;
            inqueue[pos] = 1;
        }

        // start flooding
        while (queue.size() > 0) {
            // find minimum
            FloodPoint extracted = null;
            // remove the minimum from the queue
            extracted = (FloodPoint) queue.remove(0);
            int x = extracted.x;
            int y = extracted.y;
            int label = extracted.label;
            // check pixels around extracted pixel
            int[] labels = new int[connectedPixels];
            labels[0] = getLabel(lut, x, y - 1);
            labels[1] = getLabel(lut, x + 1, y);
            labels[2] = getLabel(lut, x, y + 1);
            labels[3] = getLabel(lut, x - 1, y);
            if (connectedPixels == 8) {
                labels[4] = getLabel(lut, x - 1, y - 1);
                labels[5] = getLabel(lut, x + 1, y - 1);
                labels[6] = getLabel(lut, x + 1, y + 1);
                labels[7] = getLabel(lut, x - 1, y + 1);
            }
            boolean onEdge = isEdge(labels, extracted);
            if (onEdge) {
                // leave edges without label
            } else {
                // set pixel with label
                lut[x + width * y] = extracted.label;
            }
            if (!inQueue(inqueue, x, y - 1)) {
                addPoint(queue, inqueue, map, x, y - 1, label);
            }
            if (!inQueue(inqueue, x + 1, y)) {
                addPoint(queue, inqueue, map, x + 1, y, label);
            }
            if (!inQueue(inqueue, x, y + 1)) {
                addPoint(queue, inqueue, map, x, y + 1, label);
            }
            if (!inQueue(inqueue, x - 1, y)) {
                addPoint(queue, inqueue, map, x - 1, y, label);
            }
            if (connectedPixels == 8) {
                if (!inQueue(inqueue, x - 1, y - 1)) {
                    addPoint(queue, inqueue, map, x - 1, y - 1, label);
                }
                if (!inQueue(inqueue, x + 1, y - 1)) {
                    addPoint(queue, inqueue, map, x + 1, y - 1, label);
                }
                if (!inQueue(inqueue, x + 1, y + 1)) {
                    addPoint(queue, inqueue, map, x + 1, y + 1, label);
                }
                if (!inQueue(inqueue, x - 1, y + 1)) {
                    addPoint(queue, inqueue, map, x - 1, y + 1, label);
                }
            }
            // draw the current pixel set to frame, WARNING: slow...
            //result.setRGB(0, 0, width, height, lut, 0, width);
            //frame.getGraphics().drawImage(result,
            //     0, 0, width, height, null);
        }
        return lut;
    }

    private boolean inQueue(int[] inqueue, int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        if (inqueue[x + width * y] == 1) {
            return true;
        }
        return false;
    }

    private boolean isEdge(int[] labels, FloodPoint extracted) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] != extracted.label && labels[i] != -1) {
                return true;
            }
        }
        return false;
    }

    private int getLabel(int[] lut, int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return -2;
        }
        return lut[x + width * y];
    }

    private void addPoint(SortedVector queue,
                          int[] inqueue, int[] map,
                          int x, int y, int label) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        queue.add(new FloodPoint(x, y, label, map[x + width * y] & 0xff));
        inqueue[x + width * y] = 1;
    }

    private void fill(int x1, int y1, int x2, int y2,
                      int[] array, int value) {
        for (int y = y1; y < y2; y++) {
            for (int x = x1; x < x2; x++) {
                // clip to boundaries
                if (y >= 0 && x >= 0 && y < height && x < width) {
                    array[x + width * y] = value;
                }
            }
        }
    }

    class FloodPoint implements Comparable<Object> {
        int x;
        int y;
        int label;
        int grey;

        public FloodPoint(int x, int y, int label, int grey) {
            this.x = x;
            this.y = y;
            this.label = label;
            this.grey = grey;
        }

        @Override
        public int compareTo(Object o) {
            FloodPoint other = (FloodPoint) o;
            if (this.grey < other.grey) {
                return -1;
            } else if (this.grey > other.grey) {
                return 1;
            }
            return 0;
        }
    }

}