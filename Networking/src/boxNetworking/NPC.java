package boxNetworking;
import java.awt.Color;
import java.io.Serializable;

public class NPC implements Serializable{
	private int posX;
	private int posY;
	private float moveSpeed;
	private Color color;
	public NPC(int x, int y){
		posX = x;
		posY = y;
		moveSpeed = .1f;
		color = randColor();
	}
	public Color randColor(){
		return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void setX(int x){
		posX = x;
	}
	public void setY(int y){
		posY = y;
	}
	public void moveX(int x){
		posX += x;
	}
	public void moveY(int y){
		posY += y;
	}
	public float getMovementspeed(){
		return moveSpeed;
	}
	public void randomMove(){
		int num = (int)(Math.random()*4);
		switch(num){
		case 0:
			if(posX<800)
				posX += 1;
			break;
		case 1:

			if(posX>0)
				posX -= 1;
			break;
		case 2:
			if(posY<600)
				posY += 1;
			break;
		case 3:
			if(posY>0)
				posY -= 1;
			break;
		}
		
	}

}
