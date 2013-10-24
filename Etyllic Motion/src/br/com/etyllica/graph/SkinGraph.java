package br.com.etyllica.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.Layer;

public class SkinGraph extends Application{

	private List<ColorPoint> good = new ArrayList<ColorPoint>();
	private List<ColorPoint> bad = new ArrayList<ColorPoint>();
	
	private boolean validateGraph = true;

	public SkinGraph(int w, int h){
		super(w,h);
	}

	@Override
	public void load() {

		//Significance R = 45~235

		//Concentration When Green is Higher
		//R = 88~183

		//Concentration When Blue is Higher
		//R = 93~207

		addGoodPoints();
		addBadPoints();

		int blueDots = 0; 

		for(Layer ponto: good){
			if(ponto.getY()>256){
				blueDots++;
			}
		}

		System.out.println("GreenDots = "+(good.size()-blueDots));
		System.out.println("BlueDots = "+blueDots);

		loading = 100;
	}

	private void addGoodPoint(int r, int g, int b){

		good.add(new ColorPoint(r,g,b));

	}

	private void addBadPoint(int r, int g, int b){

		ColorPoint badPoint = new ColorPoint(r, g, b);
		badPoint.setColor(Color.RED);

		bad.add(badPoint);

	}

	@Override
	public void draw(Graphic g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawMouseCross(g);

		if(validateGraph){
			drawValidation(g);
		}
		
		g.setAlpha(80);

		drawBadPoints(g);
		drawGoodPoints(g);

		g.setAlpha(100);

		g.setColor(Color.BLACK);
		g.drawLine(56, 219, 225, 59);

	}

	private void drawValidation(Graphic g){

		g.setAlpha(50);
		
		int w = 255;
		int h = 255;
		
		g.setColor(Color.BLACK);
		
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				if(validateGraph(i, j)){
					g.fillRect(i, j, 1, 1);
				}
				
			}
		}
	}

	private boolean validateGraph(int x, int y){
		int maxTolerance = 20;
		int minTolerance = maxTolerance;
		
		//my=(5*x)/6-25/3;
		//my=(17*x)/18-155/9;
		int my=(8*x)/9-40/9;
		
		if(x>105&&x<175){
			minTolerance = 50;
		}

		return x>40&&x<230&&(y>my-minTolerance&&y<my+maxTolerance);
	}

	private boolean validate(int r, int g, int b){
		int minR = 56;

		if(r<minR){
			return false;
		}

		return true;
	}

	private void drawGoodPoints(Graphic g){

		for(ColorPoint ponto: good){
			ponto.draw(g);
		}
	}

	private void addGoodPoints(){
		addGoodPoint(85,78,62);
		addGoodPoint(115,103,81);
		addGoodPoint(108,100,81);
		addGoodPoint(97,92,73);
		addGoodPoint(106,99,83);
		addGoodPoint(93,94,76);
		addGoodPoint(97,90,74);
		addGoodPoint(102,95,76);
		addGoodPoint(108,101,82);
		addGoodPoint(61,60,42);
		addGoodPoint(71,63,52);
		addGoodPoint(254,230,184);
		addGoodPoint(255,225,187);
		addGoodPoint(254,224,186);
		addGoodPoint(255,214,182);
		addGoodPoint(255,222,177);
		addGoodPoint(245,192,150);
		addGoodPoint(207,166,134);
		addGoodPoint(226,177,144);
		addGoodPoint(186,100,75);
		addGoodPoint(141,78,63);
		addGoodPoint(118,65,47);
		addGoodPoint(147,82,62);
		addGoodPoint(158,94,66);
		addGoodPoint(109,70,63);
		addGoodPoint(116,83,68);
		addGoodPoint(103,69,57);
		addGoodPoint(105,64,58);
		addGoodPoint(82,52,50);
		addGoodPoint(94,59,53);
		addGoodPoint(53,40,34);
		addGoodPoint(52,38,35);
		addGoodPoint(153,111,112);
		addGoodPoint(183,141,155);
		addGoodPoint(158,121,128);
		addGoodPoint(187,146,164);
		addGoodPoint(128,86,96);
		addGoodPoint(140,100,98);
		addGoodPoint(153,127,138);
		addGoodPoint(166,127,132);
		addGoodPoint(165,125,136);
		addGoodPoint(171,138,145);
		addGoodPoint(171,135,135);
		addGoodPoint(252,231,168);
		addGoodPoint(251,226,186);
		addGoodPoint(254,231,179);
		addGoodPoint(224,204,203);
		addGoodPoint(193,178,175);
		addGoodPoint(176,160,127);
		addGoodPoint(119,83,57);
		addGoodPoint(168,127,97);
		addGoodPoint(150,102,79);
		addGoodPoint(161,117,88);
		addGoodPoint(126,91,69);
		addGoodPoint(162,121,91);
		addGoodPoint(160,110,83);
		addGoodPoint(139,88,61);
		addGoodPoint(94,78,79);
		addGoodPoint(86,71,74);
		addGoodPoint(80,65,70);
		addGoodPoint(89,71,87);
		addGoodPoint(52,36,37);
		addGoodPoint(75,59,62);
		addGoodPoint(106,76,88);
		addGoodPoint(112,94,110);
		addGoodPoint(107,90,106);
		addGoodPoint(113,84,104);
		addGoodPoint(81,67,82);
		addGoodPoint(80,63,73);
		addGoodPoint(94,73,92);
		addGoodPoint(112,90,111);
		addGoodPoint(103,79,95);
		addGoodPoint(98,84,101);
		addGoodPoint(116,95,110);
		addGoodPoint(110,88,101);
		addGoodPoint(106,85,94);
		addGoodPoint(116,86,84);
		addGoodPoint(119,87,90);
		addGoodPoint(119,86,69);
		addGoodPoint(122,84,75);
		addGoodPoint(118,87,92);
		addGoodPoint(128,87,83);
		addGoodPoint(157,136,141);
		addGoodPoint(132,127,121);
		addGoodPoint(144,136,149);
		addGoodPoint(112,100,104);
		addGoodPoint(149,147,158);
		addGoodPoint(108,108,110);
		addGoodPoint(99,90,85);
		addGoodPoint(103,104,109);
		addGoodPoint(254,228,153);
		addGoodPoint(255,217,146);
		addGoodPoint(253,247,163);
		addGoodPoint(255,233,163);
		addGoodPoint(255,233,157);
		addGoodPoint(255,255,169);
		addGoodPoint(184,153,158);
		addGoodPoint(183,148,152);
		addGoodPoint(193,164,156);
		addGoodPoint(115,97,97);
		addGoodPoint(146,126,128);
		addGoodPoint(154,148,116);
		addGoodPoint(114,106,83);
		addGoodPoint(165,159,123);
		addGoodPoint(138,105,88);
		addGoodPoint(142,103,98);
		addGoodPoint(135,101,92);
		addGoodPoint(144,104,96);
		addGoodPoint(143,103,91);
		addGoodPoint(154,104,103);
		addGoodPoint(145,86,108);
		addGoodPoint(145,80,104);
		addGoodPoint(127,85,109);
		addGoodPoint(128,67,100);
		addGoodPoint(119,75,102);
		addGoodPoint(175,153,156);
		addGoodPoint(189,172,180);
		addGoodPoint(190,170,171);
		addGoodPoint(166,146,158);
		addGoodPoint(181,154,161);
		addGoodPoint(172,148,144);
		addGoodPoint(177,154,170);
		addGoodPoint(171,145,154);
		addGoodPoint(182,156,159);
		addGoodPoint(162,148,148);
		addGoodPoint(141,124,130);
		addGoodPoint(151,136,139);
		addGoodPoint(217,187,185);
		addGoodPoint(154,143,147);
		addGoodPoint(199,181,179);
		addGoodPoint(176,160,147);
		addGoodPoint(184,168,169);
		addGoodPoint(125,93,78);
		addGoodPoint(123,91,70);
		addGoodPoint(142,113,105);
		addGoodPoint(202,174,171);
		addGoodPoint(198,165,160);
		addGoodPoint(141,102,107);
		addGoodPoint(128,91,82);
		addGoodPoint(158,121,115);
		addGoodPoint(140,105,103);
		addGoodPoint(151,114,105);
		addGoodPoint(165,143,129);
		addGoodPoint(204,189,166);
		addGoodPoint(209,192,182);
		addGoodPoint(138,125,116);
		addGoodPoint(255,187,176);
		addGoodPoint(255,212,201);
		addGoodPoint(254,212,190);
		addGoodPoint(254,211,192);
		addGoodPoint(134,96,73);
		addGoodPoint(195,177,177);
		addGoodPoint(191,159,138);
		addGoodPoint(128,95,88);
		addGoodPoint(149,121,100);
		addGoodPoint(143,108,86);
		addGoodPoint(210,166,157);
		addGoodPoint(114,90,104);
		addGoodPoint(107,83,99);
		addGoodPoint(112,93,99);
		addGoodPoint(107,101,115);
		addGoodPoint(142,117,97);
		addGoodPoint(156,121,117);
		addGoodPoint(128,91,73);
		addGoodPoint(135,98,79);
		addGoodPoint(149,110,71);
		addGoodPoint(148,111,92);
		addGoodPoint(174,141,126);
		addGoodPoint(193,155,146);
		addGoodPoint(223,185,176);
		addGoodPoint(163,135,150);
		addGoodPoint(180,139,157);
		addGoodPoint(159,122,139);
		addGoodPoint(198,154,167);
		addGoodPoint(133,69,31);
		addGoodPoint(128,77,30);
		addGoodPoint(144,71,16);
		addGoodPoint(122,62,36);
		addGoodPoint(174,136,135);
		addGoodPoint(185,142,133);
		addGoodPoint(155,113,115);
		addGoodPoint(171,118,112);
		addGoodPoint(133,101,90);
		addGoodPoint(143,104,97);
		addGoodPoint(134,95,90);
		addGoodPoint(134,98,86);
		addGoodPoint(124,78,80);
		addGoodPoint(129,87,89);
		addGoodPoint(143,107,117);
		addGoodPoint(200,192,216);
		addGoodPoint(145,105,113);
		addGoodPoint(130,100,108);
		addGoodPoint(150,102,80);
		addGoodPoint(162,110,86);
		addGoodPoint(143,81,66);
		addGoodPoint(169,122,104);
		addGoodPoint(190,157,178);
		addGoodPoint(183,141,151);
		addGoodPoint(169,121,143);
		addGoodPoint(152,105,123);
		addGoodPoint(168,89,48);
		addGoodPoint(161,85,35);
		addGoodPoint(171,95,46);
		addGoodPoint(78,75,58);
		addGoodPoint(111,97,96);
		addGoodPoint(100,85,80);
		addGoodPoint(154,94,84);
		addGoodPoint(145,94,75);
		addGoodPoint(251,204,176);
		addGoodPoint(252,202,177);
		addGoodPoint(253,188,156);
		addGoodPoint(125,105,107);
		addGoodPoint(147,118,120);
		addGoodPoint(128,103,98);
		addGoodPoint(161,132,137);
		addGoodPoint(99,87,107);
		addGoodPoint(103,92,106);
		addGoodPoint(107,94,111);
		addGoodPoint(133,88,108);
		addGoodPoint(149,106,123);
		addGoodPoint(190,148,152);
		addGoodPoint(103,91,105);
		addGoodPoint(199,198,232);
		addGoodPoint(185,188,231);
		addGoodPoint(176,173,202);
		addGoodPoint(137,102,96);
		addGoodPoint(202,163,148);
		addGoodPoint(251,214,188);
		addGoodPoint(253,216,187);
		addGoodPoint(155,111,108);
		addGoodPoint(161,103,92);
		addGoodPoint(214,175,178);
		addGoodPoint(171,141,153);
		addGoodPoint(105,76,62);
		addGoodPoint(141,86,81);
		addGoodPoint(129,72,63);
		addGoodPoint(174,139,143);
		addGoodPoint(154,125,130);
		addGoodPoint(159,127,132);
		addGoodPoint(214,181,192);
		addGoodPoint(175,140,147);
		addGoodPoint(224,188,200);
		addGoodPoint(229,197,208);
		addGoodPoint(183,161,164);
		addGoodPoint(211,172,155);
		addGoodPoint(205,157,147);
		addGoodPoint(211,167,154);
		addGoodPoint(175,135,109);
	}

	private void addBadPoints(){

		addBadPoint(255,0,0);
		addBadPoint(0,255,0);
		addBadPoint(0,0,255);

		addBadPoint(243,247,224);
		addBadPoint(242,246,247);
		addBadPoint(0,1,22);
		addBadPoint(197,201,127);
		addBadPoint(12,23,9);
		addBadPoint(174,180,146);
		addBadPoint(106,112,110);
		addBadPoint(58,62,65);
		addBadPoint(231,241,206);
		addBadPoint(209,206,217);
		addBadPoint(117,125,136);
		addBadPoint(184,201,208);
		addBadPoint(99,94,88);
		addBadPoint(61,81,116);
		addBadPoint(24,32,45);
		addBadPoint(85,133,171);
		addBadPoint(101,135,183);
		addBadPoint(252,254,253);
		addBadPoint(69,77,88);
		addBadPoint(66,82,105);
		addBadPoint(19,24,46);
		addBadPoint(75,83,102);
		addBadPoint(72,114,138);
		addBadPoint(66,57,48);
		addBadPoint(239,197,149);
		addBadPoint(238,228,227);
		addBadPoint(70,41,27);
		addBadPoint(68,40,19);
		addBadPoint(5,4,0);
		addBadPoint(32,14,2);
		addBadPoint(118,103,82);
		addBadPoint(170,153,123);
		addBadPoint(196,173,139);
		addBadPoint(206,178,154);
		addBadPoint(177,126,81);
		addBadPoint(46,45,51);
		addBadPoint(52,47,51);
		addBadPoint(180,156,130);
		addBadPoint(186,159,112);
		addBadPoint(45,26,22);
		addBadPoint(250,255,233);
		addBadPoint(233,187,153);
		addBadPoint(249,254,231);
		addBadPoint(208,154,118);
		addBadPoint(249,155,155);
		addBadPoint(59,47,35);
		addBadPoint(119,146,141);
		addBadPoint(162,96,82);
		addBadPoint(120,119,115);
		addBadPoint(183,154,156);
		addBadPoint(181,164,170);
		addBadPoint(192,177,180);
		addBadPoint(155,127,149);
		addBadPoint(73,48,77);
		addBadPoint(176,170,172);
		addBadPoint(13,17,2);
		addBadPoint(131,44,17);
		addBadPoint(176,173,56);
		addBadPoint(93,129,119);
		addBadPoint(1,1,1);
		addBadPoint(40,12,1);
		addBadPoint(70,64,2);
		addBadPoint(51,30,1);
		addBadPoint(89,70,28);
		addBadPoint(86,102,55);
		addBadPoint(107,54,20);
		addBadPoint(17,18,10);
		addBadPoint(165,175,177);
		addBadPoint(2,2,0);
		addBadPoint(46,59,68);
		addBadPoint(7,16,25);
		addBadPoint(0,0,0);
		addBadPoint(64,50,67);
		addBadPoint(30,24,24);
		addBadPoint(221,223,220);
		addBadPoint(31,49,63);
		addBadPoint(47,37,46);
		addBadPoint(74,91,117);
		addBadPoint(7,7,5);
		addBadPoint(68,67,63);
		addBadPoint(14,11,6);
		addBadPoint(20,33,50);
		addBadPoint(3,1,2);
		addBadPoint(124,125,119);
		addBadPoint(184,169,102);
		addBadPoint(153,45,58);
		addBadPoint(179,196,204);
		addBadPoint(62,94,141);
		addBadPoint(38,54,51);
		addBadPoint(208,237,233);
		addBadPoint(30,35,28);
		addBadPoint(85,40,0);
		addBadPoint(109,66,13);
		addBadPoint(201,158,80);
		addBadPoint(9,24,21);
		addBadPoint(12,26,27);
		addBadPoint(78,100,114);
		addBadPoint(201,210,209);
		addBadPoint(119,237,203);
		addBadPoint(243,255,255);
		addBadPoint(204,213,228);
		addBadPoint(145,128,121);
		addBadPoint(165,176,170);
		addBadPoint(203,240,255);
		addBadPoint(20,14,16);
		addBadPoint(53,47,57);
		addBadPoint(247,152,184);
		addBadPoint(193,212,218);
		addBadPoint(110,126,151);
		addBadPoint(253,247,235);
		addBadPoint(162,129,112);
		addBadPoint(14,9,15);
		addBadPoint(138,100,99);
		addBadPoint(13,12,20);
		addBadPoint(37,47,72);
		addBadPoint(60,75,82);
		addBadPoint(181,113,162);
		addBadPoint(106,126,161);
		addBadPoint(200,213,206);
		addBadPoint(229,255,252);
		addBadPoint(101,98,151);
		addBadPoint(145,158,167);
		addBadPoint(45,38,10);
		addBadPoint(65,67,90);
		addBadPoint(131,144,161);
		addBadPoint(90,92,105);
		addBadPoint(30,36,48);
		addBadPoint(18,23,27);
		addBadPoint(188,201,181);
		addBadPoint(40,69,99);
		addBadPoint(127,85,60);
		addBadPoint(100,113,121);
		addBadPoint(164,200,216);
		addBadPoint(234,233,229);
		addBadPoint(64,60,61);
		addBadPoint(195,194,163);
		addBadPoint(73,82,81);
		addBadPoint(20,32,44);
		addBadPoint(43,68,64);
		addBadPoint(252,255,255);
		addBadPoint(211,236,230);
		addBadPoint(200,247,253);
		addBadPoint(116,113,140);
		addBadPoint(120,150,158);
		addBadPoint(69,106,114);
		addBadPoint(247,247,247);
		addBadPoint(47,62,69);
		addBadPoint(55,47,44);
		addBadPoint(210,218,221);
		addBadPoint(76,58,82);
		addBadPoint(6,28,69);
		addBadPoint(90,51,56);
		addBadPoint(5,1,24);
		addBadPoint(37,38,32);
		addBadPoint(197,177,106);
		addBadPoint(166,158,112);
		addBadPoint(138,142,171);
		addBadPoint(48,48,76);
		addBadPoint(157,164,172);
		addBadPoint(55,68,120);
		addBadPoint(42,48,100);
		addBadPoint(108,119,164);
		addBadPoint(54,64,117);
		addBadPoint(50,46,61);
		addBadPoint(75,46,0);
		addBadPoint(216,202,80);
		addBadPoint(182,181,91);
		addBadPoint(42,38,65);
		addBadPoint(36,29,62);
		addBadPoint(1,0,6);
		addBadPoint(104,53,0);
		addBadPoint(36,112,86);
		addBadPoint(33,59,169);
		addBadPoint(22,17,23);
		addBadPoint(3,3,3);
		addBadPoint(9,29,56);
		addBadPoint(36,37,65);
		addBadPoint(2,2,2);
		addBadPoint(45,37,34);
		addBadPoint(11,0,6);
		addBadPoint(82,71,77);
		addBadPoint(184,190,190);
		addBadPoint(144,193,223);
		addBadPoint(80,43,61);
		addBadPoint(186,135,52);
		addBadPoint(32,24,21);
		addBadPoint(186,50,70);
		addBadPoint(70,55,98);
		addBadPoint(45,47,46);
		addBadPoint(32,35,26);
		addBadPoint(7,18,48);
		addBadPoint(32,68,144);
		addBadPoint(64,48,74);
		addBadPoint(27,24,53);
		addBadPoint(63,69,81);
	}

	private void drawBadPoints(Graphic g){

		g.setColor(Color.RED);
		for(Layer ponto: bad){
			ponto.draw(g);
		}
	}

	int mx = 0;
	int my = 0;

	private void drawGrid(Graphic g){

		int spacing = 10;

		for(int i=0;i<w/spacing;i++){
			g.drawLine(i*spacing, 0, i*spacing, h);
		}

		for(int j=0;j<h/spacing;j++){
			g.drawLine(0, j*spacing, w, j*spacing);
		}

	}

	private void drawMouseCross(Graphic g){
		g.drawLine(mx, 0, mx, h);
		g.drawLine(0, my, w, my);
	}


	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_ESPACO)){
			validateGraph = !validateGraph;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		mx = event.getX();
		my = event.getY();

		for(ColorPoint ponto: good){

			if(ponto.colideCirclePoint(mx, my)){
				ponto.setOver(true);	
			}else{
				ponto.setOver(false);
			}

		}

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){


			System.out.println("MX = "+event.getX());
			System.out.println("MY = "+event.getY());

			for(ColorPoint ponto: good){

				if(ponto.colideCirclePoint(mx, my)){
					ponto.setOver(true);
					System.out.print("R = "+ponto.getR());
					System.out.print(" G = "+ponto.getG());
					System.out.print(" B = "+ponto.getB());
					System.out.println(" Y = "+(ponto.getB()+(ponto.getG()-ponto.getB())));					
				}

			}


		}

		return null;
	}

}
