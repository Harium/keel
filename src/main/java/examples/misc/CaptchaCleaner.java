package examples.misc;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.layer.Layer;

public class CaptchaCleaner extends Application {

	private BufferedImage b;
	
	private FakeCamera cam = new FakeCamera();

	private Layer background;
	private Layer foreground;

	private Color backColor = null;
	private Color foreColor = null;
	
	private String fileName;
	
	private ColorStrategy colorStrategy = new ColorStrategy();

	public CaptchaCleaner(int w, int h) {
		super(w, h);
	}

	@Override
	public void updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.VK_SPACE)){
			saveImage(fileName);
		}
		
		if(event.isKeyDown(KeyEvent.VK_RIGHT_ARROW)){
			cam.nextFrame();
			fileName = "captcha"+Integer.toString(cam.getCurrentFrame());
			reset(fileName);
		}
		
		if(event.isKeyDown(KeyEvent.VK_LEFT_ARROW)){
			cam.previousFrame();
			fileName = "captcha"+Integer.toString(cam.getCurrentFrame());
			reset(fileName);
		}
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(backColor);
		g.fillRect(background);

		g.setColor(foreColor);
		g.fillRect(foreground);
		
		g.drawImage(b, 0,0);
		
	}
	
	private final String CAPTCHA_FOLDER = "captcha/";

	@Override
	public void load() {
						
		int IMAGES_TO_LOAD = 5;
		
		fileName = "captcha0";
		
		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			
			String file = "captcha"+Integer.toString(i);
			
			cam.addImage(CAPTCHA_FOLDER+file+".bmp");
			
		}
		
		reset(fileName);
		
		loading = 100;
		
	}
	
	private void reset(String fileName){

		backColor = null;
		foreColor = null;
				
		loading = 10;

		b = cam.getBufferedImage();

		int w = b.getWidth();
		int h = b.getHeight();

		background = new Layer(100,0,100,25);
		foreground = new Layer(200,0,100,25);
		
		loading = 20;

		Map<Integer, Integer> colors = new HashMap<Integer, Integer>();

		for(int j=0;j<h;j++){

			for(int i=0;i<w;i++){

				int rgb = b.getRGB(i, j);

				if(colors.containsKey(rgb)){
					colors.put(rgb, colors.get(rgb)+1);
				}else{
					if(backColor==null){
						backColor = new Color(rgb);
					}else if(foreColor==null){
						foreColor = new Color(rgb);
					}
					colors.put(rgb, 0);
				}

			}

		}
		
		loading = 40;

		int border = 1;
		
		int line = 0;
		
		for(int i=border;i<w-border;i++){

			for(int j=border;j<h-border;j++){
				
				int rgb = b.getRGB(i, j);

				if(colorStrategy.isColor(rgb, foreColor.getRGB())){

					line++;
					
				}else{
					
					if(line==1){
						
						b.setRGB(i, j-1, backColor.getRGB());
						b.setRGB(i+1, j-1, backColor.getRGB());
					}
					
					line = 0;
				}
				
			}
		}
				
	}
	
	private void saveImage(String fileName){

		String folder = "assets/images/"+CAPTCHA_FOLDER+"clean/";
		
		try {			
			
		    File outputfile = new File(folder+fileName+".png");
		    ImageIO.write(b, "png", outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}


}


