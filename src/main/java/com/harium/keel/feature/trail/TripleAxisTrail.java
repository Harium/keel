package com.harium.keel.feature.trail;

import com.badlogic.gdx.math.Vector3;

public class TripleAxisTrail {

	private int size = 10;
	private float deltaMin = 0.2f;
	private float tolerance = 0.1f;
	//deltaMax = 0.85 * fullSize

	private TrailAxis axisX;
	private TrailAxis axisY;
	private TrailAxis axisZ;
	
	private TripleAxisTrailListener listener;

	public TripleAxisTrail() {
		this(10);
	}

	public TripleAxisTrail(int size) {
		super();

		this.size = size;

		axisX = new TrailAxis(size);
		axisY = new TrailAxis(size);
		axisZ = new TrailAxis(size);
	}
	
	public TripleAxisTrail(int size, TripleAxisTrailListener listener) {
		this(size);
		this.listener = listener;
	}

	public void add(Vector3 point) {
		addValue(axisX, point.x);
		addValue(axisY, point.y);
		addValue(axisZ, point.z);
	}

	private void addValue(TrailAxis axis, float value) {
		axis.addValue(value);
		
		if(axis.getDeltaMod() > deltaMin) {
			evaluateTrail();
		}
	}

	private void evaluateTrail() {
		if(listener == null) {
			return;
		}
		
		listener.listenTrail(axisX.getDelta(), axisY.getDelta(), axisZ.getDelta());
	}
	
	public RangeFlag evaluateDeltaX() {
		return evaluateDelta(axisX.getDelta());
	}
	
	public RangeFlag evaluateDeltaY() {
		return evaluateDelta(axisY.getDelta());
	}
	
	public RangeFlag evaluateDeltaZ() {
		return evaluateDelta(axisZ.getDelta());
	}
	
	private RangeFlag evaluateDelta(double delta) {
		
		RangeFlag flag = RangeFlag.NEUTRAL;
		
		if(delta > deltaMin) {
			flag = RangeFlag.POSITIVE;
		} else if(delta < -deltaMin) {
			flag = RangeFlag.NEGATIVE;
		}
		
		return flag;
	}

	public TrailAxis getAxisX() {
		return axisX;
	}

	public TrailAxis getAxisY() {
		return axisY;
	}

	public TrailAxis getAxisZ() {
		return axisZ;
	}

	public Vector3 getPoint(int index) {
		float x = axisX.getValues().get(index);
		float y = axisY.getValues().get(index);
		float z = axisZ.getValues().get(index);

		return new Vector3(x, y, z);
	}

	public int getSize() {
		return size;
	}

	public TripleAxisTrailListener getListener() {
		return listener;
	}

	public void setListener(TripleAxisTrailListener listener) {
		this.listener = listener;
	}

	public float getDeltaMin() {
		return deltaMin;
	}

	public void setDeltaMin(float deltaMin) {
		this.deltaMin = deltaMin;
	}	

}
