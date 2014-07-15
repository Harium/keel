package br.com.etyllica.motion.core.helper;

import br.com.etyllica.linear.vector.Vec3D;


public class AnglesToRotationAxis extends RotationAxis {

	//Angles to RotationAxis
	//transforms independent angles in rotation axis
	public AnglesToRotationAxis(double angleX, double angleY, double angleZ){
		super();
		
		Vec3D vector = new Vec3D(angleX, angleY, angleZ);
		
		this.angle = Math.toDegrees(vector.length());
		
		vector.normalize();
		
		this.rotationX = vector.v[0];
		
		this.rotationY = vector.v[1];
		
		this.rotationZ = vector.v[2];
		
		//Usage: glRotatef(angle, axisX, axisY, axisZ);
				
	}
	
}
