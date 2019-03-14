package org.opencv.modules.calib3d;

import com.harium.etyl.geometry.Point2D;
import com.harium.etyl.geometry.Point3D;
import org.opencv.CvStatus;
import org.opencv.OpenCv;
import org.opencv.criteria.CriteriaType;
import org.opencv.criteria.CvTermCriteria;

import java.util.List;

public class Posit {

    private double[] rotation;
    private double[] translation;

    public Posit() {
        super();

        rotation = new double[3 * 3];
        translation = new double[1 * 3];
    }

    private static class CvPOSITObject {

        int N;

        double[] inv_matr;
        double[] obj_vecs;
        double[] img_vecs;

        public CvPOSITObject(int n) {
            super();

            /* buffer for inverse matrix = N*3*float */
            /* buffer for storing weakImagePoints = numPoints * 2 * float */
            /* buffer for storing object vectors = N*3*float */
            /* buffer for storing image vectors = N*2*float */

            this.N = n - 1;

            int inv_matr_size = N * 3;
            int obj_vec_size = inv_matr_size;
            int img_vec_size = N * 2;

            inv_matr = new double[inv_matr_size];
            obj_vecs = new double[obj_vec_size];
            img_vecs = new double[img_vec_size];
        }

    }

    public CvStatus icvPOSIT(List<Point3D> points, List<Point2D> imagePoints,
                             double focalLength, CvTermCriteria criteria) {

        CvPOSITObject pObject = icvCreatePOSITObject(points);

        int i, j, k;
        int count = 0;

        boolean converged = false;

        double inorm, jnorm, invInorm, invJnorm, invScale, scale = 0, inv_Z = 0;
        double diff = criteria.getEpsilon();
        double inv_focalLength = 1 / focalLength;

        /* Check bad arguments */
        if (imagePoints == null)
            return CvStatus.CV_NULLPTR_ERR;
        if (pObject == null)
            return CvStatus.CV_NULLPTR_ERR;
        if (focalLength <= 0)
            return CvStatus.CV_BADFACTOR_ERR;
        if ((criteria.getType() == null))
            return CvStatus.CV_BADFLAG_ERR;
        if ((criteria.hasType(CriteriaType.CV_TERMCRIT_EPS) && criteria.getEpsilon() < 0))
            return CvStatus.CV_BADFACTOR_ERR;
        if ((criteria.hasType(CriteriaType.CV_TERMCRIT_ITER) && criteria.getMaxIter() <= 0))
            return CvStatus.CV_BADFACTOR_ERR;

        /* init variables */
        int N = pObject.N;
        double[] objectVectors = pObject.obj_vecs;
        double[] invMatrix = pObject.inv_matr;
        double[] imgVectors = pObject.img_vecs;

        while (!converged) {
            if (count == 0) {

                /* subtract out origin to get image vectors */
                for (i = 0; i < N; i++) {
                    imgVectors[i] = imagePoints.get(i + 1).x - imagePoints.get(0).x;
                    imgVectors[N + i] = imagePoints.get(i + 1).y - imagePoints.get(0).y;
                }

            } else {
                diff = 0;
                /* compute new SOP (scaled orthograthic projection) image from pose */
                for (i = 0; i < N; i++) {

                    /* objectVector * k */
                    double old;
                    double tmp = objectVectors[i] * rotation[6] /*[2][0]*/ +
                            objectVectors[N + i] * rotation[7] /*[2][1]*/ +
                            objectVectors[2 * N + i] * rotation[8] /*[2][2]*/;

                    tmp *= inv_Z;
                    tmp += 1;

                    old = imgVectors[i];
                    imgVectors[i] = imagePoints.get(i + 1).x * tmp - imagePoints.get(0).x;

                    diff = Math.max(diff, Math.abs(imgVectors[i] - old));

                    old = imgVectors[N + i];
                    imgVectors[N + i] = imagePoints.get(i + 1).y * tmp - imagePoints.get(0).y;

                    diff = Math.max(diff, (float) Math.abs(imgVectors[N + i] - old));
                }

            }

            /* calculate I and J vectors */
            for (i = 0; i < 2; i++) {
                for (j = 0; j < 3; j++) {
                    rotation[3 * i + j] /*[i][j]*/ = 0;
                    for (k = 0; k < N; k++) {
                        rotation[3 * i + j] /*[i][j]*/ += invMatrix[j * N + k] * imgVectors[i * N + k];
                    }
                }
            }

            inorm = rotation[0] /*[0][0]*/ * rotation[0] /*[0][0]*/ +
                    rotation[1] /*[0][1]*/ * rotation[1] /*[0][1]*/ +
                    rotation[2] /*[0][2]*/ * rotation[2] /*[0][2]*/;

            jnorm = rotation[3] /*[1][0]*/ * rotation[3] /*[1][0]*/ +
                    rotation[4] /*[1][1]*/ * rotation[4] /*[1][1]*/ +
                    rotation[5] /*[1][2]*/ * rotation[5] /*[1][2]*/;

            invInorm = OpenCv.cvInvSqrt(inorm);
            invJnorm = OpenCv.cvInvSqrt(jnorm);

            inorm *= invInorm;
            jnorm *= invJnorm;

            rotation[0] /*[0][0]*/ *= invInorm;
            rotation[1] /*[0][1]*/ *= invInorm;
            rotation[2] /*[0][2]*/ *= invInorm;

            rotation[3] /*[1][0]*/ *= invJnorm;
            rotation[4] /*[1][1]*/ *= invJnorm;
            rotation[5] /*[1][2]*/ *= invJnorm;

            /* row2 = row0 x row1 (cross product) */
            rotation[6] /*->m[2][0]*/ = rotation[1] /*->m[0][1]*/ * rotation[5] /*->m[1][2]*/ -
                    rotation[2] /*->m[0][2]*/ * rotation[4] /*->m[1][1]*/;

            rotation[7] /*->m[2][1]*/ = rotation[2] /*->m[0][2]*/ * rotation[3] /*->m[1][0]*/ -
                    rotation[0] /*->m[0][0]*/ * rotation[5] /*->m[1][2]*/;

            rotation[8] /*->m[2][2]*/ = rotation[0] /*->m[0][0]*/ * rotation[4] /*->m[1][1]*/ -
                    rotation[1] /*->m[0][1]*/ * rotation[3] /*->m[1][0]*/;

            scale = (inorm + jnorm) / 2.0f;
            inv_Z = scale * inv_focalLength;

            count++;
            converged = (criteria.hasType(CriteriaType.CV_TERMCRIT_EPS) && (diff < criteria.getEpsilon()));
            converged |= (criteria.hasType(CriteriaType.CV_TERMCRIT_ITER) && (count == criteria.getMaxIter()));

        }

        invScale = 1 / scale;

        translation[0] /*[0][0]*/ = imagePoints.get(0).x * invScale;
        translation[1] /*[1][0]*/ = imagePoints.get(0).y * invScale;
        translation[2] /*[2][0]*/ = 1 / inv_Z;

        return CvStatus.CV_NO_ERR;
    }

    private CvPOSITObject icvCreatePOSITObject(List<Point3D> objPoints) {

        int numPoints = objPoints.size();

        /* check bad arguments */
        if (numPoints < 4)
            return null;

        int i;

        int N = numPoints - 1;

        CvPOSITObject pObject = new CvPOSITObject(numPoints);

        /****************************************************************************************\
         * Construct object vectors from object points *
         \****************************************************************************************/
        for (i = 0; i < numPoints - 1; i++) {

            pObject.obj_vecs[i] = objPoints.get(i + 1).x - objPoints.get(0).x;
            pObject.obj_vecs[N + i] = objPoints.get(i + 1).y - objPoints.get(0).y;
            pObject.obj_vecs[2 * N + i] = objPoints.get(i + 1).z - objPoints.get(0).z;

        }
        /****************************************************************************************\
         * compute pseudoinverse matrix *
         \****************************************************************************************/
        pObject.inv_matr = icvPseudoInverse3D(pObject.obj_vecs, N, 0);

        return pObject;
    }

    public double[] icvPseudoInverse3D(double[] a, int n, int method) {

        double[] b = new double[n * 3];

        int k;

        if (method == 0) {

            double ata00 = 0;

            double ata11 = 0;

            double ata22 = 0;

            double ata01 = 0;

            double ata02 = 0;

            double ata12 = 0;

            double det = 0;

            /* compute matrix ata = transpose(a) * a */
            for (k = 0; k < n; k++) {
                double a0 = a[k];
                double a1 = a[n + k];
                double a2 = a[2 * n + k];

                ata00 += a0 * a0;
                ata11 += a1 * a1;
                ata22 += a2 * a2;

                ata01 += a0 * a1;
                ata02 += a0 * a2;
                ata12 += a1 * a2;
            }
            /* inverse matrix ata */
            {
                double inv_det;
                double p00 = ata11 * ata22 - ata12 * ata12;
                double p01 = -(ata01 * ata22 - ata12 * ata02);
                double p02 = ata12 * ata01 - ata11 * ata02;

                double p11 = ata00 * ata22 - ata02 * ata02;
                double p12 = -(ata00 * ata12 - ata01 * ata02);
                double p22 = ata00 * ata11 - ata01 * ata01;

                det += ata00 * p00;
                det += ata01 * p01;
                det += ata02 * p02;

                inv_det = 1 / det;

                /* compute resultant matrix */
                for (k = 0; k < n; k++) {
                    double a0 = a[k];
                    double a1 = a[n + k];
                    double a2 = a[2 * n + k];

                    b[k] = (p00 * a0 + p01 * a1 + p02 * a2) * inv_det;
                    b[n + k] = (p01 * a0 + p11 * a1 + p12 * a2) * inv_det;
                    b[2 * n + k] = (p02 * a0 + p12 * a1 + p22 * a2) * inv_det;
                }
            }
        }

		/*if ( method == 1 ) {
		}
		 */

        return b;
    }

    public double[] getRotation() {
        return rotation;
    }

    public void setRotation(double[] rotation) {
        this.rotation = rotation;
    }

    public double[] getTranslation() {
        return translation;
    }

    public void setTranslation(double[] translation) {
        this.translation = translation;
    }

}
