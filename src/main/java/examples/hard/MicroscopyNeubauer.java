package examples.hard;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.commons.context.Application;
import examples.hard.microscopy.BloodSampleNeubauerApplication;

public class MicroscopyNeubauer extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public MicroscopyNeubauer() {
		super(1280,720);
	}

	public Application startApplication() {

		initialSetup("../../");
		
		//return new EmptyNeubauerApplication(w,h);
		return new BloodSampleNeubauerApplication(w,h);
		
	}	

}
