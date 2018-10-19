package com.harium.keel.effect.neuroph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Code from: https://github.com/neuroph/neuroph/blob/master/neuroph-2.9/ImageRec/src/main/java/org/neuroph/imgrec/filter/impl/GuoHallThiningFilter.java
 */
public class GuoHallThinning implements Effect {

    private boolean blackLetters = true;

    int[][] imageM;
    int width;
    int height;

    /**
     * @param input The input image should be binary
     * @return
     */
    @Override
    public ImageSource apply(ImageSource input) {
        width = input.getWidth();
        height = input.getHeight();

        MatrixSource filteredImage = new MatrixSource(input);

        imageM = new int[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int col = input.getR(i, j);
                if (blackLetters) {
                    imageM[i][j] = 1 - (col / 255);
                } else {
                    imageM[i][j] = col / 255;
                }
            }
        }

        while (true) {

            int[][] start = new int[width][height];

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    start[i][j] = imageM[i][j];
                }
            }

            thiningGuoHallIteration(0);
            thiningGuoHallIteration(1);

            boolean same = true;
            MainforLoop:
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (start[i][j] != imageM[i][j]) {
                        same = false;
                        break MainforLoop;
                    }

                }
            }
            if (same) {
                break;
            }
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int alpha = input.getA(i, j);
                int col;
                if (blackLetters) {
                    col = 255 - imageM[i][j] * 255;
                } else {
                    col = imageM[i][j] * 255;
                }
                int rgb = ColorHelper.getARGB(col, col, col, alpha);

                filteredImage.setRGB(i, j, rgb);
            }
        }

        return filteredImage;
    }

    public void thiningGuoHallIteration(int iter) {
        int[][] marker = new int[width][height];
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int p2 = imageM[i - 1][j];
                int p3 = imageM[i - 1][j + 1];
                int p4 = imageM[i][j + 1];
                int p5 = imageM[i + 1][j + 1];
                int p6 = imageM[i + 1][j];
                int p7 = imageM[i + 1][j - 1];
                int p8 = imageM[i][j - 1];
                int p9 = imageM[i - 1][j - 1];

                int C = (~p2 & (p3 | p4)) + (~p4 & (p5 | p6))
                        + (~p6 & (p7 | p8)) + (~p8 & (p9 | p2));
                int N1 = (p9 | p2) + (p3 | p4) + (p5 | p6) + (p7 | p8);
                int N2 = (p2 | p3) + (p4 | p5) + (p6 | p7) + (p8 | p9);
                int N = N1 < N2 ? N1 : N2;
                int m = iter == 0 ? ((p6 | p7 | ~p9) & p8) : ((p2 | p3 | ~p5) & p4);

                if (C == 1 && (N >= 2 && N <= 3) & m == 0) {
                    marker[i][j] = 1;
                }

            }

        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int tmp = 1 - marker[i][j];
                if (imageM[i][j] == tmp && imageM[i][j] == 1) {
                    imageM[i][j] = 1;
                } else {
                    imageM[i][j] = 0;
                }

            }
        }

    }

    public GuoHallThinning blackLetters(boolean blackLetters) {
        this.blackLetters = blackLetters;
        return this;
    }

}
