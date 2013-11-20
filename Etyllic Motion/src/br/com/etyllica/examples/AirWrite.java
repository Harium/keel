package br.com.etyllica.examples;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.air.PolygonMatcher;
import br.com.etyllica.motion.core.SkinColorFilter;
import br.com.etyllica.motion.features.Component;

public class AirWrite extends Application{

	private CameraV4L4J cam;

	private SkinColorFilter colorFilter;

	private final int NUMBER_OF_POINTS = 50;

	private List<Point2D> points;

	private boolean hide = false;
	private boolean pixels = true;
	private boolean freeze = false;

	private int xImage = 0;
	private int yImage = 0;
	
	private String match = "_";
	
	private PolygonMatcher matcher = new PolygonMatcher();

	public AirWrite(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Open Camera";

		cam = new CameraV4L4J(0);

		loadingPhrase = "Setting Filter";

		colorFilter = new SkinColorFilter();

		points = new ArrayList<Point2D>();

		for(int i=0;i<NUMBER_OF_POINTS;i++){
			points.add(new Point2D(0,0));
		}

		loading = 100;
	}

	private Component screen = new Component(w, h);

	private void reset(BufferedImage b){

		Point2D point = colorFilter.filterFirst(b, screen);


		if(!freeze){

			if(point!=null){

				Point2D firstPoint = points.get(0);

				firstPoint.setLocation(point);

				Collections.rotate(points, -1);
			}
		}

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_SPACE)){
			freeze = !freeze;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_ESC)){
			match = "_";
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		BufferedImage b = cam.getBufferedImage();

		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);

		tx.translate(-b.getWidth(), 0);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		BufferedImage mirror = op.filter(b, null); 

		reset(mirror);

		//reset(b);

		g.drawImage(mirror, xImage, yImage);

		for(Point2D point: points){

			g.fillCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 5);

		}

		String regex = matcher.toRegExp(points);
		
		checkRegex(regex);

		g.drawShadow(20, 20, regex);

		g.drawShadow(50, 50, match);

	}
	
	private void checkRegex(String regex){
		
		//Too Simple
		final String oneRegex = "B+.*C+.*D+";

		final String twoRegex = "B+D+.*C+.(A|B)+.*D+";
		
		final String threeRegex = "B+D+.*C+.*D+.*C+.*A+";

		/*if(regex.matches(oneRegex)){
			match += "1";
		}*/
		
		if(regex.matches(twoRegex)){
			match += "2";
		}
		
		if(regex.matches(threeRegex)){
			match += "3";
		}
		
	}

}
