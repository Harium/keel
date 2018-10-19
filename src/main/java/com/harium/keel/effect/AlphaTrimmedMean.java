// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
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
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

import java.util.Arrays;

/**
 * Alpha Trimmed Mean filter.
 * <p>The alpha trimmed mean filter is normally used to reduce noise in an image, somewhat like the mean and median filter. However, it often does a better job than the mean filter of preserving useful detail in the image.</p>
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class AlphaTrimmedMean extends RadiusEffect implements Effect {

    private int t = 1;

    /**
     * Get trimmed value.
     *
     * @return Trimmed value.
     */
    public int getT() {
        return t;
    }

    /**
     * Set trimmed value.
     *
     * @param t Trimmed value.
     */
    public void setT(int t) {
        this.t = Math.min((radius * 2 + 1) * (radius * 2 + 1) / 2, Math.max(0, t));
    }

    /**
     * Initializes a new instance of the AlphaTrimmedMean class.
     */
    public AlphaTrimmedMean() {
        super();
    }

    /**
     * Initializes a new instance of the AlphaTrimmedMean class.
     *
     * @param radius Radius.
     */
    public AlphaTrimmedMean(int radius) {
        super(radius);
    }

    /**
     * Initializes a new instance of the AlphaTrimmedMean class.
     *
     * @param radius Radius.
     * @param t      Trimmed value.
     */
    public AlphaTrimmedMean(int radius, int t) {
        super(radius);
        setT(t);
    }

    @Override
    public ImageSource apply(ImageSource input) {

        int width = input.getWidth();
        int height = input.getHeight();
        int Xline, Yline;
        int lines = calcLines(radius);
        int maxArray = lines * lines;
        int c;

        MatrixSource copy = new MatrixSource(input);

        if (input.isGrayscale()) {
            int[] avgL = new int[maxArray];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    c = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < width) && (Yline >= 0) && (Yline < height)) {
                                // Gray Value
                                avgL[c] = copy.getB(Xline, Yline);
                            } else {
                                // Gray Value
                                avgL[c] = copy.getB(x, y);
                            }
                            c++;
                        }
                    }

                    Arrays.sort(avgL);

                    //alpha trimmed mean
                    double mean = 0;
                    for (int i = t; i < c - t; i++) {
                        mean += avgL[i];
                    }

                    input.setGray(x, y, (int) (mean / (avgL.length - 2 * t)));
                }
            }
        } else {
            int[] avgR = new int[maxArray];
            int[] avgG = new int[maxArray];
            int[] avgB = new int[maxArray];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    c = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < width) && (Yline >= 0) && (Yline < height)) {
                                avgR[c] = copy.getR(Xline, Yline);
                                avgG[c] = copy.getG(Xline, Yline);
                                avgB[c] = copy.getB(Xline, Yline);
                            } else {
                                avgR[c] = copy.getR(x, y);
                                avgG[c] = copy.getG(x, y);
                                avgB[c] = copy.getB(x, y);
                            }
                            c++;
                        }
                    }

                    Arrays.sort(avgR);
                    Arrays.sort(avgG);
                    Arrays.sort(avgB);

                    //alpha trimmed mean
                    double meanR = 0, meanG = 0, meanB = 0;
                    for (int i = t; i < c - t; i++) {
                        meanR += avgR[i];
                        meanG += avgG[i];
                        meanB += avgB[i];
                    }

                    meanR /= (avgR.length - 2 * t);
                    meanG /= (avgG.length - 2 * t);
                    meanB /= (avgB.length - 2 * t);

                    int rgb = ColorHelper.getRGB((int) meanR, (int) meanG, (int) meanB);
                    input.setRGB(y, x, rgb);
                }
            }
        }
        return input;
    }

}