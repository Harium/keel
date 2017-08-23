package examples.hard.microscopy.cell;

import com.harium.etyl.commons.graphics.Color;

public class RedBloodCell extends Cell {
		
	public RedBloodCell(int x, int y) {
		super(x, y);
		
		this.color = new Color(0xD0, 0xA0, 0x89);
	}
		
}
