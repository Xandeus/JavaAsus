package fractals;

import java.awt.Color;

public class Shape {
	double x,y,dX,dY;
	double r;
	boolean isActive = false;
	Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	public Shape(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
		
	}
	public Shape(int x, int y, int dX,int dY){
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
		
	}
	public Shape(int x, int y, int dX,int dY,Color color){
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
		this.color = color;
	}
	public int getX(){
		return (int)x;
	}
	public int getY(){
		return (int)y;
	}
	public int getR(){
		return (int)r;
	}
	public int getDX(){
		return (int)dX;
	}
	public int getDY(){
		return (int)dY;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setR(int r){
		this.r = r;
	}
	public void setDX(int dx){
		dX = dx;
	}
	public void setDY(int dy){
		dY = dy;
	}
	public void change(int x, int y, int r){
		this.x+=x;
		this.y+=y;
		this.r+=r;
	}
	public void alter(int x, int y){
		this.dX+=x;
		this.dY+=y;
	}
	public boolean isActive(){
		return isActive;
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void changeState(){
		isActive = !isActive;
	}
}
