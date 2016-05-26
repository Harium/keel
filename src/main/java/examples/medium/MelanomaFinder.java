package examples.medium;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;
import examples.medium.application.MelanomaFinderApplication;

public class MelanomaFinder extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MelanomaFinder() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		MelanomaFinder finder = new MelanomaFinder();
		finder.init();
	}

	@Override
	public Application startApplication() {
				
		initialSetup("../../../../");
		
		return new MelanomaFinderApplication(w,h);
		
	}	

}
