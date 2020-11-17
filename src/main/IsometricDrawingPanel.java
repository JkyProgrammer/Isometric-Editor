package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.JPanel;

import graphics.Dot;
import graphics.Line;

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
	private boolean boundsCheck (Pos p, int flex) {
		Pair<Integer, Integer> c = p.getSquareCoord();
		if (c.a < -flex) return true;
		if (c.b < -flex) return true;
		if (c.a > getWidth() + flex) return true;
		if (c.b > getHeight() + flex) return true;
		return false;
	}
	
	private boolean drawDot (Pos _p, Graphics g) {
		Pos p = posConvert (_p);
		if (boundsCheck (p, 20)) return true;
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
				isOutOfBounds = boundsCheck(posConvert(rowStartCoord), 20);
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
				isOutOfBounds = boundsCheck(posConvert(rowStartCoord), 20);
			}
		}
		
		for (Entry<Pos, Object> o : objects.entrySet()) {
			if (o.getValue().getClass() == Dot.class) {
				g.setColor(((Dot)o.getValue()).c);
				drawDot (o.getKey(), g);
			} else if (o.getValue().getClass() == Line.class) {
				g.setColor(((Line)o.getValue()).c);
				Pair<Integer, Integer> p1 = posConvert (((Line)o.getValue()).position1).getSquareCoord();
				Pair<Integer, Integer> p2 = posConvert (((Line)o.getValue()).position2).getSquareCoord();
				g.drawLine(p1.a, p1.b, p2.a, p2.b);
			}
		}
	}
	
	private HashMap<Pos, Object> objects = new HashMap<Pos, Object> ();
	
	public Dot addDot (Pos p, Color c) {
		Dot d = new Dot ();
		d.c = c;
		d.position = p;
		objects.put(p, d);
		repaint ();
		return d;
	}
	
	public Line addLine (Pos p1, Pos p2, Color c) {
		Line l = new Line ();
		l.c = c;
		l.position1 = p1;
		l.position2 = p2;
		objects.put(p1, l);
		repaint ();
		return l;
	}

	public void paintObject (Object o) {
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		if (o.getClass() == Dot.class) {
			Pair<Integer, Integer> pos = ((Dot)o).position.getSquareCoord();
			x1 = pos.a - 2;
			x2 = pos.a + 2;
			y1 = pos.b - 2;
			y2 = pos.b + 2;
		} else if (o.getClass() == Line.class) {
			Rectangle rect = ((Line)o).getBoundingBox();
			x1 = rect.x;
			x2 = rect.x + rect.width;
			y1 = rect.y;
			y2 = rect.y + rect.height;
		}
		
		repaint (x1, y1, x2-x1, y2-y1);
	}
}
