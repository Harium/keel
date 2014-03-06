package application.examples;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.CrossSearch;
import br.com.etyllica.motion.filter.face.SkinBorderFilter;
import br.com.etyllica.motion.filter.modifier.QuickHullModifier;
import br.com.etyllica.motion.filter.polygon.NoiseQuickHullFilter;
import br.com.etyllica.motion.filter.wand.MagicWandConvexFilter;
import br.com.etyllica.util.SVGColor;

public class FaceSampledStatic extends Application{

	private FakeCamera cam = new FakeCamera();

	private CrossSearch blackFilter = new CrossSearch();
	
	private CrossSearch whiteFilter = new CrossSearch();
	
	//private SkinBorderFilter skinFilter = new SkinBorderFilter(0, 0);

	private QuickHullModifier quickHullFilter = new QuickHullModifier();

	private boolean hide = false;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private Component blackSampledFeature;
	private Component lightDirection;
	private Component skinFeature;

	private Component box;

	private Polygon blackPolygon = new Polygon();
	private Polygon whitePolygon = new Polygon();
	private Polygon skinPolygon = new Polygon();


	public FaceSampledStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}		

		loadingPhrase = "Configuring Filter";
		
		ColorStrategy blackColorFilter = new ColorStrategy(Color.BLACK.getRGB());
		blackColorFilter.setTolerance(0x50);
		
		blackFilter.setColorStrategy(blackColorFilter);
		
		//border: 4 and step: 4
		blackFilter.setBorder(4);
		blackFilter.setStep(4);		
		
		//White Color
		ColorStrategy whiteColorFilter = new ColorStrategy(Color.WHITE.getRGB());
		whiteColorFilter.setTolerance(0x64);
		
		whiteFilter.setColorStrategy(whiteColorFilter);
		
		whiteFilter.setBorder(4);
		whiteFilter.setStep(4);
		
		/*skinFilter.setTolerance(0x10);
		skinFilter.setBorder(4);
		skinFilter.setStep(4);
		
		quickFilter.setRadius(18);
		quickFilter.setMinNeighboors(2);*/
		
		//quickFilter.setRadius(20);

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();

		//Sampled
		blackSampledFeature = blackFilter.filterFirst(b, new Component(w, h));
		blackPolygon.reset();

		//TODO Separate polygons
		box = quickFilter.filter(b, blackSampledFeature).get(0);

		//blackPolygon = quickFilter.getPolygon();
		blackPolygon = new Polygon(quickFilter.getPolygon().xpoints, quickFilter.getPolygon().ypoints, quickFilter.getPolygon().npoints);
		
		
		//White Component
		lightDirection = whiteFilter.filter(b, new Component(w, h)).get(0);
		whitePolygon.reset();
		quickFilter.filter(b, lightDirection).get(0);
		whitePolygon = new Polygon(quickFilter.getPolygon().xpoints, quickFilter.getPolygon().ypoints, quickFilter.getPolygon().npoints);
		
		skinFeature = skinFilter.filter(b, new Component(w, h)).get(0);
		skinPolygon.reset();
		quickFilter.filter(b, skinFeature).get(0);
		skinPolygon = new Polygon(quickFilter.getPolygon().xpoints, quickFilter.getPolygon().ypoints, quickFilter.getPolygon().npoints);
		
	}

	private List<Component> separateComponents(Component feature){

		List<Component> result = new ArrayList<Component>();

		List<Point2D> points = new ArrayList<Point2D>(feature.getPoints());

		Component currentComponent = new Component(0, 0);		

		currentComponent.add(points.get(0));

		int p = 1;

		Point2D pt = points.get(p);		

		final int radius = quickFilter.getRadius();

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
		float difY = (y - cy)*(y - cy);

		return difX + difY < radius*radius;

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			cam.nextFrame();
			reset(cam.getBufferedImage());
		}

		else if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){
			cam.previousFrame();
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}

		if(event.isKeyDown(KeyEvent.TSK_C)){
			drawCleanedOnly = !drawCleanedOnly;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_B)){
			drawBox = !drawBox;
		}


		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {
		
		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);

		g.setColor(SVGColor.BLACK);
		g.setBasicStroke(2);
		g.drawPolygon(blackPolygon);
		
		g.setColor(SVGColor.WHITE);
		g.setBasicStroke(1);
		
		g.setColor(SVGColor.ORANGE);
		g.drawPolygon(whitePolygon);
		
		g.setColor(SVGColor.BLUE_VIOLET);
		g.drawPolygon(skinPolygon);
		
		g.setAlpha(60);
		drawFeaturedPoints(g, blackSampledFeature, Color.GREEN);
		g.setAlpha(100);
		
		if(drawBox){
			//drawBox(g);	
		}

	}
	
	private void drawBox(Graphic g){
		if(box!=null){
			g.setColor(Color.BLACK);
			g.drawRect(box.getLowestX(), box.getLowestY(), box.getW(), box.getH());
		}
		
		//DrawSkin
			
		g.setColor(SVGColor.CORAL);
		
		int bn = 1;
		int bx = 0;
		int by = 0;
		
		int step = 2;
		
		 if(box!=null){
			for(int j=box.getLowestY();j<box.getLowestY()+box.getH();j+=step){

				for(int i=box.getLowestX();i<box.getLowestX()+box.getW();i+=step){
					
					int rgb = cam.getBufferedImage().getRGB(i, j);
					
					if(blackPolygon.contains(i, j)){
					
						if(blackFilter.isColor(Color.BLACK.getRGB(), rgb, 0x55)){
							g.setColor(Color.BLACK);
							
							if(blackFilter.isSkin(rgb)){
								g.setColor(Color.RED);
							}
							
						}else if(blackFilter.isSkin(rgb)){
							g.setColor(Color.BLUE);
						}else{
							g.setColor(Color.GREEN);
						}
						
						
						g.fillRect(i,j,1,1);
						
						bn++;
						bx+=i;
						by+=j;
						
					}
										
				}

			}
		}
		
		g.setColor(Color.BLACK);
		g.fillCircle(xOffset+(int)bx/bn, yOffset+(int)by/bn, 10);
		g.setColor(Color.WHITE);
		g.fillCircle(xOffset+(int)bx/bn, yOffset+(int)by/bn, 5);
		
		
	}

	private void drawFeaturedPoints(Graphic g, Component feature, Color color){

		int radius = quickFilter.getRadius();

		if(!drawCleanedOnly){
			for(Point2D ponto: feature.getPoints()){

				g.setColor(color);
				g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

				g.setColor(Color.WHITE);
				g.drawCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), radius);

			}
		}

		List<Point2D> cleanedPoints = cleanPoints(feature.getPoints());
		int pn = cleanedPoints.size();
		
		double px = 0;
		double py = 0;

		for(Point2D point: cleanedPoints){
			g.setColor(Color.BLUE);
			g.fillCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), 5);

			g.setColor(Color.WHITE);
			g.drawCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), radius);

			px+=point.getX();
			py+=point.getY();
		}

		//Draw Middle Point
		g.setColor(Color.BLACK);
		g.fillCircle(xOffset+(int)px/pn, yOffset+(int)py/pn, 10);
		g.setColor(Color.RED);
		g.fillCircle(xOffset+(int)px/pn, yOffset+(int)py/pn, 5);

	}

	private List<Point2D> cleanPoints(List<Point2D> points){

		List<Point2D> cleanPoints = new ArrayList<Point2D>();

		double radius = quickFilter.getRadius();

		for(int i=0;i<points.size()-1;i++){

			Point2D point = points.get(i);

			for(int j=i+1;j<points.size();j++){

				Point2D pointJ = points.get(j);

				if(insideCircle(point.getX(), point.getY(), radius, pointJ.getX(), pointJ.getY())){
					cleanPoints.add(pointJ);
					break;
				}

			}

		}

		return cleanPoints;

	}

	private boolean insideCircle(double cx, double cy, double radius, double px, double py){

		double difX = (px - cx)*(px - cx);
		double difY = (py - cy)*(py - cy);

		return difX + difY < radius*radius;

	}

}
