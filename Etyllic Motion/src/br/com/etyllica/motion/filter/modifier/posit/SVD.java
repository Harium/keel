package br.com.etyllica.motion.filter.modifier.posit;

public class SVD {
	
	protected double[][] B;
	
	protected boolean PseudoInverse(double[][] A) 
	/*
	B returns the pseudoinverse of A (A with dimension N rows x 3 columns).
	It is the matrix v.[diag(1/wi)].(u)t (cf. svdcmp())
	Function returns True if B has maximum rank, False otherwise
	 */
	{
		
		int N = A[0].length;
		
		B = new double[A.length][N];		

		int nbCoords = 3;

		double[][] temp = new double[3][3];

		double[] W = new double[3];
		double WMAX;
		double TOL = 0.01;
		int i,j,k;
		boolean isMaxRank = true;/* stays true if no singular value under tolerance level */

		double[][] V = new double[nbCoords][nbCoords];

		/*Singular value decomposition*/
		svdcmp(A,N,nbCoords,W,V);

		/*Getting largest singular value*/
		WMAX=0.0;
		for (i=0;i<nbCoords;i++){
			if (W[i]>WMAX) WMAX=W[i];
		}

		/*Checking for signular values smaller than TOL times the largest one*/
		for (i=0;i<nbCoords;i++){
			
			if (W[i]<TOL*WMAX){
				
				W[i]=0;
				
				isMaxRank = false;
				
				break;
			}
			
		}

		if(isMaxRank){

			/*Computing B*/     
			for (i=0;i<3;i++){
				for (j=0;j<3;j++){
					temp[i][j]=V[i][j]/W[j];
				}
			}

			for (i=0;i<3;i++){
				for (j=0;j<N;j++){
					B[i][j]=0.0;
					for (k=0;k<3;k++){
						B[i][j]+=temp[i][k]*A[j][k];
					}
				}
			}

		}

		return isMaxRank;
	}
	
	private void svdcmp(double[][] a, int m, int n, double w[], double[][] v) {
		/*
		Given a matrix a with dimension m x n, get singular value decomposition, 
		a=U.W.(V)t. Matrix U replaces a in output. Diagonal matrix W is provided as vector w.
		m must be larger than n or equal to it. If not, user must pad with zeroes.
		 */
			boolean flag = false;
			
			int i, its, j, jj, k, l = 0;
			
			int nm = 0;
			
			double c,f,h,s,x,y,z;
			
			double anorm=0.0,g=0.0,scale=0.0;
			
			double[] rv1;
			
			double myNb = 1;

			if (m < n){
				System.err.println("SVDCMP: You must augment A with extra zero rows");
			}
			
			rv1 = new double[n];
			
			for (i=1;i<=n;i++) {
				
				l=i+1;
				
				rv1[i-1]=scale*g;
				
				g=s=scale=0.0;
				
				if (i <= m) {
					for (k=i;k<=m;k++) scale += Math.abs(a[k-1][i-1]);
					if (scale!=0) {
						
						for (k=i;k<=m;k++) {
							a[k-1][i-1] /= scale;
							s += a[k-1][i-1]*a[k-1][i-1];
						}
						
						f=a[i-1][i-1];
						
						g = -SIGN(Math.sqrt(s),f);
						
						h=f*g-s;
						
						a[i-1][i-1]=f-g;
						
						if (i != n) {
							for (j=l;j<=n;j++) {
								for (s=0.0,k=i;k<=m;k++) s += a[k-1][i-1]*a[k-1][j-1];
								f=s/h;
								for (k=i;k<=m;k++) a[k-1][j-1] += f*a[k-1][i-1];
							}
						}
						for (k=i;k<=m;k++) a[k-1][i-1] *= scale;
					}
				}
				
				w[i-1]=scale*g;
				
				g=s=scale=0.0;
				
				if (i <= m && i != n) {
					
					for (k=l;k<=n;k++) scale += Math.abs(a[i-1][k-1]);
					
					if (scale!=0) {
						for (k=l;k<=n;k++) {
							a[i-1][k-1] /= scale;
							s += a[i-1][k-1]*a[i-1][k-1];
						}
						
						f=a[i-1][l-1];
						
						g = -SIGN(Math.sqrt(s),f);
						
						h=f*g-s;
						
						a[i-1][l-1]=f-g;
						
						for (k=l;k<=n;k++) rv1[k-1]=a[i-1][k-1]/h;
						/*	if (i != m) { */
						for (j=l;j<=m;j++) {
							for (s=0.0,k=l;k<=n;k++) s += a[j-1][k-1]*a[i-1][k-1];
							for (k=l;k<=n;k++) a[j-1][k-1] += s*rv1[k-1];
						}
						/*	}*/
						for (k=l;k<=n;k++) a[i-1][k-1] *= scale;
					}
				}
				
				anorm = Math.max(anorm,(Math.abs(w[i-1])+Math.abs(rv1[i-1])));
				
			}
			
			for (i=n;i>=1;i--) {
				
				if (i < n) {
					
					if (g!=0) {
						
						for (j=l;j<=n;j++){
							v[j-1][i-1]=(a[i-1][j-1]/a[i-1][l-1])/g;
						}
						
						for (j=l;j<=n;j++) {
							
							for (s=0.0,k=l;k<=n;k++){
								s += a[i-1][k-1]*v[k-1][j-1];
							}
							
							for (k=l;k<=n;k++){
								v[k-1][j-1] += s*v[k-1][i-1];
							}
						}
					}
					
					for (j=l;j<=n;j++){
						v[i-1][j-1]=v[j-1][i-1]=0.0;
					}
				}
				
				v[i-1][i-1]=1.0;
				
				g=rv1[i-1];
				
				l=i;
			}
			
			for (i=n;i>=1;i--) {
				
				l=i+1;
				
				g=w[i-1];
				
				if (i < n){
					for (j=l;j<=n;j++) a[i-1][j-1]=0.0;
				}
				
				if (g!=0) {
					
					g=1.0/g;
					if (i != n) {
						for (j=l;j<=n;j++) {
							for (s=0.0,k=l;k<=m;k++) s += a[k-1][i-1]*a[k-1][j-1];
							f=(s/a[i-1][i-1])*g;
							for (k=i;k<=m;k++) a[k-1][j-1] += f*a[k-1][i-1];
						}
					}
					for (j=i;j<=m;j++) a[j-1][i-1] *= g;
				}
				else {
					for (j=i;j<=m;j++) a[j-1][i-1]=0.0;
				}
				++a[i-1][i-1];
			}
			for (k=n;k>=1;k--) {
				
				for (its=1;its<=30;its++) {
					
					flag = true;
					
					for (l=k;l>=1;l--) {
						
						nm=l-1;
						
						if (Math.abs(rv1[l-1])+anorm == anorm) {
							flag = false;
							break;
						}
						
						if (Math.abs(w[nm])+anorm == anorm) break;
						
					}
					
					if (flag) {
						
						c=0.0;
						s=1.0;
						
						for (i=l;i<=k;i++) {
							
							f=s*rv1[i-1];
							
							if (Math.abs(f)+anorm != anorm) {
								
								g=w[i-1];
								h=pythag(f,g);
								w[i-1]=h;
								h=1.0/h;
								c=g*h;
								s=(-f*h);
								for (j=1;j<=m;j++) {
									y=a[j-1][nm-1];
									z=a[j-1][i-1];
									a[j-1][nm-1]=y*c+z*s;
									a[j-1][i-1]=z*c-y*s;
								}
							}
						}
					}
					
					z=w[k-1];
					
					if (l == k) {
						
						if (z < 0.0) {
							w[k-1] = -z;
							for (j=1;j<=n;j++) v[j-1][k-1]=(-v[j-1][k-1]);
						}
						break;
						
					}
					
					if (its == 50){
						System.err.println("No convergence in 50 SVDCMP iterations");
					}
					
					x=w[l-1];
					
					nm=k-1;
					
					y=w[nm-1];
					
					g=rv1[nm-1];
					
					h=rv1[k-1];
					
					f=((y-z)*(y+z)+(g-h)*(g+h))/(2.0*h*y);
					
					g=pythag(f,1.0);
					
					f=((x-z)*(x+z)+h*((y/(f+SIGN(g,f)))-h))/x;
					
					c=s=1.0;
					
					for (j=l;j<=nm;j++) {
						
						i=j+1;
						
						g=rv1[i-1];
						
						y=w[i-1];
						
						h=s*g;
						
						g=c*g;
						
						z=pythag(f,h);
						
						rv1[j-1]=z;
						c=f/z;
						s=h/z;
						f=x*c+g*s;
						g=g*c-x*s;
						h=y*s;
						y=y*c;
						
						for (jj=1;jj<=n;jj++) {
							x=v[jj-1][j-1];
							z=v[jj-1][i-1];
							v[jj-1][j-1]=x*c+z*s;
							v[jj-1][i-1]=z*c-x*s;
						}
						
						z=pythag(f,h);
						
						w[j-1]=z;
						
						if (z!=0) {
							z=1.0/z;
							c=f*z;
							s=h*z;
						}
						
						f=(c*g)+(s*y);
						x=(c*y)-(s*g);
						for (jj=1;jj<=m;jj++) {
							y=a[jj-1][j-1];
							z=a[jj-1][i-1];
							a[jj-1][j-1]=y*c+z*s;
							a[jj-1][i-1]=z*c-y*s;
						}
						
					}
					
					rv1[l-1]=0.0;
					rv1[k]=f;
					w[k-1]=x;
					
				}
			}
			
		}

		/****************************************************************************/
	
	private double SIGN(double a, double b) {
		
		return ((b) >= 0.0 ? Math.abs(a) : -Math.abs(a));
		
	}
	
	private double SQR(double a) {
		return a*a;
	}
		
	private double pythag(double a, double b) {
		
		double absa, absb;

		absa = Math.abs(a);
		
		absb = Math.abs(b);
		
		if (absa > absb) return absa * Math.sqrt(1.0 + SQR(absb/absa));
		
		else return (absb == 0.0 ? 0.0 : absb * Math.sqrt(1.0 + SQR(absa/absb)));
		
	}
	
}
