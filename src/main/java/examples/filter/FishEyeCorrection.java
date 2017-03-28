package examples.filter;

import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.filter.application.FishEyeCorrectionApplication;

public class FishEyeCorrection extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public FishEyeCorrection() {
		super(800, 480);
	}

	public Application startApplication() {
		
		initialSetup("../../../../");
		
		return new FishEyeCorrectionApplication(w,h);
		
	}	

}