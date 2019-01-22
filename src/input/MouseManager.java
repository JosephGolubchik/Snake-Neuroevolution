package input;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import gui.GUI;

public class MouseManager implements MouseListener{

	public GUI gui;
	public Point mousePos;
	public Point clicked = null;
	
	public MouseManager(GUI gui) {
		this.gui = gui;
	}

	public void tick() {
		int mouse_x=MouseInfo.getPointerInfo().getLocation().x-gui.display.getCanvas().getLocationOnScreen().x;
		int mouse_y=MouseInfo.getPointerInfo().getLocation().y-gui.display.getCanvas().getLocationOnScreen().y;
		mousePos = new Point(mouse_x, mouse_y);
//		System.out.println(mousePos); 
	}
	
	
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
