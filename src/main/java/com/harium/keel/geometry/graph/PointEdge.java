package com.harium.keel.geometry.graph;

import com.harium.etyl.geometry.Point2D;
import com.harium.storage.graph.Node;
import com.harium.storage.graph.WeightEdge;

public class PointEdge extends WeightEdge<Point2D> {

    public PointEdge(Node<Point2D> origin, Node<Point2D> destination) {
        super(origin, destination);
    }

}
