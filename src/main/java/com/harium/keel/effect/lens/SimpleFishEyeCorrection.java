package com.harium.keel.effect.lens;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Original ideas found at: http://www.tannerhelland.com/4743/simple-algorithm-correcting-lens-distortion/
 */

public class SimpleFishEyeCorrection implements Effect {

    private float strength = 1f;
    private float zoom = 1;

    public SimpleFishEyeCorrection() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource image) {
        MatrixSource output = new MatrixSource(image);

        int w = image.getWidth();
        int h = image.getHeight();

        int halfWidth = w / 2;
        int halfHeight = h / 2;

        double correctionRadius = Math.sqrt(w * w + h * h) / strength;

        for (int j = 0; j < h; j++) {

            for (int i = 0; i < w; i++) {

                int newX = i - halfWidth;
                int newY = j - halfHeight;

                double distance = Math.sqrt(newX * newX + newY * newY);
                double r = distance / correctionRadius;

                double theta = 1;

                if (r != 0) {
                    theta = Math.atan(r) / r;
                }

                double sourceX = halfWidth + theta * newX * zoom;
                double sourceY = halfHeight + theta * newY * zoom;

                int rgb = image.getRGB(i, j);

                if (sourceX >= w || sourceY >= h) {
                    System.out.println("Problem here!");
                }

                output.setRGB((int) sourceX, (int) sourceY, rgb);
            }
        }

        return output;
    }

    public SimpleFishEyeCorrection strength(float strength) {
        this.strength = strength;
        return this;
    }

    public SimpleFishEyeCorrection zoom(float zoom) {
        this.zoom = zoom;
        return this;
    }

}
