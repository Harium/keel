package com.harium.keel.util;

public class CameraUtil {

    private CameraUtil() {}

    /**
     * Calculate the focal length of camera
     * @param realWidth - real object width
     * @param distanceFromCamera - real distance from camera
     * @param widthInPixels - size of object in captured image (in pixels)
     * @return the Focal Length
     */
    public static double focalLength(double realWidth, double distanceFromCamera, int widthInPixels) {
        return (distanceFromCamera * widthInPixels) / realWidth;
    }

    /**
     * Calculate the FOV of camera, using focalLength and sensorWidth
     * @param focalLength
     * @param sensorWidth
     * @return FOV
     */
    public static float fieldOfView(float focalLength, float sensorWidth) {
        double fov = 2 * Math.atan(.5 * sensorWidth / focalLength);
        return (float) Math.toDegrees(fov);
    }

}
