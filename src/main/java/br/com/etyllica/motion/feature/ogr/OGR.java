package br.com.etyllica.motion.feature.ogr;

import br.com.etyllica.linear.graph.Graph;

public interface OGR<T> {

	public Graph<T> findGraph(boolean[][] mask);
	
}
