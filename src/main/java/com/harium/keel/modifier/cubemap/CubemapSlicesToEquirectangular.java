package com.harium.keel.modifier.cubemap;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.interpolation.Algorithm;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.effect.Resize;

public class CubemapSlicesToEquirectangular implements Modifier<ImageSource[], ImageSource> {

    public static final int TOP = 0;
    public static final int LEFT = 1;
    public static final int FRONT = 2;
    public static final int RIGHT = 3;
    public static final int BACK = 4;
    public static final int BOTTOM = 5;

    // Aspect 2:1
    int width = 2048;
    int height = 1024;

    boolean doAvgColor = false;

    protected Algorithm algorithm = Algorithm.BICUBIC;
    private final Resize resize = new Resize();

    @Override
    public ImageSource apply(ImageSource[] feature) {
        // output image size, render big at first, then reduce this size by 50% (anti-aliasing)
        float fW = width * 2;
        float fH = height * 2;

        ImageSource imOutput = new MatrixSource((int) fW, (int) fH);

        int targetColor;

        for (int j = 0; j < (int) fH; j++) {
            for (int i = 0; i < (int) fW; i++) {
                // value range from -1 to 1
                float u = 2 * i / fW - 1;
                float v = 2 * j / fH - 1;

                // convert to horizontal and vertical angle
                double theta = u * Math.PI;
                double phi = v * Math.PI / 2;

                // find corresponding location on sphere (inside a cube)
                double x = Math.cos(phi) * Math.cos(theta);
                double y = Math.sin(phi);
                double z = Math.cos(phi) * Math.sin(theta);

                // find appropriate color from cube faces
                targetColor = findColor(x, y, z, feature);
                imOutput.setRGB(i, j, targetColor);
            }
        }

        resize.size(width, height).using(algorithm);
        imOutput = resize.apply(imOutput);

        return imOutput;
    }

    private int findColor(double x, double y, double z, ImageSource[] slices) {
        int width = slices[TOP].getWidth();
        int height = slices[TOP].getHeight();

        ImageSource target;
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);

        if (absX > absY && absX > absZ) { // on left or right face
            boolean isLeft = x < 0;

            // intersection point on x plane
            z /= absX;
            y /= absX;

            if (isLeft) {
                z = -z;
                target = slices[LEFT];
            } else {
                target = slices[RIGHT];
            }

            int xx = (int) (((z + 1) * width) / 2);
            int yy = (int) (((y + 1) * height) / 2);

            xx = Math.max(0, Math.min(width - 1, xx));
            yy = Math.max(0, Math.min(height - 1, yy));

            return selectColor(width, height, target, xx, yy);
        } else if (absY > absX && absY > absZ) { // on top or bottom face
            boolean isTop = y < 0;

            // intersection point on y plane
            z /= absY;
            x /= absY;

            if (isTop) {
                z = -z;
                target = slices[TOP];
            } else {
                target = slices[BOTTOM];
            }

            int xx = (int) (((x + 1) * width) / 2);
            int yy = (int) (((z + 1) * height) / 2);

            xx = Math.max(0, Math.min(width - 1, xx));
            yy = Math.max(0, Math.min(height - 1, yy));

            return selectColor(width, height, target, xx, yy);
        } else { // on front or back face
            boolean isFront = z < 0;

            // intersection point on z plane
            y /= absZ;
            x /= absZ;

            if (isFront) {
                target = slices[FRONT];
            } else {
                x = -x;
                target = slices[BACK];
            }

            int xx = (int) Math.round(((x + 1) * width) / 2);
            int yy = (int) Math.round(((y + 1) * height) / 2);

            xx = Math.max(0, Math.min(width - 1, xx));
            yy = Math.max(0, Math.min(height - 1, yy));

            return selectColor(width, height, target, xx, yy);
        }
    }

    private int selectColor(int width, int height, ImageSource target, int xx, int yy) {
        if (doAvgColor && xx > 0 && xx < width - 1 && yy > 0 && yy < height - 1) {
            int r = 0;
            int g = 0;
            int b = 0;
            for (int y = -1; y < 3; y++) {
                for (int x = -1; x < 3; x++) {
                    int rgb = target.getRGB(xx+x, yy+y);
                    r += ColorHelper.getRed(rgb);
                    g += ColorHelper.getGreen(rgb);
                    b += ColorHelper.getBlue(rgb);
                }
            }

            r /= 9;
            g /= 9;
            b /= 9;
            return ColorHelper.getRGB(r, g, b);
        } else {
            return target.getRGB(xx, yy);
        }
    }

    public CubemapSlicesToEquirectangular using(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public CubemapSlicesToEquirectangular avgColor(boolean doAvgColor) {
        this.doAvgColor = doAvgColor;
        return this;
    }

    public CubemapSlicesToEquirectangular width(int width) {
        this.width = width;
        this.height = height / 2;
        return this;
    }

}
