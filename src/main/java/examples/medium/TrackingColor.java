package examples.medium;


import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;
import examples.medium.application.TrackingColorApplication;

public class TrackingColor extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public TrackingColor() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		
		TrackingColor finder = new TrackingColor();
		finder.init();	
		
	}

	@Override
	public Application startApplication() {
		
		initialSetup("../../../../");
		
		return new TrackingColorApplication(w,h);
		
	}	

}
