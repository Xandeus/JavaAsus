package space;

import java.awt.Point;

public class Enemy extends Character{
	public Enemy(int x, int y,int w,int h,CelestialBody b){
		super(x,y,w,h,b);
	}
	public void moveX(float speed){
		posX += speed;
	}
	public void moveY(float speed){
		posY += speed;
	}
}