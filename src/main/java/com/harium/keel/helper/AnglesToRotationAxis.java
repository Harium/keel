package com.harium.keel.helper;


public class AnglesToRotationAxis extends RotationAxis {

    public AnglesToRotationAxis(double pitch, double yaw, double roll) {
        super();
        matrix.setFromEulerAngles((float) yaw, (float) pitch, (float) roll);
    }

}
