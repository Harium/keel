package examples.basic;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;
import examples.basic.application.QuickHullExampleApplication;

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
