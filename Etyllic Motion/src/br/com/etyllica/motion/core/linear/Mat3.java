package br.com.etyllica.motion.core.linear;

/**
 * 
 * Based on js-aruco code: http://code.google.com/p/js-aruco/source/browse/trunk/src/posit2.js
 * 
 */

public class Mat3 {

	public double[][] m;

	public Mat3() {
		super();

		this.m = new double[3][3];
	}

	public static Mat3 clone(Mat3 b) {

		Mat3 matrix = new Mat3();

		double[][] a = b.m;

		matrix.m[0][0] = a[0][0];
		matrix.m[0][1] = a[0][1];
		matrix.m[0][2] = a[0][2];
		matrix.m[1][0] = a[1][0];
		matrix.m[1][1] = a[1][1];
		matrix.m[1][2] = a[1][2];
		matrix.m[2][0] = a[2][0];
		matrix.m[2][1] = a[2][1];
		matrix.m[2][2] = a[2][2];

		return matrix;

	}

	public Mat3 copy(Mat3 b) {

		double[][] a = b.m;

		this.m[0][0] = a[0][0];
		this.m[0][1] = a[0][1];
		this.m[0][2] = a[0][2];
		this.m[1][0] = a[1][0];
		this.m[1][1] = a[1][1];
		this.m[1][2] = a[1][2];
		this.m[2][0] = a[2][0];
		this.m[2][1] = a[2][1];
		this.m[2][2] = a[2][2];

		return this;
	}

	public static Mat3 fromRows(Vec3 a, Vec3 b, Vec3 c) {

		Mat3 matrix = new Mat3();

		double[][] m = matrix.m;

		m[0][0] = a.v[0];
		m[0][1] = a.v[1];
		m[0][2] = a.v[2];
		m[1][0] = b.v[0];
		m[1][1] = b.v[1];
		m[1][2] = b.v[2];
		m[2][0] = c.v[0];
		m[2][1] = c.v[1];
		m[2][2] = c.v[2];

		return matrix;
	}

	public static Mat3 fromDiagonal(Vec3 a){

		Mat3 matrix = new Mat3();

		double[][] m = matrix.m;

		m[0][0] = a.v[0];
		m[1][1] = a.v[1];
		m[2][2] = a.v[2];

		return matrix;
	}

	public static Mat3 transpose(Mat3 a){

		Mat3 matrix = new Mat3();

		double[][] m = matrix.m;

		m[0][0] = a.m[0][0];
		m[0][1] = a.m[1][0];
		m[0][2] = a.m[2][0];
		m[1][0] = a.m[0][1];
		m[1][1] = a.m[1][1];
		m[1][2] = a.m[2][1];
		m[2][0] = a.m[0][2];
		m[2][1] = a.m[1][2];
		m[2][2] = a.m[2][2];

		return matrix;
	}

	public static Mat3 mult(Mat3 a, Mat3 b){

		Mat3 matrix = new Mat3();

		double[][] m = matrix.m;

		m[0][0] = a.m[0][0] * b.m[0][0] + a.m[0][1] * b.m[1][0] + a.m[0][2] * b.m[2][0];
		m[0][1] = a.m[0][0] * b.m[0][1] + a.m[0][1] * b.m[1][1] + a.m[0][2] * b.m[2][1];
		m[0][2] = a.m[0][0] * b.m[0][2] + a.m[0][1] * b.m[1][2] + a.m[0][2] * b.m[2][2];
		m[1][0] = a.m[1][0] * b.m[0][0] + a.m[1][1] * b.m[1][0] + a.m[1][2] * b.m[2][0];
		m[1][1] = a.m[1][0] * b.m[0][1] + a.m[1][1] * b.m[1][1] + a.m[1][2] * b.m[2][1];
		m[1][2] = a.m[1][0] * b.m[0][2] + a.m[1][1] * b.m[1][2] + a.m[1][2] * b.m[2][2];
		m[2][0] = a.m[2][0] * b.m[0][0] + a.m[2][1] * b.m[1][0] + a.m[2][2] * b.m[2][0];
		m[2][1] = a.m[2][0] * b.m[0][1] + a.m[2][1] * b.m[1][1] + a.m[2][2] * b.m[2][1];
		m[2][2] = a.m[2][0] * b.m[0][2] + a.m[2][1] * b.m[1][2] + a.m[2][2] * b.m[2][2];

		return matrix;
	}

	public static Vec3 multVector(Mat3 m, Vec3 a){

		return new Vec3(
				m.m[0][0] * a.v[0] + m.m[0][1] * a.v[1] + m.m[0][2] * a.v[2],
				m.m[1][0] * a.v[0] + m.m[1][1] * a.v[1] + m.m[1][2] * a.v[2],
				m.m[2][0] * a.v[0] + m.m[2][1] * a.v[1] + m.m[2][2] * a.v[2]);
	}

	public Vec3 column(int index){
		
		double[][] m = this.m;

		return new Vec3( m[0][index], m[1][index], m[2][index] );
	}

	public Vec3 row(int index){
		double[][] m = this.m;

		return new Vec3( m[index][0], m[index][1], m[index][2] );
	}

}
