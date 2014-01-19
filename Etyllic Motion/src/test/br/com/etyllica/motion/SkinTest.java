package test.br.com.etyllica.motion;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.loader.image.ImageLoader;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.debug.Tester;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.face.ExtremelySimpleFindSkinFilter;
import br.com.etyllica.motion.filter.face.FindEyeFilter;

public class SkinTest extends Application{

	private Component screen;
	private ExtremelySimpleFindSkinFilter filter;
	private FindEyeFilter eyesFilter;

	private Color pickColor = Color.WHITE;
	private boolean hide = false;
	private boolean pixels = true;

	public SkinTest(int w, int h) {
		super(w, h);		
	}

	private int currentTest = 0;
	private List<BufferedImage> tests = new ArrayList<BufferedImage>();

	@Override
	public void load() {

		//TODO Change to Camera Size in Real Application
		//filter = new FindSkinFilter(640, 480);
		filter = new ExtremelySimpleFindSkinFilter(640, 480);
		eyesFilter = new FindEyeFilter(640, 480);

		screen = new Component(640, 480);

		//UnitTests
		unitTests();

		//Load Images

		final int IMAGES_TO_LOAD = 50;

		for(int i=1;i<=IMAGES_TO_LOAD;i++){

			tests.add(ImageLoader.getInstance().getImage("skin/skin"+Integer.toString(i)+".jpg"));

			loadingPhrase = "Carregando Imagem "+i+"...";
			loading = (i*100)/IMAGES_TO_LOAD;

		}


		loading = 100;
	}

	private void unitTests(){

		int rgb = new Color(188,189,233).getRGB();
		
		//Right Tests
		rgb = new Color(163,96,77).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(97,64,55).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(202,158,181).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(161,123,134).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(158,110,88).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(93,72,81).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		//High Contrast
		rgb = new Color(255,227,205).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		//Medium Illumination
		rgb = new Color(90,82,71).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(90,83,99).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(88,86,63).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		
		System.out.println("\nNew Tests");
		
		rgb = new Color(188,189,233).getRGB();
		//Gray/Blue Photos
		Tester.test(filter.validateColor(rgb), true);

		rgb = new Color(100,87,105).getRGB();
		Tester.test(filter.validateColor(rgb), true);

		//Asian Face
		rgb = new Color(142,134,132).getRGB();
		Tester.test(filter.validateColor(rgb), true);

		rgb = new Color(114,114,126).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		//High Contrast
		rgb = new Color(130,70,8).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(229,163,147).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(191,143,123).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(205,139,141).getRGB();
		Tester.test(filter.validateColor(rgb), true);

		//False Noises
		rgb = new Color(167,166,197).getRGB();
		Tester.test(filter.validateColor(rgb), true);

		//Dark Pixels
		rgb = new Color(100,86,79).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		rgb = new Color(110,107,114).getRGB();
		Tester.test(filter.validateColor(rgb), true);
		
		System.out.println("\nNoises");
		//Wall (Light Noises)
		rgb = new Color(165,156,115).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		rgb = new Color(105,99,99).getRGB();
		Tester.test(filter.validateColor(rgb), false);

		rgb = new Color(94,88,88).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		//Bizarre Colored
		rgb = new Color(198,91,63).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		//Light Wall
		rgb = new Color(192,149,130).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		rgb = new Color(237,222,179).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		//Dark Noise
		rgb = new Color(93,82,86).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		
		rgb = new Color(118,108,99).getRGB();
		Tester.test(filter.validateColor(rgb), false);
		

	}	

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){
			pickColor = new Color(tests.get(currentTest).getRGB((int)event.getX(), (int)event.getY()));

			int rgb = pickColor.getRGB();

			filter.validateColor(rgb);
			
			System.out.println("good.addPoint("+pickColor.getRed()+","+pickColor.getGreen()+","+pickColor.getBlue()+");");
		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_MIDDLE)){
			
			previousImage();
			
		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)){
			pickColor = new Color(tests.get(currentTest).getRGB((int)event.getX(), (int)event.getY()));

			//nextImage();
			System.out.println("bad.addPoint("+pickColor.getRed()+","+pickColor.getGreen()+","+pickColor.getBlue()+");");
		}

		return GUIEvent.NONE;
	}

	private void previousImage(){
		currentTest+=tests.size()-1;
		currentTest%=tests.size();
	}

	private void nextImage(){
		currentTest++;
		currentTest%=tests.size();
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}

		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			nextImage();
		}
		if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){
			previousImage();
		}

		return GUIEvent.NONE;
	}


	@Override
	public void draw(Graphic g) {

		BufferedImage test = tests.get(currentTest); 

		g.drawImage(test, 0, 0);

		List<Component> components = filter.filter(test, screen);

		//eyesFilter.filter(bimg, components);

		Collections.sort(components);

		for(Component component: components){

			if(pixels){

				g.setAlpha(50);

				g.setColor(Color.BLUE);
				for(Point2D point: component.getPoints()){
					g.fillRect((int)point.getX(),(int)point.getY(), 1, 1);
				}

			}

			g.setAlpha(80);
			g.setColor(Color.GREEN);
			g.drawRect(component.getCamada());
			g.setColor(Color.WHITE);
			g.drawStringShadow(component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Integer.toString(component.getNumeroPontos()),Color.BLACK);
		}

		//Draw Bigger component
		if(components.size()>0){

			g.setColor(Color.RED);
			g.drawRect(components.get(0).getCamada());

		}

		if(!hide){
			g.setColor(pickColor);
			g.fillRect(600, 0, 40, 60);

			g.setColor(Color.WHITE);
			g.drawShadow(600, 10, Integer.toString(pickColor.getRed()));
			g.drawShadow(600, 30, Integer.toString(pickColor.getGreen()));
			g.drawShadow(600, 50, Integer.toString(pickColor.getBlue()));
		}

	}



}
