package com.harium.keel.modifier.ogr;

import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.ogr.OGR;
import com.harium.keel.modifier.hull.HullModifier;
import com.harium.etyl.linear.Point2D;
import com.harium.etyl.linear.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class RectangularOGRModifier implements HullModifier<List<Point2D>> {

    private OGR<Integer> ogr;

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

        Graph<Integer> graph = ogr.findGraph(mask);

        if (graph == null) {
            graph = new Graph<Integer>();
        }

        if (graph.getNodes().size() != 4) {

            for (int i = graph.getNodes().size(); i < 4; i++) {
                graph.addNode(feature.getCenter());
            }

        }

        Point2D a = graph.getNodes().get(1).getPoint();
        Point2D b = graph.getNodes().get(2).getPoint();
        Point2D c = graph.getNodes().get(0).getPoint();
        Point2D d = graph.getNodes().get(3).getPoint();

        //isQuadGraph
        if (a.getY() == c.getY()) {

            a = graph.getNodes().get(0).getPoint();
            b = graph.getNodes().get(1).getPoint();
            c = graph.getNodes().get(3).getPoint();
            d = graph.getNodes().get(2).getPoint();

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
