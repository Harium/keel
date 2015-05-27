package br.com.etyllica.motion.features.trail;

import br.com.abby.linear.Point3D;
import br.com.etyllica.motion.model.RangeFlag;

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

	public void add(Point3D point) {
		addValue(axisX, point.getX());
		addValue(axisY, point.getY());
		addValue(axisZ, point.getZ());
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
