package com.harium.keel.feature.ogr;

public class LineInterval {

	private int height = 0;
	
	private int start = 0;
	
	private int length = 0;
		
	public LineInterval(int start, int length, int height) {
		super();
		
		this.start = start;
		this.length = length;
		this.height = height;
	}

	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return start+length-1;
	}

	public int getLength() {
		return length;
	}
	
	public int getCenter() {
		return start+length/2;
	}

	public int getHeight() {
		return height;
	}
	
	public int expand() {
		return length++;
	}
	
	public boolean intersect(LineInterval interval) {
		if(height-interval.height > 1 || height-interval.height < -1) {
			return false;
		}
		
		return (start >= interval.start && start < interval.start+interval.length)||(interval.start >= start && interval.start < start+length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LineInterval that = (LineInterval) o;

		if (height != that.height) return false;
		if (start != that.start) return false;
		return length == that.length;
	}

	@Override
	public int hashCode() {
		int result = height;
		result = 31 * result + start;
		result = 31 * result + length;
		return result;
	}
}
