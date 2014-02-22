package br.com.etyllica.motion.filter.modifier;

import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.posit.CoplanarPosit;
import br.com.etyllica.motion.posit.Pose;


public class PositCoplanarModifier implements ComponentModifierStrategy {

	private double error;
	
	private double[] translation;
	
	private double[][] rotation;
	
	private CoplanarPosit posit;
	
	public PositCoplanarModifier() {
		super();
		
		posit = new CoplanarPosit(1, 760);
	}

	@Override
	public Component modifyComponent(Component component) {
		
		Pose pose = posit.pose(component.getPoints());
		
		this.error = pose.getBestError();
		
		this.translation = pose.getBestTranslation();
		
		this.rotation = pose.getBestRotation();

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

	public double[][] getRotation() {
		return rotation;
	}

	public void setRotation(double[][] rotation) {
		this.rotation = rotation;
	}
}