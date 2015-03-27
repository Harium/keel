package examples.filter;

import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.filter.application.FishEyeCorrectionApplication;

public class FishEyeCorrection extends Etyllica {

	private static final long serialVersionUID = 1L;

	public FishEyeCorrection() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		initialSetup("../../../../");
		
		return new FishEyeCorrectionApplication(w,h);
		
	}	

}