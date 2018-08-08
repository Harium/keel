package com.harium.keel.helper;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.geometry.Point3D;
import com.harium.keel.modifier.posit.Pose;
import org.opencv.OpenCv;

public class RotationAxis {

    Matrix4 matrix;
    protected double error = 0;

    public RotationAxis() {
        super();
        matrix = new Matrix4();
    }

    public RotationAxis(Pose pose) {
        super();
        this.error = pose.getBestError();

        this.computeRotationValues(pose.getBestRotation());
        this.computeTranslationValues(pose.getBestTranslation());
    }

    protected void computeTranslationValues(double[] translation) {
        float x = (float) translation[0];
        float y = (float) translation[1];
        float z = (float) translation[2];

        matrix.translate(x, y, z);
    }

    protected void computeRotationValues(double[] rotation) {
        float[] f = new float[16];
        f[0] = (float)rotation[0];
        f[1] = (float)rotation[1];
        f[2] = (float)rotation[2];
        f[3] = 0;
        f[4] = (float)rotation[3];
        f[5] = (float)rotation[4];
        f[6] = (float)rotation[5];
        f[7] = 0;
        f[8] = (float)rotation[6];
        f[9] = (float)rotation[7];
        f[10] = (float)rotation[8];
        f[11] = 0;
        f[12] = 0;
        f[13] = 0;
        f[14] = 0;
        f[15] = 1;
        matrix = new Matrix4(f);
    }

    public void computeRotationValues(double[][] rotation) {
        float[] f = new float[16];
        f[0] = (float)rotation[0][0];
        f[1] = (float)rotation[0][1];
        f[2] = (float)rotation[0][2];
        f[3] = 0;
        f[4] = (float)rotation[1][0];
        f[5] = (float)rotation[1][1];
        f[6] = (float)rotation[1][2];
        f[7] = 0;
        f[8] = (float)rotation[2][0];
        f[9] = (float)rotation[2][1];
        f[10] = (float)rotation[2][2];
        f[11] = 0;
        f[12] = 0;
        f[13] = 0;
        f[14] = 0;
        f[15] = 1;

        matrix = new Matrix4(f);
    }

    public Point3D transform(Point3D point) {
        Vector3 vector = new Vector3((float)point.getX(), (float)point.getY(), (float)point.getZ());
        vector.mul(matrix);

        return new Point3D(vector.x, vector.y, vector.z);
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public Quaternion calculateQuaternion() {
        Quaternion quaternion = new Quaternion();
        quaternion.setFromMatrix(matrix);
        return quaternion;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }
}
