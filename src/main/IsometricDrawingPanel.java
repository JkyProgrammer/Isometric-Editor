package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.JPanel;

import graphics.Dot;

public class IsometricDrawingPanel extends JPanel {	
	private boolean isDrawingGrid = true;
	
	public void setDrawingGrid (boolean t) {
		isDrawingGrid = t;
		repaint ();
	}
	
	public boolean getDrawingGrid () {
		return isDrawingGrid;
	}
	
	/**
	 * Returns true if a point is outside the bounds of the view
	 * @param p
	 * @return
	 */
	private boolean boundsCheck (Pos p) {
		Pair<Integer, Integer> c = p.getSquareCoord();
		if (c.a < 0) return true;
		if (c.b < 0) return true;
		if (c.a > getWidth()) return true;
		if (c.b > getHeight()) return true;
		return false;
	}
	
	private boolean drawDot (Pos _p, Graphics g) {
		Pos p = posConvert (_p);
		if (boundsCheck (p)) return true;
		Pair<Integer, Integer> loc = p.getSquareCoord();
		g.fillOval(loc.a-1, loc.b-1, 2, 2);
		return false;
	}
	
	private Pos posConvert (Pos p) {
		Pos out = new Pos (0, 0, false);
		out.setSquareCoord (
				p.getSquareCoord().a + (getWidth()/2),
				(getHeight()/2) - p.getSquareCoord().b);
		return out;
	}
	
	@Override
	public void paint (Graphics g) {
		if (isDrawingGrid) {
			g.setColor(Color.black);
			Pos bottomLeft = new Pos (-(getWidth()/2), -(getHeight()/2), false);
			bottomLeft.roundIsometric(20);
			// Top Left Side
			Pos rowStartCoord = bottomLeft.copy();
			boolean isOutOfBounds = false;
			while (!isOutOfBounds) {
				boolean innerIsOutOfBounds = false;
				Pos p = rowStartCoord.copy();
				while (!innerIsOutOfBounds) {
					innerIsOutOfBounds = drawDot (p, g);
					p.translateIsometrically (20, 0);
				}
				rowStartCoord.translateIsometrically(20, 20);
				isOutOfBounds = boundsCheck(posConvert(rowStartCoord));
			}
			
			// Bottom Right Side
			rowStartCoord = bottomLeft.copy();
			isOutOfBounds = false;
			while (!isOutOfBounds) {
				rowStartCoord.translateIsometrically(20, -20);
				boolean innerIsOutOfBounds = false;
				Pos p = rowStartCoord.copy();
				while (!innerIsOutOfBounds) {
					innerIsOutOfBounds = drawDot (p, g);
					p.translateIsometrically (20, 0);
				}
				isOutOfBounds = boundsCheck(posConvert(rowStartCoord));
			}
		}
		
		for (Entry<Pos, Object> o : objects.entrySet()) {
			if (o.getValue().getClass() == Dot.class) {
				g.setColor(((Dot)o.getValue()).c);
				drawDot (o.getKey(), g);
			}
		}
		
	}
	
	private HashMap<Pos, Object> objects = new HashMap<Pos, Object> ();
	
	public void addDot (Pos p, Color c) {
		Dot d = new Dot ();
		d.c = c;
		d.position = p;
		objects.put(p, d);
		repaint ();
	}
}
