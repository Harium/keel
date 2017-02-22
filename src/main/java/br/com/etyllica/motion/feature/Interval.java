package br.com.etyllica.motion.feature;

public class Interval {

	private int start;
	private int end;
	
	public Interval(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	public int length() {
		return end - start;
	}
	
}
