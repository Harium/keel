package examples.basic;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.basic.application.ClusterExampleApplication;

public class ClusterExample extends EtyllicaApplet {

	private static final long serialVersionUID = 1L;

	public ClusterExample() {
		super(800, 480);
	}

	public Application startApplication() {
		return new ClusterExampleApplication(w, h);
	}	

}
