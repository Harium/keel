package examples.medium.ogr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.linear.graph.WeightEdge;
import br.com.etyllica.motion.core.SearchStrategy;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.modifier.ogr.LetterOGRModifier;

public class OGRApplication extends Application {

	private BufferedLayer image;

	private ColorFilter blackFilter;
	
	private List<Component> blackComponents;
	private Map<Component, Graph<Integer>> graphs = new HashMap<Component, Graph<Integer>>();

	private Component screen;
	
	private Color TEXT_COLOR = new Color(87, 85, 86);
	
	private LetterOGRModifier ogr;

	public OGRApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
		//Define the area to search for elements
		
		//image = new BufferedLayer("ogr/livro.jpg");
		//blackFilter = new HardColorFilter(w, h, TEXT_COLOR);
		
		image = new BufferedLayer("ogr/pt-sans.png");
		blackFilter = new ColorFilter(w, h, Color.BLACK);
		
		screen = new Component(0, 0, image.getW(), image.getH());
		
		loading = 10;
		//Create the image with elements
		
		//Define blue and black filters
		//
		
		blackFilter.setTolerance(20);
		//blackFilter.addValidation(new MaxDimensionValidation(20));
				
		SearchStrategy floodFill = blackFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		loading = 20;
		
		ogr = new LetterOGRModifier();
		
		//Filter the image
		blackComponents = blackFilter.filter(image.getBuffer(), screen);
		
		for(Component component: blackComponents) {
			graphs.put(component, ogr.modify(component));
		}
		
		loading = 50;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setAlpha(100);
		image.draw(g);

		g.setAlpha(90);

		//Draw a red line around the black components
		
		for(int i = 0; i < blackComponents.size(); i++) {
			
			Component component = blackComponents.get(i);
			
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawRect(component.getRectangle());
			
			g.setColor(Color.BLUE);
			Graph<Integer> graph = graphs.get(component);
			if (graph != null)
				drawGraph(g, graphs.get(component));
		}
		
	}
	
	
	
	@Override
	public void updateMouse(PointerEvent event) {

		/*if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			Color color = pickColor(event.getX(), event.getY());

			System.out.println(color.getRed());
			System.out.println(color.getGreen());
			System.out.println(color.getBlue());
			System.out.println("---------");
		}*/
	}
	
	private Color pickColor(int px, int py) {
		return new Color(image.getBuffer().getRGB(px, py));
	}
	
	private void drawGraph(Graphics g, Graph<Integer> graph) {
		for(WeightEdge<Integer> edge: graph.getEdges()) {
			g.drawLine(edge.getOrigin().getPoint(), edge.getDestination().getPoint());
		}
		
		for(Node<Integer> node: graph.getNodes()) {
			g.fillCircle(node.getPoint(), 4);
		}
	}

}
