package br.com.etyllica.motion.ogr;

import java.util.ArrayList;
import java.util.List;

import examples.etyllica.graph.model.IntegerEdge;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;

public class RectangularOGR implements OGR {

	@Override
	public Graph findGraph(boolean[][] mask) {
		
		if(mask == null || mask.length == 0) {
			return null;
		}
		
		int h = mask.length;
		int w = mask[0].length;

		final int sizeLimiar = w/3;

		Graph graph = new Graph();
				
		List<LineInterval> intervals = new ArrayList<LineInterval>();

		int lastIntervalCount = 0;
		
		int startInterval = 0;
		
		boolean foundInterval = false;
		
		boolean foundFirstInterval = false;
		
		boolean isQuad = false;
		
		boolean isClosed = false;

		for (int j = 0; j < h; j++) {

			intervals.clear();
			
			startInterval = 0;

			foundInterval = false;
						
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
	
				if(lastIntervalCount == 2) {
					
					lastIntervalCount = processCloseCornerGraph(graph, intervals);
					
					isClosed = true;
					
				} else if(isClosed) {
					
					processExpandClosedCornerGraph(graph, intervals);
				}
			}			
		}
				
		if(isQuad && intervals.size() == 1) {
			
			processCloseQuadGraph(graph, intervals);
			isClosed = true;
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

			graph.addEdge(new IntegerEdge(firstNode, secondNode));

			intervalCount = 2;

		}

		return intervalCount;

	}
	
	private int processOpenGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Start of First Interval
		Point2D firstPoint = new Point2D(firstInterval.getStart(), firstInterval.getHeight());
		
		//Put the firstPoint as firstNode 
		Node firstNode = graph.addNode(0, firstPoint);
		
		LineInterval secondInterval = intervals.get(1);
		
		//End of Second Interval
		Point2D secondPoint = new Point2D(secondInterval.getEnd(), secondInterval.getHeight());
		
		Node secondNode = graph.addNode(secondPoint);
		
		//Root is now the secondPoint
		Node root = graph.getNodes().get(1);
		
		graph.addEdge(new IntegerEdge(root, firstNode));
		
		graph.addEdge(new IntegerEdge(root, secondNode));
		
		return intervals.size();
		
	}
	
	private int processExpandGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Expand leftNode
		int x = firstInterval.getStart();
		
		Node firstNode = graph.getNodes().get(0);
		
		if(x <= firstNode.getPoint().getX()) {
			firstNode.setLocation(x, firstInterval.getHeight());
		}
		
		//Expand rightNode
		LineInterval secondInterval = intervals.get(1);
		
		x = secondInterval.getEnd();
		
		Node secondNode = graph.getNodes().get(2);
		
		if(x > secondNode.getPoint().getX()) {
						
			secondNode.setLocation(x, secondInterval.getHeight());
		}
		
		return intervals.size();
		
	}
	
	private int processCloseCornerGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
				
		Node lastNode = graph.addNode(new Point2D(interval.getCenter(), interval.getHeight()));
		
		Node leftNode = graph.getNodes().get(0);
		Node rightNode = graph.getNodes().get(2);
		
		//Make a circular graph
		graph.addEdge(new IntegerEdge(rightNode, lastNode));		
		graph.addEdge(new IntegerEdge(lastNode, leftNode));
		
		return intervals.size();
	}
	
	private int processCloseQuadGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
		
		Node lastRightNode = graph.addNode(new Point2D(interval.getEnd(), interval.getHeight()));
		Node lastLeftNode = graph.addNode(new Point2D(interval.getStart(), interval.getHeight()));
		
		Node leftNode = graph.getNodes().get(1);
		Node rightNode = graph.getNodes().get(2);
		
		//Make a circular graph
		graph.addEdge(new IntegerEdge(rightNode, lastRightNode));
		graph.addEdge(new IntegerEdge(lastRightNode, lastLeftNode));
		graph.addEdge(new IntegerEdge(lastLeftNode, leftNode));		
				
		return intervals.size();
	}
	
	private int processExpandClosedCornerGraph(Graph graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
				
		Node lastNode = graph.getNodes().get(3);
		
		Point2D center = getCenter(graph);
				
		if(lastNode.getPoint().getX()>center.getX()) {
			
			lastNode.setLocation(interval.getStart(), interval.getHeight());
			
		} else {
			
			lastNode.setLocation(interval.getEnd(), interval.getHeight());
		}
		
				
				
		return intervals.size();		
	}
	
	private Point2D getCenter(Graph graph) {
		
		int px = 0;
		int py = 0;
		int pn = graph.getNodes().size();
		
		for(Node node : graph.getNodes()) {
			px += node.getPoint().getX();
			py += node.getPoint().getY();
		}
		
		return new Point2D(px/pn, py/pn);
		
	}

	private boolean isValid(boolean value) {
		return value;
	}

}
