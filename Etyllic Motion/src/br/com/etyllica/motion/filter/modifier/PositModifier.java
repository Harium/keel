package br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import org.opencv.OpenCv;
import org.opencv.criteria.CvTermCriteria;
import org.opencv.modules.calib3d.Posit;

import br.com.etyllica.linear.Point3D;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;

public class PositModifier implements ComponentModifierStrategy {

	private double angle;
	
	private double axisX;
	
	private double axisY;
	
	private double axisZ;
	
	private CvTermCriteria criteria;
	
	private double focalLength;
	
	public PositModifier(double w, double h) {
		super();
		
		this.focalLength = w;
						
		criteria = new CvTermCriteria(30);
		
	}
	
	@Override
	public Component modifyComponent(Component component) {
		
		List<Point3D> objPoints = new ArrayList<Point3D>();
		
		//A Point equivalent
		objPoints.add(new Point3D(-0.5, 0, -0.5));
		
		//B Point equivalent
		objPoints.add(new Point3D(+0.5, 0, -0.5));
		
		//C Point equivalent
		objPoints.add(new Point3D(-0.5, 0, +0.5));
		
		//D Point equivalent
		objPoints.add(new Point3D(+0.5, 0, +0.5));
			
		Posit posit = new Posit();		
		
		posit.icvPOSIT(objPoints, component.getPoints(), focalLength, criteria);

		computeRotationValues(posit.getRotation());

		return component;
		
	}
	
	public void computeRotationValues(double[] rotation){
		
		this.angle = Math.toDegrees(Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2));
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2+3*1] - rotation[1+3*2])+OpenCv.cvSqr(rotation[0+3*2] - rotation[2+3*0])+OpenCv.cvSqr(rotation[1+3*0] - rotation[0+3*1]));
		
		this.axisX = (rotation[2+3*1] - rotation[1+3*2])/norm;

		this.axisY = (rotation[0+3*2] - rotation[2+3*0])/norm;

		this.axisZ = (rotation[1+3*0] - rotation[0+3*1])/norm;
		
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
