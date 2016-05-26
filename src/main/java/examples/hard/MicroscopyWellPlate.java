package examples.hard;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.hard.microscopy.EmptyWellPlateApplication;

public class MicroscopyWellPlate extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public MicroscopyWellPlate() {
		super(1280,720);
	}

	@Override
	public Application startApplication() {
				
		String s = getClass().getResource("").toString();
				
		setPath(s+"../../");
		
		return new EmptyWellPlateApplication(w,h);		
		
	}	

}
