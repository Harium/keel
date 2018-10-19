// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Ivan Kuckir, 2013
// ivan at kuckir.com
//
//
//    This library is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Lesser General Public
//    License as published by the Free Software Foundation; either
//    version 2.1 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with this library; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//

package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

import static com.harium.keel.effect.helper.EffectHelper.*;

/**
 * Gaussian Box Blur. Minimum Size = 3x3
 * Reference: http://blog.ivank.net/fastest-gaussian-blur.html
 *
 * @author Diego Catalano
 */
public class GaussianBoxBlur implements Effect {

    private double std;
    private int r;

    /**
     * Get Radius.
     *
     * @return Radius.
     */
    public int getRadius() {
        return r;
    }

    /**
     * Set Radius.
     *
     * @param radius Radius.
     */
    public GaussianBoxBlur radius(int radius) {
        this.r = Math.max(1, radius);
        return this;
    }

    /**
     * Initialize a new instance of the GaussianBoxBlur class.
     */
    public GaussianBoxBlur() {
        this(1);
    }

    /**
     * Initialize a new instance of the GaussianBoxBlur class.
     *
     * @param standardDeviation Standard deviation.
     */
    public GaussianBoxBlur(double standardDeviation) {
        this(standardDeviation, 3);
    }

    /**
     * Initialize a new instance of the GaussianBoxBlur class.
     *
     * @param standartDeviation Standard deviation.
     * @param radius            Radius.
     */
    public GaussianBoxBlur(double standartDeviation, int radius) {
        this.std = standartDeviation;
        this.r = radius;
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {

        MatrixSource copy = new MatrixSource(fastBitmap);
        int[] boxs = BoxesForGauss(std, r);

        BoxBlurRGB(fastBitmap, copy, (boxs[0] - 1) / 2);
        BoxBlurRGB(copy, fastBitmap, (boxs[1] - 1) / 2);
        BoxBlurRGB(fastBitmap, copy, (boxs[2] - 1) / 2);

        return fastBitmap;
    }

    private void BoxBlurRGB(ImageSource source, ImageSource dest, int r) {

        for (int i = 0; i < source.getHeight(); i++) {
            for (int j = 0; j < source.getWidth(); j++) {
                dest.setRGB(j, i, source.getRGB(j, i));
            }
        }

        BoxBlurH_RGB(dest, source, r);
        BoxBlurT_RGB(source, dest, r);
    }

    private void BoxBlurH_RGB(ImageSource source, ImageSource dest, int r) {
        int w = source.getWidth();
        int h = source.getHeight();

        double iarr = 1 / (double) (r + r + 1);

        //Red channel
        for (int i = 0; i < h; i++) {
            int ti = i * w, li = ti, ri = ti + r;
            int fv = getRed(ti, source), lv = getRed(ti + w - 1, source), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) {
                val += getRed(ti + j, source);
            }
            for (int j = 0; j <= r; j++) {
                val += getRed(ri++, source) - fv;
                setRed(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = r + 1; j < w - r; j++) {
                val += getRed(ri++, source) - getRed(li++, source);
                setRed(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = w - r; j < w; j++) {
                val += lv - getRed(li++, source);
                setRed(ti++, (int) Math.round(val * iarr), dest);
            }
        }

        //Green channel
        for (int i = 0; i < h; i++) {
            int ti = i * w, li = ti, ri = ti + r;
            int fv = getGreen(ti, source), lv = getGreen(ti + w - 1, source), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) val += getGreen(ti + j, source);
            for (int j = 0; j <= r; j++) {
                val += getGreen(ri++, source) - fv;
                setGreen(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = r + 1; j < w - r; j++) {
                val += getGreen(ri++, source) - getGreen(li++, source);
                setGreen(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = w - r; j < w; j++) {
                val += lv - getGreen(li++, source);
                setGreen(ti++, (int) Math.round(val * iarr), dest);
            }
        }

        //Blue channel
        for (int i = 0; i < h; i++) {
            int ti = i * w, li = ti, ri = ti + r;
            int fv = getBlue(ti, source), lv = getBlue(ti + w - 1, source), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) val += getBlue(ti + j, source);
            for (int j = 0; j <= r; j++) {
                val += getBlue(ri++, source) - fv;
                setBlue(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = r + 1; j < w - r; j++) {
                val += getBlue(ri++, source) - getBlue(li++, source);
                setBlue(ti++, (int) Math.round(val * iarr), dest);
            }
            for (int j = w - r; j < w; j++) {
                val += lv - getBlue(li++, source);
                setBlue(ti++, (int) Math.round(val * iarr), dest);
            }
        }

    }

    private void BoxBlurT_RGB(ImageSource copy, ImageSource original, int r) {

        int w = original.getWidth();
        int h = original.getHeight();

        double iarr = 1 / (double) (r + r + 1);

        //Red channel
        for (int i = 0; i < w; i++) {
            int ti = i, li = ti, ri = ti + r * w;
            int fv = getRed(ti, copy), lv = getRed(ti + w * (h - 1), copy), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) val += getRed(ti + j * w, copy);
            for (int j = 0; j <= r; j++) {
                val += getRed(ri, copy) - fv;
                setRed(ti, (int) Math.round(val * iarr), original);
                ri += w;
                ti += w;
            }
            for (int j = r + 1; j < h - r; j++) {
                val += getRed(ri, copy) - getRed(li, copy);
                setRed(ti, (int) Math.round(val * iarr), original);
                li += w;
                ri += w;
                ti += w;
            }
            for (int j = h - r; j < h; j++) {
                val += lv - getRed(li, copy);
                setRed(ti, (int) Math.round(val * iarr), original);
                li += w;
                ti += w;
            }
        }

        //Green channel
        for (int i = 0; i < w; i++) {
            int ti = i, li = ti, ri = ti + r * w;
            int fv = getGreen(ti, copy), lv = getGreen(ti + w * (h - 1), copy), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) val += getGreen(ti + j * w, copy);
            for (int j = 0; j <= r; j++) {
                val += getGreen(ri, copy) - fv;
                setGreen(ti, (int) Math.round(val * iarr), original);
                ri += w;
                ti += w;
            }
            for (int j = r + 1; j < h - r; j++) {
                val += getGreen(ri, copy) - getGreen(li, copy);
                setGreen(ti, (int) Math.round(val * iarr), original);
                li += w;
                ri += w;
                ti += w;
            }
            for (int j = h - r; j < h; j++) {
                val += lv - getGreen(li, copy);
                setGreen(ti, (int) Math.round(val * iarr), original);
                li += w;
                ti += w;
            }
        }

        //Blue channel
        for (int i = 0; i < w; i++) {
            int ti = i, li = ti, ri = ti + r * w;
            int fv = getBlue(ti, copy), lv = getBlue(ti + w * (h - 1), copy), val = (r + 1) * fv;
            for (int j = 0; j < r; j++) val += getBlue(ti + j * w, copy);
            for (int j = 0; j <= r; j++) {
                val += getBlue(ri, copy) - fv;
                setBlue(ti, (int) Math.round(val * iarr), original);
                ri += w;
                ti += w;
            }
            for (int j = r + 1; j < h - r; j++) {
                val += getBlue(ri, copy) - getBlue(li, copy);
                setBlue(ti, (int) Math.round(val * iarr), original);
                li += w;
                ri += w;
                ti += w;
            }
            for (int j = h - r; j < h; j++) {
                val += lv - getBlue(li, copy);
                setBlue(ti, (int) Math.round(val * iarr), original);
                li += w;
                ti += w;
            }
        }

    }

    private int[] BoxesForGauss(double sigma, int n)  // standard deviation, number of boxes
    {
        double wIdeal = Math.sqrt((12 * sigma * sigma / (double) n) + 1);  // Ideal averaging filter width
        double wl = Math.floor(wIdeal);
        if (wl % 2 == 0) wl--;
        double wu = wl + 2;

        double mIdeal = (12 * sigma * sigma - n * wl * wl - 4 * n * wl - 3 * n) / (-4 * wl - 4);
        double m = Math.round(mIdeal);
        // var sigmaActual = Math.sqrt( (m*wl*wl + (n-m)*wu*wu - n)/12 );

        int[] sizes = new int[n];
        for (int i = 0; i < n; i++) {
            if (i < m)
                sizes[i] = (int) wl;
            else
                sizes[i] = (int) wu;
        }

        return sizes;
    }
}