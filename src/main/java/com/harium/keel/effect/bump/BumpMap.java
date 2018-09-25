package com.harium.keel.effect.bump;

import com.harium.keel.core.Effect;

public abstract class BumpMap implements Effect {

    protected int border = 1;
    protected float scale = 255;

    public BumpMap border(int border) {
        this.border = border;
        return this;
    }

    public BumpMap scale(float scale) {
        this.scale = scale;
        return this;
    }
}
