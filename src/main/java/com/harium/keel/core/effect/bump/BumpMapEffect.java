package com.harium.keel.core.effect.bump;

import com.harium.keel.core.effect.Effect;

public abstract class BumpMapEffect implements Effect {

    protected int border = 1;
    protected float scale = 255;

    public BumpMapEffect border(int border) {
        this.border = border;
        return this;
    }

    public BumpMapEffect scale(float scale) {
        this.scale = scale;
        return this;
    }
}
