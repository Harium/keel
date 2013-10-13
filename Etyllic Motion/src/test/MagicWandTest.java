package test;

import java.awt.Color;
import java.awt.Polygon;
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
import br.com.etyllica.motion.custom.wand.MagicWandFilter;

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

	private int currentImage = 1;
	
	@Override
	public void load() {

		int IMAGES_TO_LOAD = 2;
		
		for(int i=0;i<IMAGES_TO_LOAD;i++){
			tests.add(ImageLoader.getInstance().getImage("wand/wand"+Integer.toString(i)+".png"));
		}
		
		filter.setW(tests.get(currentImage).getWidth());
		filter.setH(tests.get(currentImage).getHeight());

		findAngle(tests.get(currentImage));

		loading = 100;
	}

	Polygon polygon = new Polygon();
	
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

		return 0;
	}
	
	private void findPoints(int i, int j, BufferedImage b){
		
		int w = b.getWidth();
		int h = b.getHeight();

		int offsetX = 0;
		int offsetY = 0;
		
		polygon.addPoint(i, j);
		
		if(validateColor(b.getRGB(i+1, j))){
			
			offsetX = 1;
			offsetY = 0;
			findRightPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);
			
		}else if(validateColor(b.getRGB(i+1, j+1))){
			
			offsetX = 1;
			offsetY = 1;
			findRightPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);
			
		}

		if(validateColor(b.getRGB(i-1, j+1))){
			
			offsetX = -1;
			offsetY = 1;
			findRightPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);
			
		}else if(validateColor(b.getRGB(i, j+1))){
			
			offsetX = 0;
			offsetY = 1;
			findRightPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);
			
		}		
		
	}
	
	private void findRightPoints(int i, int j, int offsetX, int offsetY, BufferedImage b){
		
		if(validateColor(b.getRGB(i+offsetX, j+offsetY))){
			
			findRightPoints(i+offsetX, j+offsetY, offsetX, offsetY, b);
			
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
				
		for(int i=0; i<polygon.npoints; i++){
			g.fillCircle(xImage+polygon.xpoints[i], yImage+polygon.ypoints[i], 5);
			//draw Line
		}		

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

}
