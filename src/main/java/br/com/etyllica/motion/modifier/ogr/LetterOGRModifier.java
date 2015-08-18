package br.com.etyllica.motion.modifier.ogr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		List<Node<Integer>> activeNodes = new ArrayList<Node<Integer>>();

		List<LineInterval> intervals = new LineIntervalModifier().modify(mask);

		Map<Integer, List<LineInterval>> map = LineIntervalModifier.groupIntervals(intervals);

		Graph<Integer> graph = new Graph<Integer>();

		int lastIntervalCount = 0;

		for(int i=0; i < map.size(); i++) {

			List<LineInterval> line = map.get(i);

			if(graph.isEmpty()) {
				firstLine(graph, i, line, activeNodes);
			} else {
				if(line.size() < lastIntervalCount) {
					joint(activeNodes, graph, line);
				} else if(line.size() > lastIntervalCount) {
					fork(activeNodes, graph, lastIntervalCount, line);
				} else {
					expand(activeNodes, map, i, line);
				}
			}

			lastIntervalCount = line.size();
		}

		return graph;
	}

	private void expand(List<Node<Integer>> activeNodes,
			Map<Integer, List<LineInterval>> map, int i, List<LineInterval> line) {
		if(i >= map.size()/2) {
			//Pull nodes
			for(int l = 0;l < line.size(); l++) {
				LineInterval interval = line.get(l);

				Node<Integer> node;
				if(activeNodes.size()<=l) {
					//TODO workaround
					node = activeNodes.get(0);
				} else {
					node = activeNodes.get(l);
				}
				node.getPoint().setLocation(interval.getCenter(), i);
			}
		}
	}

	private void fork(List<Node<Integer>> activeNodes, Graph<Integer> graph,
			int lastIntervalCount, List<LineInterval> line) {
		if(lastIntervalCount == 1) {
			Node<Integer> joint = activeNodes.get(0);
			activeNodes.clear();

			for(int l = 0;l < line.size(); l++) {
				LineInterval interval = line.get(l);

				Node<Integer> node = new Node<Integer>(interval.getCenter(), interval.getHeight());

				graph.addNode(node);
				graph.addEdge(new WeightEdge<Integer>(node, joint));

				activeNodes.add(node);
			}
		} else {
			System.out.println("Not implemented yet.");
		}
	}

	private void joint(List<Node<Integer>> activeNodes, Graph<Integer> graph,
			List<LineInterval> line) {
		for(int l = 0;l < line.size(); l++) {

			LineInterval interval = line.get(l);

			Node<Integer> joint = new Node<Integer>(interval.getCenter(), interval.getHeight());

			if(activeNodes.size() > l+1) {
				Node<Integer> leftNode = activeNodes.get(l);
				Node<Integer> rightNode = activeNodes.get(l+1);

				graph.addEdge(new WeightEdge<Integer>(joint, leftNode));
				graph.addEdge(new WeightEdge<Integer>(joint, rightNode));
			} else {
				//TODO workaround
				Node<Integer> lastCenteredNode = activeNodes.get(0);

				graph.addEdge(new WeightEdge<Integer>(joint, lastCenteredNode));
			}

			graph.addNode(joint);
			activeNodes.clear();
			activeNodes.add(joint);
		}
	}

	private void firstLine(Graph<Integer> graph, int i, List<LineInterval> line, List<Node<Integer>> activeNodes) {
		//Found root
		LineInterval firstInterval = line.get(0);
		addNode(graph, activeNodes, firstInterval);

		if(line.size() > 1) {
			for(int l = 1;l<line.size();l++) {
				addNode(graph, activeNodes, line.get(l));
			}
		}
	}

	private void addNode(Graph<Integer> graph, List<Node<Integer>> activeNodes, LineInterval interval) {
		Node<Integer> node = new Node<Integer>(interval.getCenter(), interval.getHeight());
		graph.addNode(node);
		activeNodes.add(node);
	}

}
