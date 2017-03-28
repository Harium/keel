package examples.medium.skin;

import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.awt.camera.CameraSarxosWebcam;
import br.com.etyllica.keel.feature.Component;

public class SkinStrategyCameraApplication extends SimpleFaceFinderApplication {
	
	public SkinStrategyCameraApplication(int w, int h) {
		super(w, h);
	}
	
	protected void initCamera() {
		cam = new CameraSarxosWebcam();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(cam.getBufferedImage(), 0, 0);
		reset();
		//drawComponents(g);
		drawComponent(g, bestCandidate);
		
		for(Component feature: faceComponents) {
			drawComponent(g, feature);
		}
		//drawComponents(g);
	}
	
}
