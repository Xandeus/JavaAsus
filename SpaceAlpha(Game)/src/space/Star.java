package space;

import java.awt.Color;

public class Star implements CelestialBody {
	int radius;
	int wLocX, wLocY;
	double scale,persistence,frequency,amplitude;
	int octaves;
	int density = (int) (Math.random() * 1000), volume = (int) (Math.random() * 1000),
			mass = (int) (Math.random() * 1000);
	Color[][] terrain;
	final int WHITE = 0, RED = 1, YELLOW = 2;
	int starColor;
	public Star() {
		radius = ((int) (Math.random() * 51) + 50);
		starColor = (int)(Math.random()*3);
	}

	public Star(int r) {
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

	public Color[][] getTerrain() {
		return terrain;
	}
	public Color[][] getTerrain(int time) {
		return generateTerrain(time);
	}

	public void generateTerrain(double scale, int octaves, double persistence, double frequency, double amplitude) {
		this.scale = scale;
		this.octaves = octaves;
		this.persistence = persistence;
		this.frequency = frequency;
		this.amplitude = amplitude;
		terrain = new Color[detail][detail];
		for (int x = 0; x < detail; x += 1) {
			for (int y = 0; y < detail; y += 1) {
				double h = SimplexNoise.OctavePerlin(x * scale, y * scale, octaves, persistence, frequency, amplitude,
						(Math.random() * 100) * scale);
				int c = (int) ((h + 1) / 2.0 * 255.0);
				switch(starColor){
					case(WHITE):
						terrain[x][y] = (new Color(255, 255, 255,c	));
						break;
					case(RED):
						terrain[x][y] = (new Color(255, c, c));
						break;
					case(YELLOW):
						terrain[x][y] = (new Color(255, 255, c));
						break;
				}
			}
		}
	}
	public Color[][] generateTerrain(int z) {
		terrain = new Color[detail][detail];
		for (int x = 0; x < detail; x += 1) {
			for (int y = 0; y < detail; y += 1) {
				double h = SimplexNoise.OctavePerlin(x * scale, y * scale,z*scale, octaves, persistence, frequency, amplitude,
						(Math.random() * 100) * scale);
				int c = (int) ((h + 1) / 2.0 * 255.0);
				terrain[x][y] = (new Color(255, 255, c));

			}
		}
		return terrain;
	}

	public String getType() {
		return "Star";
	}

	public String getResources() {
		return "N/A";
	}

	public int getResourceTotal() {
		return 0;
	}

	public boolean hasAtmosphere() {
		return false;
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
		return (int) ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
	}

	// Sets gui window location x
	public void setWindowLocX(int x) {
		wLocX = x;
	}

	public void setWindowLocY(int y) {
		wLocY = y;
	}


}
