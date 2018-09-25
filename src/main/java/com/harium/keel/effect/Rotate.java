package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.interpolation.Algorithm;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.rotate.RotateBicubic;
import com.harium.keel.effect.rotate.RotateBilinear;
import com.harium.keel.effect.rotate.RotateNearestNeighbor;
import com.harium.keel.effect.rotate.RotateOperation;

public class Rotate implements Effect {

    private Algorithm algorithm = Algorithm.NEAREST_NEIGHBOR;

    protected double angle = 0;
    protected boolean keepSize = false;

    protected int fillRed = 0;
    protected int fillGreen = 0;
    protected int fillBlue = 0;
    protected int fillAlpha = 0;

    @Override
    public ImageSource apply(ImageSource input) {
        RotateOperation operation;

        switch (algorithm) {
            case BICUBIC:
                operation = new RotateBicubic();
                break;
            case BILINEAR:
                operation = new RotateBilinear();
                break;
            default:
            case NEAREST_NEIGHBOR:
                operation = new RotateNearestNeighbor();
                break;
        }

        operation.angle(angle).keepSize(keepSize).fillColor(fillRed, fillGreen, fillBlue, fillAlpha);
        return operation.apply(input);
    }

    public Rotate angle(double angle) {
        this.angle = angle;
        return this;
    }

    public Rotate keepSize(boolean keepSize) {
        this.keepSize = keepSize;
        return this;
    }

    public Rotate fillColor(int r, int g, int b) {
        this.fillRed = r;
        this.fillGreen = g;
        this.fillBlue = b;
        return this;
    }

    public Rotate fillColor(int r, int g, int b, int a) {
        this.fillRed = r;
        this.fillGreen = g;
        this.fillBlue = b;
        this.fillAlpha = a;
        return this;
    }

    public Rotate using(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

}
