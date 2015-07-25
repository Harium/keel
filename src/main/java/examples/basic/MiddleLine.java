package examples.basic;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;
import examples.basic.application.MiddleLineExampleApplication;

public class MiddleLine extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MiddleLine() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		return new MiddleLineExampleApplication(w,h);
		
	}	

}
