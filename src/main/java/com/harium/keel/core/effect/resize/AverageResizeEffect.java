package com.harium.keel.core.effect.resize;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Resize filter using average
 * Code from: https://stackoverflow.com/a/9586779/7030976
 */
public class AverageResizeEffect extends ResizeEffect {

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource out = new MatrixSource(width, height);

        int oldWidth = input.getWidth();
        int oldHeight = input.getHeight();

        int j;
        int i;
        int l;
        int c;
        float t;
        float u;
        float tmp;
        float d1, d2, d3, d4;
        int p1, p2, p3, p4; /* nearby pixels */
        int red, green, blue;

        for (j = 0; j < height; j++) {
            for (i = 0; i < width; i++) {
                tmp = (float) (j) / (float) (height - 1) * (oldHeight - 1);
                l = (int) Math.floor(tmp);
                if (l < 0) {
                    l = 0;
                } else {
                    if (l >= oldHeight - 1) {
                        l = oldHeight - 2;
                    }
                }

                u = tmp - l;
                tmp = (float) (i) / (float) (width - 1) * (oldWidth - 1);
                c = (int) Math.floor(tmp);
                if (c < 0) {
                    c = 0;
                } else {
                    if (c >= oldWidth - 1) {
                        c = oldWidth - 2;
                    }
                }
                // Threshold
                t = tmp - c;

                /* coefficients */
                d1 = (1 - t) * (1 - u);
                d2 = t * (1 - u);
                d3 = t * u;
                d4 = (1 - t) * u;

                /* nearby pixels */
                p1 = out.getRGB(c, l);
                p2 = out.getRGB(c, l + 1);
                p3 = out.getRGB(c + 1, l);
                p4 = out.getRGB(c + 1, l + 1);

                /* color components */
                int alpha = ColorHelper.getAlpha(p1);
                red = getRed(p1, p2, p3, p4, d1, d2, d3, d4);
                green = getGreen(p1, p2, p3, p4, d1, d2, d3, d4);
                blue = getBlue(p1, p2, p3, p4, d1, d2, d3, d4);

                /* new pixel R G B  */
                int rgbn = ColorHelper.getARGB(red, green, blue, alpha);

                out.setRGB(i, j, rgbn);
            }
        }

        return out;
    }

    private int getRed(int p1, int p2, int p3, int p4, float d1, float d2, float d3, float d4) {
        return (int) (ColorHelper.getRed(p1) * d1 + ColorHelper.getRed(p2) * d2 + ColorHelper.getRed(p3) * d3 + ColorHelper.getRed(p4) * d4);
    }

    private int getGreen(int p1, int p2, int p3, int p4, float d1, float d2, float d3, float d4) {
        return (int) (ColorHelper.getGreen(p1) * d1 + ColorHelper.getGreen(p2) * d2 + ColorHelper.getGreen(p3) * d3 + ColorHelper.getGreen(p4) * d4);
    }

    private int getBlue(int p1, int p2, int p3, int p4, float d1, float d2, float d3, float d4) {
        return (int) (ColorHelper.getBlue(p1) * d1 + ColorHelper.getBlue(p2) * d2 + ColorHelper.getBlue(p3) * d3 + ColorHelper.getBlue(p4) * d4);
    }


}
