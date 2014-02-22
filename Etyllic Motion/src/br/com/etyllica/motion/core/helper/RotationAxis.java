package br.com.etyllica.motion.core.helper;

import org.opencv.OpenCv;

public class RotationAxis {

	protected double angle = 0;
	
	protected double axisX = 0;
	
	protected double axisY = 0;
	
	protected double axisZ = 0;
	
	public RotationAxis(){
		super();
	}
	
	protected void computeRotationValues(double[] rotation){
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2+3*1] - rotation[1+3*2])+OpenCv.cvSqr(rotation[0+3*2] - rotation[2+3*0])+OpenCv.cvSqr(rotation[1+3*0] - rotation[0+3*1]));
		
		this.axisX = (rotation[2+3*1] - rotation[1+3*2])/norm;

		this.axisY = (rotation[0+3*2] - rotation[2+3*0])/norm;

		this.axisZ = (rotation[1+3*0] - rotation[0+3*1])/norm;
		
	}
	
	public void computeRotationValues(double[][] rotation){
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0][0] + rotation[1][1] + rotation[2][2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2][1] - rotation[1][2])+OpenCv.cvSqr(rotation[0][2] - rotation[2][0])+OpenCv.cvSqr(rotation[1][0] - rotation[0][1]));
		
		this.axisX = (rotation[2][1] - rotation[1][2])/norm;

		this.axisY = (rotation[0][2] - rotation[2][0])/norm;

		this.axisZ = (rotation[1][0] - rotation[0][1])/norm;
		
	}
	
	public double getAngle() {
		return angle;
	}

	public double getAxisX() {
		return axisX;
	}

	public double getAxisY() {
		return axisY;
	}

	public double getAxisZ() {
		return axisZ;
	}
	
}
