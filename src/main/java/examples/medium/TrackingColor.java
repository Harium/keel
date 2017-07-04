package examples.medium;


import br.com.etyllica.Etyllica;
import br.com.etyllica.commons.context.Application;
import examples.medium.application.area.TrackingMultiAreaWithCameraApplication;

public class TrackingColor extends Etyllica {

	private static final long serialVersionUID = 1L;

	public TrackingColor() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		
		TrackingColor finder = new TrackingColor();
		finder.init();	
		
	}

	public Application startApplication() {
		
		initialSetup("../../../../");
		
		//return new TrackingCameraColorApplication(w,h);
		return new TrackingMultiAreaWithCameraApplication(w,h);
		
	}	

}
