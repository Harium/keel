

import examples.medium.skin.SkinGraphic;
import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;

public class EtyllicMotion extends EtyllicaApplet {

	public EtyllicMotion() {
		super(800, 480);
		//super(256,256);
	}

	public Application startApplication() {
		
		initialSetup("../../");
		
		//Basic Examples		
		
		//return new QuickHullExampleApplication(w,h);
		
		//return new MultipleTrackingApplication(w,h);
		
		//return new SimpleCam(w,h);
		
		//return new MagicWandStatic(w,h);
		
		
		//Medium Examples
		
		//return new AirWrite(w,h);				
		
		//return new AugmentedRealityStatic(w,h);
		
		//return new MagicWand(w,h);
		
		//return new BarCodeExample(w,h);
		
		//return new FaceStatic(w,h);
		
		//return new PirateHatApplication(w,h);
		//return new FaceSkinFilter(w,h);
				
		//return new HollowController(w,h);
		//return new HollowTest(w,h);
		
		//return new FaceApplication(w,h);
		
		//return new FaceSampledMultiFilterStatic(w,h);
		
		//return new FaceSampledStatic(w,h);
		
		//return new FaceSampledReal(w,h);
		
		//return new MagicWand(w,h);
		
		//return new MagicWandStatic(w,h);
				
		//return new BarCodeExample(w,h);
		
		//return new CaptchaCleaner(w,h);
		
		return new SkinGraphic(w,h);
		
		//return new OGRApplication(w,h);
		
	}	

}
