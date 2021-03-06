package physicsGame;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private float posX, posY;
	private int width, height;
	private float dX = 0, dY = 0;
	private boolean colliding = false;
	private Color color;
	Image image;

	public Player(int x, int y, int w, int h) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		try {
			image = ImageIO.read(new File("res/boxAlt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Player(int x, int y, int w, int h, Color c) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		color = c;
	}

	public void updatePos() {
		posX += dX;
		posY += dY;
	}

	public void setX(int x) {
		posX = x;
	}

	public void setY(int y) {
		posY = y;
	}

	public void setdX(float x) {
		dX = x;
	}

	public void setdY(float y) {
		dY = y;
	}

	public void setColliding(boolean value) {
		colliding = value;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void incX(float x) {
		dX += x;
	}

	public void incY(float y) {
		dY += y;
	}

	public int getX() {
		return (int) (posX);
	}

	public int getY() {
		return (int) (posY);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public float getdX() {
		return dX;
	}

	public float getdY() {
		return dY;
	}

	public Color getColor() {
		return color;
	}

	public Image getImage() {
		return image;
	}
}
