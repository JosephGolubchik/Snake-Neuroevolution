package game;

import java.awt.Point;
import java.util.Iterator;

public class Game_org {

	private int grid_width, grid_height;
	private Snake snake;
	private Fruit fruit;
	private int score;

	public Game_org(int grid_width, int grid_height) {
		this.grid_width = grid_width;
		this.grid_height = grid_height;
		initGame();
	}
	
	private void initGame() {
		snake = new Snake(grid_width/2, grid_height/2);
		createFruit();
		double[] grid = getGrid();
		snake.createBrain(grid);
		score = 0;
	}
	
	public void tick() {
		checkGameOver();
		moveWithBrain();
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

	public void moveWithBrain() {
		double[] grid = getGrid();
		double[] output = snake.getBrain().calculate(grid);
		int choice = 0;
		for (int i = 1; i < output.length; i++) {
			if(output[i] > output[choice]) choice = i;
		}
		if(choice == 0)
			snake.setDirection('w');
		if(choice == 1)
			snake.setDirection('a');
		if(choice == 2)
			snake.setDirection('s');
		if(choice == 3)
			snake.setDirection('d');
		System.out.println("choice: " + choice);
	}
	
	
	
	public double[] getGrid() {
		double[] grid = new double[grid_width*grid_height];
		
		grid[grid_width * snake.getHead().getY() + snake.getHead().getX()] = 1;
		
		for (int i = 0; i < snake.getBody().size()-1; i++) {
			grid[grid_width * snake.getBody().get(i).getY() + snake.getBody().get(i).getX()] = 2;
		}
		
		grid[grid_width * fruit.getY() + fruit.getX()] = 3;
		
		return grid;
	}
	
//	public int[] distanceFromWall() {
//		int[] distances = new int[8];
//		Point snake_pos = new Point(snake.getHead().getX(), snake.getHead().getY());
//		
//		distances[0] = (int) snake_pos.getY(); 				 // north
//		distances[1] = grid_width - (int) snake_pos.getX();  // east
//		distances[2] = grid_height - (int) snake_pos.getY(); // south
//		distances[3] = (int) snake_pos.getX();				 // west
//		
//		distances[4] = distance(snake_pos, )				 // north-east
//		distances[5] = grid_width - (int) snake_pos.getX();  // east
//		distances[6] = grid_height - (int) snake_pos.getY(); // south
//		distances[7] = (int) snake_pos.getX();				 // west
//		
//		
//		
//		return distances;
//	}
	
	public double distance(Point p0, Point p1) {
		double dx = p0.getX() - p1.getX();
		double dy = p0.getY() - p1.getY();
		return Math.sqrt(dx*dx + dy*dy);
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
