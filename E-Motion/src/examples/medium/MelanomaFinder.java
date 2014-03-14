package examples.medium;


import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import examples.medium.application.MelanomaFinderApplication;

public class MelanomaFinder extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MelanomaFinder() {
		super(640, 480);
	}

	@Override
	public Application startApplication() {
				
		String s = getClass().getResource("").toString();
				
		setPath(s+"../../");
		
		return new MelanomaFinderApplication(w,h);		
		
	}	

}
