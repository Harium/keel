package com.harium.keel.helper;

import com.harium.etyl.linear.Point3D;
import org.opencv.OpenCv;

import com.harium.keel.modifier.posit.Pose;

public class RotationAxis {

	protected double error = 0;
	
	protected double angle = 0;
	
	protected double rotationX = 0;
	
	protected double rotationY = 0;
	
	protected double rotationZ = 0;
	
	protected double x = 0;
	
	protected double y = 0;
	
	protected double z = 0;
		
	public RotationAxis(){
		super();
	}
	
	public RotationAxis(Pose pose){
		super();
				
		this.error = pose.getBestError();
		
		this.computeTranslationValues(pose.getBestTranslation());
		
		this.computeRotationValues(pose.getBestRotation());
		
	}
	
	protected void computeTranslationValues(double[] translation){
		
		this.x = translation[0];
		
		//Y as Normal
		this.y = translation[2];
		
		this.z = translation[1];
		
	}
	
	protected void computeRotationValues(double[] rotation) {
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2+3*1] - rotation[1+3*2])+OpenCv.cvSqr(rotation[0+3*2] - rotation[2+3*0])+OpenCv.cvSqr(rotation[1+3*0] - rotation[0+3*1]));
		
		this.rotationX = (rotation[2+3*1] - rotation[1+3*2])/norm;
		this.rotationX = -this.rotationX;

		//Using Y as Normal
		this.rotationY = (rotation[1+3*0] - rotation[0+3*1])/norm;
		
		this.rotationZ = (rotation[2+3*0] - rotation[0+3*2])/norm;
		this.rotationZ = -this.rotationZ;
		
		
		//this.rotationX = -Math.asin(-rotation[1+3*2]);
	    //this.rotationY = -Math.atan2(rotation[0+3*2], rotation[2+3*2]);
	    //this.rotationZ = Math.atan2(rotation[1+3*0], rotation[1+3*1]);
	    	
	}
	
	public void computeRotationValues(double[][] rotation){
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0][0] + rotation[1][1] + rotation[2][2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2][1] - rotation[1][2])+OpenCv.cvSqr(rotation[0][2] - rotation[2][0])+OpenCv.cvSqr(rotation[1][0] - rotation[0][1]));
		
		this.rotationX = (rotation[2][1] - rotation[1][2])/norm;
		this.rotationX = -this.rotationX;

		//Using Y as Normal
		this.rotationY = (rotation[1][0] - rotation[0][1])/norm;

		this.rotationZ = (rotation[2][0] - rotation[0][2])/norm;
		this.rotationZ = -this.rotationZ;
		
		//this.rotationX = -Math.asin(-rotation[1][2]);
	    //this.rotationY = -Math.atan2(rotation[0][2], rotation[2][2]);
	    //this.rotationZ = Math.atan2(rotation[1][0], rotation[1][1]);
		
	}
	
	public Point3D transformPoint(Point3D point) {
		
		double m[] = rotationMatrix();
		
		double rx = m[0]*point.getX()+m[1]*point.getY()+m[2]*point.getZ();//+m[3]*0;
		
		//Swap py and pz to reflect rotations
		double ry = m[4]*point.getX()+m[5]*point.getY()+m[6]*point.getZ();//+m[7]*0;
		
		double rz = m[8]*point.getX()+m[9]*point.getY()+m[10]*point.getZ();//+m[11]*0;
		
		//double pw = m[12]*point.getX()+m[13]*point.getY()+m[14]*point.getZ()+m[15]*0;

		return new Point3D(rx, -ry, rz);
						
	}
	
	private double[] rotationMatrix() {
		
		double m[] = new double[16];
		
		double s = Math.sin(Math.toRadians(angle));
		double c = Math.cos(Math.toRadians(angle));
		double d = 1-c;
		
		double x = -rotationX;
		double y = rotationY;
		double z = -rotationZ;
		
		//First line
		m[0] = x*x*d+c;
		m[1] = x*y*d-z*s;
		m[2] = x*z*d+y*s;
		m[3] = 0;
		
		m[4] = y*x*d+z*s;
		m[5] = y*y*d+c;
		m[6] = y*z*d-x*s;
		m[7] = 0;
		
		m[8] = z*x*d-y*s;
		m[9] = z*y*d+x*s;
		m[10] = z*z*d+c;
		m[11] = 0;
		
		m[12] = 0;
		m[13] = 0;
		m[14] = 0;
		m[15] = 1;
		
		return m;
		
	}
	
	public double getAngle() {
		return angle;
	}

	public double getRotationX() {
		return rotationX;
	}

	public double getRotationY() {
		return rotationY;
	}

	public double getRotationZ() {
		return rotationZ;
	}
		
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}
	
}
