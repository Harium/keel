package examples.medium.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.custom.AverageColorFilter;
import br.com.etyllica.motion.filter.TrackingByNegativeColorFilter;
import br.com.etyllica.motion.filter.validation.CountComponentPoints;
import br.com.etyllica.motion.filter.validation.MaxComponentDimension;
import br.com.etyllica.motion.filter.validation.MinComponentDimension;

public class MelanomaFinderApplication extends Application {

	private BufferedImage buffer;
	
	private TrackingByNegativeColorFilter skinFilter;
		
	private Component screen;
	
	private List<Component> candidates;
	
	private Color averageColor;
		
	public MelanomaFinderApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		//Create the image with elements
		buffer = new BufferedLayer("melanoma/melanoma1.png").getModifiedBuffer();

		AverageColorFilter filter = new AverageColorFilter();
		filter.process(buffer);
		
		averageColor = filter.getColor();
		
		int width = buffer.getWidth();
		
		//Define the area to search for elements
		screen = new Component(0, 0, width, buffer.getHeight());
		
		//Define skin filter
		skinFilter = new TrackingByNegativeColorFilter(w, h, averageColor, 30);
		
		skinFilter.addValidation(new CountComponentPoints(50));
		skinFilter.addValidation(new MinComponentDimension(20));
		skinFilter.addValidation(new MaxComponentDimension(width/2));
		
		loading = 80;
		
		loadingPhrase = "Start Filter";

		candidates = skinFilter.filter(buffer, screen);
		
		loadingPhrase = "Filter Complete";
				
	}
		
	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(buffer, 0, 0);
		
		g.setAlpha(90);
		
		int biggestArea = 0;
		
		Component biggestComponent = candidates.get(0);
		
		//Draw a red line around the black components
		for(int i=0;i<candidates.size(); i++) {
			
			Component candidate = candidates.get(i);
			
			if(candidate.getArea() > biggestArea) {
				biggestComponent = candidate;
				biggestArea = candidate.getArea();
			}
			
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.BLACK);
			g.drawPolygon(candidates.get(i).getBoundingBox());
		}
		
		//Draw Biggest Component
		g.setColor(Color.BLUE);
		g.drawPolygon(biggestComponent.getBoundingBox());
		
		g.setColor(averageColor);
		g.fillRect(0, 0, 40, 30);
		
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
	
}
