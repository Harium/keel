package examples.hard.microscopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.etyllica.core.graphics.Graphic;
import examples.hard.microscopy.cell.RedBloodCell;

public class BloodSampleNeubauerApplication extends EmptyNeubauerApplication {

	private List<RedBloodCell> cells;
	
	public BloodSampleNeubauerApplication(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		super.load();
		
		cells = new ArrayList<RedBloodCell>();
		
		Random rand = new Random();
		
		loadingPhrase = "Creating cells";
		
		final int COUNT_CELLS = 700;
		
		for(int i=0;i<COUNT_CELLS;i++) {
		
			loading = i/COUNT_CELLS;
			
			loadingPhrase = "Creating "+i+"/"+COUNT_CELLS+" cells.";
			
			int cx = rand.nextInt(60*zoom);
			int cy = rand.nextInt(60*zoom);
			
			cells.add(new RedBloodCell(cx, cy));
			
		}		
		
		loading = 100;
	}
	
	@Override
	public void draw(Graphic g) {
		
		drawNeubauer(g);
		
		for(RedBloodCell cell: cells) {
			cell.draw(g, offsetX, offsetY, zoom);
		}
		
		drawInformation(g);

	}
		
}
