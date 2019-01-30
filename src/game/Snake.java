package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import neural_network.NeuralNetwork;

public class Snake {
	
	private ArrayList<SnakeNode> body;
	private char direction;
	private NeuralNetwork brain;
	
	public Snake(int x, int y) {
		body = new ArrayList<SnakeNode>();
		body.add(new SnakeNode(x, y));
	}
	
	public Iterator<SnakeNode> iterator(){
		return body.iterator();
	}
	
	public void createBrain(double[] input) {
		brain = new NeuralNetwork(input.length, 10, 4);
	}
	
	public void reset(int x, int y) {
		body.clear();
		body = new ArrayList<SnakeNode>();
		body.add(new SnakeNode(x, y));
	}
	
	public NeuralNetwork getBrain() {
		return brain;
	}

	public void move() {
		SnakeNode head = body.get(body.size()-1);
		if(direction == 'w') { // up
			body.add(new SnakeNode(head.getX(), head.getY()-1));
			body.remove(0);
		}
		else if(direction == 'a') { // left
			body.add(new SnakeNode(head.getX()-1, head.getY()));
			body.remove(0);
		}
		else if(direction == 's') { // down
			body.add(new SnakeNode(head.getX(), head.getY()+1));
			body.remove(0);
		}
		else if(direction == 'd') { // right
			body.add(new SnakeNode(head.getX()+1, head.getY()));
			body.remove(0);
		}
	}
	
	public void addNode() {
		SnakeNode head = body.get(body.size()-1);
		if(direction == 'w') { // up
			body.add(new SnakeNode(head.getX(), head.getY()-1));
		}
		else if(direction == 'a') { // left
			body.add(new SnakeNode(head.getX()-1, head.getY()));
		}
		else if(direction == 's') { // down
			body.add(new SnakeNode(head.getX(), head.getY()+1));
		}
		else if(direction == 'd') { // right
			body.add(new SnakeNode(head.getX()+1, head.getY()));
		}
		
	}
	
	public boolean collision() {
		if(body.size() > 1) {
			for (int i = 0; i < body.size()-1; i++) {
				if(getHead().getX() == body.get(i).getX() && getHead().getY() == body.get(i).getY()) return true;
			}
		}
		return false;
	}
	
	// Getters and setters
	public SnakeNode getHead() {
		return body.get(body.size()-1);
	}
	
	public ArrayList<SnakeNode> getBody() {
		return body;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}
	
}
