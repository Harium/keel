package com.harium.keel.effect.rotate;

import com.harium.keel.core.Effect;

public abstract class RotateOperation implements Effect {

    protected double angle = 0;
    double angleCos;
    double angleSin;

    protected boolean keepSize = false;

    protected int newWidth;
    protected int newHeight;

    protected int fillRed = 0;
    protected int fillGreen = 0;
    protected int fillBlue = 0;
    protected int fillAlpha = 0;

    /**
     * Get Angle.
     *
     * @return Angle.
     */
    public double angle() {
        return angle;
    }

    /**
     * Set Angle.
     *
     * @param angle Angle [0..360].
     */
    public RotateOperation angle(double angle) {
        this.angle = angle;
        // Calculate cos and sin with negative angle
        double angleRad = -angle * Math.PI / 180;
        angleCos = Math.cos(angleRad);
        angleSin = Math.sin(angleRad);
        return this;
    }

    /**
     * Keep original size.
     *
     * @return True if keep the original image size, otherwise false.
     */
    public boolean keepSize() {
        return keepSize;
    }

    /**
     * Set keep original size.
     *
     * @param keepSize True if keep the original image size, otherwise false.
     */
    public RotateOperation keepSize(boolean keepSize) {
        this.keepSize = keepSize;
        return this;
    }

    /**
     * Set Fill color.
     *
     * @param red   Red channel's value.
     * @param green Green channel's value.
     * @param blue  Blue channel's value.
     */
    public RotateOperation fillColor(int red, int green, int blue) {
        this.fillRed = red;
        this.fillGreen = green;
        this.fillBlue = blue;
        return this;
    }

    /**
     * Set Fill color.
     *
     * @param red   Red channel's value.
     * @param green Green channel's value.
     * @param blue  Blue channel's value.
     * @param alpha Alpha channel's value.
     */
    public RotateOperation fillColor(int red, int green, int blue, int alpha) {
        this.fillRed = red;
        this.fillGreen = green;
        this.fillBlue = blue;
        this.fillAlpha = alpha;
        return this;
    }

    protected int calculateWidth(int w, int h) {
        double uCos = Math.abs(angleCos);
        double uSin = Math.abs(angleSin);

        if (angle < 90 || (angle > 180 && angle < 270)) {
            return (int) (w * uCos + h * uSin);
        } else {
            return (int) (w * uSin + h * uCos);
        }
    }

    protected int calculateHeight(int w, int h) {
        double uCos = Math.abs(angleCos);
        double uSin = Math.abs(angleSin);

        if (angle < 90 || (angle > 180 && angle < 270)) {
            return (int) (h * uCos + w * uSin);
        } else {
            return (int) (h * uSin + w * uCos);
        }
    }

}
