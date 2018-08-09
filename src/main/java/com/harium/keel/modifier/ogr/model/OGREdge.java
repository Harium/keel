package com.harium.keel.modifier.ogr.model;

import com.harium.storage.graph.Node;
import com.harium.storage.graph.WeightEdge;

public class OGREdge extends WeightEdge<OGRNodeData> {
    public OGREdge(Node<OGRNodeData> origin, Node<OGRNodeData> destination) {
        super(origin, destination);
    }
}
