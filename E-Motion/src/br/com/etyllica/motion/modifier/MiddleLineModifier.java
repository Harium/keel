package br.com.etyllica.motion.modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Edge;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.motion.filter.dynamic.Direction;
import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.helper.PointListHelper;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;


/**
 * MiddleLine modifier
 */


public class MiddleLineModifier implements ComponentModifierStrategy, ComponentModifier<Graph> {

	public MiddleLineModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {

		Graph g = modify(component);

		Component polygon = new Component(0, 0);

		for(Point2D ponto: g.getNodes()) {
			polygon.add(ponto);
		}

		return polygon;

	}

	public Graph modify(Component component) {

		List<Point2D> points = component.getPoints();
		
		List<Point2D> list = PointListHelper.cloneList(points);

		Graph graph = new Graph();
		
		if (points.size() < 3) return graph;

		List<Point2D> mediumNodes = new ArrayList<Point2D>();
		
		List<Direction> directions = processDirections(list);

		for (int i=0; i < list.size(); i++) {
			
			List<Direction> subList = directions.subList(0, 3);
			
			if(isRegion(subList)) {

				addNodes(list, mediumNodes, graph);
				
				rotateLists(3, directions, list);
				
				i+=2;
				
				continue;
				
			}
			
			rotateLists(1, directions, list);			

		}

		addVisibleComponents(graph, mediumNodes, list);

		return graph;

	}
	
	private void rotateLists(int times, List<?> ... lists) {
		
		for(int i = 0; i < times; i++) {
		
			for(List<?> list : lists) {
			
				Collections.rotate(list, -1);
				
			}
			
		}
		
	}
	
	private void addNodes(List<Point2D> list, List<Point2D> mediumNodes, Graph graph) {
		
		Node a = new Node(list.get(0));
		Node b = new Node(list.get(1));
		Node c = new Node(list.get(2));
		Node d = new Node(list.get(3));

		graph.addNode(a);
		graph.addNode(b);
		graph.addNode(c);
		graph.addNode(d);

		graph.addEdge(new Edge(a, b));
		graph.addEdge(new Edge(b, c));
		graph.addEdge(new Edge(c, d));
		graph.addEdge(new Edge(d, a));

		double nodeSumX = a.getX() + b.getX() + c.getX() + d.getX();
		
		double nodeSumY = a.getY() + b.getY() + c.getY() + d.getY();
		
		Node mediumNode = new Node((nodeSumX)/4, (nodeSumY)/4);

		graph.addNode(mediumNode);

		mediumNodes.add(mediumNode);
		
	}
	
	private List<Direction> processDirections(List<Point2D> list) {
		
		List<Direction> directions = new ArrayList<Direction>();

		for (int i=1; i < list.size(); i++) {

			Point2D fa = list.get(i-1);
			Point2D fb = list.get(i);

			directions.add(classify(fa,fb));
			
		}
		
		return directions;
	}

	private void addVisibleComponents(Graph graph, List<Point2D> mediumNodes, List<Point2D> list) {

		Node centroid = new Node(getCentroid(list));

		graph.addNode(centroid);

		for(Point2D node: mediumNodes) {
			graph.addEdge(new Edge(new Node(node), centroid));	
		}

	}

	private Point2D getCentroid(List<Point2D> points) {

		int px = 0;
		int py = 0;

		int n = points.size();

		for(Point2D point: points) {
			px += point.getX();
			py += point.getY();
		}

		return new Point2D(px/n, py/n);

	}

	public static Direction classify(Point2D a, Point2D b) {

		double dx = b.getX()-a.getX();

		double dy = b.getY()-a.getY();

		double tolerance = 3;

		//Right
		if(dx > 0) {

			//Down
			if(dy > 0) {

				if(dx-dy < tolerance) {
					return Direction.DOWN_RIGHT;
				}

				//Right is Higher
				if(dx > dy) {
					return Direction.RIGHT;
					//Down is Higher
				} else {
					return Direction.DOWN;
				}

				//Up
			} else {

				//Flip Coordinates Vertically
				dy = -dy;

				if(dx-dy < tolerance) {
					return Direction.UP_RIGHT;					
				}

				//Right is Higher
				if(dx > dy) {
					return Direction.RIGHT;
					//Up is Higher
				} else {
					return Direction.UP;
				}

			}

			//Left
		} else {

			//Flip Coordinates Horizontally 
			dx = -dx;

			//Down
			if(dy > 0) {

				if(dx-dy < tolerance) {
					return Direction.DOWN_LEFT;					
				}

				//Left is Higher
				if(dx > dy) {
					return Direction.LEFT;
					//Down is Higher
				} else {
					return Direction.DOWN;
				}

				//Up
			} else {

				dy = -dy;

				if(dx-dy < tolerance) {
					return Direction.UP_LEFT;
				}

				//Left is Higher
				if(dx > dy) {
					return Direction.LEFT;
					//Up is Higher
				} else {
					return Direction.UP;
				}

			}

		}

	}

	private boolean isRegion(List<Direction> list) {

		if(list.size()<3) {

			return false;
		}

		Direction a = list.get(0);

		Direction b = list.get(1);

		Direction c = list.get(2);

		return isRegion(a, b, c);

	}

	private boolean isRegion(Direction a, Direction b, Direction c) {

		if(isUpDirection(a)&&isRightDirection(b)&&isDownDirection(c)) {
			return true;
		}

		if(isRightDirection(a)&&isDownDirection(b)&&isLeftDirection(c)) {
			return true;
		}

		if(isDownDirection(a)&&isLeftDirection(b)&&isUpDirection(c)) {
			return true;
		}

		if(isLeftDirection(a)&&isUpDirection(b)&&isRightDirection(c)) {
			return true;
		}

		return false;

	}

	private boolean isUpDirection(Direction direction) {
		return (direction == Direction.UP)||(direction == Direction.UP_LEFT)||(direction == Direction.UP_RIGHT);
	}

	private boolean isDownDirection(Direction direction) {
		return (direction == Direction.DOWN)||(direction == Direction.DOWN_LEFT)||(direction == Direction.DOWN_RIGHT);
	}

	private boolean isRightDirection(Direction direction) {
		return (direction == Direction.RIGHT)||(direction == Direction.UP_RIGHT)||(direction == Direction.DOWN_RIGHT);
	}

	private boolean isLeftDirection(Direction direction) {
		return (direction == Direction.LEFT)||(direction == Direction.UP_LEFT)||(direction == Direction.DOWN_LEFT);
	}

}
