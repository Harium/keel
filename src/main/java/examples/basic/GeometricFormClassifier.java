package examples.basic;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;
import examples.basic.application.ColoredGeometricFormApplication;
import examples.basic.application.GeometricFormApplication;

public class GeometricFormClassifier extends Etyllica {

	private static final long serialVersionUID = 1L;

	public GeometricFormClassifier() {
		super(800, 480);
	}

	@Override
	public Application startApplication() {
		
		//return new GeometricFormApplication(w,h);
		return new ColoredGeometricFormApplication(w,h);
	}	

}
