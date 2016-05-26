package examples.basic;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.basic.application.MultipleTrackingApplication;

public class MultipleColorTracking extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public MultipleColorTracking() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		return new MultipleTrackingApplication(w,h);
	}	

}
