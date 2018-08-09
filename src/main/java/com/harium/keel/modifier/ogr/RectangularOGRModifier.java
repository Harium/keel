package com.harium.keel.modifier.ogr;

import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.ogr.OGR;
import com.harium.keel.modifier.hull.HullModifier;
import com.harium.etyl.geometry.Point2D;
import com.harium.storage.graph.Graph;
import com.harium.keel.modifier.ogr.model.OGRNodeData;

import java.util.ArrayList;
import java.util.List;

public class RectangularOGRModifier implements HullModifier<List<Point2D>> {

    private OGR<OGRNodeData> ogr;

    public RectangularOGRModifier() {
        super();

        ogr = new RectangularOGR();
    }

    public PointFeature modifyComponent(PointFeature component) {

        PointFeature box = new PointFeature();

        for (Point2D point : modify(component)) {
            box.add(point);
        }

        return box;
    }

    public List<Point2D> modify(PointFeature feature) {

        boolean[][] mask = feature.generateMask();

        Graph<OGRNodeData> graph = ogr.findGraph(mask);

        if (graph == null) {
            graph = new Graph<OGRNodeData>();
        }

        if (graph.getNodes().size() != 4) {

            for (int i = graph.getNodes().size(); i < 4; i++) {
                graph.addNode(OGRNodeData.buildNode(feature.getCenter()));
            }

        }

        Point2D a = graph.getNodes().get(1).getData().getPoint();
        Point2D b = graph.getNodes().get(2).getData().getPoint();
        Point2D c = graph.getNodes().get(0).getData().getPoint();
        Point2D d = graph.getNodes().get(3).getData().getPoint();

        //isQuadGraph
        if (a.getY() == c.getY()) {

            a = graph.getNodes().get(0).getData().getPoint();
            b = graph.getNodes().get(1).getData().getPoint();
            c = graph.getNodes().get(3).getData().getPoint();
            d = graph.getNodes().get(2).getData().getPoint();

        }

        List<Point2D> list = new ArrayList<Point2D>();

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        for (Point2D point : list) {
            point.setOffset(feature.getX(), feature.getY());
        }

        return list;
    }
}
