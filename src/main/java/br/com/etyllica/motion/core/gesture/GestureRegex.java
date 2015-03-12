package br.com.etyllica.motion.core.gesture;

public class GestureRegex {

	//Situations with or has to be MEDIUM_LINE or LONG_LINE
	
	//Line Sizes
	private static final String LONG_LINE = "6,";
	
	private static final String MEDIUM_LINE = "4,";
	
	private static final String SMALL_LINE = "2,";
		
	//Arrows
	public static final String RIGHT_ARROW = ".*(B|D){"+LONG_LINE+"}.*A{"+MEDIUM_LINE+"}.*D{"+MEDIUM_LINE+"}.*C{"+MEDIUM_LINE+"}.*";
		
	public static final String LEFT_ARROW = ".*(A|C){"+LONG_LINE+"}.*B{"+MEDIUM_LINE+"}.*C{"+MEDIUM_LINE+"}.*D{"+MEDIUM_LINE+"}.*";
	
	public static final String UP_ARROW = ".*(A|B){"+LONG_LINE+"}.*C{"+MEDIUM_LINE+"}.*B{"+MEDIUM_LINE+"}.*D{"+MEDIUM_LINE+"}.*";
	
	public static final String DOWN_ARROW = ".*(C|D){"+LONG_LINE+"}.*A{"+MEDIUM_LINE+"}.*D{"+MEDIUM_LINE+"}.*B{"+MEDIUM_LINE+"}.*";
	
	public static final String DOWN_ARROW_LEFT_HANDED = ".*(C|D){"+LONG_LINE+"}.*B{"+MEDIUM_LINE+"}.*C{"+MEDIUM_LINE+"}.*A{"+MEDIUM_LINE+"}.*";

	//Numbers
	public static final String ONE = ".*B{"+MEDIUM_LINE+"}(C|D){"+LONG_LINE+"}(A|C){"+MEDIUM_LINE+"}(D|B){"+MEDIUM_LINE+"}";

	public static final  String TWO = ".*B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}C{"+MEDIUM_LINE+"}A+B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}B+.*";
			
	public static final String THREE = ".*B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}C{"+SMALL_LINE+"}A*B+D{"+SMALL_LINE+"}C{"+SMALL_LINE+"}A{"+SMALL_LINE+"}.*";
	
	public static final String FOUR = ".*C{"+MEDIUM_LINE+"}(B|D){"+MEDIUM_LINE+"}(A|B){"+MEDIUM_LINE+"}(C|D){"+LONG_LINE+"}.*";
    
	public static final String FIVE = ".*(A|C){"+MEDIUM_LINE+"}(C|D){"+MEDIUM_LINE+"}B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}C{"+SMALL_LINE+"}A{"+SMALL_LINE+"}";
	
	public static final String SIX = ".*A+C+(C|D){"+MEDIUM_LINE+"}D+B+A+C{"+SMALL_LINE+"}";
	
	public static final String SEVEN = ".*(B|D){"+MEDIUM_LINE+"}C{"+LONG_LINE+"}B{"+MEDIUM_LINE+"}(A|C){"+MEDIUM_LINE+"}(B|D){"+MEDIUM_LINE+"}";
	
	public static final String EIGHT = ".*B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}C{"+MEDIUM_LINE+"}D{"+SMALL_LINE+"}B{"+SMALL_LINE+"}A{"+MEDIUM_LINE+"}";
	
	public static final String NINE = ".*C{"+SMALL_LINE+"}A{"+SMALL_LINE+"}B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}(C|D){"+MEDIUM_LINE+"}C{"+SMALL_LINE+"}A{"+SMALL_LINE+"}";
	
	public static final String NINE_CCW = ".*A{"+SMALL_LINE+"}C{"+SMALL_LINE+"}D{"+SMALL_LINE+"}B{"+SMALL_LINE+"}(C|D){"+LONG_LINE+"}";
	
	public static final String ZERO = ".*B{"+SMALL_LINE+"}D{"+SMALL_LINE+"}(C|D){"+MEDIUM_LINE+"}C{"+SMALL_LINE+"}(A|B){"+MEDIUM_LINE+"}";
	
	//Math Operations
	public static final String PLUS = ".*(C|D){"+LONG_LINE+"}(B|A){"+MEDIUM_LINE+"}(B|D){"+LONG_LINE+"}";
	
	public static final String PLUS_LEFT_HANDED = ".*(C|D){"+LONG_LINE+"}B{"+MEDIUM_LINE+"}(A|C){"+LONG_LINE+"}";
}
