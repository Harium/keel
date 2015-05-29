package br.com.etyllica.motion.modifier;

import java.util.ArrayList;
import java.util.List;

import org.opencv.criteria.CvTermCriteria;
import org.opencv.modules.calib3d.Posit;

import br.com.etyllica.linear.Point3D;
import br.com.etyllica.motion.core.helper.RotationAxis;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.feature.Component;

public class PositModifier extends RotationAxis implements ComponentModifierStrategy {
	
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
			
}
