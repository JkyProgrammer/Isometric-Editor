package main;

public class Pos {
	/*
	 * Square coordinates are provided in the form
	 * 
	 * y
	 * ^
	 * |
	 * |----> x
	 */
	private int sqX;
	private int sqY;
	
	
	/*
	 * Isometric coordinates are provided in the form
	 * 
	 *       y       x
	 *       ^   |   ^
	 *        \  |  /
	 *         \ | /
	 * 	        \./
	 *          /|\
	 *         / | \
	 *        /  |  \
	 * 
	 * 
	 * 
	 */
	private int imX;
	private int imY;
	
	public static float halfratio = (float)Math.pow(3f, (1f/2f))/2f;
	private static float ratio = (float)Math.pow(3f, (1f/2f));
	
	
	private void calculateIsometricCoord () {
		imX = (int)(sqY + (sqX/ratio));
		imY = (int)(sqY - (sqX/ratio));
	}
	
	private void calculateSquareCoord () {
		sqX = (int) ((imX-imY)*halfratio);
		sqY = (imX+imY)/2;
	}
	
	public Pair<Integer, Integer> getSquareCoord () { return new Pair<Integer, Integer> (sqX, sqY); }
	public Pair<Integer, Integer> getIsometricCoord () { return new Pair<Integer, Integer> (imX, imY); }
	
	public void setSquareCoord (int x, int y) {
		sqX = x;
		sqY = y;
		calculateIsometricCoord ();
	}
	
	public void setIsometricCoord (int x, int y) {
		imX = x;
		imY = y;
		calculateSquareCoord ();
	}
	
	public void roundIsometric (int nearest) {
		imX = (int)Math.round((double)imX/nearest)*nearest;
		imY = (int)Math.round((double)imY/nearest)*nearest;
		calculateSquareCoord ();
	}
	
	public void translateIsometrically (int i, int j) {
		imX += i;
		imY += j;
		calculateSquareCoord();
	}
	
	public Pos (int x, int y, boolean isIsometricCoord) {
		if (!isIsometricCoord) {
			sqX = x;
			sqY = y;
			calculateIsometricCoord ();
		} else {
			imX = x;
			imY = y;
			calculateSquareCoord();
		}
	}
	
	public Pos copy () {
		return new Pos (imX, imY, true);
	}
}
