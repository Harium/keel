package com.harium.keel.effect.lens;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Known as Barrel Distortion Correction
 */
public class LensCorrection implements Effect {

    private double a = 0;
    private double b = 0;
    private double c = 1.0;

    public LensCorrection() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource image) {
        MatrixSource out = new MatrixSource(image);

        int w = image.getWidth();
        int h = image.getHeight();

        // parameters for correction
        double paramA = -a; // affects only the outermost pixels of the image
        double paramB = -b; // most cases only require b optimization
        double paramC = -c; // most uniform correction
        double paramD = 1.0 - paramA - paramB - paramC; // describes the linear scaling of the image

        int halfWidth = w / 2;

        int halfHeight = h / 2;

        int d = Math.min(w, h) / 2;    // radius of the circle

        for (int j = 0; j < h; j++) {

            for (int i = 0; i < w; i++) {

                int deltaX = (i - halfWidth) / d;
                int deltaY = (j - halfHeight) / d;

                // distance or radius of dst image
                double dstR = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // distance or radius of src image (with formula)
                double srcR = (paramA * dstR * dstR * dstR + paramB * dstR * dstR + paramC * dstR + paramD) * dstR;

                // comparing old and new distance to get factor
                double factor = Math.abs(dstR / srcR);

                // coordinates in source image
                double srcXd = halfWidth + (deltaX * factor * d);
                double srcYd = halfHeight + (deltaY * factor * d);

                // no interpolation yet (just nearest point)
                int srcX = (int) srcXd;
                int srcY = (int) srcYd;

                if (srcX >= 0 && srcY >= 0 && srcX < w && srcY < h) {
                    int rgb = image.getRGB(i, j);
                    out.setRGB(srcX, srcY, rgb);
                }
            }
        }

        return out;
    }

    public LensCorrection a(double a) {
        this.a = a;
        return this;
    }

    public LensCorrection b(double b) {
        this.b = b;
        return this;
    }

    public LensCorrection c(double c) {
        this.c = c;
        return this;
    }

}

