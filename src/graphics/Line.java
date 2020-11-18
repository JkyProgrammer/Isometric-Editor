package graphics;

import java.awt.Color;
import java.awt.Rectangle;

import main.Pair;
import main.Pos;

public class Line {
	public Pos position1;
	public Pos position2;
	public Color c;
	
	public Line () {}
	
	public Rectangle getBoundingBox () {
		Rectangle r = new Rectangle ();
		Pair<Integer, Integer> p1 = position1.getSquareCoord();
		Pair<Integer, Integer> p2 = position2.getSquareCoord();
		r.x = Math.min(p1.a, p2.a);
		r.y = Math.min(p1.b, p2.b);
		
		r.width = Math.abs(p1.a - p2.a);
		r.height = Math.abs(p1.b - p2.b);
		
		return r;
	}
}
