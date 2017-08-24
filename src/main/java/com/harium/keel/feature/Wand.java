package com.harium.keel.feature;

import com.harium.etyl.commons.graphics.Color;

public class Wand extends Component {

    public Wand(Component component) {
        super(component.getW(), component.getH());
        this.points = component.getPoints();
    }

    private Color color = Color.BLUE;

    private double angle;

    private double distance;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
