package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.RedLedFilter;


public class HollowController extends Application {

	private Component screen;

	public HollowController(int w, int h) {
		super(w,h);
	}

	private CameraV4L4J cam;

	private BufferedImage buf;

	private RedLedFilter ledFilter;
	
	private ColorFilter activeFilter;

	private List<Component> lastButtons;

	private List<Component> components;

	@Override
	public void load() {

		cam = new CameraV4L4J(0);
		
		final int w = cam.getBufferedImage().getWidth();
		final int h = cam.getBufferedImage().getHeight();

		screen = new Component(w,h);

		//Loading Filters
		ledFilter = new RedLedFilter(w, h);
		activeFilter = new ColorFilter(w, h, Color.WHITE);

		lastButtons = new ArrayList<Component>(8);

		loading = 100;

	}

	@Override
	public void timeUpdate(long now){

		System.out.println("TIME UPDATE");

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event){

		if(event.isKeyDown(KeyEvent.TSK_R)){
			activated = false;
		}
		
		return GUIEvent.NONE;
	}

	private boolean activated = false;

	@Override
	public void draw(Graphic g) {

		buf = cam.getBufferedImage();

		g.drawImage(buf,0,0);

		activated = false;
		
		for(Component component: lastButtons){

			List<Component> active = activeFilter.filter(buf, component);

			Color color = Color.YELLOW;

			if(active!=null){
				
				color = Color.RED;
				activated = true;
				
			}
			g.setColor(color);

			g.drawRect(component.getLayer());			

		}

		if(!activated){

			components = ledFilter.filter(buf, screen);

			if(components!=null){

				Color color = Color.GREEN;
				if(components.size()==8){

					lastButtons.clear();
					lastButtons.addAll(components);

					color = Color.BLUE;
				}

				for(Component component: components){
					g.setColor(color);
					g.drawRect(component.getLayer());
					/*g.setColor(Color.WHITE);
					g.escreveLabelSombra(component.getMenorX(), component.getMenorY(), component.getW(), component.getH(), Integer.toString(component.getNumeroPontos()),Color.BLACK);*/
				}

			}
		}

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent	.NONE;
	}

}
