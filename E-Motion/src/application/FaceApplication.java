package application;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.Layer;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;


public class FaceApplication extends Application {

	private Component screen;
	
	private boolean[][] emptyMask;
	
	public FaceApplication(int w, int h) {
		super(w,h);
	}
	
	private CameraV4L4J cam;

	private Layer lastFace;

	private BufferedImage buf;

	private ColorFilter findFaceFilter;
	private ColorFilter findEye;

	private List<Component> faces;
	private List<Component> eyes;

	@Override
	public void load() {

		cam = new CameraV4L4J(0);
		
		int w = cam.getBufferedImage().getWidth(); 
		int h = cam.getBufferedImage().getHeight();
				
		screen = new Component(w, h);
		
		emptyMask = new boolean[w][h];
		
		for(int j=0;j<h;j++){
			
			for(int i=0;i<w;i++){
				emptyMask[i][j] = false;
			}
			
		}
		
		//findFace = new FindSkinFilter(w,h);
		findFaceFilter = new ColorFilter(w,h);
		//findFaceFilter.setPixelStrategy(new SkinColorStrategy());
		
		findEye = new ColorFilter(w,h, Color.BLACK);
		
		lastFace = new ImageLayer(0,0,w,h);
		
		loading = 100;

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event){

		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			maxX++;
			System.out.println(maxX);
		}
		else if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){
			maxX--;
			System.out.println(maxX);
		}

		if(event.isKeyDown(KeyEvent.TSK_SETA_CIMA)){
			//distFace+=0.1;
			maxPontos+=20;
			System.out.println(maxPontos);
		}
		else if(event.isKeyDown(KeyEvent.TSK_SETA_BAIXO)){
			//distFace-=0.1;
			maxPontos-=20;
			System.out.println(maxPontos);
		}

		if(event.isKeyDown(KeyEvent.TSK_R)){
			//maiorRelevancia = 0;
			//new Voicer().say("Putz, o sistema resetou");
		}

		return GUIEvent.NONE;
	}
	
	private double distFace = 4.0;

	@Override
	public void draw(Graphic g) {

		buf = cam.getBufferedImage();

		g.drawImage(buf,0,0);
		
		faces = findFaceFilter.filter(buf, screen);
		
		for(Component face: faces){
			eyes.addAll(findEye.filter(buf, face));
		}
		
		for(Component component: faces){

			//TODO Draw Pixels
			g.setColor(Color.GREEN);
			for(Point2D point: component.getPoints()){
				g.drawRect(point.getX(), point.getY(), 1, 1);
			}
			
			//TODO Write Number of Points
			g.setColor(Color.WHITE);
			g.drawRect(component.getLayer());
			g.drawStringShadow(component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Integer.toString(component.getPointCount()), Color.BLACK);

		}
		
		for(Component component: eyes){
			
			g.setColor(Color.BLUE);
			for(Point2D point: component.getPoints()){
				g.drawRect(point.getX(), point.getY(), 1, 1);
			}
			
			//TODO Write Number of Points
			g.setColor(Color.BLUE);
			g.drawRect(component.getLayer());
			g.drawStringShadow(component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Integer.toString(component.getPointCount()), Color.BLACK);

		}
		
		//TODO DRAW only the biggest Face
		//pintaFace(g, faces);

		//TODO Draw Marks

		/*g.setColor(Color.WHITE);
		g.escreveSombra(19,15,"maxPontos = "+Integer.toString(maxPontos));

		g.escreveSombra(19,35,"maxPontos/9 = "+Integer.toString(maxPontos/9));

		g.escreveSombra(19,55,"maxPontos/5 = "+Integer.toString(maxPontos/5));

		g.escreveSombra(19,75,"distFace = "+Double.toString(distFace));

		g.escreveSombra(19,95,"LarguraFace = "+Integer.toString(lastFace.getW()));

		g.escreveSombra(19,105,"LarguraFace/2 = "+Integer.toString(lastFace.getW()/2));
		g.escreveSombra(19,115,"LarguraFace/5 = "+Integer.toString(lastFace.getW()/5));
		g.escreveSombra(19,125,"LarguraFace*5 = "+Integer.toString(lastFace.getW()*5));*/

	}

	private int maxX = 600;
	private int maxPontos = 700;
		
	private void pintaFace(Graphic g, List<Component> componentes){

		int maiorRelevancia = 0;

		for(Component componente:componentes){

			if(componente.getPointCount()>=maxPontos){

				if(componente.getHighestX()<maxX){

					//Boxes Verticais
					if(componente.getH()>componente.getW()){
						//Desenha possíveis faces
						//g.setColor(Color.GREEN);
						//g.drawRect(lista.getCamada());

						int relevancia = (componente.getH()*componente.getW())-componente.getLowestY();

						if(relevancia>maiorRelevancia){
							maiorRelevancia = relevancia;
							//TODO Apenas mudar quando diferença for grande para garantir estabilidade
							if(lastFace.getW()*lastFace.getH()<componente.getH()*componente.getH())
								lastFace = componente.getLayer();
						}
					}

				}

			}
		}

		//Posso remover daqui
		if(lastFace!=null){
			g.setColor(Color.RED);
			g.drawRect(lastFace);
		}

		//return achaIris(g, bimg);

	}

	public GUIEvent updateMouse(PointerEvent arg0) {

		return GUIEvent	.NONE;
	}

}
