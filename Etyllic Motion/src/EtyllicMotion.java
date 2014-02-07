

import application.AugmentedReality;
import application.CaptchaCleaner;
import application.FaceApplication;
import application.HollowController;
import application.examples.AirWrite;
import application.examples.AugmentedRealityStatic;
import application.examples.BarCodeExample;
import application.examples.FaceSampledMultiFilterStatic;
import application.examples.FaceSampledReal;
import application.examples.FaceSampledStatic;
import application.examples.FaceStatic;
import application.examples.HollowTest;
import application.examples.MagicWand;
import application.examples.MagicWandStatic;
import application.examples.MultipleTracking;
import application.examples.SimpleCam;
import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import br.com.etyllica.motion.graph.SkinGraph;

public class EtyllicMotion extends Etyllica{

	public EtyllicMotion() {
		super(800, 480);
		//super(256,256);
	}

	@Override
	public Application startApplication() {
		
		//return new MultipleTracking(w,h);
		
		//return new SimpleCam(w,h);
				
		//return new AirWrite(w,h);
		
		//return new MagicWandStatic(w,h);
		
		return new AugmentedRealityStatic(w,h);
		
		//return new MagicWand(w,h);
		
		//return new BarCodeExample(w,h);
		
		//return new FaceStatic(w,h);
		
		//return new FaceSampledMultiFilterStatic(w,h);
		
		//return new HollowController(w,h);
		//return new HollowTest(w,h);
		
		//return new FaceApplication(w,h);
		//return new SkinTest(w,h);
		
		//return new FaceSampledMultiFilterStatic(w,h);
		
		//return new FaceSampledStatic(w,h);
		
		//return new FaceSampledReal(w,h);
		
		
		//return new MagicWand(w,h);
		
		//return new MagicWandStatic(w,h);
				
		//return new BarCode(w,h);
		
		//return new CaptchaCleaner(w,h);
		
		//return new SkinGraph(w,h);
		
	}	

}
