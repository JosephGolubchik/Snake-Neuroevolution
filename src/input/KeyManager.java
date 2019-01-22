package input;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private char last_pressed;
	private boolean new_pressed;


	public KeyManager() {
		new_pressed = false;
	}

	public void tick() {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		last_pressed = e.getKeyChar();
		new_pressed = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	// Getters and setters
	public char getLast_pressed() {
		return last_pressed;
	}
	
	public boolean getNewPressed() {
		return new_pressed;
	}
	
	public void setNewPressed(boolean bool) {
		new_pressed = bool;
	}
}
