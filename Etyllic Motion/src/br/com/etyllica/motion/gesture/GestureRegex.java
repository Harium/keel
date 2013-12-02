package br.com.etyllica.motion.gesture;

public class GestureRegex {

	//Line Sizes
	private static final String LONG_LINE = "5,";
	
	private static final String MEDIUM_LINE = "3,";
	
	private static final String SMALL_LINE = "2,";
		
	//Arrows
	public static final String RIGHT_ARROW = ".*(B|D){"+LONG_LINE+"}.*A{"+SMALL_LINE+"}.*D{"+SMALL_LINE+"}.*C{"+SMALL_LINE+"}.*";
		
	public static final String LEFT_ARROW = ".*(A|C){"+LONG_LINE+"}.*B{"+SMALL_LINE+"}.*C{"+SMALL_LINE+"}.*D{"+SMALL_LINE+"}.*";
	
	public static final String UP_ARROW = ".*(A|B){"+LONG_LINE+"}.*C{"+SMALL_LINE+"}.*B{"+SMALL_LINE+"}.*D{"+SMALL_LINE+"}.*";
	
	public static final String DOWN_ARROW = ".*(C|D){"+LONG_LINE+"}.*A{"+SMALL_LINE+"}.*D{"+SMALL_LINE+"}.*B{"+SMALL_LINE+"}.*";
	
	public static final String DOWN_ARROW_LEFT_HANDED = ".*(C|D){"+LONG_LINE+"}.*B{"+SMALL_LINE+"}.*C{"+SMALL_LINE+"}.*A{"+SMALL_LINE+"}.*";

	//Numbers
	public static final String ONE = ".*B{"+SMALL_LINE+"}(C|D){"+MEDIUM_LINE+"}(C|A)+(B|D){"+SMALL_LINE+"}";

	public static final  String TWO = ".*B*D{"+SMALL_LINE+"}C{"+SMALL_LINE+"}(A|B){"+SMALL_LINE+"}D{"+SMALL_LINE+"}.*";
			
	public static final String THREE = ".*B*D{"+SMALL_LINE+"}C{"+SMALL_LINE+"}A*B*D{"+SMALL_LINE+"}C+A+.*";
	
	public static final String FOUR = ".*(C|D){"+SMALL_LINE+"}(B|D){"+SMALL_LINE+"}(A|B){"+SMALL_LINE+"}(C|D){"+LONG_LINE+"}.*";
    
}
