package com.harium.keel.marker;

import com.harium.etyl.geometry.math.Vector2i;

public class RectangularMarker {

    private Vector2i a;
    private Vector2i b;
    private Vector2i c;
    private Vector2i d;

    public RectangularMarker() {
        super();
    }

    public RectangularMarker(Vector2i a, Vector2i b, Vector2i c, Vector2i d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Vector2i getA() {
        return a;
    }

    public void setA(Vector2i a) {
        this.a = a;
    }

    public Vector2i getB() {
        return b;
    }

    public void setB(Vector2i b) {
        this.b = b;
    }

    public Vector2i getC() {
        return c;
    }

    public void setC(Vector2i c) {
        this.c = c;
    }

    public Vector2i getD() {
        return d;
    }

    public void setD(Vector2i d) {
        this.d = d;
    }

    public Vector2i findPosition(float px, float py) {
        Vector2i out = new Vector2i();
        findPosition(px, py, out);
        return out;
    }

    public void findPosition(float px, float py, Vector2i out) {
        float abX = a.x + px * (b.x - a.x);
        float abY = a.y + px * (b.y - a.y);

        float dcX = d.x + px * (c.x - d.x);
        float dcY = d.y + px * (c.y - d.y);

        float ox = abX + px * (dcX - abX);
        float oy = abY + py * (dcY - abY);

        out.set((int) ox, (int) oy);
    }

}
