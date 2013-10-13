package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.Tecla;
import br.com.etyllica.core.loader.ImageLoader;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.custom.wand.MagicWandFilter;
import br.com.etyllica.motion.features.Component;

public class MagicWandTest extends Application{

	private MagicWandFilter filter = new MagicWandFilter(0, 0);

	private boolean hide = false;
	private boolean pixels = true;

	public MagicWandTest(int w, int h) {
		super(w, h);
	}

	private List<BufferedImage> tests = new ArrayList<BufferedImage>();

	private int xImage = 30;
	private int yImage = 30;

	private int currentImage = 2;

	private Component component;

	@Override
	public void load() {

		int IMAGES_TO_LOAD = 3;

		loadingPhrase = "Loading Images";
		
		for(int i=0;i<IMAGES_TO_LOAD;i++){
			tests.add(ImageLoader.getInstance().getImage("wand/wand"+Integer.toString(i)+".png"));
		}

		loadingPhrase = "Configuring Filter";
		
		int w = tests.get(currentImage).getWidth();
		int h = tests.get(currentImage).getHeight();
		
		filter.setW(w);
		filter.setH(h);

		component = new Component(w, h);

		loadingPhrase = "Filtering...";
		
		findAngle(tests.get(currentImage));

		loading = 100;
	}

	private double findAngle(BufferedImage b){

		int w = b.getWidth();
		int h = b.getHeight();

		//Find First Point
		boolean found = false;

		int i = 0;
		int j = 0;

		for(j=0;j<h;j++){

			for(i=0;i<w;i++){

				if(validateColor(b.getRGB(i, j))){

					found = true;
					break;

				}

			}

			if(found){
				break;
			}

		}


		//Points are to Down, Left or Right
		findPoints(i, j, b);

		turnIntoBox(component);

		return 0;
	}

	private void findPoints(int i, int j, BufferedImage b){

		int w = b.getWidth();
		int h = b.getHeight();

		int offsetX = 0;
		int offsetY = 0;

		System.out.println("Found: "+i+" "+j);
		component.add(i, j);

		if(validateColor(b.getRGB(i+1, j))){

			offsetX = 1;
			offsetY = 0;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);

		}else if(validateColor(b.getRGB(i+1, j+1))){

			offsetX = 1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);

		}

		if(validateColor(b.getRGB(i-1, j+1))){

			offsetX = -1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);

		}else if(validateColor(b.getRGB(i, j+1))){

			offsetX = 0;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);

		}

		System.out.println("Return: "+i+" "+j);

	}

	private Ponto2D a;
	private Ponto2D b;
	private Ponto2D c;
	private Ponto2D d;

	private void turnIntoBox(Component component){
		
		System.out.println("Degenerating "+component.getPoints().size()+" points into 4.");

		a = component.getPoints().get(0); //Lower X
		b = component.getPoints().get(0); //Lower equal Y
		c = component.getPoints().get(0); //Higher Y
		d = component.getPoints().get(0); //Higher equal X

		for(Ponto2D point: component.getPoints()){

			if(point.getX()<a.getX()){
				a = point;
			}else if(point.getY()>=c.getY()){

				if(point.getY()>c.getY()||point.getX()<c.getX()){
					c = point;
				}

			}
			
			if(point.getY()<=b.getY()){
				b = point;
			}else if(point.getX()>=d.getX()){
				d = point;
			}

		}

	}

	private void findOtherPoints(int i, int j, int offsetX, int offsetY, BufferedImage b){

		if(validateColor(b.getRGB(i+offsetX, j+offsetY))){

			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);

		}else{
			findPoints(i, j, b);
		}

	}

	private boolean validateColor(int rgb){
		return filter.isBlack(rgb);
	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {

		if(event.getPressed(Tecla.TSK_H)){
			hide = !hide;
		}

		if(event.getPressed(Tecla.TSK_P)){
			pixels = !pixels;
		}

		return GUIEvent.NONE;
	}


	@Override
	public void draw(Grafico g) {

		g.drawImage(tests.get(currentImage), xImage, yImage);		

		g.setColor(Color.BLUE);

		for(Ponto2D ponto: component.getPoints()){
			g.fillCircle(xImage+(int)ponto.getX(), yImage+(int)ponto.getY(), 5);
			//draw Line
		}
		
		drawBox(g);

	}
	
	private void drawBox(Grafico g){
				
		g.setColor(Color.RED);
		
		drawLine(g, a, b);
		drawLine(g, a, c);
		
		drawLine(g, b, d);
		drawLine(g, c, d);
		
		drawPoint(g, a);
		drawPoint(g, b);
		drawPoint(g, c);
		drawPoint(g, d);
		

		g.setColor(Color.BLACK);
		g.drawString("A", xImage+(int)a.getX()-20, yImage+(int)a.getY()-10);
		g.drawString("B", xImage+(int)b.getX()+15, yImage+(int)b.getY()-10);
		
		g.drawString("C", xImage+(int)c.getX()-20, yImage+(int)c.getY()+10);
		g.drawString("D", xImage+(int)d.getX()+15, yImage+(int)d.getY()+10);
		
	}
	
	private void drawLine(Grafico g, Ponto2D a, Ponto2D b){
		
		g.drawLine(xImage+(int)a.getX(), yImage+(int)a.getY(), xImage+(int)b.getX(), yImage+(int)b.getY());
		
	}

	private void drawPoint(Grafico g, Ponto2D point){
		g.fillCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 3);
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

}
