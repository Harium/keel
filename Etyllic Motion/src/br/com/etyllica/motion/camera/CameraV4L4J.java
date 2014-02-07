package br.com.etyllica.motion.camera;

import java.awt.image.BufferedImage;

import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;


public class CameraV4L4J implements CaptureCallback, Camera {

	/** WebCam - v4l4j*/

	private BufferedImage buffer;
	
	private int width = 640;
	private int height = 480;
	private int std = V4L4JConstants.STANDARD_WEBCAM;
	private int channel = 0;

	private String device = "/dev/video0";

	private VideoDevice     videoDevice;
	private FrameGrabber    frameGrabber;

	public CameraV4L4J() {
		this(0);
	}
	
	public CameraV4L4J(int id){
		device = "/dev/video"+Integer.toString(id);
		
		try{
			initFrameGrabber();
		} catch (V4L4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startCapture();

	}

	private void initFrameGrabber() throws V4L4JException{
		
		videoDevice = new VideoDevice(device);
		frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
		frameGrabber.setCaptureCallback(this);
		width = frameGrabber.getWidth();
		height = frameGrabber.getHeight();
		
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//System.out.println("Starting capture at "+width+"x"+height);
	}
	
	public void startCapture(){
		try {
			frameGrabber.startCapture();
		} catch (V4L4JException e){
			System.err.println("Error starting the capture");
			cleanupCapture();
			e.printStackTrace();
		}
	}

	private void cleanupCapture() {
		try {
			frameGrabber.stopCapture();
		} catch (StateException ex) {
			// the frame grabber may be already stopped, so we just ignore
			// any exception and simply continue.
		}

		// release the frame grabber and video device
		videoDevice.releaseFrameGrabber();
		videoDevice.release();
	}
	public VideoDevice getVideoDevice(){
		return videoDevice;
	}
	public FrameGrabber getFrameGrabber(){
		return frameGrabber;
	}

	@Override
	public void exceptionReceived(V4L4JException e) {
		e.printStackTrace();
	}
	
	@Override
	public void nextFrame(VideoFrame frame) {
 
		buffer = frame.getBufferedImage();
		
		frame.recycle();
	}
	
	public BufferedImage getBufferedImage(){
		
		return buffer;
	}

}
