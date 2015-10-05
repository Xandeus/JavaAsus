package Chess;

import java.awt.Color;

public abstract class GamePiece {
	int x, y;
	int pX,pY;
	Color color;
	String name;
	public GamePiece(int x, int y,Color color,int posX, int posY){
		this.x = x;
		this.y = y;
		pX = posX;
		pY = posY;
		
		this.color = color;
	}
	
	public abstract void move(int x, int y);
	public abstract void avaliableMoves(GamePiece[][] pieces);
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getPosX(){
		return pX;
	}
	public int getPosY(){
		return pY;
	}
	public String getName(){
		return name;
	}
	public Color getColor(){
		return color;
	}
}
