package examples.modifiers.edge;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;

public class CannyEdgeExample extends Etyllica {

	private static final long serialVersionUID = 1L;

	public CannyEdgeExample() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		CannyEdgeExample example = new CannyEdgeExample();
		example.init();
	}

	@Override
	public Application startApplication() {
		initialSetup("../");
		
		return new CannyEdgeApplication(w,h);
	}	

}
