package br.com.etyllica.motion.ogr;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Edge;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;

public class RectangularOGR implements OGR {

	@Override
	public Graph findGraph(boolean[][] mask) {
		
		int h = mask.length;
		int w = mask[0].length;

		final int sizeLimiar = w/3;

		Graph graph = new Graph();
		
		List<LineInterval> intervals = new ArrayList<LineInterval>();

		int lastIntervalCount = 0;
		
		boolean foundFirstInterval = false;

		for (int j = 0; j < h; j++) {

			int startInterval = 0;

			boolean foundInterval = false;
			
			for (int i=0; i < w; i++) {

				if(isValid(mask[j][i])) {

					if(!foundInterval) {

						foundInterval = true;

						startInterval = i;
					}
					
					if(i == w-1) {
						//Close interval
						intervals.add(new LineInterval(startInterval, i-startInterval, j));

						foundInterval = false;						
					}

				} else if(foundInterval) {

					//Close interval					
					intervals.add(new LineInterval(startInterval, i-startInterval, j));

					foundInterval = false;
				}

			}
			
			if(!foundFirstInterval && intervals.size() == 1) {

				foundFirstInterval = true;

				lastIntervalCount = processFirstInterval(graph, intervals, sizeLimiar);

			} else if(intervals.size() == 2) {
				
				if(lastIntervalCount == 1) {
					
					lastIntervalCount = processOpenGraph(graph, intervals);
				}
				
				if(lastIntervalCount == 2) {
					//expand
				}
									
			}
			
			intervals.clear();
			
		}

		return graph;
	}

	private int processFirstInterval(Graph graph, List<LineInterval> intervals, int sizeLimiar) {

		LineInterval firstInterval = intervals.get(0);

		int intervalCount = 0;

		int height = firstInterval.getHeight();

		if(firstInterval.getLength() < sizeLimiar) {

			Point2D firstPoint = new Point2D(firstInterval.getCenter(), height);

			graph.addNode(firstPoint);

			intervalCount = 1;

		} else {

			Point2D firstPoint = new Point2D(firstInterval.getStart(), height);

			Node firstNode = graph.addNode(firstPoint);
			
			Point2D lastPoint = new Point2D(firstInterval.getStart()+firstInterval.getLength(), height);

			Node secondNode = graph.addNode(lastPoint);

			graph.addEdge(new Edge(firstNode, secondNode));

			intervalCount = 2;

		}

		return intervalCount;

	}
	
	private int processOpenGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		Point2D firstPoint = new Point2D(firstInterval.getCenter(), firstInterval.getHeight());
		
		Node firstNode = graph.addNode(firstPoint);
		
		LineInterval secondInterval = intervals.get(1);
		
		Point2D secondPoint = new Point2D(secondInterval.getCenter(), secondInterval.getHeight());
		
		Node secondNode = graph.addNode(secondPoint);
		
		Node root = graph.getNodes().get(0);
		
		graph.addEdge(new Edge(root, firstNode));
		
		graph.addEdge(new Edge(root, secondNode));
		
		return intervals.size();
		
	}

	private boolean isValid(boolean value) {
		return value;
	}

}
