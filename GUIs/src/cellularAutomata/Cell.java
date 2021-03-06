package cellularAutomata;

import java.awt.Color;

public class Cell {
	private int posX, posY;
	private int nX, nY;
	public static final int WATER = 0,DIRT=1;
	private int type;
	private int vel = 0;
	private boolean isAlive = false;
	Color color;
	public Cell(int x, int y , int nX, int nY){
		posX = x;
		posY = y;
		this.nX = nX; 
		this.nY = nY;
		color = new Color(0, (int) (Math.random() * 205)+50, 0);
	}
	public boolean isAlive(){
		return isAlive;
	}
	public void setAlive(boolean state){
		isAlive = state;
	}
	public void setAlive(boolean state, int type){
		isAlive = state;
		this.type = type;
		if(type == WATER){
			color = new Color(0,0,(int) (Math.random() * 205)+50);
		}
		else{
			color = new Color((int) (Math.random() * 30)+150,42,42);
		}
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public int getPosX(){
		return nX;
	}
	public int getPosY(){
		return nY;
	}
	public int getVel(){
		return vel;
	}
	public void setVel(int v){
		vel = v;
	}
	public void setType(int t){
		type = t;
	}
	public int getType(){
		return type;
	}
	public Color getColor(){
		return color;
	}
}