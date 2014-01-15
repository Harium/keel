package br.com.etyllica.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.loader.ImageLoader;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.hollowcontroller.FindRedLedFilter;

public class HollowTest extends Application{

	private List<Component> rootComponent = new ArrayList<Component>();
	private FindRedLedFilter filter;

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
		filter = new FindRedLedFilter((int)w, (int)h);
		
		rootComponent.add(new Component((int)w,(int)h));

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

		List<Component> components = filter.filter(test, rootComponent);
		
		Color color = Color.RED;
		if(components.size()==8){
			color = Color.BLUE;
		}
		
		for(Component component: components){
			g.setColor(color);
			g.drawRect(component.getCamada());
			g.setColor(Color.WHITE);
			g.drawStringShadow(component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Integer.toString(component.getNumeroPontos()),Color.BLACK);
		}		
		
	}



}
