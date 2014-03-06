package examples.basic;


import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.basic.application.MultipleTrackingApplication;

public class MultipleTracking extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MultipleTracking() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		return new MultipleTrackingApplication(w,h);		
		
	}	

}
