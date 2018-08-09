package com.harium.keel.modifier.ogr;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.feature.ogr.LineInterval;
import com.harium.keel.feature.ogr.OGR;
import com.harium.keel.modifier.ogr.model.OGREdge;
import com.harium.keel.modifier.ogr.model.OGRNodeData;
import com.harium.storage.graph.Graph;
import com.harium.storage.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class RectangularOGR implements OGR<OGRNodeData> {

	@Override
	public Graph<OGRNodeData> findGraph(boolean[][] mask) {
		
		if(mask == null || mask.length == 0) {
			return null;
		}
		
		int h = mask.length;
		int w = mask[0].length;

		final int sizeLimiar = w/3;

		Graph<OGRNodeData> graph = new Graph<>();
				
		List<LineInterval> intervals = new ArrayList<>();

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

	private int processFirstInterval(Graph<OGRNodeData> graph, List<LineInterval> intervals, int sizeLimiar) {

		LineInterval firstInterval = intervals.get(0);

		int intervalCount = 0;

		int height = firstInterval.getHeight();

		if(firstInterval.getLength() < sizeLimiar) {

			Point2D firstPoint = new Point2D(firstInterval.getCenter(), height);
			graph.addNode(OGRNodeData.buildNode(firstPoint));
			intervalCount = 1;

		} else {

			Point2D leftPoint = new Point2D(firstInterval.getStart(), height);

			Node<OGRNodeData> firstNode = graph.addNode(OGRNodeData.buildNode(leftPoint));
			
			Point2D rightPoint = new Point2D(firstInterval.getEnd(), height);

			Node<OGRNodeData> secondNode = graph.addNode(OGRNodeData.buildNode(rightPoint));

			graph.addEdge(new OGREdge(firstNode, secondNode));

			intervalCount = 2;

		}

		return intervalCount;

	}
	
	private int processOpenGraph(Graph<OGRNodeData> graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Start of First Interval
		Point2D firstPoint = new Point2D(firstInterval.getStart(), firstInterval.getHeight());
		
		//Put the firstPoint as firstNode 
		Node<OGRNodeData> firstNode = graph.addNode(0, OGRNodeData.buildNode(firstPoint));
		
		LineInterval secondInterval = intervals.get(1);
		
		//End of Second Interval
		Point2D secondPoint = new Point2D(secondInterval.getEnd(), secondInterval.getHeight());
		
		Node<OGRNodeData> secondNode = graph.addNode(OGRNodeData.buildNode(secondPoint));
		
		//Root is now the secondPoint
		Node<OGRNodeData> root = graph.getNodes().get(1);
		
		graph.addEdge(new OGREdge(root, firstNode));
		
		graph.addEdge(new OGREdge(root, secondNode));
		
		return intervals.size();
		
	}
	
	private int processExpandGraph(Graph<OGRNodeData> graph, List<LineInterval> intervals) {
		
		LineInterval firstInterval = intervals.get(0);
		
		//Expand leftNode
		int x = firstInterval.getStart();
		
		Node<OGRNodeData> firstNode = graph.getNodes().get(0);
		
		if(x <= firstNode.getData().getX()) {
			firstNode.getData().setLocation(x, firstInterval.getHeight());
		}
		
		//Expand rightNode
		LineInterval secondInterval = intervals.get(1);
		
		x = secondInterval.getEnd();
		
		Node<OGRNodeData> secondNode = graph.getNodes().get(2);
		
		if(x > secondNode.getData().getX()) {
						
			secondNode.getData().setLocation(x, secondInterval.getHeight());
		}
		
		return intervals.size();
		
	}
	
	private int processCloseCornerGraph(Graph<OGRNodeData> graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
				
		Node<OGRNodeData> lastNode = graph.addNode(OGRNodeData.buildNode(new Point2D(interval.getCenter(), interval.getHeight())));
		
		Node<OGRNodeData> leftNode = graph.getNodes().get(0);
		Node<OGRNodeData> rightNode = graph.getNodes().get(2);
		
		//Make a circular graph
		graph.addEdge(new OGREdge(rightNode, lastNode));
		graph.addEdge(new OGREdge(lastNode, leftNode));
		
		return intervals.size();
	}
	
	private int processCloseQuadGraph(Graph<OGRNodeData> graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
		
		Node<OGRNodeData> lastRightNode = graph.addNode(OGRNodeData.buildNode(new Point2D(interval.getEnd(), interval.getHeight())));
		Node<OGRNodeData> lastLeftNode = graph.addNode(OGRNodeData.buildNode(new Point2D(interval.getStart(), interval.getHeight())));
		
		Node<OGRNodeData> leftNode = graph.getNodes().get(1);
		Node<OGRNodeData> rightNode = graph.getNodes().get(2);
		
		// Make a circular graph
		graph.addEdge(new OGREdge(rightNode, lastRightNode));
		graph.addEdge(new OGREdge(lastRightNode, lastLeftNode));
		graph.addEdge(new OGREdge(lastLeftNode, leftNode));
				
		return intervals.size();
	}
	
	private int processExpandClosedCornerGraph(Graph<OGRNodeData> graph, List<LineInterval> intervals) {
		
		LineInterval interval = intervals.get(0);
				
		Node<OGRNodeData> lastNode = graph.getNodes().get(3);
		
		Point2D center = getCenter(graph);
				
		if(lastNode.getData().getX()>center.getX()) {
			
			lastNode.getData().setLocation(interval.getStart(), interval.getHeight());
			
		} else {
			lastNode.getData().setLocation(interval.getEnd(), interval.getHeight());
		}
				
		return intervals.size();		
	}
	
	private Point2D getCenter(Graph<OGRNodeData> graph) {
		
		int px = 0;
		int py = 0;
		int pn = graph.getNodes().size();
		
		for(Node<OGRNodeData> node : graph.getNodes()) {
			px += node.getData().getX();
			py += node.getData().getY();
		}
		
		return new Point2D(px/pn, py/pn);
		
	}

	private boolean isValid(boolean value) {
		return value;
	}


}
