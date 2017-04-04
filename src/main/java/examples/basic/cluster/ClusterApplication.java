package examples.basic.cluster;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.classifier.cluster.Cluster;
import br.com.etyllica.keel.classifier.cluster.DBScan;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClusterApplication extends Application {

    private boolean computing = false;

    private Set<Point2D> points;
    private List<Cluster> clusters;

    private DBScan clusterClassifier;

    public ClusterApplication(int w, int h) {
        super(w, h);//Size of our application
    }

    @Override
    public void load() {
        points = new HashSet<Point2D>();
        clusterClassifier = new DBScan(40, 5);

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFontSize(18);
        g.drawStringX("Cluster Example", 40);

        g.drawStringX("Press Left Mouse Button to Add Points", 70);
        g.drawStringX("Press Right Mouse Button to Compute the Clusters", 90);

        drawClusters(g);

        g.setColor(Color.BLACK);
        for (Point2D point : points) {
            g.drawCircle(point.getX(), point.getY(), 5);
        }
    }

    private void drawClusters(Graphics g) {
        if (clusters == null)
            return;

        g.setColor(SVGColor.RED);
        for (Cluster cluster : clusters) {


            List<Point2D> clusterPoints = cluster.getPoints();
            int count = 0;
            Point2D lastPoint = clusterPoints.get(clusterPoints.size() - 1);
            for (Point2D point : cluster.getPoints()) {
                g.drawLine(point, lastPoint);
                lastPoint = clusterPoints.get(count);
                count++;
            }
            g.drawCircle(cluster.centroid, 10);
        }
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
            //Add a new Point to the list
            points.add(new Point2D(event.getX(), event.getY()));
            System.out.println("points " + points.size());
        }

        if (event.isButtonUp(MouseEvent.MOUSE_BUTTON_RIGHT)) {
            if (!computing) {
                computing = true;
                //Compute the Clusters

                clusters = clusterClassifier.cluster(points);
                computing = false;
            }
        }
    }

}
