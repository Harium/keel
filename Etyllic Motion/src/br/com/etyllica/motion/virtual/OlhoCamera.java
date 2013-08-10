package br.com.etyllica.motion.virtual;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;



import br.com.etyllica.camera.Camera;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.Layer;

public class OlhoCamera extends Controle{		

	private int olhos = 2;

	private BufferedLayer cm;

	private int areaOlho = 2;

	Layer olhoD;
	Layer olhoE;
	Layer nariz;

	private boolean mask;

	public OlhoCamera(int dedos){
		super();

		this.olhos = dedos;

		cm = new BufferedLayer(0,0);

		olhoE = new Layer(0,0);
		olhoD = new Layer(0,0);
		nariz = new Layer(0,0);

		iris = new Layer(0,0);

		mask = true;
	}	

	public void gerencia(){
		if(camera.getBufferedImage()!=null){

			cm.igualaImagem(camera.getBufferedImage());

			BufferedImage b = new BufferedImage(camera.getBufferedImage().getWidth(null),camera.getBufferedImage().getHeight(null),BufferedImage.TYPE_INT_RGB);
			b.getGraphics().drawImage(camera.getBufferedImage(),0,0,null);

			//Mask
			//eyeFilter(b);
			//procura onde estava antes
			reductFilter(cm.getImagemBuffer());

			//Se n�o achar, eyeFilter
			eyeFilter(cm.getImagemBuffer());
		}
	}

	public Vector<Layer> getDedos(){
		return mira;
	}

	public Camera getCamera(){
		return camera;
	}

	public BufferedLayer getCM(){
		return cm;
	}

	public int getX(int dedo){
		return fatorX(larguraCam-mira.get(dedo).getX());
	}
	public int getY(int dedo){
		return fatorY(mira.get(dedo).getY());
	}

	public Layer getAreaOlhoE(){
		return olhoE;
	}
	public Layer getAreaOlhoD(){
		return olhoD;
	}

	//int maiorX = 0;

	//int toleranciaPele;
	//int cordePele;

	//int toleranciaIris;

	//Posso pegar o rect baseado nos Yellows

	public Layer getFakeIris(){
		return iris;
	}

	Layer iris;

	int irisX;
	int irisXL;
	int irisY;
	int irisYL;

	public void reductFilter(BufferedImage b){

		//Para cada olho, varrer somente o rect do Olho
		//Se todos os quadrados forem in�teis, EyeFilter
		//Acha novamente o Olho
		for(int i = 0;i<mira.size();i++){
			//Procurar Azul no meio de Preto
			//Procurar Linhas Azuis com vizinhos em cima preto(inicio)
			//E linhas azuis no fim dos grafos com vizinhos de baixo preto

			//Se for tudo azul ou tudo preto, eyefilter
		}

	}

	private int[][] preencheM(BufferedImage b){

		int rect = 10;

		int w = b.getWidth();
		int h = b.getHeight();

		//Matriz de abstra��o
		int[][] m = new int[w][h];

		//N�meros especiais
		//0 - preto
		//1 - amarelo
		int numerodopai = 2;

		int numeroblack = 0;
		int areablack = 0;

		//maiorX = 0;

		int mediaEX = larguraCam/2;
		int mediaDX = larguraCam/2;
		int mediaEY = alturaCam/2;
		int mediaDY = alturaCam/2;

		for(int j=rect;j<h-rect;j++){

			for(int i=rect;i<w-rect;i++){

				int rgb = b.getRGB(i,j);

				//if(ehBranco(rgb,150,110)){
				if(ehdaCor(new Color(0x50,0x50,0x50).getRGB(),rgb,58)){

					//N�mero do pai
					//Verifica Redondeza
					if(m[i-1][j-1]>1){
						m[i][j] = m[i-1][j-1]; 
					}
					else if(m[i][j-1]>1){
						m[i][j] = m[i][j-1];
					}
					else if(m[i+1][j-1]>1){
						m[i][j] = m[i+1][j-1];
					}
					else if(m[i-1][j]>1){
						m[i][j] = m[i-1][j];
					}
					else{
						m[i][j] = numerodopai++;
						//Define Novo Grafo X e Y

					}

					int dim = 110;
					
					int rd = new Color(rgb).getRed();
					rd-=dim;
					if(rd<0)
						rd = 0;
					int gr = new Color(rgb).getGreen();
					gr-=dim;
					if(gr<0)
						gr = 0;
					int bl = new Color(rgb).getBlue();

					Color n = new Color(rd,gr,bl);

					b.setRGB(i,j,n.getRGB());
					//b.setRGB(i,j,Color.BLUE.getRGB());

					/*
					switch(numerodopai%3){

					case 1:
						b.setRGB(i,j,Color.CYAN.getRGB());
						break;
					case 2:
						b.setRGB(i,j,Color.ORANGE.getRGB());
						break;
					default:
						b.setRGB(i,j,Color.BLUE.getRGB());
						break;
					}
					 */

					//verificaRedondeza(i,j);

					//b.setRGB(i,j,Color.BLUE.getRGB());
					//m[i][j] = 1;
				}

				//Procura �ris
				//else if(ehdaCor(Color.BLACK.getRGB(),rgb,16)){
				else if(
						ehdaCor(Color.BLACK.getRGB(),rgb,28)
						//&&(areablack<=larguraCam/3))				
				)
				{
					//else if(ehdaCor(new Color(0x66,0x39,0x34).getRGB(),rgb,22)){
					//N�mero do pai

					//achou amarelo
					//m[i][j] = 1;
					m[i][j] = -1;
					b.setRGB(i,j,Color.YELLOW.getRGB());

					if(i<irisX){
						irisX = i;
					}
					else if(i>irisXL){
						irisXL = i;
					}
					if(j<irisY){
						irisY = j;
					}
					else if(j>irisYL){
						irisYL = j;
					}

					p.add(new Layer(i,j));

					if(i>larguraCam/2){
						mediaDX+=i;
						mediaDX/=2;

						mediaDY+=j;
						mediaDY/=2;
					}
					else{
						mediaEX+=i;
						mediaEX/=2;

						mediaEY+=j;
						mediaEY/=2;
					}
					//soma e divide por dois

				}
				else{				


					if(m[i-1][j-1]<1){
						m[i][j] = m[i-1][j-1];
						areablack++;
					}
					else if(m[i][j-1]<1){
						m[i][j] = m[i][j-1];
						areablack++;
					}
					else if(m[i+1][j-1]<1){
						m[i][j] = m[i+1][j-1];
						areablack++;
					}
					else if(m[i-1][j]<1){
						m[i][j] = m[i-1][j];
						areablack++;
					}
					else{
						m[i][j] = numeroblack--;
						//Define Novo Grafo X e Y
						areablack = 1;

					}

					//b.setRGB(i,j,Color.BLACK.getRGB());
					int dim = 110;
					
					int rd = new Color(rgb).getRed();
					rd-=dim;
					if(rd<0)
						rd = 0;
					int gr = new Color(rgb).getGreen();
					int bl = new Color(rgb).getBlue();
					bl-=dim;
					if(bl<0)
						bl = 0;

					Color n = new Color(rd,gr,bl);

					b.setRGB(i,j,n.getRGB());
					
					m[i][j] = numeroblack;
				}
			}

		}

		return m;
	}

	Vector<Layer> p;

	public void eyeFilter(BufferedImage b){

		mira.removeAllElements();


		p = new Vector<Layer>();

		int[][] m;

		m = preencheM(b);


		//Todas as Layers foram colocadas no vetor.
		int somaXD = 0;
		int somaXE = 0;

		int somaYD = 0;
		int somaYE = 0;
		int psize = p.size();

		int ex = 0,ey = 0;

		int e=0,d=0;

		if(psize>0){

			for(int c=0;c<p.size();c++){

				//S� para aproveitar a vari�vel
				ex = p.get(c).getX();
				ey = p.get(c).getY();

				if(ex>larguraCam/2){
					somaXD+=p.get(c).getX();
					somaYD+=p.get(c).getY();
					d++;
				}
				else{
					somaXE+=p.get(c).getX();
					somaYE+=p.get(c).getY();
					e++;
				}

			}

			//Se forem encontrados amarelos
			//Tira a m�dia e define o Olho 

			//N�o fazer esse tipo de m�dia ou n�o deixar variar muito
			if(e>0){
				ex = somaXE/e;
				ey = somaYE/e;

				//ex = mediaEX;
				//ey = mediaEY;

				mira.add(new Layer(ex,ey));
			}

			//N�o deixar esse Rect mudar muito 5%~10%
			olhoE = crossScan(m, ex, ey, b);

			//N�o fazer esse tipo de m�dia ou n�o deixar variar muito
			if(d>0){
				ex = somaXD/d;
				ey = somaYD/d;
				mira.add(new Layer(ex,ey));
			}

			//N�o deixar esse Rect mudar muito
			olhoD = crossScan(m, ex, ey, b);

			iris.setCoordinates(irisX, irisY);
			iris.setW(irisXL-irisX);
			iris.setH(irisYL-irisY);

			irisX = larguraCam;
			irisY = alturaCam;
			irisXL = 0;
			irisYL = 0;
		}

		//System.out.println("Numero de grafos: "+numerodopai);

	}

	int maiorX = larguraCam;
	int maiorY = alturaCam;

	int menorX = 0;
	int menorY = 0;

	private boolean ehdaCor(int veri, int rgb, int tolerancia){
		Color c = new Color(rgb);

		int toleranciaR = tolerancia;
		int toleranciaG = tolerancia;
		int toleranciaB = tolerancia;

		Color vcor = new Color(veri);

		//3D2622
		int rs = vcor.getRed();
		int gs = vcor.getGreen();
		int bs = vcor.getBlue();

		if((c.getRed()<rs+toleranciaR)&&(c.getRed()>rs-toleranciaR/2)&&
				(c.getGreen()<gs+toleranciaG)&&(c.getGreen()>gs-toleranciaG/2)&&
				(c.getBlue()<bs+toleranciaB)&&(c.getBlue()>bs-toleranciaB/2))
		{
			return true;
		}


		return false;		
	}

	public boolean ehBranco(int rgb, int tolerancia, int brancura){

		Color c = new Color(rgb);

		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();

		int media = (red+green+blue)/3;

		if(media<brancura)
			return false;

		if(moduloDiferenca(red, media)>tolerancia){
			return false;
		}
		else if(moduloDiferenca(green, media)>tolerancia){
			return false;
		}
		else if(moduloDiferenca(blue, media)>tolerancia){
			return false;
		}

		return true;
	}

	public boolean ehCinza(int rgb, int tolerancia){

		Color c = new Color(rgb);

		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();

		int media = (red+green+blue)/3;

		if(moduloDiferenca(red, media)>tolerancia){
			return false;
		}
		else if(moduloDiferenca(green, media)>tolerancia){
			return false;
		}
		else if(moduloDiferenca(blue, media)>tolerancia){
			return false;
		}

		return true;
	}

	//CrossScan Melhorado
	private Layer crossScan(int[][] m, int x, int y, BufferedImage b){

		int maiorX = x;
		int maiorY = y+1;

		int menorX = x;
		int menorY = y-1;

		boolean achoupreto = true;

		//int j = y;
		int i = x;

		//Para i indo para a direita
		while(achoupreto){

			achoupreto = false;

			for(int j=y;j>=menorY;j--){

				if(j<0)
					break;

				if(m[i][j]<=0){
					if(j<=menorY){
						menorY = j-1;
						//menorY = j;
					}

					achoupreto = true;
				}

			}

			for(int j=y;j<=maiorY;j++){

				if(j>=b.getHeight())
					break;

				if(m[i][j]<=0){
					if(j>=maiorY){
						maiorY = j+1;
						//maiorY = j;
					}

					achoupreto = true;
				}

			}

			i++;

			if(i>=b.getWidth())
				break;
		}

		maiorX = i;

		//maiorY = y+1;
		//menorY = y-1;
		i = x;

		achoupreto = true;

		//Para i indo para a esquerda
		while(achoupreto){

			achoupreto = false;

			for(int j=y;j>=menorY;j--){

				if(j<0)
					break;

				if(m[i][j]<=0){
					if(j<=menorY){
						menorY = j-1;
						//menorY = j;
					}

					achoupreto = true;
				}

			}

			for(int j=y;j<=maiorY;j++){

				if(j>=b.getHeight())
					break;

				if(m[i][j]<=0){
					if(j>=maiorY){
						maiorY = j+1;
						//maiorY = j;
					}

					achoupreto = true;
				}

			}

			i--;

			if(i<=0)
				break;
		}

		menorX = i;

		return new Layer(menorX,menorY,maiorX-menorX,maiorY-menorY);
	}

}
