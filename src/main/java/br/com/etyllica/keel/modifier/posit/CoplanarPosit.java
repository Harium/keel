package br.com.etyllica.keel.modifier.posit;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.linear.vector.Mat3D;
import br.com.etyllica.linear.vector.Vec3D;

public class CoplanarPosit {

	private List<Vec3D> model;

	private Mat3D modelVectors;

	private Mat3D modelPseudoInverse;

	private Vec3D modelNormal;

	private double focalLength;

	public CoplanarPosit(double modelSize, double focalLength) {
		this.model = this.buildModel(modelSize);
		this.focalLength = focalLength;

		this.init();
	}

	private List<Vec3D> buildModel(double modelSize) {

		double half = modelSize / 2.0;

		List<Vec3D> model = new ArrayList<Vec3D>();

		model.add(new Vec3D(-half,  half, 0.0));
		model.add(new Vec3D( half,  half, 0.0));
		model.add(new Vec3D( half,  -half, 0.0));
		model.add(new Vec3D(-half,  -half, 0.0));

		return model;

	}

	private void init(){

		Vec3D d = new Vec3D();

		Mat3D v = new Mat3D();

		Mat3D u;

		this.modelVectors = Mat3D.fromRows(
				Vec3D.sub(this.model.get(1), this.model.get(0)),
				Vec3D.sub(this.model.get(2), this.model.get(0)),
				Vec3D.sub(this.model.get(3), this.model.get(0)) );

		u = Mat3D.clone(this.modelVectors);

		SVD.svdcmp(u.m, 3, 3, d.v, v.m);

		this.modelPseudoInverse = Mat3D.mult(
				Mat3D.mult(v, Mat3D.fromDiagonal( Vec3D.inverse(d) ) ), Mat3D.transpose(u) );

		this.modelNormal = v.column( d.minIndex() );

	}

	public Pose pose(List<Point2D> points) {

		Vec3D eps = new Vec3D(1.0, 1.0, 1.0);

		Mat3D rotation1 = new Mat3D();
		Mat3D rotation2 = new Mat3D();

		Vec3D translation1 = new Vec3D();
		Vec3D translation2 = new Vec3D();

		this.pos(points, eps, rotation1, translation1, rotation2, translation2);

		double error1 = this.iterate(points, rotation1, translation1);
		double error2 = this.iterate(points, rotation2, translation2);

		return error1 < error2?
				new Pose(error1, rotation1.m, translation1.v, error2, rotation2.m, translation2.v):
					new Pose(error2, rotation2.m, translation2.v, error1, rotation1.m, translation1.v);
	}

	private void pos(List<Point2D> points, Vec3D eps, Mat3D rotation1, Vec3D translation1, Mat3D rotation2, Vec3D translation2) {

		Vec3D xi = new Vec3D(points.get(1).getX(), points.get(3).getX(), points.get(2).getX());
		Vec3D yi = new Vec3D(points.get(1).getY(), points.get(3).getY(), points.get(2).getY());

		Vec3D xs = Vec3D.addScalar( Vec3D.mult(xi, eps), -points.get(0).getX());
		Vec3D ys = Vec3D.addScalar( Vec3D.mult(yi, eps), -points.get(0).getY());

		Vec3D i0 = Mat3D.multVector(this.modelPseudoInverse, xs);
		Vec3D j0 = Mat3D.multVector(this.modelPseudoInverse, ys);
		
		double s = j0.square() - i0.square();

		double ij = Vec3D.dot(i0, j0);
		double r = 0.0, theta = 0.0;

		if (0.0 == s) {

			r = Math.sqrt( Math.abs(2.0 * ij) );
			theta = (-Math.PI / 2.0) * (ij < 0.0? -1: (ij > 0.0? 1.0: 0.0) );

		} else {

			r = Math.sqrt( Math.sqrt(s * s + 4.0 * ij * ij) );
			theta = Math.atan(-2.0 * ij / s);

			if (s < 0.0){
				theta += Math.PI;
			}

			theta /= 2.0;
		}

		double lambda = r * Math.cos(theta);
		double mu = r * Math.sin(theta);

		//First possible rotation/translation
		Vec3D i = Vec3D.add(i0, Vec3D.multScalar(this.modelNormal, lambda));
		Vec3D j = Vec3D.add(j0, Vec3D.multScalar(this.modelNormal, mu));

		double inorm = i.normalize();
		double jnorm = j.normalize();

		Vec3D k = Vec3D.cross(i, j);
		rotation1.copy(Mat3D.fromRows(i, j, k));

		double scale = (inorm + jnorm) / 2.0;

		Vec3D temp = Mat3D.multVector(rotation1, this.model.get(0));

		translation1.copy(
				points.get(0).getX() / scale - temp.v[0],
				points.get(0).getY() / scale - temp.v[1],
				this.focalLength / scale);

		//Second possible rotation/translation
		i = Vec3D.sub(i0, Vec3D.multScalar(this.modelNormal, lambda) );
		j = Vec3D.sub(j0, Vec3D.multScalar(this.modelNormal, mu) );
		inorm = i.normalize();
		jnorm = j.normalize();
		k = Vec3D.cross(i, j);
		rotation2.copy(Mat3D.fromRows(i, j, k));

		scale = (inorm + jnorm) / 2.0;
		temp = Mat3D.multVector(rotation2, this.model.get(0));

		translation2.copy(
				points.get(0).getX() / scale - temp.v[0],
				points.get(0).getY() / scale - temp.v[1],
				this.focalLength / scale);
	}

	private double iterate (List<Point2D> points, Mat3D rotation, Vec3D translation) {

		double prevError = Double.POSITIVE_INFINITY;

		Mat3D rotation1 = new Mat3D();
		Mat3D rotation2 = new Mat3D();

		Vec3D translation1 = new Vec3D();
		Vec3D translation2 = new Vec3D();

		Vec3D eps;
		
		double error = 0, error1, error2;
		
		final int iterations = 100;

		for (int i = 0; i < iterations; ++ i){
			eps = Vec3D.addScalar( Vec3D.multScalar( 
					Mat3D.multVector( this.modelVectors, rotation.row(2) ), 1.0 / translation.v[2]), 1.0);

			this.pos(points, eps, rotation1, translation1, rotation2, translation2);

			error1 = this.getError(points, rotation1, translation1);
			error2 = this.getError(points, rotation2, translation2);

			if (error1 < error2){
				rotation.copy(rotation1);
				translation.copy(translation1);
				error = error1;
			}else{
				rotation.copy(rotation2);
				translation.copy(translation2);
				error = error2;
			}

			if ( (error <= 2.0) || (error > prevError) ){
				break;
			}

			prevError = error;
		}

		return error;
	}

	private double getError (List<Point2D> points, Mat3D rotation, Vec3D translation){

		Vec3D v1 = Vec3D.add( Mat3D.multVector(rotation, this.model.get(0)), translation);
		Vec3D v2 = Vec3D.add( Mat3D.multVector(rotation, this.model.get(1)), translation);
		Vec3D v3 = Vec3D.add( Mat3D.multVector(rotation, this.model.get(2)), translation);
		Vec3D v4 = Vec3D.add( Mat3D.multVector(rotation, this.model.get(3)), translation);

		double ia1, ia2, ia3, ia4, ma1, ma2, ma3, ma4;

		v1.v[0] *= this.focalLength / v1.v[2];
		v1.v[1] *= this.focalLength / v1.v[2];
		v2.v[0] *= this.focalLength / v2.v[2];
		v2.v[1] *= this.focalLength / v2.v[2];
		v3.v[0] *= this.focalLength / v3.v[2];
		v3.v[1] *= this.focalLength / v3.v[2];
		v4.v[0] *= this.focalLength / v4.v[2];
		v4.v[1] *= this.focalLength / v4.v[2];

		List<Point2D>modeled = new ArrayList<Point2D>();
		
		modeled.add(new Point2D(v1.v[0], v1.v[1]));
		modeled.add(new Point2D(v2.v[0], v2.v[1]));
		modeled.add(new Point2D(v3.v[0], v3.v[1]));
		modeled.add(new Point2D(v4.v[0], v4.v[1]));
		
		ia1 = this.angle( points.get(0), points.get(1), points.get(2) );
		ia2 = this.angle( points.get(1), points.get(3), points.get(0) );
		ia3 = this.angle( points.get(3), points.get(2), points.get(1) );
		ia4 = this.angle( points.get(2), points.get(0), points.get(3) );

		ma1 = this.angle( modeled.get(0), modeled.get(1), modeled.get(3) );
		ma2 = this.angle( modeled.get(1), modeled.get(2), modeled.get(0) );
		ma3 = this.angle( modeled.get(2), modeled.get(3), modeled.get(1) );
		ma4 = this.angle( modeled.get(3), modeled.get(0), modeled.get(2) );

		return ( Math.abs(ia1 - ma1) +
				Math.abs(ia2 - ma2) +
				Math.abs(ia3 - ma3) +
				Math.abs(ia4 - ma4) ) / 4.0;
	}

	private double angle (Point2D a, Point2D b, Point2D c) {

		double x1 = b.getX() - a.getX(), y1 = b.getY() - a.getY();

		double x2 = c.getX() - a.getX(), y2 = c.getY() - a.getY();
		
		return Math.acos( (x1 * x2 + y1 * y2) / 
				(Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2) ) ) * 180.0 / Math.PI;
	}

}
