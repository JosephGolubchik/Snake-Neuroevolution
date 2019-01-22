package game;

public class Fruit {

	private int x;
	private int y;
	private boolean eaten;

	public Fruit(int x, int y) {
		this.x = x;
		this.y = y;
		eaten = false;
	}

	// Getters and setters
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
}
