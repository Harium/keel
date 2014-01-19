

import test.br.com.etyllica.motion.SkinTest;
import application.CaptchaCleaner;
import application.FaceApplication;
import application.HollowController;
import br.com.etyllica.Etyllica;
import br.com.etyllica.examples.AirWrite;
import br.com.etyllica.examples.BarCode;
import br.com.etyllica.examples.FaceSampledMultiFilterStatic;
import br.com.etyllica.examples.FaceSampledReal;
import br.com.etyllica.examples.FaceSampledStatic;
import br.com.etyllica.examples.HollowTest;
import br.com.etyllica.examples.MagicWand;
import br.com.etyllica.examples.MagicWandStatic;
import br.com.etyllica.examples.tutorials.SimpleCam;
import br.com.etyllica.motion.graph.SkinGraph;

public class EtyllicMotion extends Etyllica{

	public EtyllicMotion() {
		super(800, 480);
		//super(256,256);
	}

	@Override
	public void startGame() {
		
		updateDelay = 40;
				
		//setMainApplication(new SimpleCam(w,h));
		
		//setMainApplication(new AirWrite(w,h));
		
		//setMainApplication(new HollowController(w,h));
		//setMainApplication(new HollowTest(w,h));
		
		//setMainApplication(new FaceApplication(w,h));
		//setMainApplication(new SkinTest(w,h));
		
		//setMainApplication(new FaceSampledMultiFilterStatic(w,h));
		
		//setMainApplication(new FaceSampledStatic(w,h));
		//setMainApplication(new FaceSampledReal(w,h));
		
		
		setMainApplication(new MagicWand(w,h));
		
		//setMainApplication(new MagicWandStatic(w,h));
		
		//setMainApplication(new BarCode(w,h));
		
		//setMainApplication(new CaptchaCleaner(w,h));
		
		//setMainApplication(new SkinGraph(w,h));
		
	}	

}
