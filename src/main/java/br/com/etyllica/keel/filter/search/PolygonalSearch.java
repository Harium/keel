package br.com.etyllica.keel.filter.search;

import java.awt.Polygon;

import br.com.etyllica.keel.core.BooleanMaskSearch;

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
