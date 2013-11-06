package test;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.camera.FakeCamera;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.custom.face.SkinBorderFilter;
import br.com.etyllica.motion.custom.wand.BorderFilter;
import br.com.etyllica.motion.custom.wand.MagicWandConvexFilter;
import br.com.etyllica.motion.features.Component;

public class FaceSampledStatic extends Application{

	private FakeCamera cam = new FakeCamera();

	private SkinBorderFilter cornerFilter = new SkinBorderFilter(0, 0);
	private BorderFilter colorFilter = new BorderFilter(0, 0);

	private MagicWandConvexFilter filter = new MagicWandConvexFilter(0, 0);

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private Component box;
	private Component feature;

	private Component sampledFeature;	

	private double angle = 0;
	
	private Polygon featurePolygon = new Polygon();
	private Polygon sampledPolygon = new Polygon();
	
	public FaceSampledStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			//cam.addImage("wand/wand"+Integer.toString(i)+".png");
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}		

		cornerFilter.setColor(Color.WHITE.getRGB());
		cornerFilter.setBorder(2);
		cornerFilter.setStep(2);
				
		colorFilter.setColor(Color.BLACK.getRGB());
		colorFilter.setBorder(4);
		colorFilter.setStep(4);

		loadingPhrase = "Configuring Filter";
		filter.setWandColor(Color.BLACK);
		filter.setTolerance(0);

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();

		filter.setW(w);
		filter.setH(h);

		feature = cornerFilter.filter(b, new Component(w, h)).get(0);

		List<Component> result = filter.filter(b, feature);

		box = result.get(0);

		//Sampled
		sampledFeature = colorFilter.filter(b, new Component(w, h)).get(0);

		//box = feature;

		featurePolygon.reset();
		sampledPolygon.reset();
		
		for(Ponto2D ponto: quickHull(feature.getPoints())){
			featurePolygon.addPoint((int)ponto.getX(), (int)ponto.getY());
		}
		
		for(Ponto2D ponto: quickHull(sampledFeature.getPoints())){
			sampledPolygon.addPoint((int)ponto.getX(), (int)ponto.getY());
		}

		angle = filter.getAngle();
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

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);

		/*if(pn>=4){

		int px = 0;
		int py = 0;
		int pn = feature.getPoints().size();

			int lpx = 0;
			int lpy = 0;

			for(Ponto2D ponto: feature.getPoints()){

				g.setColor(Color.BLACK);
				g.drawLine(lpx, lpy, (int)ponto.getX(), (int)ponto.getY());

				px += (int)ponto.getX();
				py += (int)ponto.getY();

				g.setColor(Color.BLUE);
				g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

				lpx = (int)ponto.getX();
				lpy = (int)ponto.getY();

			}

			g.setColor(Color.PINK);
			g.fillCircle(px/pn, py/pn, 10);

			if(box.getPoints().size()>3){
				drawBox(g, box);
			}

		}*/


		g.setAlpha(60);
		drawFeaturedPoints(g, sampledFeature, Color.GREEN);
		drawFeaturedPoints(g, feature, Color.BLUE);
		g.setAlpha(100);
				
		
		g.setColor(Color.BLUE);
		g.drawPolygon(featurePolygon);
		
		g.setColor(Color.GREEN);
		g.drawPolygon(sampledPolygon);

		g.drawString("Angle = "+angle, 50, 30);

	}

	private void drawBox(Graphic g, Component box){

		g.setColor(Color.RED);

		Ponto2D a = box.getPoints().get(0);
		Ponto2D b = box.getPoints().get(1);
		Ponto2D c = box.getPoints().get(2);
		Ponto2D d = box.getPoints().get(3);

		Ponto2D ac = new Ponto2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);
		Ponto2D ab = new Ponto2D((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);

		Ponto2D bd = new Ponto2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);
		Ponto2D cd = new Ponto2D((c.getX()+d.getX())/2, (c.getY()+d.getY())/2);

		drawLine(g, a, b);
		drawLine(g, a, c);

		drawLine(g, b, d);
		drawLine(g, c, d);

		drawPoint(g, a);
		drawPoint(g, b);
		drawPoint(g, c);
		drawPoint(g, d);

		g.setColor(Color.YELLOW);
		drawLine(g, ab, cd);
		drawPoint(g, ab);
		drawPoint(g, cd);

		g.setColor(Color.GREEN);
		drawLine(g, ac, bd);

		drawPoint(g, ac);
		drawPoint(g, bd);


		g.setColor(Color.BLACK);
		g.drawString("A", xOffset+(int)a.getX()-20, yOffset+(int)a.getY()-10);
		g.drawString("B", xOffset+(int)b.getX()+15, yOffset+(int)b.getY()-10);

		g.drawString("C", xOffset+(int)c.getX()-20, yOffset+(int)c.getY()+10);
		g.drawString("D", xOffset+(int)d.getX()+15, yOffset+(int)d.getY()+10);

	}

	private void drawLine(Graphic g, Ponto2D a, Ponto2D b){		
		g.drawLine(xOffset+(int)a.getX(), yOffset+(int)a.getY(), xOffset+(int)b.getX(), yOffset+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Ponto2D point){
		g.fillCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), 3);
	}

	private void drawFeaturedPoints(Graphic g, Component feature, Color color){

		for(Ponto2D ponto: feature.getPoints()){

			g.setColor(color);
			g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

		}

	}

	//From www.ahristov.com/tutorial/geometry-games/convex-hull.html
	public List<Ponto2D> quickHull(List<Ponto2D> points) {
				
		//if (points.size() < 3) return (ArrayList)points.clone();
		if (points.size() < 3) return points;
		
		List<Ponto2D> convexHull = new ArrayList<Ponto2D>();
		
		// find extremals
		int minPoint = -1, maxPoint = -1;
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() < minX) {
				minX = points.get(i).getX();
				minPoint = i;
			} 
			if (points.get(i).getX() > maxX) {
				maxX = points.get(i).getX();
				maxPoint = i;       
			}
		}
		Ponto2D A = points.get(minPoint);
		Ponto2D B = points.get(maxPoint);
		convexHull.add(A);
		convexHull.add(B);
		points.remove(A);
		points.remove(B);

		ArrayList<Ponto2D> leftSet = new ArrayList<Ponto2D>();
		ArrayList<Ponto2D> rightSet = new ArrayList<Ponto2D>();

		for (int i = 0; i < points.size(); i++) {
			Ponto2D p = points.get(i);
			if (pointLocation(A,B,p) == -1)
				leftSet.add(p);
			else
				rightSet.add(p);
		}
		hullSet(A,B,rightSet,convexHull);
		hullSet(B,A,leftSet,convexHull);

		return convexHull;
	}

	public void hullSet(Ponto2D A, Ponto2D B, ArrayList<Ponto2D> set, List<Ponto2D> hull) {
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0) return;
		if (set.size() == 1) {
			Ponto2D p = set.get(0);
			set.remove(p);
			hull.add(insertPosition,p);
			return;
		}
		double dist = Double.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++) {
			Ponto2D p = set.get(i);
			double distance  = distance(A,B,p);
			if (distance > dist) {
				dist = distance;
				furthestPoint = i;
			}
		}
		Ponto2D P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition,P);

		// Determine who's to the left of AP
		ArrayList<Ponto2D> leftSetAP = new ArrayList<Ponto2D>();
		for (int i = 0; i < set.size(); i++) {
			Ponto2D M = set.get(i);
			if (pointLocation(A,P,M)==1) {
				//set.remove(M);
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Ponto2D> leftSetPB = new ArrayList<Ponto2D>();
		for (int i = 0; i < set.size(); i++) {
			Ponto2D M = set.get(i);
			if (pointLocation(P,B,M)==1) {
				//set.remove(M);
				leftSetPB.add(M);
			}
		}
		hullSet(A,P,leftSetAP,hull);
		hullSet(P,B,leftSetPB,hull);
	}

	public double pointLocation(Ponto2D A, Ponto2D B, Ponto2D P) {
		double cp1 = (B.getX()-A.getX())*(P.getY()-A.getY()) - (B.getY()-A.getY())*(P.getX()-A.getX());
		return (cp1>0)?1:-1;
	}
	
	public double distance(Ponto2D a, Ponto2D b, Ponto2D c) {
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double num = ABx*(a.getY()-c.getY())-ABy*(a.getX()-c.getX());
		if (num < 0) num = -num;
		return num;
	}

}
