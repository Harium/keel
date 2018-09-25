package com.harium.keel.effect.resize;

import com.harium.keel.core.Effect;

public abstract class ResizeOperation implements Effect {

    protected int width, height;

    public ResizeOperation width(int width) {
        this.width = width;
        return this;
    }

    public ResizeOperation height(int height) {
        this.height = height;
        return this;
    }

    public ResizeOperation size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

}
