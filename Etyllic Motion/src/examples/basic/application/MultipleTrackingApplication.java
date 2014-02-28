package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class MultipleTrackingApplication extends Application{

	private BufferedImage buffer;
	
	private FloodFillSearch blueFilter;
	
	private FloodFillSearch blackFilter;
	
	private Component screen;
	
	private List<Component> blueComponents;
	
	private List<Component> blackComponents;
	
	public MultipleTrackingApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		
		screen = new BoundingComponent(w, h);
		
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		drawBuffer(w, h);
		
		blackFilter = new FloodFillSearch(w, h);
		blackFilter.setColorStrategy(new ColorStrategy(Color.BLACK.getRGB()));
		
		blueFilter = new FloodFillSearch(w, h);
		blueFilter.setColorStrategy(new ColorStrategy(Color.BLUE.getRGB()));		
		
		
		blueComponents = blueFilter.filter(buffer, screen);
		
		blackComponents = blackFilter.filter(buffer, screen);
		
	}
	
	private void drawBuffer(int w, int h){
		
		Graphics2D g = buffer.createGraphics();
				
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, w, h);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(40, 40, 100, 80);
		
		g.fillRect(160, 40, 100, 80);
		
		g.fillRect(300, 200, 80, 80);
		
		g.setColor(Color.BLUE);
		
		g.fillRect(30, 200, 100, 80);
		
		g.fillOval(420, 20, 20, 200);
		
		g.fillOval(390, 100, 80, 600);
		
		
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(buffer, 0, 0);
		
		g.setAlpha(90);		
		for(Component component: blackComponents){
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawPolygon(component.getBoundingBox());
		}
		
		for(Component component: blueComponents){
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.YELLOW);
			g.drawRect(component.getRectangle());
		}
		
	}
	
}
