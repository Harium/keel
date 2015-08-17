package br.com.etyllica.motion.modifier.ogr;

import java.util.ArrayList;
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

	public Graph<Integer> modify(boolean[][] mask) {

		List<Node<Integer>> activeNodes = new ArrayList<Node<Integer>>();

		List<LineInterval> intervals = new LineIntervalModifier().modify(mask);

		Map<Integer, List<LineInterval>> map = LineIntervalModifier.groupIntervals(intervals);

		Graph<Integer> graph = new Graph<Integer>();

		int lastIntervalCount = 0;

		for(int i=0; i<map.size(); i++) {

			List<LineInterval> line = map.get(i);

			if(graph.isEmpty()) {
				firstLine(graph, i, line, activeNodes);
			} else {
				if(line.size() < lastIntervalCount) {
					for(int l = 0;l < line.size(); l++) {

						LineInterval interval = line.get(l);

						Node<Integer> joint = new Node<Integer>(interval.getCenter());

						Node<Integer> leftNode = activeNodes.get(l);
						Node<Integer> rightNode = activeNodes.get(l+1);

						graph.addEdge(new WeightEdge<Integer>(joint, leftNode));
						graph.addEdge(new WeightEdge<Integer>(joint, rightNode));

						graph.addNode(joint);
						activeNodes.clear();
						activeNodes.add(joint);
					}
				} else if(line.size() > lastIntervalCount) {

					if(lastIntervalCount == 1) {
						
						Node<Integer> joint = activeNodes.get(0);
						activeNodes.clear();

						for(int l = 0;l < line.size(); l++) {
							LineInterval interval = line.get(l);

							Node<Integer> node = new Node<Integer>(interval.getCenter());

							graph.addNode(node);
							graph.addEdge(new WeightEdge<Integer>(node, joint));

							activeNodes.add(node);
						}
					} else {
						System.out.println("Not implemented yet.");
					}
				}
			}

			lastIntervalCount = line.size();
		}

		return graph;
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
		Node<Integer> node = new Node<Integer>(interval.getCenter());
		graph.addNode(node);
		activeNodes.add(node);
	}

	private Node<Integer> getNode(Map<Point2D, Node<Integer>>  nodes, LineInterval interval) {
		return nodes.get(new Point2D(interval.getCenter(), interval.getHeight()));
	}

	public Graph<Integer> modify(Component component) {

		return modify(component.generateMask());
	}

}
