package examples.filter;

import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.filter.application.FishEyeVision;

public class FishEyeEffect extends Etyllica {

	private static final long serialVersionUID = 1L;

	public FishEyeEffect() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		initialSetup("../../../../");
		
		return new FishEyeVision(w,h);
		
	}	

}