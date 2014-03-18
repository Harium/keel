package examples.hard;


import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.hard.microscopy.EmptyWellPlateApplication;

public class MicroscopyWellPlate extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MicroscopyWellPlate() {
		super(640, 480);
	}

	@Override
	public Application startApplication() {
				
		String s = getClass().getResource("").toString();
				
		setPath(s+"../../");
		
		return new EmptyWellPlateApplication(w,h);		
		
	}	

}
