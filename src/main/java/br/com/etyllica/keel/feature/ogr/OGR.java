package br.com.etyllica.keel.feature.ogr;

import br.com.etyllica.linear.graph.Graph;

public interface OGR<T> {

	public Graph<T> findGraph(boolean[][] mask);
	
}
