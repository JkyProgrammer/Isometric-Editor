package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				handleMouseDown (e.getPoint());
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
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(500, 500);
	}
	
	public void handleMouseDown (Point loc) {
		Pos pos = new Pos (loc.x - (panel.getWidth()/2), (panel.getHeight()/2)-loc.y, false);
		pos.roundIsometric(20);
		panel.addDot(pos, Color.red);
	}
}
