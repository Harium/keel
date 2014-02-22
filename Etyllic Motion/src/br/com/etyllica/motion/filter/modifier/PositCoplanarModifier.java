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
	
	public PositCoplanarModifier() {
		super();
		
		posit = new CoplanarPosit(1, 760);
		
		imagePoints = new ArrayList<Point2D>();
	}

	@Override
	public Component modifyComponent(Component component) {
				
		List<Point2D> points = component.getPoints();
		
		imagePoints.clear();
		
		imagePoints.add(points.get(0));
		imagePoints.add(points.get(1));
		imagePoints.add(points.get(3));
		imagePoints.add(points.get(2));
		
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