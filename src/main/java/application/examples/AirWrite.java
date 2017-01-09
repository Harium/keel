package application.examples;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.gesture.GestureRegex;
import br.com.etyllica.motion.core.gesture.PolygonMatcher;
import br.com.etyllica.motion.core.source.BufferedImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;

public class AirWrite extends Application {

	private CameraV4L4J cam;
	private BufferedImageSource source = new BufferedImageSource();

	private ColoredPointSearch colorFilter;
	
	private ColorStrategy colorStrategy;

	private final int NUMBER_OF_POINTS = 45;

	private List<Point2D> points;

	private boolean hide = false;
	private boolean pixels = true;
	private boolean freeze = false;

	private int xImage = 0;
	private int yImage = 0;
	
	private String match = "_";
	
	private Component component = new Component((int)w,(int)h);
	
	private PolygonMatcher matcher = new PolygonMatcher();
	
	private BufferedImage mirror;

	public AirWrite(int w, int h) {
		super(w, h);
	}

	private Component screen;
	
	@Override
	public void load() {

		loadingInfo = "Open Camera";

		cam = new CameraV4L4J(0);
		
		screen = new Component(0, 0, cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());
		
		loadingInfo = "Setting PolygonMatcher";
		matcher.setMinDistance(8);		
		
		loadingInfo = "Setting Filter";

		colorStrategy = new ColorStrategy(Color.BLACK);
		
		colorFilter = new ColoredPointSearch(cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());
		colorFilter.setBorder(95);
		colorFilter.setPixelStrategy(colorStrategy);
		
		points = component.getPoints();
		
		for(int i=0;i<NUMBER_OF_POINTS;i++){
			points.add(new Point2D(0,0));
		}

		loading = 100;
	}

	private void reset(BufferedImage b){
		source.setImage(b);
		
		Component point = colorFilter.filterFirst(source, screen);

		if(!freeze){

			if(point!=null){

				Point2D firstPoint = points.get(0);

				firstPoint.setLocation(point.getX(), point.getY());

				Collections.rotate(points, -1);
			}
		}

	}

	@Override
	public void updateMouse(PointerEvent event) {
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
			colorStrategy.setColor(mirror.getRGB((int)event.getX(), (int)event.getY()));
		}
	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.VK_P)){
			pixels = !pixels;
		}
		
		if(event.isKeyDown(KeyEvent.VK_SPACE)){
			freeze = !freeze;
		}
		
		if(event.isKeyDown(KeyEvent.VK_ESC)){
			match = "_";
		}
		
		if(event.isKeyDown(KeyEvent.VK_EQUALS)){
			colorStrategy.setOffsetTolerance(+1);
		}
		
		if(event.isKeyDown(KeyEvent.VK_MINUS)){
			colorStrategy.setOffsetTolerance(-1);
		}
		
		if(event.isKeyDown(KeyEvent.VK_UP_ARROW)){
			colorFilter.setBorder(colorFilter.getBorder()+1);
			System.out.println("Border: "+colorFilter.getBorder());
		}
		
		if(event.isKeyDown(KeyEvent.VK_DOWN_ARROW)){
			colorFilter.setBorder(colorFilter.getBorder()-1);
		}

	}

	@Override
	public void draw(Graphics g) {
		
		BufferedImage b = cam.getBufferedImage();
		
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);

		tx.translate(-b.getWidth(), 0);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		mirror = op.filter(b, null); 

		reset(mirror);

		//reset(b);

		g.drawImage(mirror, xImage, yImage);
		
		g.setColor(colorStrategy.getColor());
		g.fillRect(0, 0, 30, 30);
		g.setColor(Color.WHITE);
		g.drawStringShadow(Integer.toString(colorStrategy.getMaxToleranceRed()), 10, 15);
		
		int border = colorFilter.getBorder();
		
		g.drawRect(border, border, mirror.getWidth()-border*2, mirror.getHeight()-border*2);
		
		g.setColor(Color.WHITE);

		for(Point2D point: points){

			g.setColor(Color.WHITE);
			g.fillCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 5);
			g.setColor(Color.BLACK);
			g.drawCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 5);

		}

		String regex = matcher.toRegExp(points);
		
		if(checkRegex(regex)){
			resetPoints();
		}
		

		g.drawStringShadow(regex, 20, 20);

		g.drawStringShadow(match, 50, 50);

	}
	
	private void resetPoints(){
				
		for(Point2D point: points){
			point.setLocation(0,0);
		}
		
	}
	
	private boolean checkRegexNumber(String regex){
		
		if(regex.matches(GestureRegex.ONE)){
			match += "1";
			return true;
		}
		
		if(regex.matches(GestureRegex.TWO)){
			match += "2";
			return true;
		}
		
		if(regex.matches(GestureRegex.THREE)){
			match += "3";
			return true;
		}
		
		if(regex.matches(GestureRegex.FOUR)){
			match += "4";
			return true;
		}
		
		if(regex.matches(GestureRegex.FIVE)){
			match += "5";
			return true;
		}
		
		if(regex.matches(GestureRegex.SIX)){
			match += "6";
			return true;
		}
		
		if(regex.matches(GestureRegex.SEVEN)){
			match += "7";
			return true;
		}
		
		if(regex.matches(GestureRegex.EIGHT)){
			match += "8";
			return true;
		}
		
		if(regex.matches(GestureRegex.NINE)||regex.matches(GestureRegex.NINE_CCW)){
			match += "9";
			return true;
		}
		
		return false;
		
	}
	
	
	private boolean checkRegex(String regex){
		
		if(checkRegexNumber(regex)){
			return true;
		}
		
		if(regex.matches(GestureRegex.PLUS)||regex.matches(GestureRegex.PLUS_LEFT_HANDED)){
			match += "+";
			return true;
		}
		
		if(regex.matches(GestureRegex.RIGHT_ARROW)){
			match += "→";
			return true;
		}else if(regex.matches(GestureRegex.LEFT_ARROW)){
			match += "←";
			return true;
		}else if(regex.matches(GestureRegex.UP_ARROW)){
			match += "↑";
			return true;
		}else if(regex.matches(GestureRegex.DOWN_ARROW)||regex.matches(GestureRegex.DOWN_ARROW_LEFT_HANDED)){
			match += "↓";
			return true;
		}
		
		
				
		return false;
	}

}
