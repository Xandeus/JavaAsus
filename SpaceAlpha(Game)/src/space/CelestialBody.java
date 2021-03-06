package space;

import java.awt.Color;

public interface CelestialBody {
	int detail = 250;
	public int getWindowLocX();
	public int getWindowLocY();
	public void setWindowLocX(int x);
	public void setWindowLocY(int y);
	public int getRadius();
	public int getDensity();
	public int getVolume();
	public int getMass();
	public int getResourceTotal();
	public boolean hasAtmosphere();
	public String getType();
	public String getResources();
	public Color[][] getTerrain();
	public Color[][] getTerrain(int time);

	public void generateTerrain(double scale,int octaves,double persistence, double frequency,double amplitude);
}
