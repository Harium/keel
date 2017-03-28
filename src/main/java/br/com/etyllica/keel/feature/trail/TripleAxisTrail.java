package br.com.etyllica.keel.feature.trail;

import com.badlogic.gdx.math.Vector3;

import br.com.etyllica.core.linear.Point3D;

public class TripleAxisTrail {

	private int size = 10;
	private double deltaMin = 0.2;
	private double tolerance = 0.1;
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

	private void addValue(TrailAxis axis, double value) {
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

	public Point3D getPoint(int index) {
		double x = axisX.getValues().get(index);
		double y = axisY.getValues().get(index);
		double z = axisZ.getValues().get(index);

		return new Point3D(x, y, z);
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

	public double getDeltaMin() {
		return deltaMin;
	}

	public void setDeltaMin(double deltaMin) {
		this.deltaMin = deltaMin;
	}	

}
