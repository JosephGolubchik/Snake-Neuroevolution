package game;

import java.awt.Point;

public class Game {

	private int grid_width, grid_height;
	private Snake snake;
	private Fruit fruit;
	private int score;

	public Game(int grid_width, int grid_height) {
		this.grid_width = grid_width;
		this.grid_height = grid_height;
		initGame();
	}
	
	private void initGame() {
		snake = new Snake(grid_width/2, grid_height/2);
		createFruit();
		score = 0;
	}
	
	public void tick() {
		checkGameOver();
		snake.move();
		updateFruit();
	}
	
	public void checkGameOver() {
		if(checkOutOfGrid() || snake.collision())
			initGame();
	}
	
	public boolean checkOutOfGrid() {
		if(snake.getHead().getX() < 0 || snake.getHead().getX() > grid_width-1 ||
		   snake.getHead().getY() < 0 || snake.getHead().getY() > grid_height-1) {
			return true;
		}
		return false;
	}
	
	public void updateFruit() {
		if(fruit.isEaten()) {
			int rand_x = (int) (Math.random() * (grid_width-1));
			int rand_y = (int) (Math.random() * (grid_height-1));
			fruit.setLocation(rand_x, rand_y);
			fruit.setEaten(false);
			snake.addNode();
		}
		if(snake.getHead().getX() == fruit.getX() && snake.getHead().getY() == fruit.getY()) {
			fruit.setEaten(true);
			score++;
//			System.out.println(score);
		}
	}
	
	public void createFruit() {
		int rand_x = (int) (Math.random() * (grid_width-1));
		int rand_y = (int) (Math.random() * (grid_height-1));
		fruit = new Fruit(rand_x, rand_y);
	}

	// Getters and setters
	
	public int getGrid_width() {
		return grid_width;
	}

	public int getGrid_height() {
		return grid_height;
	}

	public Snake getSnake() {
		return snake;
	}

	public Fruit getFruit() {
		return fruit;
	}

}
