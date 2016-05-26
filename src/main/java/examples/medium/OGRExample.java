package examples.medium;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;
import examples.medium.ogr.OGRApplication;

public class OGRExample extends Etyllica {

	private static final long serialVersionUID = 1L;

	public OGRExample() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		OGRExample finder = new OGRExample();
		finder.init();
	}

	@Override
	public Application startApplication() {
				
		initialSetup("../");
		
		return new OGRApplication(w,h);
		
	}	

}
