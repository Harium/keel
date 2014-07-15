package br.com.etyllica.motion.core.helper;

import org.opencv.OpenCv;

import br.com.etyllica.motion.modifier.posit.Pose;

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
		
		this.y = translation[1];
		
		this.z = translation[2];
		
	}
	
	protected void computeRotationValues(double[] rotation){
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2+3*1] - rotation[1+3*2])+OpenCv.cvSqr(rotation[0+3*2] - rotation[2+3*0])+OpenCv.cvSqr(rotation[1+3*0] - rotation[0+3*1]));
		
		this.rotationX = (rotation[2+3*1] - rotation[1+3*2])/norm;
		this.rotationX = -this.rotationX;

		//Using Y as Normal
		this.rotationY = (rotation[1+3*0] - rotation[0+3*1])/norm;
		
		this.rotationZ = (rotation[2+3*0] - rotation[0+3*2])/norm;
		this.rotationZ = -this.rotationZ;
				
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
