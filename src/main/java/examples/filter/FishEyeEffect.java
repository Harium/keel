package examples.filter;

import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.commons.context.Application;
import examples.filter.application.FishEyeVision;

public class FishEyeEffect extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public FishEyeEffect() {
		super(800, 480);
	}

	public Application startApplication() {
		
		initialSetup("../../");
		
		return new FishEyeVision(w,h);
		
	}	

}