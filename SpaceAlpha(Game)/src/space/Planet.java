package space;

import java.awt.Color;

public class Planet implements CelestialBody {
	Color[][] terrain;
	static String[] resources = { "Iron", "Copper", "Gold", "Silicon", "Fuel", };
	String[] planetResources = new String[10];
	int radius;
	int density = (int) (Math.random() * 1000), volume = (int) (Math.random() * 1000),
			mass = (int) (Math.random() * 1000);
	int wLocX, wLocY;
	int resourceTotal = (int) (Math.random() * 2000) + 500;
	// Change to better system
	int atmoSphere = (int) (Math.random() * 2);
	String pType;
	int climate;
	int TEMPERATE = 0, DESERT = 1;

	public Planet() {
		if ((int) (Math.random() * 2) == 0) {
			pType = "Terrestrial";
			radius = ((int) (Math.random() * 11) + 5);
			planetResources[0] = resources[(int) (Math.random() * 5)];
			climate = (int) (Math.random() * 2);
		} else {
			pType = "Gas";
			radius = ((int) (Math.random() * 11) + 15);
			planetResources[0] = "N/A";
		}

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

	public Color[][] getTerrain() {
		return terrain;
	}

	public void generateTerrain(double scale, int octaves, double persistence, double frequency, double amplitude) {
		terrain = new Color[detail][detail];
		for (int x = 0; x < detail; x += 1) {
			for (int y = 0; y < detail; y += 1) {
				double h = SimplexNoise.OctavePerlin(x * scale, y * scale, octaves, persistence, frequency, amplitude,
						(Math.random() * 100) * scale);
				if (pType.equals("Terrestrial")) {
					if (climate == TEMPERATE) {
						if (h < 0) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(0, 0, c));
						} else if (h < .1) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(0, c, c));
						} else if (h < .2) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(c, c, 0));
						} else if (h < .9) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(0, c, 0));
						} else if (h < 1) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(c, c, c));
						}
					} else {
						if (h < -.5) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(0, 0, c));
						} else if (h < .1) {
							int c = (int) ((h + 1) / 2.0 * 255.0);
							terrain[x][y] = (new Color(255, c, 0));
						
						} 
					}
				} else {
					if (h < 1) {
						int c = (int) ((h + 1) / 2.0 * 255.0);
						terrain[x][y] = (new Color((int)(100*Math.random()), (int)(100*Math.random()), c));
					}

				}
			}
		}
	}

	public String getType() {
		return pType + " planet";
	}

	public String getResources() {
		return planetResources[0];// resource;
	}

	public int getResourceTotal() {
		return resourceTotal;
	}

	public boolean hasAtmosphere() {
		if (atmoSphere == 0)
			return true;
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

	@Override
	public Color[][] getTerrain(int time) {
		// TODO Auto-generated method stub
		return null;
	}

}
