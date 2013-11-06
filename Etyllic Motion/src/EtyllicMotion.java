

import test.FaceSampledStatic;
import test.HollowTest;
import test.MagicWand;
import test.MagicWandStatic;
import test.SkinTest;
import application.CaptchaCleaner;
import application.FaceApplication;
import application.HollowController;
import br.com.etyllica.Etyllica;
import br.com.etyllica.graph.SkinGraph;

public class EtyllicMotion extends Etyllica{

	public EtyllicMotion() {
		super(640, 480);
		//super(256,256);		 
	}

	@Override
	public void startGame() {
		//setMainApplication(new LuvMotion(w,h));
		//setMainApplication(new Toxique(w,h));
		//setMainApplication(new HollowController(w,h));
		//setMainApplication(new HollowTest(w,h));
		
		//setMainApplication(new FaceApplication(w,h));
		//setMainApplication(new SkinTest(w,h));
		//setMainApplication(new FaceSampledStatic(w,h));
		
		
		//setMainApplication(new MagicWand(w,h));
		setMainApplication(new MagicWandStatic(w,h));
		
		//setMainApplication(new CaptchaCleaner(w,h));
		
		//setMainApplication(new SkinGraph(w,h));
		//setMainApplication(new AuReality(w,h));
	}	

}
