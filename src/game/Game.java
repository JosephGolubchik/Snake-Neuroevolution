package game;

import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;

public class Game {

	private int grid_width, grid_height;
	private Snake snake;
	private Snake[] snakes;
	private int snake_id;
	private int[] scores;
	private int score;
	private Fruit fruit;
	private final int POP_SIZE = 5;
	private boolean running;
	private long start_time;

	public Game(int grid_width, int grid_height) {
		this.grid_width = grid_width;
		this.grid_height = grid_height;
		snake_id = -1;
		snakes = new Snake[POP_SIZE];
		scores = new int[POP_SIZE];
		for(int i = 0; i < snakes.length; i++) {
			snakes[i] = new Snake(grid_width/2, grid_height/2);
			snake = snakes[i];
			snake.createBrain(getGrid());
		}
		snake = snakes[0];
		initGame();
	}

	private void createSnakes() {
		int best_snake_id = maxIndexArray(scores);
		Snake best_snake = snakes[best_snake_id];
		for(int i = 0; i < snakes.length; i++) {
			snakes[i] = best_snake;
		}
		for(int i = 0; i < snakes.length; i++) {
			snakes[i].getBrain().mutate(0.1);
			snakes[i].reset(grid_width/2, grid_height/2);
		}
	}

	private int maxIndexArray(int[] arr) {
		int index = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] > arr[index]) index = i;
		}
		return index;
	}

	private void initGame() {
		start_time = System.currentTimeMillis();
		snake_id++;
		running = true;
		snake = snakes[snake_id];
		createFruit();
		score = 0;
	}

	public void tick() {
		checkGameOver();
		if(running) {
			moveWithBrain();
			snake.move();
//			System.out.println(snake.getHead().getX() + "," + snake.getHead().getY());
			updateFruit();
		}
	}

	public void checkGameOver() {
		if(checkOutOfGrid() || snake.collision() || System.currentTimeMillis() - start_time > 1000) {
			if(snake_id < snakes.length - 1) {
				scores[snake_id] = score;
				initGame();
			}
			else {
				if(running) {
					running = false;
					System.out.println(Arrays.toString(scores));
					createSnakes();
					snake_id = -1;
					initGame();
				}
				//				running = false;
			}
		}
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
		if(!checkOutOfGrid()) {
			double[] grid = getGrid();
			double[] output = snake.getBrain().calculate(grid);
			int choice = 0;
			for (int i = 1; i < output.length; i++) {
				if(output[i] > output[choice]) choice = i;
			}
			if(choice == 0)
				if(snake.getDirection() != 's') snake.setDirection('w');
			if(choice == 1)
				if(snake.getDirection() != 'd') snake.setDirection('a');
			if(choice == 2)
				if(snake.getDirection() != 'w') snake.setDirection('s');
			if(choice == 3)
				if(snake.getDirection() != 'a') snake.setDirection('d');
		}
	}



	public double[] getGrid() {
		double[] grid = new double[grid_width*grid_height];

		grid[grid_width * snake.getHead().getY() + snake.getHead().getX()] = 1;

		for (int i = 0; i < snake.getBody().size()-1; i++) {
			grid[grid_width * snake.getBody().get(i).getY() + snake.getBody().get(i).getX()] = 2;
		}

		if(fruit != null)
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
