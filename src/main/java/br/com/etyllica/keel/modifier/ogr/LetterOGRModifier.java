package br.com.etyllica.keel.modifier.ogr;

import br.com.etyllica.keel.core.ComponentModifier;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.feature.ogr.LineInterval;
import com.harium.etyl.commons.math.EtylMath;
import com.harium.etyl.linear.graph.Graph;
import com.harium.etyl.linear.graph.Node;
import com.harium.etyl.linear.graph.WeightEdge;

import java.util.*;

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

        for (int i = 0; i < map.size(); i++) {

            List<LineInterval> line = map.get(i);

            if (graph.isEmpty()) {
                firstLine(activeNodes, graph, line);
            } else {
                if (line.size() < lastLine.size()) {
                    join(activeNodes, graph, lastLine, line);
                } else if (line.size() > lastLine.size()) {
                    fork(activeNodes, graph, lastLine, line);
                } else {

                    List<LineInterval> lastlasLine = map.get(i - 2);

                    if (lastlasLine == null || lastlasLine.size() != line.size()) {
                        //First Expand
                        firstLineExpand(activeNodes, graph, lastLine, line);
                    } else {
                        expand(activeNodes, graph, lastLine, line);
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
        if (nodes != null) {
            nodes.clear();
        }

        LineInterval firstInterval = line.get(0);
        Node<Integer> root = addCenterNode(activeNodes, graph, firstInterval);

        if (line.size() > 1) {
            for (int l = 1; l < line.size(); l++) {
                addCenterNode(activeNodes, graph, line.get(l));
            }
        }
    }

    private void join(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine,
                      List<LineInterval> line) {

        for (int l = 0; l < line.size(); l++) {

            LineInterval interval = line.get(l);

            Node<Integer> joint = new Node<Integer>(interval.getCenter(), interval.getHeight());
            joint.setData(interval.getHeight());
            graph.addNode(joint);

            for (int i = 0; i < lastLine.size(); i++) {
                LineInterval lastInterval = lastLine.get(i);

                if (interval.intersect(lastInterval)) {
                    Node<Integer> lastNode = getCenterNode(activeNodes, lastInterval);
                    graph.addEdge(new WeightEdge<Integer>(joint, lastNode));
                }
            }

            addActiveNode(activeNodes, interval, joint);
        }
    }

    private void fork(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph,
                      List<LineInterval> lastLine, List<LineInterval> line) {

        Set<LineInterval> visited = new HashSet<LineInterval>(line.size());

        for (int i = 0; i < lastLine.size(); i++) {

            LineInterval lastInterval = lastLine.get(i);

            Node<Integer> joint = getCenterNode(activeNodes, lastLine.get(i));
            Node<Integer> jointRoot = getRoots(graph, joint).get(0);

            //For each current interval, link with root
            for (int l = 0; l < line.size(); l++) {
                LineInterval interval = line.get(l);

                if (lastInterval.intersect(interval)) {
                    //Create new Node
                    Node<Integer> node = addCenterNode(activeNodes, graph, interval);
                    graph.addEdge(new WeightEdge<Integer>(jointRoot, node));

                    //Remove expanded joint
                    graph.removeNode(joint);
                    visited.add(interval);
                }
            }
        }

        for (LineInterval interval : line) {
            if (!visited.contains(interval)) {
                addCenterNode(activeNodes, graph, interval);
            }
        }
    }

    private void firstLineExpand(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine, List<LineInterval> line) {
        for (int l = 0; l < line.size(); l++) {
            List<Node<Integer>> nodes = activeNodes.get(lastLine.get(l));

            LineInterval interval = line.get(l);
            Node<Integer> node = nodes.get(0);

            Node<Integer> created = addCenterNode(activeNodes, graph, interval);
            graph.addEdge(new WeightEdge<Integer>(created, node));
        }
    }

    private void expand(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, List<LineInterval> lastLine, List<LineInterval> line) {
        for (int l = 0; l < line.size(); l++) {
            LineInterval lastInterval = lastLine.get(l);
            List<Node<Integer>> nodes = activeNodes.get(lastInterval);

            LineInterval interval = line.get(l);
            Node<Integer> node = nodes.get(0);

            if (lastInterval.getLength() > interval.getLength() * 3) {
                //Divide in three
                Node<Integer> center = createCenterNode(activeNodes, graph, lastInterval);

                Node<Integer> start = createStartNode(activeNodes, graph, lastInterval);
                graph.addEdge(new WeightEdge<Integer>(start, node));

                Node<Integer> end = createEndNode(activeNodes, graph, lastInterval);
                graph.addEdge(new WeightEdge<Integer>(end, node));

                double ps = dist(interval, start);
                double pe = dist(interval, end);
                double pc = dist(interval, center);

                if (pc <= ps && pc <= ps) {
                    graph.addEdge(new WeightEdge<Integer>(center, node));
                    addActiveNode(activeNodes, interval, center);
                } else if (ps <= pe && ps <= pc) {
                    graph.addEdge(new WeightEdge<Integer>(start, node));
                    addActiveNode(activeNodes, interval, start);
                } else if (pe <= ps && pe <= pc) {
                    graph.addEdge(new WeightEdge<Integer>(end, node));
                    addActiveNode(activeNodes, interval, end);
                }


            } else if (lastInterval.getLength() > interval.getLength() * 2) {
                //Divide in two
                Node<Integer> start = createStartNode(activeNodes, graph, lastInterval);

                Node<Integer> end = createEndNode(activeNodes, graph, lastInterval);
                graph.addEdge(new WeightEdge<Integer>(start, end));

                double ps = dist(interval, start);
                double pe = dist(interval, end);

                if (ps <= pe) {
                    //Expand by Start
                    graph.addEdge(new WeightEdge<Integer>(start, node));
                    addActiveNode(activeNodes, interval, start);
                } else {
                    //Expand by End
                    graph.addEdge(new WeightEdge<Integer>(end, node));
                    addActiveNode(activeNodes, interval, end);
                }

            } else {
                //Move point
                node.getPoint().setLocation(interval.getCenter(), interval.getHeight());
                addActiveNode(activeNodes, interval, node);
            }
        }
    }

    private void addActiveNode(Map<LineInterval, List<Node<Integer>>> activeNodes, LineInterval interval, Node<Integer> node) {
        if (!activeNodes.containsKey(interval)) {
            activeNodes.put(interval, new ArrayList<Node<Integer>>());
        }
        activeNodes.get(interval).add(node);
    }

    private Node<Integer> getCenterNode(Map<LineInterval, List<Node<Integer>>> activeNodes, LineInterval interval) {
        List<Node<Integer>> list = activeNodes.get(interval);
        int center = list.size() / 2;
        return list.get(center);
    }

    private List<Node<Integer>> getRoots(Graph<Integer> graph, Node<Integer> node) {
        List<Node<Integer>> roots = new ArrayList<Node<Integer>>();
        for (WeightEdge<Integer> edge : graph.getEdges(node)) {
            if (edge.getOrigin() == node) {
                roots.add(edge.getDestination());
            } else {
                roots.add(edge.getOrigin());
            }
        }

        if (roots.isEmpty()) {
            roots.add(node);
        }

        return roots;
    }

    private Node<Integer> createStartNode(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, LineInterval interval) {
        Node<Integer> node = new Node<Integer>(interval.getStart(), interval.getHeight());
        node.setData(interval.getHeight());
        graph.addNode(node);
        //addActiveNode(activeNodes, interval, node);

        return node;
    }

    private Node<Integer> createEndNode(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, LineInterval interval) {
        Node<Integer> node = new Node<Integer>(interval.getEnd(), interval.getHeight());
        node.setData(interval.getHeight());
        graph.addNode(node);
        //addActiveNode(activeNodes, interval, node);

        return node;
    }

    private Node<Integer> createCenterNode(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, LineInterval interval) {
        Node<Integer> node = new Node<Integer>(interval.getCenter(), interval.getHeight());
        node.setData(interval.getHeight());
        graph.addNode(node);

        return node;
    }

    private Node<Integer> addCenterNode(Map<LineInterval, List<Node<Integer>>> activeNodes, Graph<Integer> graph, LineInterval interval) {
        Node<Integer> node = new Node<Integer>(interval.getCenter(), interval.getHeight());
        node.setData(interval.getHeight());
        graph.addNode(node);
        addActiveNode(activeNodes, interval, node);

        return node;
    }

    private double dist(Node<Integer> a, Node<Integer> b) {
        return a.getPoint().distance(b.getPoint());
    }

    private double dist(LineInterval interval, Node<Integer> b) {
        return EtylMath.diffMod(interval.getCenter(), b.getPoint().getX());
    }

}
