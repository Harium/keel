package com.harium.keel.feature.ogr;


import com.harium.etyl.linear.graph.Graph;

public interface OGR<T> {

	Graph<T> findGraph(boolean[][] mask);
	
}
