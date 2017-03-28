package br.com.etyllica.keel.modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.core.ComponentModifier;
import br.com.etyllica.keel.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.feature.Direction;
import br.com.etyllica.keel.helper.PointListHelper;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.common.IntegerEdge;


/**
 * MiddleLine modifier
 */


public class MiddleLineModifier implements ComponentModifierStrategy, ComponentModifier<Component, Graph<Integer>> {

	public MiddleLineModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {

		Graph<Integer> g = modify(component);

		Component polygon = new Component(0, 0);

		for(Node<Integer> node: g.getNodes()) {
			polygon.add(node.getPoint());
		}

		return polygon;

	}

	public Graph<Integer> modify(Component component) {

		List<Point2D> points = component.getPoints();
		
		List<Point2D> list = PointListHelper.cloneList(points);

		Graph<Integer> graph = new Graph<Integer>();
		
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
	
	private void addNodes(List<Point2D> list, List<Point2D> mediumNodes, Graph<Integer> graph) {
		
		Node<Integer> a = new Node<Integer>(list.get(0));
		Node<Integer> b = new Node<Integer>(list.get(1));
		Node<Integer> c = new Node<Integer>(list.get(2));
		Node<Integer> d = new Node<Integer>(list.get(3));

		graph.addNode(a);
		graph.addNode(b);
		graph.addNode(c);
		graph.addNode(d);

		graph.addEdge(new IntegerEdge(a, b));
		graph.addEdge(new IntegerEdge(b, c));
		graph.addEdge(new IntegerEdge(c, d));
		graph.addEdge(new IntegerEdge(d, a));

		double nodeSumX = 0;
		double nodeSumY = 0;
		
		for(Node<Integer> node: graph.getNodes()) {
			
			Point2D point = node.getPoint();
			
			nodeSumX += point.getX();
			nodeSumY += point.getY();
		}
		
		Node<Integer> mediumNode = new Node<Integer>((nodeSumX)/4, (nodeSumY)/4);

		graph.addNode(mediumNode);

		mediumNodes.add(mediumNode.getPoint());
		
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

	private void addVisibleComponents(Graph<Integer> graph, List<Point2D> mediumNodes, List<Point2D> list) {

		Node<Integer> centroid = new Node<Integer>(getCentroid(list));

		graph.addNode(centroid);

		for(Point2D node: mediumNodes) {
			graph.addEdge(new IntegerEdge(new Node<Integer>(node), centroid));	
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
