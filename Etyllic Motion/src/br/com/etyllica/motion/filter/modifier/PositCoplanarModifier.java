package br.com.etyllica.motion.filter.modifier;

import br.com.etyllica.motion.core.helper.RotationAxis;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.posit.CoplanarPosit;
import br.com.etyllica.motion.posit.Pose;


public class PositCoplanarModifier extends RotationAxis implements ComponentModifierStrategy {

	private double error;
	
	private double[] translation;
		
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