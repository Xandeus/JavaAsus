package conwayLife;

import java.awt.Color;

public class Cell {
	private int posX, posY;
	private int nX, nY;
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
	public Color getColor(){
		return color;
	}
}
