package test.br.com.etyllica.motion.filter;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.filter.FilterImpl;

public class FilterImplTest {
	
	private FilterUnabstract filter;
	
	@Before
	public void setUp(){
		
		filter = new FilterUnabstract();
		
	}
	
	@Test
	public void testIsBlack() {
		
		assertTrue(filter.isBlack(Color.BLACK.getRGB()));
		
		assertTrue(filter.isBlack(new Color(0,0,0).getRGB()));
		
		assertTrue(filter.isBlack(new Color(0x40,0x40,0x40).getRGB()));
		
		assertTrue(filter.isBlack(new Color(0x48,0x48,0x47).getRGB()));
		
		assertTrue(filter.isBlack(new Color(0x46,0x46,0x48).getRGB()));
		
		assertFalse(filter.isBlack(new Color(0x0,0x0,0x49).getRGB()));
		
		assertFalse(filter.isBlack(new Color(0x49,0x49,0x49).getRGB()));
		
		assertFalse(filter.isBlack(Color.WHITE.getRGB()));
				
	}
	
	@Test
	public void testGetRed() {
		
		assertEquals(0x40, filter.getRed(new Color(0x40,0x40,0x40).getRGB()));
		
		assertEquals(0x60, filter.getRed(new Color(0x60,0x0,0x0).getRGB()));
		
	}
	
	@Test
	public void testGetGreen() {
		
		assertEquals(0x40, filter.getGreen(new Color(0x40,0x40,0x40).getRGB()));
		
		assertEquals(0x90, filter.getGreen(new Color(0x95,0x90,0x65).getRGB()));
		
	}
	
	@Test
	public void testGetBlue() {
		
		assertEquals(0x41, filter.getBlue(new Color(0x41,0x41,0x41).getRGB()));
		
		assertEquals(0x65, filter.getBlue(new Color(0x95,0x90,0x65).getRGB()));
		
	}
	
	@Test
	public void testIsSkin() {
				
		assertFalse(filter.isSkin(new Color(255,0,0).getRGB()));
		assertFalse(filter.isSkin(new Color(0,255,0).getRGB()));
		assertFalse(filter.isSkin(new Color(0,0,255).getRGB()));

		assertFalse(filter.isSkin(new Color(243,247,224).getRGB()));
		assertFalse(filter.isSkin(new Color(242,246,247).getRGB()));
		assertFalse(filter.isSkin(new Color(0,1,22).getRGB()));
		assertFalse(filter.isSkin(new Color(197,201,127).getRGB()));
		assertFalse(filter.isSkin(new Color(12,23,9).getRGB()));
		assertFalse(filter.isSkin(new Color(174,180,146).getRGB()));
		assertFalse(filter.isSkin(new Color(106,112,110).getRGB()));
		assertFalse(filter.isSkin(new Color(58,62,65).getRGB()));
		assertFalse(filter.isSkin(new Color(231,241,206).getRGB()));
		assertFalse(filter.isSkin(new Color(209,206,217).getRGB()));
		assertFalse(filter.isSkin(new Color(117,125,136).getRGB()));
		assertFalse(filter.isSkin(new Color(184,201,208).getRGB()));
		assertFalse(filter.isSkin(new Color(99,94,88).getRGB()));
		assertFalse(filter.isSkin(new Color(61,81,116).getRGB()));
		assertFalse(filter.isSkin(new Color(24,32,45).getRGB()));
		assertFalse(filter.isSkin(new Color(85,133,171).getRGB()));
		assertFalse(filter.isSkin(new Color(101,135,183).getRGB()));
		assertFalse(filter.isSkin(new Color(252,254,253).getRGB()));
		assertFalse(filter.isSkin(new Color(69,77,88).getRGB()));
		assertFalse(filter.isSkin(new Color(66,82,105).getRGB()));
		assertFalse(filter.isSkin(new Color(19,24,46).getRGB()));
		assertFalse(filter.isSkin(new Color(75,83,102).getRGB()));
		assertFalse(filter.isSkin(new Color(72,114,138).getRGB()));
		assertFalse(filter.isSkin(new Color(66,57,48).getRGB()));
		assertFalse(filter.isSkin(new Color(239,197,149).getRGB()));
		assertFalse(filter.isSkin(new Color(238,228,227).getRGB()));
		assertFalse(filter.isSkin(new Color(70,41,27).getRGB()));
		assertFalse(filter.isSkin(new Color(68,40,19).getRGB()));
		assertFalse(filter.isSkin(new Color(5,4,0).getRGB()));
		assertFalse(filter.isSkin(new Color(32,14,2).getRGB()));
		assertFalse(filter.isSkin(new Color(118,103,82).getRGB()));
		assertFalse(filter.isSkin(new Color(170,153,123).getRGB()));
		assertFalse(filter.isSkin(new Color(196,173,139).getRGB()));
		assertFalse(filter.isSkin(new Color(206,178,154).getRGB()));
		assertFalse(filter.isSkin(new Color(177,126,81).getRGB()));
		assertFalse(filter.isSkin(new Color(46,45,51).getRGB()));
		assertFalse(filter.isSkin(new Color(52,47,51).getRGB()));
		assertFalse(filter.isSkin(new Color(180,156,130).getRGB()));
		assertFalse(filter.isSkin(new Color(186,159,112).getRGB()));
		assertFalse(filter.isSkin(new Color(45,26,22).getRGB()));
		assertFalse(filter.isSkin(new Color(250,255,233).getRGB()));
		assertFalse(filter.isSkin(new Color(233,187,153).getRGB()));
		assertFalse(filter.isSkin(new Color(249,254,231).getRGB()));
		assertFalse(filter.isSkin(new Color(208,154,118).getRGB()));
		assertFalse(filter.isSkin(new Color(249,155,155).getRGB()));
		assertFalse(filter.isSkin(new Color(59,47,35).getRGB()));
		assertFalse(filter.isSkin(new Color(119,146,141).getRGB()));
		assertFalse(filter.isSkin(new Color(162,96,82).getRGB()));
		assertFalse(filter.isSkin(new Color(120,119,115).getRGB()));
		assertFalse(filter.isSkin(new Color(183,154,156).getRGB()));
		assertFalse(filter.isSkin(new Color(181,164,170).getRGB()));
		assertFalse(filter.isSkin(new Color(192,177,180).getRGB()));
		assertFalse(filter.isSkin(new Color(155,127,149).getRGB()));
		assertFalse(filter.isSkin(new Color(73,48,77).getRGB()));
		assertFalse(filter.isSkin(new Color(176,170,172).getRGB()));
		assertFalse(filter.isSkin(new Color(13,17,2).getRGB()));
		assertFalse(filter.isSkin(new Color(131,44,17).getRGB()));
		assertFalse(filter.isSkin(new Color(176,173,56).getRGB()));
		assertFalse(filter.isSkin(new Color(93,129,119).getRGB()));
		assertFalse(filter.isSkin(new Color(1,1,1).getRGB()));
		assertFalse(filter.isSkin(new Color(40,12,1).getRGB()));
		assertFalse(filter.isSkin(new Color(70,64,2).getRGB()));
		assertFalse(filter.isSkin(new Color(51,30,1).getRGB()));
		assertFalse(filter.isSkin(new Color(89,70,28).getRGB()));
		assertFalse(filter.isSkin(new Color(86,102,55).getRGB()));
		assertFalse(filter.isSkin(new Color(107,54,20).getRGB()));
		assertFalse(filter.isSkin(new Color(17,18,10).getRGB()));
		assertFalse(filter.isSkin(new Color(165,175,177).getRGB()));
		assertFalse(filter.isSkin(new Color(2,2,0).getRGB()));
		assertFalse(filter.isSkin(new Color(46,59,68).getRGB()));
		assertFalse(filter.isSkin(new Color(7,16,25).getRGB()));
		assertFalse(filter.isSkin(new Color(0,0,0).getRGB()));
		assertFalse(filter.isSkin(new Color(64,50,67).getRGB()));
		assertFalse(filter.isSkin(new Color(30,24,24).getRGB()));
		assertFalse(filter.isSkin(new Color(221,223,220).getRGB()));
		assertFalse(filter.isSkin(new Color(31,49,63).getRGB()));
		assertFalse(filter.isSkin(new Color(47,37,46).getRGB()));
		assertFalse(filter.isSkin(new Color(74,91,117).getRGB()));
		assertFalse(filter.isSkin(new Color(7,7,5).getRGB()));
		assertFalse(filter.isSkin(new Color(68,67,63).getRGB()));
		assertFalse(filter.isSkin(new Color(14,11,6).getRGB()));
		assertFalse(filter.isSkin(new Color(20,33,50).getRGB()));
		assertFalse(filter.isSkin(new Color(3,1,2).getRGB()));
		assertFalse(filter.isSkin(new Color(124,125,119).getRGB()));
		assertFalse(filter.isSkin(new Color(184,169,102).getRGB()));
		assertFalse(filter.isSkin(new Color(153,45,58).getRGB()));
		assertFalse(filter.isSkin(new Color(179,196,204).getRGB()));
		assertFalse(filter.isSkin(new Color(62,94,141).getRGB()));
		assertFalse(filter.isSkin(new Color(38,54,51).getRGB()));
		assertFalse(filter.isSkin(new Color(208,237,233).getRGB()));
		assertFalse(filter.isSkin(new Color(30,35,28).getRGB()));
		assertFalse(filter.isSkin(new Color(85,40,0).getRGB()));
		assertFalse(filter.isSkin(new Color(109,66,13).getRGB()));
		assertFalse(filter.isSkin(new Color(201,158,80).getRGB()));
		assertFalse(filter.isSkin(new Color(9,24,21).getRGB()));
		assertFalse(filter.isSkin(new Color(12,26,27).getRGB()));
		assertFalse(filter.isSkin(new Color(78,100,114).getRGB()));
		assertFalse(filter.isSkin(new Color(201,210,209).getRGB()));
		assertFalse(filter.isSkin(new Color(119,237,203).getRGB()));
		assertFalse(filter.isSkin(new Color(243,255,255).getRGB()));
		assertFalse(filter.isSkin(new Color(204,213,228).getRGB()));
		assertFalse(filter.isSkin(new Color(145,128,121).getRGB()));
		assertFalse(filter.isSkin(new Color(165,176,170).getRGB()));
		assertFalse(filter.isSkin(new Color(203,240,255).getRGB()));
		assertFalse(filter.isSkin(new Color(20,14,16).getRGB()));
		assertFalse(filter.isSkin(new Color(53,47,57).getRGB()));
		assertFalse(filter.isSkin(new Color(247,152,184).getRGB()));
		assertFalse(filter.isSkin(new Color(193,212,218).getRGB()));
		assertFalse(filter.isSkin(new Color(110,126,151).getRGB()));
		assertFalse(filter.isSkin(new Color(253,247,235).getRGB()));
		assertFalse(filter.isSkin(new Color(162,129,112).getRGB()));
		assertFalse(filter.isSkin(new Color(14,9,15).getRGB()));
		assertFalse(filter.isSkin(new Color(138,100,99).getRGB()));
		assertFalse(filter.isSkin(new Color(13,12,20).getRGB()));
		assertFalse(filter.isSkin(new Color(37,47,72).getRGB()));
		assertFalse(filter.isSkin(new Color(60,75,82).getRGB()));
		assertFalse(filter.isSkin(new Color(181,113,162).getRGB()));
		assertFalse(filter.isSkin(new Color(106,126,161).getRGB()));
		assertFalse(filter.isSkin(new Color(200,213,206).getRGB()));
		assertFalse(filter.isSkin(new Color(229,255,252).getRGB()));
		assertFalse(filter.isSkin(new Color(101,98,151).getRGB()));
		assertFalse(filter.isSkin(new Color(145,158,167).getRGB()));
		assertFalse(filter.isSkin(new Color(45,38,10).getRGB()));
		assertFalse(filter.isSkin(new Color(65,67,90).getRGB()));
		assertFalse(filter.isSkin(new Color(131,144,161).getRGB()));
		assertFalse(filter.isSkin(new Color(90,92,105).getRGB()));
		assertFalse(filter.isSkin(new Color(30,36,48).getRGB()));
		assertFalse(filter.isSkin(new Color(18,23,27).getRGB()));
		assertFalse(filter.isSkin(new Color(188,201,181).getRGB()));
		assertFalse(filter.isSkin(new Color(40,69,99).getRGB()));
		assertFalse(filter.isSkin(new Color(127,85,60).getRGB()));
		assertFalse(filter.isSkin(new Color(100,113,121).getRGB()));
		assertFalse(filter.isSkin(new Color(164,200,216).getRGB()));
		assertFalse(filter.isSkin(new Color(234,233,229).getRGB()));
		assertFalse(filter.isSkin(new Color(64,60,61).getRGB()));
		assertFalse(filter.isSkin(new Color(195,194,163).getRGB()));
		assertFalse(filter.isSkin(new Color(73,82,81).getRGB()));
		assertFalse(filter.isSkin(new Color(20,32,44).getRGB()));
		assertFalse(filter.isSkin(new Color(43,68,64).getRGB()));
		assertFalse(filter.isSkin(new Color(252,255,255).getRGB()));
		assertFalse(filter.isSkin(new Color(211,236,230).getRGB()));
		assertFalse(filter.isSkin(new Color(200,247,253).getRGB()));
		assertFalse(filter.isSkin(new Color(116,113,140).getRGB()));
		assertFalse(filter.isSkin(new Color(120,150,158).getRGB()));
		assertFalse(filter.isSkin(new Color(69,106,114).getRGB()));
		assertFalse(filter.isSkin(new Color(247,247,247).getRGB()));
		assertFalse(filter.isSkin(new Color(47,62,69).getRGB()));
		assertFalse(filter.isSkin(new Color(55,47,44).getRGB()));
		assertFalse(filter.isSkin(new Color(210,218,221).getRGB()));
		assertFalse(filter.isSkin(new Color(76,58,82).getRGB()));
		assertFalse(filter.isSkin(new Color(6,28,69).getRGB()));
		assertFalse(filter.isSkin(new Color(90,51,56).getRGB()));
		assertFalse(filter.isSkin(new Color(5,1,24).getRGB()));
		assertFalse(filter.isSkin(new Color(37,38,32).getRGB()));
		assertFalse(filter.isSkin(new Color(197,177,106).getRGB()));
		assertFalse(filter.isSkin(new Color(166,158,112).getRGB()));
		assertFalse(filter.isSkin(new Color(138,142,171).getRGB()));
		assertFalse(filter.isSkin(new Color(48,48,76).getRGB()));
		assertFalse(filter.isSkin(new Color(157,164,172).getRGB()));
		assertFalse(filter.isSkin(new Color(55,68,120).getRGB()));
		assertFalse(filter.isSkin(new Color(42,48,100).getRGB()));
		assertFalse(filter.isSkin(new Color(108,119,164).getRGB()));
		assertFalse(filter.isSkin(new Color(54,64,117).getRGB()));
		assertFalse(filter.isSkin(new Color(50,46,61).getRGB()));
		assertFalse(filter.isSkin(new Color(75,46,0).getRGB()));
		assertFalse(filter.isSkin(new Color(216,202,80).getRGB()));
		assertFalse(filter.isSkin(new Color(182,181,91).getRGB()));
		assertFalse(filter.isSkin(new Color(42,38,65).getRGB()));
		assertFalse(filter.isSkin(new Color(36,29,62).getRGB()));
		assertFalse(filter.isSkin(new Color(1,0,6).getRGB()));
		assertFalse(filter.isSkin(new Color(104,53,0).getRGB()));
		assertFalse(filter.isSkin(new Color(36,112,86).getRGB()));
		assertFalse(filter.isSkin(new Color(33,59,169).getRGB()));
		assertFalse(filter.isSkin(new Color(22,17,23).getRGB()));
		assertFalse(filter.isSkin(new Color(3,3,3).getRGB()));
		assertFalse(filter.isSkin(new Color(9,29,56).getRGB()));
		assertFalse(filter.isSkin(new Color(36,37,65).getRGB()));
		assertFalse(filter.isSkin(new Color(2,2,2).getRGB()));
		assertFalse(filter.isSkin(new Color(45,37,34).getRGB()));
		assertFalse(filter.isSkin(new Color(11,0,6).getRGB()));
		assertFalse(filter.isSkin(new Color(82,71,77).getRGB()));
		assertFalse(filter.isSkin(new Color(184,190,190).getRGB()));
		assertFalse(filter.isSkin(new Color(144,193,223).getRGB()));
		assertFalse(filter.isSkin(new Color(80,43,61).getRGB()));
		assertFalse(filter.isSkin(new Color(186,135,52).getRGB()));
		assertFalse(filter.isSkin(new Color(32,24,21).getRGB()));
		assertFalse(filter.isSkin(new Color(186,50,70).getRGB()));
		assertFalse(filter.isSkin(new Color(70,55,98).getRGB()));
		assertFalse(filter.isSkin(new Color(45,47,46).getRGB()));
		assertFalse(filter.isSkin(new Color(32,35,26).getRGB()));
		assertFalse(filter.isSkin(new Color(7,18,48).getRGB()));
		assertFalse(filter.isSkin(new Color(32,68,144).getRGB()));
		assertFalse(filter.isSkin(new Color(64,48,74).getRGB()));
		assertFalse(filter.isSkin(new Color(27,24,53).getRGB()));
		assertFalse(filter.isSkin(new Color(63,69,81).getRGB()));
		
assertTrue(filter.isSkin(new Color(0x40,0x40,0x40).getRGB()));
		
		assertTrue(filter.isSkin(new Color(85,78,62).getRGB()));
		assertTrue(filter.isSkin(new Color(115,103,81).getRGB()));
		assertTrue(filter.isSkin(new Color(97,92,73).getRGB()));
		assertTrue(filter.isSkin(new Color(93,94,76).getRGB()));
		assertTrue(filter.isSkin(new Color(97,90,74).getRGB()));
		assertTrue(filter.isSkin(new Color(102,95,76).getRGB()));
		assertTrue(filter.isSkin(new Color(106,99,83).getRGB()));
		assertTrue(filter.isSkin(new Color(108,101,82).getRGB()));
		assertTrue(filter.isSkin(new Color(108,100,81).getRGB()));
		assertTrue(filter.isSkin(new Color(61,60,42).getRGB()));
		assertTrue(filter.isSkin(new Color(71,63,52).getRGB()));
		assertTrue(filter.isSkin(new Color(254,230,184).getRGB()));
		assertTrue(filter.isSkin(new Color(255,225,187).getRGB()));
		assertTrue(filter.isSkin(new Color(254,224,186).getRGB()));
		assertTrue(filter.isSkin(new Color(255,214,182).getRGB()));
		assertTrue(filter.isSkin(new Color(255,222,177).getRGB()));
		assertTrue(filter.isSkin(new Color(245,192,150).getRGB()));
		assertTrue(filter.isSkin(new Color(207,166,134).getRGB()));
		assertTrue(filter.isSkin(new Color(226,177,144).getRGB()));
		assertTrue(filter.isSkin(new Color(186,100,75).getRGB()));
		assertTrue(filter.isSkin(new Color(141,78,63).getRGB()));
		assertTrue(filter.isSkin(new Color(118,65,47).getRGB()));
		assertTrue(filter.isSkin(new Color(147,82,62).getRGB()));
		assertTrue(filter.isSkin(new Color(158,94,66).getRGB()));
		assertTrue(filter.isSkin(new Color(109,70,63).getRGB()));
		assertTrue(filter.isSkin(new Color(116,83,68).getRGB()));
		assertTrue(filter.isSkin(new Color(103,69,57).getRGB()));
		assertTrue(filter.isSkin(new Color(105,64,58).getRGB()));
		assertTrue(filter.isSkin(new Color(82,52,50).getRGB()));
		assertTrue(filter.isSkin(new Color(94,59,53).getRGB()));
		assertTrue(filter.isSkin(new Color(53,40,34).getRGB()));
		assertTrue(filter.isSkin(new Color(52,38,35).getRGB()));
		assertTrue(filter.isSkin(new Color(153,111,112).getRGB()));
		assertTrue(filter.isSkin(new Color(183,141,155).getRGB()));
		assertTrue(filter.isSkin(new Color(158,121,128).getRGB()));
		assertTrue(filter.isSkin(new Color(187,146,164).getRGB()));
		assertTrue(filter.isSkin(new Color(128,86,96).getRGB()));
		assertTrue(filter.isSkin(new Color(140,100,98).getRGB()));
		assertTrue(filter.isSkin(new Color(153,127,138).getRGB()));
		assertTrue(filter.isSkin(new Color(166,127,132).getRGB()));
		assertTrue(filter.isSkin(new Color(165,125,136).getRGB()));
		assertTrue(filter.isSkin(new Color(171,138,145).getRGB()));
		assertTrue(filter.isSkin(new Color(171,135,135).getRGB()));
		assertTrue(filter.isSkin(new Color(252,231,168).getRGB()));
		assertTrue(filter.isSkin(new Color(251,226,186).getRGB()));
		assertTrue(filter.isSkin(new Color(254,231,179).getRGB()));
		assertTrue(filter.isSkin(new Color(224,204,203).getRGB()));
		assertTrue(filter.isSkin(new Color(193,178,175).getRGB()));
		assertTrue(filter.isSkin(new Color(176,160,127).getRGB()));
		assertTrue(filter.isSkin(new Color(119,83,57).getRGB()));
		assertTrue(filter.isSkin(new Color(168,127,97).getRGB()));
		assertTrue(filter.isSkin(new Color(150,102,79).getRGB()));
		assertTrue(filter.isSkin(new Color(161,117,88).getRGB()));
		assertTrue(filter.isSkin(new Color(126,91,69).getRGB()));
		assertTrue(filter.isSkin(new Color(162,121,91).getRGB()));
		assertTrue(filter.isSkin(new Color(160,110,83).getRGB()));
		assertTrue(filter.isSkin(new Color(139,88,61).getRGB()));
		assertTrue(filter.isSkin(new Color(94,78,79).getRGB()));
		assertTrue(filter.isSkin(new Color(86,71,74).getRGB()));
		assertTrue(filter.isSkin(new Color(80,65,70).getRGB()));
		assertTrue(filter.isSkin(new Color(89,71,87).getRGB()));
		assertTrue(filter.isSkin(new Color(52,36,37).getRGB()));
		assertTrue(filter.isSkin(new Color(75,59,62).getRGB()));
		assertTrue(filter.isSkin(new Color(106,76,88).getRGB()));
		assertTrue(filter.isSkin(new Color(112,94,110).getRGB()));
		assertTrue(filter.isSkin(new Color(107,90,106).getRGB()));
		assertTrue(filter.isSkin(new Color(113,84,104).getRGB()));
		assertTrue(filter.isSkin(new Color(81,67,82).getRGB()));
		assertTrue(filter.isSkin(new Color(80,63,73).getRGB()));
		assertTrue(filter.isSkin(new Color(94,73,92).getRGB()));
		assertTrue(filter.isSkin(new Color(112,90,111).getRGB()));
		assertTrue(filter.isSkin(new Color(103,79,95).getRGB()));
		assertTrue(filter.isSkin(new Color(98,84,101).getRGB()));
		assertTrue(filter.isSkin(new Color(116,95,110).getRGB()));
		assertTrue(filter.isSkin(new Color(110,88,101).getRGB()));
		assertTrue(filter.isSkin(new Color(106,85,94).getRGB()));
		assertTrue(filter.isSkin(new Color(116,86,84).getRGB()));
		assertTrue(filter.isSkin(new Color(119,87,90).getRGB()));
		assertTrue(filter.isSkin(new Color(119,86,69).getRGB()));
		assertTrue(filter.isSkin(new Color(122,84,75).getRGB()));
		assertTrue(filter.isSkin(new Color(118,87,92).getRGB()));
		assertTrue(filter.isSkin(new Color(128,87,83).getRGB()));
		assertTrue(filter.isSkin(new Color(157,136,141).getRGB()));
		assertTrue(filter.isSkin(new Color(132,127,121).getRGB()));
		assertTrue(filter.isSkin(new Color(144,136,149).getRGB()));
		assertTrue(filter.isSkin(new Color(112,100,104).getRGB()));
		assertTrue(filter.isSkin(new Color(149,147,158).getRGB()));
		assertTrue(filter.isSkin(new Color(108,108,110).getRGB()));
		assertTrue(filter.isSkin(new Color(99,90,85).getRGB()));
		assertTrue(filter.isSkin(new Color(103,104,109).getRGB()));
		assertTrue(filter.isSkin(new Color(254,228,153).getRGB()));
		assertTrue(filter.isSkin(new Color(255,217,146).getRGB()));
		assertTrue(filter.isSkin(new Color(253,247,163).getRGB()));
		assertTrue(filter.isSkin(new Color(255,233,163).getRGB()));
		assertTrue(filter.isSkin(new Color(255,233,157).getRGB()));
		assertTrue(filter.isSkin(new Color(255,255,169).getRGB()));
		assertTrue(filter.isSkin(new Color(184,153,158).getRGB()));
		assertTrue(filter.isSkin(new Color(183,148,152).getRGB()));
		assertTrue(filter.isSkin(new Color(193,164,156).getRGB()));
		assertTrue(filter.isSkin(new Color(115,97,97).getRGB()));
		assertTrue(filter.isSkin(new Color(146,126,128).getRGB()));
		assertTrue(filter.isSkin(new Color(154,148,116).getRGB()));
		assertTrue(filter.isSkin(new Color(114,106,83).getRGB()));
		assertTrue(filter.isSkin(new Color(165,159,123).getRGB()));
		assertTrue(filter.isSkin(new Color(138,105,88).getRGB()));
		assertTrue(filter.isSkin(new Color(142,103,98).getRGB()));
		assertTrue(filter.isSkin(new Color(135,101,92).getRGB()));
		assertTrue(filter.isSkin(new Color(144,104,96).getRGB()));
		assertTrue(filter.isSkin(new Color(143,103,91).getRGB()));
		assertTrue(filter.isSkin(new Color(154,104,103).getRGB()));
		assertTrue(filter.isSkin(new Color(145,86,108).getRGB()));
		assertTrue(filter.isSkin(new Color(145,80,104).getRGB()));
		assertTrue(filter.isSkin(new Color(127,85,109).getRGB()));
		assertTrue(filter.isSkin(new Color(128,67,100).getRGB()));
		assertTrue(filter.isSkin(new Color(119,75,102).getRGB()));
		assertTrue(filter.isSkin(new Color(175,153,156).getRGB()));
		assertTrue(filter.isSkin(new Color(189,172,180).getRGB()));
		assertTrue(filter.isSkin(new Color(190,170,171).getRGB()));
		assertTrue(filter.isSkin(new Color(166,146,158).getRGB()));
		assertTrue(filter.isSkin(new Color(181,154,161).getRGB()));
		assertTrue(filter.isSkin(new Color(172,148,144).getRGB()));
		assertTrue(filter.isSkin(new Color(177,154,170).getRGB()));
		assertTrue(filter.isSkin(new Color(171,145,154).getRGB()));
		assertTrue(filter.isSkin(new Color(182,156,159).getRGB()));
		assertTrue(filter.isSkin(new Color(162,148,148).getRGB()));
		assertTrue(filter.isSkin(new Color(141,124,130).getRGB()));
		assertTrue(filter.isSkin(new Color(151,136,139).getRGB()));
		assertTrue(filter.isSkin(new Color(217,187,185).getRGB()));
		assertTrue(filter.isSkin(new Color(154,143,147).getRGB()));
		assertTrue(filter.isSkin(new Color(199,181,179).getRGB()));
		assertTrue(filter.isSkin(new Color(176,160,147).getRGB()));
		assertTrue(filter.isSkin(new Color(184,168,169).getRGB()));
		assertTrue(filter.isSkin(new Color(125,93,78).getRGB()));
		assertTrue(filter.isSkin(new Color(123,91,70).getRGB()));
		assertTrue(filter.isSkin(new Color(142,113,105).getRGB()));
		assertTrue(filter.isSkin(new Color(202,174,171).getRGB()));
		assertTrue(filter.isSkin(new Color(198,165,160).getRGB()));
		assertTrue(filter.isSkin(new Color(141,102,107).getRGB()));
		assertTrue(filter.isSkin(new Color(128,91,82).getRGB()));
		assertTrue(filter.isSkin(new Color(158,121,115).getRGB()));
		assertTrue(filter.isSkin(new Color(140,105,103).getRGB()));
		assertTrue(filter.isSkin(new Color(151,114,105).getRGB()));
		assertTrue(filter.isSkin(new Color(165,143,129).getRGB()));
		assertTrue(filter.isSkin(new Color(204,189,166).getRGB()));
		assertTrue(filter.isSkin(new Color(209,192,182).getRGB()));
		assertTrue(filter.isSkin(new Color(138,125,116).getRGB()));
		assertTrue(filter.isSkin(new Color(255,187,176).getRGB()));
		assertTrue(filter.isSkin(new Color(255,212,201).getRGB()));
		assertTrue(filter.isSkin(new Color(254,212,190).getRGB()));
		assertTrue(filter.isSkin(new Color(254,211,192).getRGB()));
		assertTrue(filter.isSkin(new Color(134,96,73).getRGB()));
		assertTrue(filter.isSkin(new Color(195,177,177).getRGB()));
		assertTrue(filter.isSkin(new Color(191,159,138).getRGB()));
		assertTrue(filter.isSkin(new Color(128,95,88).getRGB()));
		assertTrue(filter.isSkin(new Color(149,121,100).getRGB()));
		assertTrue(filter.isSkin(new Color(143,108,86).getRGB()));
		assertTrue(filter.isSkin(new Color(210,166,157).getRGB()));
		assertTrue(filter.isSkin(new Color(114,90,104).getRGB()));
		assertTrue(filter.isSkin(new Color(107,83,99).getRGB()));
		assertTrue(filter.isSkin(new Color(112,93,99).getRGB()));
		assertTrue(filter.isSkin(new Color(107,101,115).getRGB()));
		assertTrue(filter.isSkin(new Color(142,117,97).getRGB()));
		assertTrue(filter.isSkin(new Color(156,121,117).getRGB()));
		assertTrue(filter.isSkin(new Color(128,91,73).getRGB()));
		assertTrue(filter.isSkin(new Color(135,98,79).getRGB()));
		assertTrue(filter.isSkin(new Color(149,110,71).getRGB()));
		assertTrue(filter.isSkin(new Color(148,111,92).getRGB()));
		assertTrue(filter.isSkin(new Color(174,141,126).getRGB()));
		assertTrue(filter.isSkin(new Color(193,155,146).getRGB()));
		assertTrue(filter.isSkin(new Color(223,185,176).getRGB()));
		assertTrue(filter.isSkin(new Color(163,135,150).getRGB()));
		assertTrue(filter.isSkin(new Color(180,139,157).getRGB()));
		assertTrue(filter.isSkin(new Color(159,122,139).getRGB()));
		assertTrue(filter.isSkin(new Color(198,154,167).getRGB()));
		assertTrue(filter.isSkin(new Color(133,69,31).getRGB()));
		assertTrue(filter.isSkin(new Color(128,77,30).getRGB()));
		assertTrue(filter.isSkin(new Color(144,71,16).getRGB()));
		assertTrue(filter.isSkin(new Color(122,62,36).getRGB()));
		assertTrue(filter.isSkin(new Color(174,136,135).getRGB()));
		assertTrue(filter.isSkin(new Color(185,142,133).getRGB()));
		assertTrue(filter.isSkin(new Color(155,113,115).getRGB()));
		assertTrue(filter.isSkin(new Color(171,118,112).getRGB()));
		assertTrue(filter.isSkin(new Color(133,101,90).getRGB()));
		assertTrue(filter.isSkin(new Color(143,104,97).getRGB()));
		assertTrue(filter.isSkin(new Color(134,95,90).getRGB()));
		assertTrue(filter.isSkin(new Color(134,98,86).getRGB()));
		assertTrue(filter.isSkin(new Color(124,78,80).getRGB()));
		assertTrue(filter.isSkin(new Color(129,87,89).getRGB()));
		assertTrue(filter.isSkin(new Color(143,107,117).getRGB()));
		assertTrue(filter.isSkin(new Color(200,192,216).getRGB()));
		assertTrue(filter.isSkin(new Color(145,105,113).getRGB()));
		assertTrue(filter.isSkin(new Color(130,100,108).getRGB()));
		assertTrue(filter.isSkin(new Color(150,102,80).getRGB()));
		assertTrue(filter.isSkin(new Color(162,110,86).getRGB()));
		assertTrue(filter.isSkin(new Color(143,81,66).getRGB()));
		assertTrue(filter.isSkin(new Color(169,122,104).getRGB()));
		assertTrue(filter.isSkin(new Color(190,157,178).getRGB()));
		assertTrue(filter.isSkin(new Color(183,141,151).getRGB()));
		assertTrue(filter.isSkin(new Color(169,121,143).getRGB()));
		assertTrue(filter.isSkin(new Color(152,105,123).getRGB()));
		assertTrue(filter.isSkin(new Color(168,89,48).getRGB()));
		assertTrue(filter.isSkin(new Color(161,85,35).getRGB()));
		assertTrue(filter.isSkin(new Color(171,95,46).getRGB()));
		assertTrue(filter.isSkin(new Color(78,75,58).getRGB()));
		assertTrue(filter.isSkin(new Color(111,97,96).getRGB()));
		assertTrue(filter.isSkin(new Color(100,85,80).getRGB()));
		assertTrue(filter.isSkin(new Color(154,94,84).getRGB()));
		assertTrue(filter.isSkin(new Color(145,94,75).getRGB()));
		assertTrue(filter.isSkin(new Color(251,204,176).getRGB()));
		assertTrue(filter.isSkin(new Color(252,202,177).getRGB()));
		assertTrue(filter.isSkin(new Color(253,188,156).getRGB()));
		assertTrue(filter.isSkin(new Color(125,105,107).getRGB()));
		assertTrue(filter.isSkin(new Color(147,118,120).getRGB()));
		assertTrue(filter.isSkin(new Color(128,103,98).getRGB()));
		assertTrue(filter.isSkin(new Color(161,132,137).getRGB()));
		assertTrue(filter.isSkin(new Color(99,87,107).getRGB()));
		assertTrue(filter.isSkin(new Color(103,92,106).getRGB()));
		assertTrue(filter.isSkin(new Color(107,94,111).getRGB()));
		assertTrue(filter.isSkin(new Color(133,88,108).getRGB()));
		assertTrue(filter.isSkin(new Color(149,106,123).getRGB()));
		assertTrue(filter.isSkin(new Color(190,148,152).getRGB()));
		assertTrue(filter.isSkin(new Color(103,91,105).getRGB()));
		assertTrue(filter.isSkin(new Color(199,198,232).getRGB()));
		assertTrue(filter.isSkin(new Color(185,188,231).getRGB()));
		assertTrue(filter.isSkin(new Color(176,173,202).getRGB()));
		assertTrue(filter.isSkin(new Color(137,102,96).getRGB()));
		assertTrue(filter.isSkin(new Color(202,163,148).getRGB()));
		assertTrue(filter.isSkin(new Color(251,214,188).getRGB()));
		assertTrue(filter.isSkin(new Color(253,216,187).getRGB()));
		assertTrue(filter.isSkin(new Color(155,111,108).getRGB()));
		assertTrue(filter.isSkin(new Color(161,103,92).getRGB()));
		assertTrue(filter.isSkin(new Color(214,175,178).getRGB()));
		assertTrue(filter.isSkin(new Color(171,141,153).getRGB()));
		assertTrue(filter.isSkin(new Color(105,76,62).getRGB()));
		assertTrue(filter.isSkin(new Color(141,86,81).getRGB()));
		assertTrue(filter.isSkin(new Color(129,72,63).getRGB()));
		assertTrue(filter.isSkin(new Color(174,139,143).getRGB()));
		assertTrue(filter.isSkin(new Color(154,125,130).getRGB()));
		assertTrue(filter.isSkin(new Color(159,127,132).getRGB()));
		assertTrue(filter.isSkin(new Color(214,181,192).getRGB()));
		assertTrue(filter.isSkin(new Color(175,140,147).getRGB()));
		assertTrue(filter.isSkin(new Color(224,188,200).getRGB()));
		assertTrue(filter.isSkin(new Color(229,197,208).getRGB()));
		assertTrue(filter.isSkin(new Color(183,161,164).getRGB()));
		assertTrue(filter.isSkin(new Color(211,172,155).getRGB()));
		assertTrue(filter.isSkin(new Color(205,157,147).getRGB()));
		assertTrue(filter.isSkin(new Color(211,167,154).getRGB()));
		assertTrue(filter.isSkin(new Color(175,135,109).getRGB()));
				
	}
	
	private class FilterUnabstract extends FilterImpl{

		@Override
		public List<Componente> filter(BufferedImage bimg,
				List<Componente> components) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean validateColor(int rgb) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean validateComponent(Componente component) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
