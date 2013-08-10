

import test.HollowTest;
import test.SkinTest;
import application.FaceApplication;
import application.HollowController;
import br.com.etyllica.Etyllica;

public class EtyllicMotion extends Etyllica{

	public EtyllicMotion() {
		super(640, 480);
	}

	@Override
	public void startGame() {
		//setMainApplication(new LuvMotion(w,h));
		//setMainApplication(new Toxique(w,h));
		//setMainApplication(new HollowController(w,h));
		//setMainApplication(new HollowTest(w,h));
		
		//setMainApplication(new FaceApplication(w,h));
		setMainApplication(new SkinTest(w,h));
		//setMainApplication(new AuReality(w,h));
	}

}
