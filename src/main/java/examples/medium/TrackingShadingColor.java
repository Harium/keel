package examples.medium;


import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;
import examples.medium.application.TrackingShadingColorApplication;

public class TrackingShadingColor extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public TrackingShadingColor() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		TrackingShadingColor finder = new TrackingShadingColor();
		finder.init();
	}

	@Override
	public Application startApplication() {
		initialSetup("../../../../");
		return new TrackingShadingColorApplication(w,h);
	}	

}
