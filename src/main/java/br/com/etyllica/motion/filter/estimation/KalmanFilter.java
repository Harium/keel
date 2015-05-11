package br.com.etyllica.motion.filter.estimation;

import jama.Matrix;

/**
 * Kalman Filter
 * Code found at: http://the-lost-beauty.blogspot.com.br/2009/12/java-implementation-of-kalman-filter.html
 */
public class KalmanFilter {
	protected Matrix X, X0;
	protected Matrix F, B, U, Q;
	protected Matrix H, R;
	protected Matrix P, P0;

	public void predict() {
		X0 = F.times(X).plus(B.times(U));

		P0 = F.times(P).times(F.transpose()).plus(Q);
	}

	public void correct(Matrix Z) {
		Matrix S = H.times(P0).times(H.transpose()).plus(R); 

		Matrix K = P0.times(H.transpose()).times(S.inverse());

		X = X0.plus(K.times(Z.minus(H.times(X0))));

		Matrix I = Matrix.identity(P0.getRowDimension(), P0.getColumnDimension());
		P = (I.minus(K.times(H))).times(P0);
	}

	//getters and setters go here
}
