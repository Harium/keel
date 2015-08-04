package examples.medium.ogr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.core.SearchStrategy;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.HardColorFilter;
import br.com.etyllica.motion.filter.search.FloodFillSearch;
import br.com.etyllica.motion.filter.search.SoftFloodFillSearch;

public class OGRApplication extends Application {

	private BufferedLayer image;

	private HardColorFilter blackFilter;
	
	private List<Component> blackComponents;

	private Component screen;
	
	private Color TEXT_COLOR = new Color(87, 85, 86);

	public OGRApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
		//Define the area to search for elements
		screen = new Component(0, 0, w, h);

		image = new BufferedLayer("ogr/livro.jpg");

		loading = 10;
		//Create the image with elements
		
		//Define blue and black filters
		blackFilter = new HardColorFilter(w, h, TEXT_COLOR);
		blackFilter.setTolerance(54);
		//blackFilter.addValidation(new MaxDimensionValidation(20));
				
		SearchStrategy floodFill = blackFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		loading = 20;
		//Filter the image
		blackComponents = blackFilter.filter(image.getBuffer(), screen);

		loading = 25;
						
		loading = 31;
				
		loading = 50;

	}
	
	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		image.draw(g);

		g.setAlpha(90);

		//Draw a red line around the black components
		
		for(int i = 0; i < blackComponents.size(); i++) {
			
			Component component = blackComponents.get(i);
			
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawRect(component.getRectangle());
		}
		
	}
	
	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			Color color = pickColor(event.getX(), event.getY());

			System.out.println(color.getRed());
			System.out.println(color.getGreen());
			System.out.println(color.getBlue());
			System.out.println("---------");
		}

		return GUIEvent.NONE;
	}
	
	private Color pickColor(int px, int py) {
		return new Color(image.getBuffer().getRGB(px, py));
	}

}
