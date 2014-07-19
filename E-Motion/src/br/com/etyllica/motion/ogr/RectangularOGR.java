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
		
		boolean isQuad = false;

		for (int j = 0; j < h; j++) {

			intervals.clear();
			
			int startInterval = 0;

			boolean foundInterval = false;
						
			for (int i=0; i < w; i++) {

				if(isValid(mask[j][i])) {

					//Start Interval
					if(!foundInterval) {

						foundInterval = true;

						startInterval = i;
					}
					
					if(i == w-1) {
						//Close interval
						intervals.add(new LineInterval(startInterval, i-startInterval+1, j));

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
				
				isQuad = lastIntervalCount == 2;
				
			} else if(intervals.size() == 2) {
				
				if(lastIntervalCount == 1) {
					
					lastIntervalCount = processOpenGraph(graph, intervals);
					
				} else if(lastIntervalCount == 2 && !isQuad) {
					
					lastIntervalCount = processExpandGraph(graph, intervals);										
				}

			} else if(intervals.size() == 1 && !isQuad) {
				
				processCloseCornerGraph(graph, intervals);
			}
			
		}
		
		if(isQuad) {
			
			processCloseQuadGraph(graph, intervals);
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

			Point2D leftPoint = new Point2D(firstInterval.getStart(), height);

			Node firstNode = graph.addNode(leftPoint);
			
			Point2D rightPoint = new Point2D(firstInterval.getEnd(), height);

			Node secondNode = graph.addNode(rightPoint);

			graph.addEdge(new Edge(firstNode, secondNode));

			intervalCount = 2;

		}

		return intervalCount;

	}
	
	private int processOpenGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Start of First Interval
		Point2D firstPoint = new Point2D(firstInterval.getStart(), firstInterval.getHeight());
		
		Node firstNode = graph.addNode(firstPoint);
		
		LineInterval secondInterval = intervals.get(1);
		
		//End of Second Interval
		Point2D secondPoint = new Point2D(secondInterval.getEnd(), secondInterval.getHeight());
		
		Node secondNode = graph.addNode(secondPoint);
		
		Node root = graph.getNodes().get(0);
		
		graph.addEdge(new Edge(root, firstNode));
		
		graph.addEdge(new Edge(root, secondNode));
		
		return intervals.size();
		
	}
	
	private int processExpandGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Expand leftNode
		int x = firstInterval.getStart();
		
		Node firstNode = graph.getNodes().get(1);
		
		if(x <= firstNode.getX()) {
			firstNode.setLocation(x, firstInterval.getHeight());
		}
		
		//Expand rightNode
		LineInterval secondInterval = intervals.get(1);
		
		x = secondInterval.getEnd();
		
		Node secondNode = graph.getNodes().get(2);
		
		if(x >= secondNode.getX()) {
						
			secondNode.setLocation(x, secondInterval.getHeight());
		}
		
		return intervals.size();
		
	}
	
	private int processCloseCornerGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
		
		Node lastNode = graph.addNode(new Point2D(interval.getCenter(), interval.getHeight()));
		
		Node leftNode = graph.getNodes().get(1);
		Node rightNode = graph.getNodes().get(2);
		
		//Make a circular graph
		graph.addEdge(new Edge(rightNode, lastNode));		
		graph.addEdge(new Edge(lastNode, leftNode));
		
		return intervals.size();
	}
	
	private int processCloseQuadGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
		
		Node lastRightNode = graph.addNode(new Point2D(interval.getEnd(), interval.getHeight()));
		Node lastLeftNode = graph.addNode(new Point2D(interval.getStart(), interval.getHeight()));
		
		Node leftNode = graph.getNodes().get(1);
		Node rightNode = graph.getNodes().get(2);
		
		//Make a circular graph
		graph.addEdge(new Edge(rightNode, lastRightNode));
		graph.addEdge(new Edge(lastRightNode, lastLeftNode));
		graph.addEdge(new Edge(lastLeftNode, leftNode));		
				
		return intervals.size();
	}

	private boolean isValid(boolean value) {
		return value;
	}

}
