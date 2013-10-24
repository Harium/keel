

import test.HollowTest;
import test.MagicWandTest;
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
		System.out.println(System.getProperty("java.library.path")); 
	}

	@Override
	public void startGame() {
		//setMainApplication(new LuvMotion(w,h));
		//setMainApplication(new Toxique(w,h));
		//setMainApplication(new HollowController(w,h));
		//setMainApplication(new HollowTest(w,h));
		
		//setMainApplication(new FaceApplication(w,h));
		//setMainApplication(new SkinTest(w,h));
		
		//setMainApplication(new MagicWandTest(w,h));
		
		setMainApplication(new CaptchaCleaner(w,h));
		
		//setMainApplication(new SkinGraph(w,h));
		//setMainApplication(new AuReality(w,h));
	}
	
	/*
	   CPPFLAGS="-I/usr/lib/jvm/java-1.7.0-openjdk.x86_64/include/linux $CPPFLAGS"
	   CPPFLAGS="-I/usr/lib/jvm/java-1.7.0-openjdk.x86_64/include $CPPFLAGS"
	   sudo ln -s /usr/lib/jvm/java-1.7.0-openjdk.x86_64/jni.h /usr/lib/jvm/java
	 */

}
