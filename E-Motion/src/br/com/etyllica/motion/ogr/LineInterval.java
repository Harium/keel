package br.com.etyllica.motion.ogr;

public class LineInterval {

	private int start = 0;
	
	private int length = 0;
	
	public LineInterval(int start, int length) {
		super();
		
		this.start = start;
		
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}
	
	public int getCenter() {
		return start+length/2;
	}
	
}
