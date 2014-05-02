package br.com.etyllica.motion.modifier;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Edge;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.motion.Direction;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.helper.PointListHelper;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;


/**
 * QuickHull modifier based on Alexander Hristov's code at
 * 
 * www.ahristov.com/tutorial/geometry-games/convex-hull.html
 *
 * @license GPL
 *
 */


public class MiddleLineModifier implements ComponentModifierStrategy {

	private Graph graph = new Graph();
	
	public MiddleLineModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {
		
		Graph g = middleLine(component.getPoints());
		
		Component polygon = new Component(0, 0);
		
		for(Point2D ponto: g.getNodes()) {
			polygon.add(ponto);
		}
		
		return polygon;
		
	}

	public Graph middleLine(List<Point2D> points) {

		List<Point2D> list = PointListHelper.cloneList(points);
		
		if (points.size() < 3) return graph;

		//Clear List
		graph.clear();
		
		List<Direction> directions = new ArrayList<Direction>();
		
		int maxIndex = list.size()-3;
		
		for (int i=0; i<list.size()-1; i++) {
				
			Point2D fa = list.get(i);
			Point2D fb = list.get(i+1);
									
			directions.add(rate(fa,fb));
			
			if(i>3&&i<maxIndex) {
								
				int initialIndex = directions.size()-3;
				
				if(isRegion(directions.subList(initialIndex, directions.size()))) {
					
					Node a = new Node(list.get(initialIndex));
					Node b = new Node(list.get(initialIndex+1));
					Node c = new Node(list.get(initialIndex+2));
					Node d = new Node(list.get(initialIndex+3));
					
					graph.addNode(a);
					graph.addNode(b);
					graph.addNode(c);
					graph.addNode(d);
										
					graph.addEdge(new Edge(a, b));
					graph.addEdge(new Edge(b, c));
					graph.addEdge(new Edge(c, d));
					graph.addEdge(new Edge(d, a));
					
					Node mediumNode = new Node((a.getX()+b.getX()+c.getX()+d.getX())/4, (a.getY()+b.getY()+c.getY()+d.getY())/4);
					
					graph.addNode(mediumNode);
					
				}
				
			}
			
		}
		
		return graph;
				
	}
	
	public static Direction rate(Point2D a, Point2D b) {
		
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

		if(list.size()!=3) {
						
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
