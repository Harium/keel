package examples.hard;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.hard.microscopy.BloodSampleNeubauerApplication;

public class MicroscopyNeubauer extends Etyl {

	private static final long serialVersionUID = 1L;

	public MicroscopyNeubauer() {
		super(1280,720);
	}

	public static void main(String[] args) {
		MicroscopyNeubauer example = new MicroscopyNeubauer();
		example.init();
	}

	public Application startApplication() {

		initialSetup("../../");
		
		//return new EmptyNeubauerApplication(w,h);
		return new BloodSampleNeubauerApplication(w,h);
		
	}	

}
