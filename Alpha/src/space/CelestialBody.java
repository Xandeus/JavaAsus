package space;

import java.awt.Color;

public interface CelestialBody {
	public int getWindowLocX();
	public int getWindowLocY();
	public void setWindowLocX(int x);
	public void setWindowLocY(int y);
	public int getRadius();
	public int getDensity();
	public int getVolume();
	public int getMass();
	public String getType();
	public Color getColor();
}