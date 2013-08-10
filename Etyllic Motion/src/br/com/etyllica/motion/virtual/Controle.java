package br.com.etyllica.motion.virtual;

import java.awt.Color;
import java.util.Vector;

import br.com.etyllica.camera.Camera;
import br.com.etyllica.layer.Layer;

public class Controle {

	Vector<Layer> mira;
	
	protected Camera camera;
	
	protected int largura = 800;
	protected int altura  = 600;
	protected int larguraCam = 320;
	protected int alturaCam  = 240;
	
	//Mal de Parkinson
	protected int smooth = 1;

	//Cor de Pele
	protected int toleranciaR = 40;
	protected int toleranciaG = 35;
	protected int toleranciaB = 35;

	//protected int veriR = 0x69;
	//protected int veriG = 0x59;
	//protected int veriB = 0x50;
	protected int veriR = 0x46;
	protected int veriG = 0x39;
	protected int veriB = 0x30;
	
	public Controle(){
		mira = new Vector<Layer>();
		camera = new Camera();
	}
	
	public Vector<Layer> getMira(){
		return mira;
	}
	
	
	protected int fatorX(int x){

		int fx = (x*largura)/larguraCam;
		//int fx = (x*largura)/640;
		return fx;
	}
	protected int fatorY(int y){
		int fy = (y*altura)/alturaCam;
		//int fy = (y*altura)/480;
		return fy;
	}

	public int getX(int dedo){
		return fatorX(larguraCam-mira.get(dedo).getX());
	}
	public int getY(int dedo){
		return fatorY(mira.get(dedo).getY());
	}
	
	protected int moduloDiferenca(int a, int b){
		int dif = a-b;
		if(dif<0)
			dif*=-1;
		return dif;
	}
	
	protected boolean ehCordePele(int rgb){
		return ehCordePele(rgb, toleranciaR, toleranciaG, toleranciaB);
	}
	protected boolean ehCordePele(int rgb, int tolerancia){
		return ehCordePele(rgb,tolerancia,tolerancia,tolerancia);
	}
	
	protected boolean ehCordePele(int rgb, int toleranciaR, int toleranciaG, int toleranciaB){
		Color c = new Color(rgb);

		//3D2622
		int rs = veriR;
		int gs = veriG;
		int bs = veriB;

		//int low = 3;
		int low = 1;
		
		if((c.getRed()<rs+toleranciaR)&&(c.getRed()>rs-toleranciaR/low)&&
				(c.getGreen()<gs+toleranciaG)&&(c.getGreen()>gs-toleranciaG/low)&&
				(c.getBlue()<bs+toleranciaB)&&(c.getBlue()>bs-toleranciaB/low))
		{
			return true;
		}


		return false;
	}

	
}
