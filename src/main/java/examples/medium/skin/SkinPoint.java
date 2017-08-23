package examples.medium.skin;


import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.commons.layer.Layer;
import com.harium.etyl.core.graphics.Graphics;

public class SkinPoint extends Layer {

    private int r, g, b;

    private boolean over = false;

    private Color color;

    public SkinPoint(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;

        this.x = r - 2;
        this.y = 255 - (b + (g - b));

        if (b > g) {
            color = Color.BLUE;
            //this.y+=256;
        } else {
            color = Color.GREEN;
        }

        this.y -= 2;

        this.w = 4;
        this.h = 4;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {

        g.setColor(color);

        int radius = 4;

        if (over) {
            radius = 8;
        }

        g.fillCircle(x + 2, y + 2, radius);
        g.setColor(Color.BLACK);
        g.drawCircle(x + 2, y + 2, radius);
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

}
