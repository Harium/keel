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

		int w = 10;
		int h = 0;

		final int sizeLimiar = w/3;

		Graph graph = new Graph();

		List<LineInterval> intervals = new ArrayList<LineInterval>();

		int lastIntervalCount = 0;

		int startInterval = 0;

		boolean foundInterval = false;

		boolean foundFirstInterval = false;

		for (int j=0;j<h; j++) {

			for (int i=0; i<w; i++) {

				if(isValid(mask[j][i])) {

					if(!foundInterval) {

						foundInterval = true;

						startInterval = i;
					}

				} else if(foundInterval) {

					intervals.add(new LineInterval(startInterval, i-startInterval, j));

					foundInterval = false;
				}

				if(!foundFirstInterval && intervals.size() == 1) {

					foundFirstInterval = true;

					lastIntervalCount = processFirstInterval(graph, intervals, sizeLimiar);

				} else if(intervals.size() != lastIntervalCount) {

					//Do

				}

			}
			
		}

		for(LineInterval interval: intervals) {

			System.out.println(interval.getStart()+" "+interval.getLength()+" "+interval.getCenter());
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

			Point2D lastPoint = new Point2D(firstInterval.getStart()+firstInterval.getLength(), height);

			Node firstNode = graph.addNode(firstPoint);

			Node secondNode = graph.addNode(lastPoint);

			graph.addEdge(new Edge(firstNode, secondNode));

			intervalCount = 2;

		}

		return intervalCount;

	}

	private boolean isValid(boolean value) {
		return value;
	}

}
