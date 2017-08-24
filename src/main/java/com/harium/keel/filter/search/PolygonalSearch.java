package com.harium.keel.filter.search;

import com.harium.etyl.linear.Polygon;
import com.harium.keel.core.BooleanMaskSearch;

public abstract class PolygonalSearch extends BooleanMaskSearch {

	protected Polygon polygon;
	
	public PolygonalSearch(int w, int h) {
		super(w, h);
		
		polygon = new Polygon();		
	}
	
	@Override
	public void setup(int w, int h) {
		super.setup(w, h);
		
		polygon.reset();
	}

	public Polygon getPolygon() {
		return polygon;
	}
	
}
