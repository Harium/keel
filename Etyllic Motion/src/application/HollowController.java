package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.camera.Camera;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.Tecla;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.hollowcontroller.FindRedLedActivatedFilter;
import br.com.etyllica.motion.hollowcontroller.FindRedLedFilter;


public class HollowController extends Application {

	private List<Componente> rootComponent = new ArrayList<Componente>();

	public HollowController(int w, int h) {
		super(w,h);
	}

	private Camera cam;

	private BufferedImage buf;

	private FindRedLedFilter filter;
	
	private FindRedLedActivatedFilter activeFilter;

	private List<Componente> lastButtons;

	private List<Componente> components;

	@Override
	public void load() {

		cam = new Camera(0);

		rootComponent.add(new Componente(cam.getBufferedImage().getWidth(),cam.getBufferedImage().getHeight()));

		//Loading Filters
		filter = new FindRedLedFilter(cam.getBufferedImage().getWidth(),cam.getBufferedImage().getHeight());
		activeFilter = new FindRedLedActivatedFilter(cam.getBufferedImage().getWidth(),cam.getBufferedImage().getHeight());

		lastButtons = new ArrayList<Componente>(8);

		loading = 100;

	}

	@Override
	public void timeUpdate(){

		System.out.println("TIME UPDATE");

	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event){

		if(event.getPressed(Tecla.TSK_R)){
			activated = false;
		}
		
		return GUIEvent.NONE;
	}

	private boolean activated = false;

	@Override
	public void draw(Grafico g) {

		buf = cam.getBufferedImage();

		g.drawImage(buf,0,0);

		activated = false;
		
		for(Componente component: lastButtons){

			List<Componente> list = new ArrayList<Componente>();
			list.add(component);

			List<Componente> active = activeFilter.filter(buf, list);

			Color color = Color.YELLOW;

			if(active!=null){
				
				color = Color.RED;
				activated = true;
				
			}
			g.setColor(color);

			g.drawRect(component.getCamada());			

		}

		if(!activated){

			components = filter.filter(buf, rootComponent);

			if(components!=null){

				Color color = Color.GREEN;
				if(components.size()==8){

					lastButtons.clear();
					lastButtons.addAll(components);

					color = Color.BLUE;
				}

				for(Componente component: components){
					g.setColor(color);
					g.drawRect(component.getCamada());
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
