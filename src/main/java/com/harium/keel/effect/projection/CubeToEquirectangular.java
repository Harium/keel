package com.harium.keel.effect.projection;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Code from: https://stackoverflow.com/a/34427087
 */
public class CubeToEquirectangular implements Effect {

    // Usually 2:1
    int width = 256, height = 128;

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource equiTexture = new MatrixSource(width, height);

        float u, v; //Normalised texture coordinates, from 0 to 1, starting at lower left corner
        double theta, phi; //Polar coordinates
        int cubeFaceWidth, cubeFaceHeight;

        cubeFaceWidth = input.getWidth() / 4; //4 horizontal faces
        cubeFaceHeight = input.getHeight() / 3; //3 vertical faces

        for (int j = 0; j < equiTexture.getHeight(); j++) {
            // Rows start from the bottom
            v = 1 - ((float) j / equiTexture.getHeight());
            theta = v * Math.PI;
            final double thetaSin = Math.sin(theta);
            final double thetaCos = Math.cos(theta);

            for (int i = 0; i < equiTexture.getWidth(); i++) {
                // Columns start from the left
                u = ((float) i / equiTexture.getWidth());
                phi = u * 2 * Math.PI;

                float x, y, z; //Unit vector
                x = (float) (Math.sin(phi) * thetaSin * -1);
                y = (float) thetaCos;
                z = (float) (Math.cos(phi) * thetaSin * -1);

                float xa, ya, za;
                float a;

                a = (float) Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(z)));

                //Vector Parallel to the unit vector that lies on one of the cube faces
                xa = x / a;
                ya = y / a;
                za = z / a;

                int xPixel, yPixel;
                int xOffset, yOffset;

                if (xa == 1) {
                    //Right
                    xPixel = (int) ((((za + 1f) / 2f) - 1f) * cubeFaceWidth);
                    xOffset = 2 * cubeFaceWidth; //Offset
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                    yOffset = cubeFaceHeight; //Offset
                } else if (xa == -1) {
                    //Left
                    xPixel = (int) ((((za + 1f) / 2f)) * cubeFaceWidth);
                    xOffset = 0;
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                    yOffset = cubeFaceHeight;
                } else if (ya == 1) {
                    //Up
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    xOffset = cubeFaceWidth;
                    yPixel = (int) ((((za + 1f) / 2f) - 1f) * cubeFaceHeight);
                    yOffset = 2 * cubeFaceHeight;
                } else if (ya == -1) {
                    //Down
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    xOffset = cubeFaceWidth;
                    yPixel = (int) ((((za + 1f) / 2f)) * cubeFaceHeight);
                    yOffset = 0;
                } else if (za == 1) {
                    //Front
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    xOffset = cubeFaceWidth;
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                    yOffset = cubeFaceHeight;
                } else if (za == -1) {
                    //Back
                    xPixel = (int) ((((xa + 1f) / 2f) - 1f) * cubeFaceWidth);
                    xOffset = 3 * cubeFaceWidth;
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                    yOffset = cubeFaceHeight;
                } else {
                    //Debug.LogWarning("Unknown face, something went wrong");
                    xPixel = 0;
                    yPixel = 0;
                    xOffset = 0;
                    yOffset = 0;
                }

                xPixel = Math.abs(xPixel);
                yPixel = Math.abs(yPixel);

                xPixel += xOffset;
                yPixel += yOffset;

                if (xPixel >= input.getWidth()) {
                    continue;
                } else if (yPixel >= input.getHeight()) {
                    continue;
                }

                int rgb = getPixel(input, xPixel, yPixel);
                equiTexture.setRGB(i, j, rgb);
            }
        }

        return equiTexture;
    }

    protected int getPixel(ImageSource input, int xPixel, int yPixel) {
        int color;
        color = input.getRGB(xPixel, yPixel);
        return color;
    }

    public int getWidth() {
        return width;
    }

    public CubeToEquirectangular width(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public CubeToEquirectangular height(int height) {
        this.height = height;
        return this;
    }
}
