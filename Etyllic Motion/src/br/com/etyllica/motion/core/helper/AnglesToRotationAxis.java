package br.com.etyllica.motion.core.helper;

import br.com.etyllica.motion.core.linear.Vec3;

public class AnglesToRotationAxis extends RotationAxis {

	//Angles to RotationAxis
	//transforms independent angles to rotation
	//Usage: glRotatef(angle, axisX, axisY, axisZ);	
	public AnglesToRotationAxis(double angleX, double angleY, double angleZ){
		super();
		
		Vec3 vector = new Vec3(angleX, angleY, angleZ);
		
		this.angle = Math.toDegrees(vector.length());
		
		vector.normalize();
		
		this.axisX = vector.v[0];
		
		this.axisY = vector.v[1];
		
		this.axisZ = vector.v[2];
				
	}
	
}
