package examples.medium;


import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.core.context.Application;
import examples.medium.skin.SimpleFaceFinderApplication;
import examples.medium.skin.SimpleSkinStrategyApplication;
import examples.medium.skin.SkinStrategyCameraApplication;

public class FindSkinColor extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public FindSkinColor() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		FindSkinColor finder = new FindSkinColor();
		finder.init();
	}

	@Override
	public Application startApplication() {
		initialSetup("../");
		//return new SimpleSkinStrategyApplication(w, h);
		return new SimpleFaceFinderApplication(w, h);
		//return new SkinStrategyCameraApplication(w, h);
	}
}
