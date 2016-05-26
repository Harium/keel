package examples.basic;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.basic.application.MiddleLineExampleApplication;

public class MiddleLine extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public MiddleLine() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		return new MiddleLineExampleApplication(w,h);
		
	}	

}
