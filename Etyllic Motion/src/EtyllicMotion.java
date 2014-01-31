

import test.br.com.etyllica.motion.SkinTest;
import application.CaptchaCleaner;
import application.FaceApplication;
import application.HollowController;
import br.com.etyllica.Etyllica;
import br.com.etyllica.context.Application;
import br.com.etyllica.examples.AirWrite;
import br.com.etyllica.examples.BarCode;
import br.com.etyllica.examples.FaceSampledMultiFilterStatic;
import br.com.etyllica.examples.FaceSampledReal;
import br.com.etyllica.examples.FaceSampledStatic;
import br.com.etyllica.examples.HollowTest;
import br.com.etyllica.examples.MagicWand;
import br.com.etyllica.examples.MagicWandStatic;
import br.com.etyllica.examples.multitracking.MultipleTracking;
import br.com.etyllica.examples.tutorials.SimpleCam;
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
		
		return new MagicWand(w,h);	
		
		
		
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
