package application.examples;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.awt.camera.CameraV4L4J;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.CrossSearch;
import br.com.etyllica.keel.modifier.hull.FastConvexHullModifier;

public class FaceSampledReal extends Application {

	private CameraV4L4J cam;
	private BufferedImageSource source = new BufferedImageSource();
	
	private CrossSearch colorFilter = new CrossSearch();

	private FastConvexHullModifier quickHull = new FastConvexHullModifier();
		
	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private Component sampledFeature;

	private Polygon sampledPolygon = new Polygon();
	
	private Component screen;

	public FaceSampledReal(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {

		loadingInfo = "Loading Images";

		cam = new CameraV4L4J(0);
		
		screen = new Component(0, 0, cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());

		ColorStrategy colorStrategy = new ColorStrategy(Color.BLACK); 
		colorFilter.setPixelStrategy(colorStrategy);
		
		final int MAGIC_NUMBER = 3;//Higher = Faster and less precise
		
		colorFilter.setBorder(MAGIC_NUMBER);
		colorFilter.setStep(MAGIC_NUMBER);

		loadingInfo = "Configuring Filter";
		
		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b) {
		source.setImage(b);
		//Sampled
		sampledFeature = colorFilter.filter(source, screen).get(0);

		sampledPolygon.reset();

		//TODO Separate polygons
		List<Point2D> points = quickHull.modify(sampledFeature).getPoints();
		
		for(Point2D point: points){
			sampledPolygon.addPoint((int)point.getX(), (int)point.getY());	
		}
	}

	private List<Component> separateComponents(Component feature){

		List<Component> result = new ArrayList<Component>();

		List<Point2D> points = new ArrayList<Point2D>(feature.getPoints());

		Component currentComponent = new Component(0, 0);		

		currentComponent.add(points.get(0));

		int p = 1;

		Point2D pt = points.get(p);		

		final int radius = 20;

		//while(points.size()>0){

			for(int i=1;i<points.size();i++){

				Point2D q = points.get(i);

				if(insideCircle((int)pt.getX(), (int)pt.getY(), radius, (int)q.getX(), (int)q.getY())){
					currentComponent.add(q);
					points.remove(i);
					continue;
				}

			}
			
		//}


		return result;


	}

	private boolean insideCircle(int cx, int cy, int radius, int px, int py){

		float difX = (x - cx)*(x - cx);
		float difY = (x - cx)*(x - cx);

		return difX + difY < radius*radius;

	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.VK_P)){
			pixels = !pixels;
		}
	}

	@Override
	public void draw(Graphics g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}
		
		reset(cam.getBufferedImage());

		g.setAlpha(60);
		//drawFeaturedPoints(g, sampledFeature, Color.GREEN);
		g.setAlpha(100);

		g.setColor(Color.GREEN);
		g.drawPolygon(sampledPolygon);

	}

	private void drawFeaturedPoints(Graphics g, Component feature, Color color){

		for(Point2D ponto: feature.getPoints()){

			g.setColor(color);
			g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

			//g.setColor(Color.WHITE);
			//g.drawCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 18);

		}

	}

}
