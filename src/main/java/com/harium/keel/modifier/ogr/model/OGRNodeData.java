package com.harium.keel.modifier.ogr.model;

import com.harium.etyl.geometry.Point2D;
import com.harium.storage.graph.Node;

public class OGRNodeData {
    Point2D point;
    Integer height;

    public OGRNodeData(Point2D point) {
        this.point = point;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public static Node<OGRNodeData> buildNode(Point2D point) {
        Node<OGRNodeData> node = new Node(point);
        node.setData(new OGRNodeData(point));
        return node;
    }

    public static Node<OGRNodeData> buildNode(int x, int y) {
        return buildNode(new Point2D(x, y));
    }

    public void setLocation(int x, int y) {
        point.setLocation(x, y);
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }
}
