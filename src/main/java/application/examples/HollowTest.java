package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.loader.image.ImageLoader;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.RedLedFilter;

public class HollowTest extends Application {

	private Component screen;
	private RedLedFilter filter;

	private BufferedImage test;

	public HollowTest(int w, int h) {
		super(w, h);
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public void load() {

		//TODO Change to Camera Size in Real Application
		filter = new RedLedFilter(w, h);
		
		screen = new Component(w, h);

		BufferedImage test1 = ImageLoader.getInstance().getImage("test/test1.png");
		BufferedImage test2 = ImageLoader.getInstance().getImage("test/test2.png");
		BufferedImage test3 = ImageLoader.getInstance().getImage("test/test3.png");
		BufferedImage test7 = ImageLoader.getInstance().getImage("test/test7.png");
		BufferedImage test8 = ImageLoader.getInstance().getImage("test/test8.png");
		
		test = test7;

		loading = 100;
	}

	@Override
	public void draw(Graphic g) {

		g.drawImage(test, 0, 0);

		List<Component> components = filter.filter(test, screen);
		
		Color color = Color.RED;
		if(components.size()==8){
			color = Color.BLUE;
		}
		
		for(Component component: components){
			g.setColor(color);
			g.drawRect(component.getLayer());
			g.setColor(Color.WHITE);
			g.drawStringShadow(component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Integer.toString(component.getPointCount()),Color.BLACK);
		}		
		
	}



}
