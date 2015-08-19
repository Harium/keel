package br.com.etyllica.motion.modifier.ogr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.WeightEdge;
import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.ogr.LineInterval;

public class LetterOGRModifier implements ComponentModifier<Component, Graph<Integer>> {

	public LetterOGRModifier() {
		super();
	}

	public Graph<Integer> modify(Component component) {
		Graph<Integer> graph = modify(component.generateMask());
		graph.moveNodes(component.getX(), component.getY());

		return graph;
	}

	public Graph<Integer> modify(boolean[][] mask) {

		List<LineInterval> intervals = new LineIntervalModifier().modify(mask);
		int w = mask[0].length;

		Map<LineInterval, List<Node<Integer>>> activeNodes = new HashMap<LineInterval, List<Node<Integer>>>();		

		Map<Integer, List<LineInterval>> map = LineIntervalModifier.groupIntervals(intervals);

		Graph<Integer> graph = new Graph<Integer>();

		List<LineInterval> lastLine = null;

		for(int i=0; i < map.size(); i++) {

			List<LineInterval> line = map.get(i);

			if(graph.isEmpty()) {
				firstLine(activeNodes, graph, line);
			} else {
				if(line.size() < lastLine.size()) {
					join(w, activeNodes, graph, lastLine, line);
				} else if(line.size() > lastLine.size()) {
					fork(w, activeNodes, graph, lastLine, line);
				} else {
					
					List<LineInterval> lastlasLine = map.get(i-2);
					
					if (lastlasLine == null || lastlasLine.size() != line.size()) {
						//First Expand
						firstLineExpand(activeNodes, graph, lastLine, line);
					} else {
						expand(w, activeNodes, graph, lastLine, line);
					}
				}
			}

			lastLine = line;
		}

		return graph;
	}

	private void firstLine(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> line) {
		//Found root
		
		List<Node<Integer>> nodes = activeNodes.get(line);
		if(nodes!=null) {
			nodes.clear();
		}
		
		LineInterval firstInterval = line.get(0);
		Node<Integer> root = addNode(activeNodes, graph, firstInterval);
		
		if(line.size() > 1) {
			for(int l = 1;l < line.size();l++) {
				addNode(activeNodes, graph, line.get(l));
			}
		}
	}
	
	private void join(int w, Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine,
			List<LineInterval> line) {

		for (int l = 0; l < line.size(); l++) {

			LineInterval interval = line.get(l);

			Node<Integer> joint = new Node<Integer>(interval.getCenter(), interval.getHeight());
			joint.setData(interval.getHeight());
			graph.addNode(joint);

			for(int i = 0; i < lastLine.size(); i++) {
				LineInterval lastInterval = lastLine.get(i);

				if(interval.intersect(lastInterval)) {
					Node<Integer> lastNode = getCenterNode(activeNodes, lastInterval);
					
					graph.addEdge(new WeightEdge<Integer>(joint, lastNode));
				}
			}

			addActiveNode(activeNodes, interval, joint);
		}
	}

	private void fork(int w, Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph,
			List<LineInterval> lastLine, List<LineInterval> line) {

		for (int l = 0; l < line.size(); l++) {

			LineInterval interval = line.get(l);

			Node<Integer> joint = new Node<Integer>(interval.getCenter(), interval.getHeight());
			joint.setData(interval.getHeight());
			graph.addNode(joint);

			for(int i = 0; i < lastLine.size(); i++) {

				LineInterval lastInterval = lastLine.get(i);

				if(interval.intersect(lastInterval)) {
					Node<Integer> lastNode = getCenterNode(activeNodes, lastInterval);
					graph.addEdge(new WeightEdge<Integer>(joint, lastNode));
				}
			}

			addActiveNode(activeNodes, interval, joint);
		}
	}
	
	private void firstLineExpand(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine, List<LineInterval> line) {
		for(int l = 0;l < line.size(); l++) {
			List<Node<Integer>> nodes = activeNodes.get(lastLine.get(l));
			
			LineInterval interval = line.get(l);
			Node<Integer> node = nodes.get(0);
						
			//
			Node<Integer> created = addNode(activeNodes, graph, interval);
			graph.addEdge(new WeightEdge<Integer>(created, node));
		}
	}
	
	private void expand(int w, Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine, List<LineInterval> line) {
				
		for(int l = 0;l < line.size(); l++) {
			List<Node<Integer>> nodes = activeNodes.get(lastLine.get(l));
			
			LineInterval interval = line.get(l);
			Node<Integer> node = nodes.get(0);
			
			node.getPoint().setLocation(interval.getCenter(), interval.getHeight());
			addActiveNode(activeNodes, interval, node);
		}
	}
	
	private void addActiveNode(Map<LineInterval, List<Node<Integer>>> activeNodes, LineInterval interval, Node<Integer> node) {
		if(!activeNodes.containsKey(interval)) {
			activeNodes.put(interval, new ArrayList<Node<Integer>>());
		}
		activeNodes.get(interval).add(node);
	}
	
	private Node<Integer> getCenterNode(Map<LineInterval, List<Node<Integer>>> activeNodes, LineInterval interval) {
		List<Node<Integer>> list = activeNodes.get(interval);
		int center = list.size()/2;
		return list.get(center);
	}

	private Node<Integer> addNode(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, LineInterval interval) {
		Node<Integer> node = new Node<Integer>(interval.getCenter(), interval.getHeight());
		node.setData(interval.getHeight());
		graph.addNode(node);
		addActiveNode(activeNodes, interval, node);

		return node;
	}

}
