package br.com.etyllica.motion.features;


public class Cross {

	private int up;
	private int down;
	private int left;
	private int right;
	private int center;
	
	private int upperLeft;
	private int upperRight;
	private int lowerLeft;
	private int lowerRight;
	
	public Cross(){
		super();
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getCenter() {
		return center;
	}

	public void setCenter(int center) {
		this.center = center;
	}

	public int getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(int upperLeft) {
		this.upperLeft = upperLeft;
	}

	public int getUpperRight() {
		return upperRight;
	}

	public void setUpperRight(int upperRight) {
		this.upperRight = upperRight;
	}

	public int getLowerLeft() {
		return lowerLeft;
	}

	public void setLowerLeft(int lowerLeft) {
		this.lowerLeft = lowerLeft;
	}

	public int getLowerRight() {
		return lowerRight;
	}

	public void setLowerRight(int lowerRight) {
		this.lowerRight = lowerRight;
	}
	
}
