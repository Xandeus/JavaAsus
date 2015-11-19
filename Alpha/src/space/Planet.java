package space;

import java.awt.Color;

public class Planet implements CelestialBody {
	Color color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	int radius;
	int density = (int)(Math.random()*1000), volume = (int)(Math.random()*1000), mass = (int)(Math.random()*1000);
	int wLocX, wLocY;

	public Planet() {
		radius = ((int) (Math.random() * 21) + 10);
	}

	public Planet(int r) {
		radius = r;
	}

	public int getWindowLocX() {
		return wLocX;
	}

	public int getWindowLocY() {
		return wLocY;
	}

	public int getLocation() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Color getColor() {
		return color;
	}
	public String getType(){
		return "Planet";
	}
	public int getRadius() {
		return radius;
	}

	public int getMass() {
		// TODO Auto-generated method stub
		return mass;
	}

	public int getDensity() {
		// TODO Auto-generated method stub
		return density;
	}

	public int getVolume() {
		// TODO Auto-generated method stub
		return volume;
	}

	// Sets gui window location x
	public void setWindowLocX(int x) {
		wLocX = x;
	}

	public void setWindowLocY(int y) {
		wLocY = y;
	}
}
