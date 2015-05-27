package br.com.etyllica.motion.camera;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

public class CameraSarxosWebcam implements Camera {

	/** CameraCapture*/
	private Webcam webcam;

	private int width = 640;
	private int height = 480;

	public CameraSarxosWebcam() {
		this(0);
	}

	public CameraSarxosWebcam(int id) {
		webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(width, height));
		webcam.open();

		startCapture();
	}

	public void startCapture() {
		webcam.open();
	}

	public void stopCapture() {
		webcam.close();
	}

	public BufferedImage getBufferedImage() {
		return webcam.getImage();
	}

}
