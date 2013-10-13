package br.com.etyllica.motion.custom.face;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.filter.BooleanMaskFilter;

public class FindSkinFilter extends BooleanMaskFilter{

	public FindSkinFilter(int w, int h) {
		super(w, h);
	}

	public List<Componente> filter(BufferedImage bimg, Componente component){

		List<Componente> result = new ArrayList<Componente>();
		
		for (int j = border; j < h-border*2; j+=step) {

			for (int i = border; i < w-border*2; i+=step) {

				if (validateColor(bimg.getRGB(i,j)) && !mask[i][j]) {

					Queue<Ponto2D> queue = new LinkedList<Ponto2D>();
					queue.add(new Ponto2D(i, j));

					Componente lista = new Componente(w,h);

					while (!queue.isEmpty()) {
						Ponto2D p = queue.remove();

						if ((p.getX() >= 0) && (p.getX() < w &&
								(p.getY() >= 0) && (p.getY() < h))) {
							if (!mask[(int)p.getX()][(int)p.getY()] && validateColor(bimg.getRGB((int)p.getX(), (int)p.getY()))) {
								mask[(int)p.getX()][(int)p.getY()] = true;

								lista.add(p);

								queue.add(new Ponto2D((int)p.getX() + step, (int)p.getY()));
								queue.add(new Ponto2D((int)p.getX() - step, (int)p.getY()));
								queue.add(new Ponto2D((int)p.getX(), (int)p.getY() + step));
								queue.add(new Ponto2D((int)p.getX(), (int)p.getY() - step));

								//queue.add(new Ponto((int)p.getX() + 1, (int)p.getY() + 1));
							}
						}
					}
					//TODO
					//Componentes de cada Grupo

					if(validateComponent(lista)){

						//System.out.println("Blob detected : " + lista.getNumeroPontos() + " pixels.");

						result.add(lista);

					}


				}
			}
		}

		return result;
	}

	@Override
	public boolean validateColor(int rgb) {

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int difG = r-g;
		int difB = r-b;
		
		int difGB = g-b;
		int sumGB = g+b;
		int minSumGB = 0;
		int maxDifGB = 0xff;

		int minDifG = 4;
		int minDifB = 0;
		int maxDifG = 40;
		int maxDifB = 80;

		int minR = 45;
		int minG = 16;
		int minB = 16;
		int maxG = 0xff;
		int maxB = 0xff;

		//Dark Ambient		
		if(r<=95&&r>minR){

			int DARK_TOLERANCE = 23;

			minSumGB = r-DARK_TOLERANCE;						
			maxDifGB = 25;

			minDifG = -4;
			minDifB = 8; //NAO AUMENTAR!

			maxDifG = 19;
			maxDifB = 26;

			minG = 35;
			minB = 30;

		}
		//High Contrast
		else if(r>=250){

			int mean = (r+g+b)/3;
			
			if(mean>230){
				return false;
			}else if(mean<200){
				return false;
			}

			minDifG = 0;
			maxDifG = 125;

			minDifB = -5;
			maxDifB = 165;

			minG = 130;	
			minB = 90;


		}
		//Light Ambient
		else if(r>=225){

			minDifG = -4;
			minDifB = -1;

			maxDifG = 45;

			minG = 150;
			maxG = r;

			minB = 165;

			//White High Contrast Scene
			if(difG>55 && difB-difG>15){
				maxB = 150;
				maxDifB = 100;

				//White Light Scene
			}else if(difG>=20){
				maxB = maxG+5;
				maxDifB = 130;

				//White High Constrast
			}else{
				minB = 110;
				maxB = 180;

				minDifG = 20;
				maxDifB = 80;
			}

			//Probable Light Noise
		}else if(r>=165&&b>r){

			minDifB = -50;
			maxDifB = -5;

			minDifG = -5;
			maxDifG = 20;

			minG = r-20;
			maxG = r+20;

			//TODO Divide scales of noise
		}else if(r>180){
			//Pure White Noise or Bright Skin
			maxDifG = 50;
			maxDifB = 60;
			
			if((b-g>40)||(difGB>40)){
				return false;
			}
						

		}else if(r>90){

			//White Noise (blue)
			if(difB-difG>10){

				minDifB = 50;
				/*if(difB<=50){
					return false;
				}*/

			}else if((difB<10&&difB>0)&&difG<=10){
				return false;
			}

			//Yellow Noise
			if(difB-difG>50){

				if(difB>50&&difB<120){
					return false;
				}else{
					//Yellow Illumination
					minG = 69;
					maxG = 150;

					minB = 5;
				}

			}



			//Not a Noise
			minDifG = -5;
			minDifB = -15;

			maxDifG = 101;

			if(difB>60){
				maxDifB = 134;
			}else{
				maxDifB = 80;	
			}


		}else if(r>82){
			minDifB = -10;
			minDifG = -10;
		}


		boolean rOK = r>minR;
		boolean gOK = g>minG&&g<maxG;
		boolean bOK = b>minB&&b<maxB;

		boolean difgOK = difG>=minDifG&&difG<=maxDifG;
		boolean difbOK = difB>=minDifB&&difB<=maxDifB;
		
		boolean difgbOK = difGB<=maxDifGB;
		boolean sumgbOK = sumGB>=minSumGB;


		return ((rOK)&&(gOK)&&(bOK)&&(difgOK)&&(difbOK)&&(difgbOK)&&(sumgbOK));

	}

	@Override
	public boolean validateComponent(Componente component) {

		boolean sizeok = true;
		//boolean sizeok = component.getH()>component.getW()+component.getW()/6;

		//boolean points = true;
		//boolean points = component.getNumeroPontos()>w*3;
		//boolean points = component.getNumeroPontos()>(w*6)/step;
		boolean points = component.getNumeroPontos()>200;


		return sizeok&&points;
	}

}
