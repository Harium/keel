package examples.medium;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.medium.application.MelanomaFinderApplication;

public class MelanomaFinder extends Etyl {

	private static final long serialVersionUID = 1L;

	public MelanomaFinder() {
		super(640, 480);
	}
	
	public static void main(String[] args) {
		MelanomaFinder finder = new MelanomaFinder();
		finder.init();
	}

	public Application startApplication() {
				
		initialSetup("../../../../");
		
		return new MelanomaFinderApplication(w,h);
		
	}	

}
