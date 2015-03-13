package examples.basic;


import examples.basic.application.QuickHullExampleApplication;
import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;

public class QuickHull extends Etyllica {

	private static final long serialVersionUID = 1L;

	public QuickHull() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		return new QuickHullExampleApplication(w,h);
		
	}	

}
