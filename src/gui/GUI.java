package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import game.Game;
import game.SnakeNode;
import input.KeyManager;
import input.MouseManager;

public class GUI implements Runnable {

	public Display display;
	private int width, height;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;
	
	private final int GRID_SIZE = 20;

	// Game
	Game game;
	
	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;


	public GUI(int width, int height){
		this.width = width;
		this.height = height;	
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager(this);
		
		game = new Game(GRID_SIZE, GRID_SIZE);
		
	}

	private void init(){

		display = new Display("Snake", width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getCanvas().addMouseListener(mouseManager);
		
	}
	
	// Input
	private void updateSnake() {
		if(keyManager.getNewPressed()) {
			game.getSnake().setDirection(keyManager.getLast_pressed());
			keyManager.setNewPressed(false);
		}
	}
	
	// Thread
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void tick(){
		keyManager.tick();
		mouseManager.tick();
		
		game.tick();
		updateSnake();
	}

	public void run(){

		init();

		int fps = 10;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}

			if(timer >= 1000000000){
				//				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}

	// Drawing
	private void render(){
		display.getFrame().repaint();
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		g.fillRect(0, 0, width, height);
		drawGame(g);
		
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	private void drawGame(Graphics g) {
		drawGrid(g);
		drawFruit(g);
		drawSnake(g);
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(new Color(255,255,255,20));
		for (int x = 0; x < game.getGrid_width(); x++) {
			for (int y = 0; y < game.getGrid_height(); y++) {
				drawCell(g, x, y, false);
			}
		}
	}
	
	private void drawSnake(Graphics g) {
		Queue<SnakeNode> body_copy = new LinkedList<SnakeNode>(game.getSnake().getBody());
		while(!body_copy.isEmpty()) {
			g.setColor(new Color(255,255,255,255));
			SnakeNode node = body_copy.poll();
			drawCell(g, node.getX(), node.getY(), true);
		}
	}
	
	private void drawFruit(Graphics g) {
		g.setColor(new Color(237, 106, 134));
		drawCell(g, (int)game.getFruit().getX(), (int)game.getFruit().getY(), true);
	}
	
	private void drawCell(Graphics g, int x, int y, boolean filled) {
		int cell_size = width / game.getGrid_width();
		if(filled)
			g.fillRect(x*cell_size, y*cell_size, cell_size-1, cell_size-1);
		else
			g.drawRect(x*cell_size, y*cell_size, cell_size-1, cell_size-1);
	}
	
	public void drawStringCentered(String s, Color color, int x, int y, int fontSize) {
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize)); 
		g.setColor(color);

		int text_width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int text_height = (int) g.getFontMetrics().getStringBounds(s, g).getHeight();

		g.drawString(s, x - text_width/2, y - text_height/2);
	}

	// Getters and setters
	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


}

