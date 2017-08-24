package com.harium.keel.modifier;

import java.util.ArrayList;
import java.util.List;

import com.harium.keel.core.ComponentModifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Component;
import com.harium.keel.helper.RotationAxis;
import com.harium.keel.modifier.posit.CoplanarPosit;
import com.harium.keel.modifier.posit.Pose;
import com.harium.etyl.linear.Point2D;

public class PositCoplanarModifier implements ComponentModifierStrategy, ComponentModifier<Component, RotationAxis> {

	private int w, h;
	
	private RotationAxis axis;
	
	private CoplanarPosit posit;
	
	private List<Point2D> imagePoints;
		
	public PositCoplanarModifier(int w, int h) {
		this(w,h,w/2);
	}
	
	public PositCoplanarModifier(int w, int h, double focalLength) {
		super();
		
		this.w = w;
		this.h = h;
		
		double rectangleSize = 1;
		
		posit = new CoplanarPosit(rectangleSize, focalLength);
		
		imagePoints = new ArrayList<Point2D>();
		
		for(int i=0;i<4;i++) {
			imagePoints.add(new Point2D(0,0));
		}
	}

	@Override
	public Component modifyComponent(Component component) {
				
		axis = modify(component);
		
		return component;
		
	}
	
	public RotationAxis modify(Component component) {
		
		List<Point2D> points = component.getPoints();
		ajustPoints(points);
		
		Pose pose = posit.pose(imagePoints);
		
		RotationAxis axis = new RotationAxis(pose);
		
		return axis;
				
	}
	
	private void ajustPoints(List<Point2D> points) {
				
		Point2D a = points.get(0);
		imagePoints.get(0).setLocation(a.getX()-w/2, h/2-a.getY());
		
		Point2D b = points.get(1);
		imagePoints.get(1).setLocation(b.getX()-w/2, h/2-b.getY());
				
		Point2D c = points.get(2);
		imagePoints.get(2).setLocation(c.getX()-w/2, h/2-c.getY());
		
		Point2D d = points.get(3);
		imagePoints.get(3).setLocation(d.getX()-w/2, h/2-d.getY());
				
	}

	public RotationAxis getAxis() {
		return axis;
	}

	public void setAxis(RotationAxis axis) {
		this.axis = axis;
	}
		
}