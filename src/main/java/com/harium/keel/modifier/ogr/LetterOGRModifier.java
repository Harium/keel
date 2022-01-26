package com.harium.keel.modifier.ogr;

import com.harium.etyl.geometry.math.EtylMath;
import com.harium.keel.core.Modifier;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.ogr.LineInterval;
import com.harium.keel.modifier.ogr.model.OGREdge;
import com.harium.keel.modifier.ogr.model.OGRNodeData;
import com.harium.storage.graph.Graph;
import com.harium.storage.graph.Node;
import com.harium.storage.graph.WeightEdge;

import java.util.*;

public class LetterOGRModifier implements Modifier<PointFeature, Graph<OGRNodeData>> {

    public LetterOGRModifier() {
        super();
    }

    public Graph<OGRNodeData> apply(PointFeature feature) {
        Graph<OGRNodeData> graph = modify(feature.generateMask());
        moveNodes(graph, feature.getX(), feature.getY());

        return graph;
    }

    private void moveNodes(Graph<OGRNodeData> graph, int x, int y) {
        for(Node<OGRNodeData> node: graph.getNodes()) {
            node.getData().getPoint().add(x, y);
        }
    }

    public Graph<OGRNodeData> modify(boolean[][] mask) {

        List<LineInterval> intervals = new LineIntervalModifier().modify(mask);
        int w = mask[0].length;

        Map<LineInterval, List<Node<OGRNodeData>>> activeNodes = new HashMap<>();

        Map<Integer, List<LineInterval>> map = LineIntervalModifier.groupIntervals(intervals);

        Graph<OGRNodeData> graph = new Graph<>();

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

    private void firstLine(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, List<LineInterval> line) {
        //Found root

        List<Node<OGRNodeData>> nodes = activeNodes.get(line);
        if (nodes != null) {
            nodes.clear();
        }

        LineInterval firstInterval = line.get(0);

        if (line.size() > 1) {
            for (int l = 1; l < line.size(); l++) {
                addCenterNode(activeNodes, graph, line.get(l));
            }
        } else if (line.size() == 1) {
            addCenterNode(activeNodes, graph, firstInterval);
        }
    }

    private void join(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, List<LineInterval> lastLine,
                      List<LineInterval> line) {

        for (int l = 0; l < line.size(); l++) {

            LineInterval interval = line.get(l);

            Node<OGRNodeData> joint = OGRNodeData.buildNode(interval.getCenter(), interval.getHeight());
            joint.getData().setHeight(interval.getHeight());
            graph.addNode(joint);

            for (int i = 0; i < lastLine.size(); i++) {
                LineInterval lastInterval = lastLine.get(i);

                if (interval.intersect(lastInterval)) {
                    Node<OGRNodeData> lastNode = getCenterNode(activeNodes, lastInterval);
                    graph.addEdge(new OGREdge(joint, lastNode));
                }
            }

            addActiveNode(activeNodes, interval, joint);
        }
    }

    private void fork(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph,
                      List<LineInterval> lastLine, List<LineInterval> line) {

        Set<LineInterval> visited = new HashSet<LineInterval>(line.size());

        for (int i = 0; i < lastLine.size(); i++) {

            LineInterval lastInterval = lastLine.get(i);

            Node<OGRNodeData> joint = getCenterNode(activeNodes, lastLine.get(i));
            Node<OGRNodeData> jointRoot = getRoots(graph, joint).get(0);

            //For each current interval, link with root
            for (int l = 0; l < line.size(); l++) {
                LineInterval interval = line.get(l);

                if (lastInterval.intersect(interval)) {
                    //Create new Node
                    Node<OGRNodeData> node = addCenterNode(activeNodes, graph, interval);
                    graph.addEdge(new OGREdge(jointRoot, node));

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

    private void firstLineExpand(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, List<LineInterval> lastLine, List<LineInterval> line) {
        for (int l = 0; l < line.size(); l++) {
            List<Node<OGRNodeData>> nodes = activeNodes.get(lastLine.get(l));

            LineInterval interval = line.get(l);
            Node<OGRNodeData> node = nodes.get(0);

            Node<OGRNodeData> created = addCenterNode(activeNodes, graph, interval);
            graph.addEdge(new OGREdge(created, node));
        }
    }

    private void expand(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, List<LineInterval> lastLine, List<LineInterval> line) {
        for (int l = 0; l < line.size(); l++) {
            LineInterval lastInterval = lastLine.get(l);
            List<Node<OGRNodeData>> nodes = activeNodes.get(lastInterval);

            LineInterval interval = line.get(l);
            Node<OGRNodeData> node = nodes.get(0);

            if (lastInterval.getLength() > interval.getLength() * 3) {
                //Divide in three
                Node<OGRNodeData> center = createCenterNode(activeNodes, graph, lastInterval);

                Node<OGRNodeData> start = createStartNode(activeNodes, graph, lastInterval);
                graph.addEdge(new OGREdge(start, node));

                Node<OGRNodeData> end = createEndNode(activeNodes, graph, lastInterval);
                graph.addEdge(new OGREdge(end, node));

                double ps = dist(interval, start);
                double pe = dist(interval, end);
                double pc = dist(interval, center);

                if (pc <= ps && pc <= ps) {
                    graph.addEdge(new OGREdge(center, node));
                    addActiveNode(activeNodes, interval, center);
                } else if (ps <= pe && ps <= pc) {
                    graph.addEdge(new OGREdge(start, node));
                    addActiveNode(activeNodes, interval, start);
                } else if (pe <= ps && pe <= pc) {
                    graph.addEdge(new OGREdge(end, node));
                    addActiveNode(activeNodes, interval, end);
                }


            } else if (lastInterval.getLength() > interval.getLength() * 2) {
                //Divide in two
                Node<OGRNodeData> start = createStartNode(activeNodes, graph, lastInterval);

                Node<OGRNodeData> end = createEndNode(activeNodes, graph, lastInterval);
                graph.addEdge(new OGREdge(start, end));

                double ps = dist(interval, start);
                double pe = dist(interval, end);

                if (ps <= pe) {
                    //Expand by Start
                    graph.addEdge(new OGREdge(start, node));
                    addActiveNode(activeNodes, interval, start);
                } else {
                    //Expand by End
                    graph.addEdge(new OGREdge(end, node));
                    addActiveNode(activeNodes, interval, end);
                }

            } else {
                //Move point
                node.getData().setLocation(interval.getCenter(), interval.getHeight());
                addActiveNode(activeNodes, interval, node);
            }
        }
    }

    private void addActiveNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, LineInterval interval, Node<OGRNodeData> node) {
        if (!activeNodes.containsKey(interval)) {
            activeNodes.put(interval, new ArrayList<Node<OGRNodeData>>());
        }
        activeNodes.get(interval).add(node);
    }

    private Node<OGRNodeData> getCenterNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, LineInterval interval) {
        List<Node<OGRNodeData>> list = activeNodes.get(interval);
        int center = list.size() / 2;
        return list.get(center);
    }

    private List<Node<OGRNodeData>> getRoots(Graph<OGRNodeData> graph, Node<OGRNodeData> node) {
        List<Node<OGRNodeData>> roots = new ArrayList<Node<OGRNodeData>>();
        for (WeightEdge<OGRNodeData> edge : graph.getEdges(node)) {
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

    private Node<OGRNodeData> createStartNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, LineInterval interval) {
        Node<OGRNodeData> node = OGRNodeData.buildNode(interval.getStart(), interval.getHeight());
        node.getData().setHeight(interval.getHeight());
        graph.addNode(node);
        //addActiveNode(activeNodes, interval, node);

        return node;
    }

    private Node<OGRNodeData> createEndNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, LineInterval interval) {
        Node<OGRNodeData> node = OGRNodeData.buildNode(interval.getEnd(), interval.getHeight());
        node.getData().setHeight(interval.getHeight());
        graph.addNode(node);
        //addActiveNode(activeNodes, interval, node);

        return node;
    }

    private Node<OGRNodeData> createCenterNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, LineInterval interval) {
        Node<OGRNodeData> node = OGRNodeData.buildNode(interval.getCenter(), interval.getHeight());
        node.getData().setHeight(interval.getHeight());
        graph.addNode(node);

        return node;
    }

    private Node<OGRNodeData> addCenterNode(Map<LineInterval, List<Node<OGRNodeData>>> activeNodes, Graph<OGRNodeData> graph, LineInterval interval) {
        Node<OGRNodeData> node = OGRNodeData.buildNode(interval.getCenter(), interval.getHeight());
        node.getData().setHeight(interval.getHeight());
        graph.addNode(node);
        addActiveNode(activeNodes, interval, node);

        return node;
    }

    private double dist(Node<OGRNodeData> a, Node<OGRNodeData> b) {
        return a.getData().getPoint().distance(b.getData().getPoint());
    }

    private double dist(LineInterval interval, Node<OGRNodeData> b) {
        return EtylMath.diffMod(interval.getCenter(), b.getData().getPoint().x);
    }

}
