package com.harium.keel.effect.projection;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class CubeToEquirectangularFaces implements Effect {

    ImageSource right;
    ImageSource left;
    ImageSource top;
    ImageSource bottom;
    ImageSource front;
    ImageSource back;

    // Usually 2:1
    int width = 256, height = 128;

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource equiTexture = new MatrixSource(width, height);

        float u, v; //Normalised texture coordinates, from 0 to 1, starting at lower left corner
        double theta, phi; //Polar coordinates
        int cubeFaceWidth, cubeFaceHeight;

        cubeFaceWidth = input.getWidth();
        cubeFaceHeight = input.getHeight();

        for (int j = 0; j < equiTexture.getHeight(); j++) {
            // Rows start from the bottom
            v = ((float) j / equiTexture.getHeight());
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

                ImageSource source;

                int xPixel, yPixel;

                if (xa == 1) {
                    //Right
                    source = right;
                    xPixel = (int) ((((za + 1f) / 2f) - 1f) * cubeFaceWidth);
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                } else if (xa == -1) {
                    //Left
                    source = left;
                    xPixel = (int) ((((za + 1f) / 2f)) * cubeFaceWidth);
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                } else if (ya == 1) {
                    //Up
                    source = top;
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    yPixel = (int) ((((za + 1f) / 2f) - 1f) * cubeFaceHeight);
                } else if (ya == -1) {
                    //Down
                    source = bottom;
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    yPixel = (int) ((((za + 1f) / 2f)) * cubeFaceHeight);
                } else if (za == 1) {
                    //Front
                    source = front;
                    xPixel = (int) ((((xa + 1f) / 2f)) * cubeFaceWidth);
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                } else if (za == -1) {
                    //Back
                    source = back;
                    xPixel = (int) ((((xa + 1f) / 2f) - 1f) * cubeFaceWidth);
                    yPixel = (int) ((((ya + 1f) / 2f)) * cubeFaceHeight);
                } else {
                    source = null;
                    xPixel = 0;
                    yPixel = 0;
                }

                if (xPixel < 0) {
                    xPixel = -xPixel;
                }
                if (yPixel < 0) {
                    yPixel = -yPixel;
                }

                yPixel = input.getHeight() - yPixel;

                if (xPixel >= input.getWidth()) {
                    continue;
                } else if (yPixel >= input.getHeight()) {
                    continue;
                }

                int rgb = getPixel(source, xPixel, yPixel);
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

    public CubeToEquirectangularFaces width(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public CubeToEquirectangularFaces height(int height) {
        this.height = height;
        return this;
    }

    public CubeToEquirectangularFaces top(ImageSource top) {
        this.top = top;
        return this;
    }

    public CubeToEquirectangularFaces bottom(ImageSource bottom) {
        this.bottom = bottom;
        return this;
    }

    public CubeToEquirectangularFaces left(ImageSource left) {
        this.left = left;
        return this;
    }

    public CubeToEquirectangularFaces right(ImageSource right) {
        this.right = right;
        return this;
    }

    public CubeToEquirectangularFaces front(ImageSource front) {
        this.front = front;
        return this;
    }

    public CubeToEquirectangularFaces back(ImageSource back) {
        this.back = back;
        return this;
    }
}
