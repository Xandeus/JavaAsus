package machinelearning;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Pathfinder {
	Random rand = new Random();
	int[] movements = new int[15];
	float fitness;
	int posX,posY;
	int sX = 100, sY = 500;
	Color color;
	public Pathfinder(){
		posX = sX;
		posY = sY;
		color =  new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		for(int i = 0; i<movements.length;i++){
			movements[i] = rand.nextInt(5);
		}
	}
	public Color getColor(){
		return color;
	}
	float fitness(Point target,int squareSize){
		double x1 = target.getX();
		double y1 = target.getY();
		int tX = sX;
		int tY = sY;
		for(int x : movements){
			moveFitness(x,squareSize);
		}
		
		float endDistance = (float)(Math.sqrt(((Math.pow((x1-sX), 2)+Math.pow((y1-sY), 2)))));
		fitness = (float)(1-(endDistance/1000));
		sX = tX;
		sY = tY;
		return fitness;
	}
	Pathfinder crossover(Pathfinder partner) {
		Pathfinder child = new Pathfinder();
		int midpoint = (rand.nextInt(movements.length));
		for (int i = 0; i < movements.length; i++) {
			if (i > midpoint)
				child.movements[i] = movements[i];
			else
				child.movements[i] = partner.movements[i];
		}
		color =  new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		return child;
	}

	void mutate(float mutationRate) {
		for (int i = 0; i < movements.length; i++) {
			if (Math.random() < mutationRate) {
				movements[i] = rand.nextInt(5);
			}
		}
	}
	private void moveFitness(int command,int squareSize){
		switch(command){
		case 0:
			sX-=squareSize;
			break;
		case 1:
			sX+=squareSize;
			break;
		case 2:
			sY-=squareSize;
			break;
		case 3:
			sY+=squareSize;
			break;
		case 4:
			break;
		}
	}
	public void moveDisplay(int command,int squareSize){
		//0 is left, 1 is right, 2 is up, 3 is down, 4 is no movement
		switch(command){
		case 0:
			posX-=squareSize;
			break;
		case 1:
			posX+=squareSize;
			break;
		case 2:
			posY-=squareSize;
			break;
		case 3:
			posY+=squareSize;
			break;
		case 4:
			break;
		}
	}
	public void resetPos(){
		posX = 100;
		posY = 500;
	}
	public int[] getMoves(){
		return movements;
	}
}
