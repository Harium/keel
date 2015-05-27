package examples.medium.application.area;

import java.awt.image.BufferedImage;

import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.camera.CameraSarxosWebcam;
import br.com.etyllica.motion.core.features.Component;


public class TrackingMultiAreaWithCameraApplication extends TrackingMultiAreaApplication {

	private BufferedLayer layer;

	public TrackingMultiAreaWithCameraApplication(int w, int h) {
		super(w, h);
	}

	@Override
	protected Component setupCamera() {
		cam = new CameraSarxosWebcam(0);

		int w = cam.getBufferedImage().getWidth();
		int h = cam.getBufferedImage().getHeight();

		screen = new Component(0, 0, w, h);
		layer = new BufferedLayer(w, h);

		return screen;
	}

	@Override
	protected void reset(BufferedImage b) {
		layer.setBuffer(b);
		layer.flipHorizontal();

		super.reset(layer.getBuffer());
	}
	
	@Override
	protected void updateCameraInput(KeyEvent event) {

	}

}