package examples.hard;


import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.hard.microscopy.EmptyNeubauerApplication;

public class MicroscopyNeubauer extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MicroscopyNeubauer() {
		super(1280,720);
	}

	@Override
	public Application startApplication() {
				
		String s = getClass().getResource("").toString();
				
		setPath(s+"../../");
		
		return new EmptyNeubauerApplication(w,h);
		
	}	

}
