package br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.helper.RotationAxis;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.posit.CoplanarPosit;
import br.com.etyllica.motion.posit.Pose;


public class PositCoplanarModifier extends RotationAxis implements ComponentModifierStrategy {

	private double error;
	
	private double[] translation;
		
	private CoplanarPosit posit;
	
	private List<Point2D> imagePoints;
	
	private int w, h;
	
	public PositCoplanarModifier(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
		
		double focalLength = w;
		
		posit = new CoplanarPosit(1, focalLength);
		
		imagePoints = new ArrayList<Point2D>();
	}

	@Override
	public Component modifyComponent(Component component) {
				
		List<Point2D> points = component.getPoints();
		
		imagePoints.clear();
		
		Point2D a = points.get(0);
		imagePoints.add(new Point2D(a.getX()-w/2, h/2-a.getY()));
		
		Point2D b = points.get(1);
		imagePoints.add(new Point2D(b.getX()-w/2, h/2-b.getY()));
		
		Point2D d = points.get(3);
		imagePoints.add(new Point2D(d.getX()-w/2, h/2-d.getY()));
		
		Point2D c = points.get(2);
		imagePoints.add(new Point2D(c.getX()-w/2, h/2-c.getY()));
		
		Pose pose = posit.pose(imagePoints);
		
		this.error = pose.getBestError();
		
		this.translation = pose.getBestTranslation();
		
		this.computeRotationValues(pose.getBestRotation());

		return component;
		
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double[] getTranslation() {
		return translation;
	}

	public void setTranslation(double[] translation) {
		this.translation = translation;
	}

}