package com.harium.keel.effect.resize;

import com.harium.keel.core.Effect;

public abstract class ResizeEffect implements Effect {

    protected int width, height;

    public ResizeEffect width() {
        this.width = width;
        return this;
    }

    public ResizeEffect height() {
        this.height = height;
        return this;
    }

}
