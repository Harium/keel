package br.com.etyllica.motion.posit;

public class Pose {

	protected double bestError;

	protected double alternativeError;

	protected double[][] bestRotation;

	protected double[] bestTranslation;

	protected double[][] alternativeRotation;

	protected double[] alternativeTranslation;

	public Pose(double error1, double[][] rotation1, double[] translation1, double error2, double[][] rotation2, double[] translation2) {
		super();

		this.bestError = error1;
		this.bestRotation = rotation1;
		this.bestTranslation = translation1;

		this.alternativeError = error2;
		this.alternativeRotation = rotation2;
		this.alternativeTranslation = translation2;

	}

	public double getBestError() {
		return bestError;
	}

	public double[][] getBestRotation() {
		return bestRotation;
	}
	
	public double[] getBestTranslation() {
		return bestTranslation;
	}

}
