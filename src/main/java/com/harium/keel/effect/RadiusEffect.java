package com.harium.keel.effect;

public abstract class RadiusEffect {

    protected int radius = 1;

    public RadiusEffect() {
        super();
    }

    public RadiusEffect(int radius) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    /**
     * Radius.
     *
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Radius.
     *
     * @param radius Radius.
     */
    public RadiusEffect setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    protected int calcLines(int radius) {
        return radius * 2 + 1;
    }
}
