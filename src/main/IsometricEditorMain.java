package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import graphics.Line;

public class IsometricEditorMain {

	JFrame frame;
	IsometricDrawingPanel panel;
	
	public static void main(String[] args) {
		IsometricEditorMain iem = new IsometricEditorMain();
		iem.classMain();
	}
	
	public void classMain () {
		frame = new JFrame ("Isometric Editor");
		panel = new IsometricDrawingPanel();
		
		panel.addDot(new Pos (0,0,true), Color.yellow);
		panel.addMouseListener(new MouseListener () {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) leftMouseUp (e.getPoint());
				if (e.getButton() == MouseEvent.BUTTON2) rightMouseUp (e.getPoint());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) leftMouseDown (e.getPoint());
				if (e.getButton() == MouseEvent.BUTTON2) rightMouseDown (e.getPoint());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panel.addMouseMotionListener(new MouseMotionListener () {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) leftMouseDragged (e.getPoint());
				if (e.getButton() == MouseEvent.BUTTON2) rightMouseDragged (e.getPoint());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(500, 500);
	}
	
	Line l = null;
	
	public void rightMouseDown (Point loc) {
		erase (loc);
	}
	
	public void rightMouseUp (Point loc) {
	}
	
	public void rightMouseDragged (Point loc) {
		erase (loc);
	}
	
	public void erase (Point loc) {
		// TODO: Erase
	}
	
	public void leftMouseDown (Point loc) {
		Pos p = new Pos (loc.x - (panel.getWidth()/2), (panel.getHeight()/2)-loc.y, false);
		p.roundIsometric(20);
		l = panel.addLine(p, p, Color.red);
	}
	
	public void leftMouseUp (Point loc) {
		l.c = Color.black;
		panel.paintObject(l);
		l = null;
	}
	
	public void leftMouseDragged (Point loc) {
		Pos p = new Pos (loc.x - (panel.getWidth()/2), (panel.getHeight()/2)-loc.y, false);
		p.roundIsometric(20);
		l.position2 = p;
		panel.paintObject(l);
	}
}
