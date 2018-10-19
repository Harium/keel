package com.harium.keel.effect.normal;

import com.harium.keel.core.Effect;

public abstract class NormalMap implements Effect {

    protected int border = 1;
    protected float scale = 255;

    public NormalMap border(int border) {
        this.border = border;
        return this;
    }

    public NormalMap scale(float scale) {
        this.scale = scale;
        return this;
    }
}
