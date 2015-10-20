package br.com.etyllica.motion.filter.search.flood;


public class ExpandableFloodFillSearch extends FloodFillSearch {
	
	public ExpandableFloodFillSearch(int w, int h) {
		super(w, h);
	}

	public ExpandableFloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, minNeighbors);
	}

	public ExpandableFloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
		super(w, h, minNeighbors, maxNeighbors);
	}

	@Override
	public boolean inBoundary(int px, int py) {
		return px > step || px <= getW() || py > step || py <= getH();
	}
	
}
