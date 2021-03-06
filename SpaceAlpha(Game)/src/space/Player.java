package space;

import java.util.ArrayList;

public class Player extends Character{
	int health=4;
	ArrayList<CelestialBody> controlledBodies = new ArrayList<CelestialBody>();
	public Player(int x, int y,int w,int h,CelestialBody b){
		super(x,y,w,h,b);	
	}
	public int getHealth(){
		return health;
	}
	public void setHealth(int x){
		health = x;
	}
	public void decreaseHealth(int x){
		health-=x;
	}
	public void addControlledBody(CelestialBody b){
		controlledBodies.add(b);
	}
}
