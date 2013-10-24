package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.Layer;
import br.com.etyllica.motion.custom.wand.MagicWandBoxFilter;

public class CaptchaCleaner extends Application{

	private BufferedLayer layer;
	
	private BufferedImage b;

	private Layer background;
	private Layer foreground;

	private Color backColor = Color.RED;
	private Color foreColor = Color.RED;

	public CaptchaCleaner(int w, int h) {
		super(w, h);
	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public void draw(Grafico g) {

		g.setColor(backColor);
		g.fillRect(background);

		g.setColor(foreColor);
		g.fillRect(foreground);
		
		g.drawImage(b, 0,0);
		
	}
	
	private final String CAPTCHA_FOLDER = "captcha/";

	@Override
	public void load() {
				
		String fileName = "captcha";
		
		MagicWandBoxFilter filter = new MagicWandBoxFilter(w, h);
		
		loading = 10;

		layer = new BufferedLayer(0, 0, CAPTCHA_FOLDER+fileName+".bmp");

		b = layer.getImagemBuffer();

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
					if(backColor==Color.RED){
						backColor = new Color(rgb);
					}else if(foreColor==Color.RED){
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

				if(filter.isColor(rgb, foreColor.getRGB())){
										
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
		
		String folder = "res/images/"+CAPTCHA_FOLDER+"clean/";
		
		try {			
			
		    File outputfile = new File(folder+fileName+".png");
		    ImageIO.write(b, "png", outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}

		loading = 100;
		
	}

}


