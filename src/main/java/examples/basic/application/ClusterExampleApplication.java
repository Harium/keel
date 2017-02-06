package examples.basic.application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.classifier.cluster.Cluster;
import br.com.etyllica.motion.classifier.cluster.DBScan;

public class ClusterExampleApplication extends Application {

	private boolean computing = false;
	
	private List<Point2D> points;
	private List<Cluster> clusters;
	
	private DBScan clusterClassifier;
	
	public ClusterExampleApplication(int w, int h) {
		super(w, h);//Size of our application
	}
		
	@Override
	public void load() {
		points = new ArrayList<Point2D>();
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
		
		for (Point2D point: points) {
			g.drawCircle(point.getX(), point.getY(), 5);
		}
		
		drawClusters(g);
	}
	
	private void drawClusters(Graphics g) {
		if (clusters == null)
			return;
		
		g.setColor(SVGColor.RED);
		for (Cluster cluster : clusters) {
			g.drawCircle(cluster.centroid, 10);
		}
		
		//clusters.
		
		/*Polygon polygon = convexHull.getPolygon();
		
		g.setColor(SVGColor.BLACK);
		
		for(Point2D point: convexHull.asList()) {
			g.drawCircle(point, 5);
		}
		
		g.setColor(SVGColor.KHAKI);
		g.drawPolygon(polygon);
		g.setColor(SVGColor.BLACK);
		g.drawPolygon(polygon);*/
	}

	@Override
	public void updateMouse(PointerEvent event) {
		
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
			//Add a new Point to the list
			points.add(new Point2D(event.getX(), event.getY()));
		}
		
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_RIGHT)) {
			if (!computing) {
				computing = true;
				//Compute the Clusters
				
				clusters = clusterClassifier.cluster(points);
				computing = false;
			}
		}
	}
		
}
