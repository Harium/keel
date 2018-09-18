package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Optical Flow
 * <p>
 * Original file found at http://users.ecs.soton.ac.uk/msn/book/new_demo/opticalFlow/
 */

public class OpticalFlowEffect implements Effect {

    private int window = 0;
    private int border = 0;
    private ImageSource initial;

    public OpticalFlowEffect() {
        super();
    }

    public ImageSource apply(ImageSource image) {

        MatrixSource output = new MatrixSource(image);

        int width = image.getWidth();
        int height = image.getHeight();

        // arrays to store the vectors
        int[] u = new int[width * height];
        int[] v = new int[width * height];

        // loop over the image
        for (int y1 = (window + border + 1); y1 < (height - window - border); y1++) {

            for (int x1 = (window + border + 1); x1 < (width - window - border); x1++) {

                int min = 9999999;
                int dx = 0;
                int dy = 0;

                // loop through the displacement area
                for (int x2 = (x1 - border); x2 < (x1 + border); x2++) {

                    for (int y2 = (y1 - border); y2 < (y1 + border); y2++) {

                        int sum = 0;

                        // loop over the window area - (2 * window) + 1
                        for (int i = -window; i < window; i++) {

                            for (int j = -window; j < window; j++) {
                                // sum pixel intensity difference squared over the window area
                                sum += (int) Math.pow((double) ((initial.getRGB(x1 + i, y1 + j) & 0xff) - (image.getRGB(x2 + i, y2 + j) & 0xff)), 2);
                            }

                        }

                        if (sum < min) {
                            min = sum;
                            dx = x2 - x1;
                            dy = y2 - y1;
                        }

                    }
                }
                // set velocity
                u[x1 + y1 * width] = dx;
                v[x1 + y1 * width] = dy;
            }
        }

        // we now have the vectors of the optical flow and need to construct a graph
        int val;

        int step = 5;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x % step != 0 || y % step != 0) || u[x + y * width] == v[x + y * width]) {
                    val = 255;
                    output.setRGB(x, y, 0xff000000 | (int) val << 16 | (int) val << 8 | (int) val);
                } else {
                    //int size = Math.abs(u[x + y * width] -  v[x + y * width]);
                    int xv = u[x + y * width];
                    int yv = v[x + y * width];
                    line(x, y, x + (xv * 2), y + (yv * 2), width, height, output);

                }
            }
        }

        return output;
    }

    private void line(int x0, int y0, int x1, int y1, int width, int height, ImageSource output) {
        if (x1 > width) x1 = width;

        if (x1 < 0) x1 = 0;

        if (y1 > height) y1 = height;

        if (y1 < 0) y1 = 0;

        int dx = x1 - x0;

        int dy = y1 - y0;

        int val = 0;

        output.setRGB(x0, y0, 0xff000000 | (int) val << 16 | (int) val << 8 | (int) val);

        if (Math.abs(dx) > Math.abs(dy)) {          // slope < 1

            float m = (float) dy / (float) dx;      // compute slope

            float b = y0 - m * x0;

            dx = (dx < 0) ? -1 : 1;

            while (x0 != x1) {

                x0 += dx;

                output.setRGB(x0, Math.round(m * x0 + b), 0xff000000 | (int) val << 16 | (int) val << 8 | (int) val);

                if (val < 250) val += 25;
            }

        } else if (dy != 0) {                              // slope >= 1

            float m = (float) dx / (float) dy;      // compute slope

            float b = x0 - m * y0;

            dy = (dy < 0) ? -1 : 1;

            while (y0 != y1) {

                y0 += dy;

                output.setRGB(Math.round(m * y0 + b), y0, 0xff000000 | (int) val << 16 | (int) val << 8 | (int) val);

                if (val < 250) val += 25;
            }

        }
    }

    public OpticalFlowEffect window(int window) {
        this.window = window;
        return this;
    }

    public OpticalFlowEffect border(int border) {
        this.border = border;
        return this;
    }

    public OpticalFlowEffect image(ImageSource image) {
        this.initial = image;
        return this;
    }

}
