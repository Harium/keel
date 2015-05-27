package br.com.etyllica.motion.features.ogr;

import br.com.etyllica.linear.graph.Graph;

public interface OGR<T> {

	public Graph<T> findGraph(boolean[][] mask);
	
}
