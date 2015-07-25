package examples.medium;


import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.core.context.Application;
import examples.medium.application.area.TrackingMultiAreaApplication;

public class TrackingStaticColor extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public TrackingStaticColor() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		TrackingStaticColor finder = new TrackingStaticColor();
		finder.init();
	}

	@Override
	public Application startApplication() {
		initialSetup("../../../../");
		//return new TrackingShadingColorApplication(w,h);
		return new TrackingMultiAreaApplication(w,h);
	}	

}
