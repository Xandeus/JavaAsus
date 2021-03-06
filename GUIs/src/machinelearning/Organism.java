package machinelearning;

import java.awt.Color;

public class Organism {
	float topspeed;
	int diameter = 10;
	PVector location;
	PVector velocity;
	PVector acceleration = new PVector(0,0);
	Color color;
	public Organism(int x, int y,int d) {
		location = new PVector(x, y);
		velocity = new PVector(0, 0);
		color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		diameter = d;
		topspeed = 5;
	}
	public int getDiameter(){
		return diameter;
	}
	public void setDiameter(int d){
		diameter = d;
	}
	public void setTopSpeed(float t){
		topspeed = t;
	}
	public PVector getLocation(){
		return location;
	}
	
	public PVector getVelocity(){
		return velocity;
	}
	
	public PVector getAcceleration(){
		return acceleration;
	}
	public Color getColor(){
		return color;
	}
}