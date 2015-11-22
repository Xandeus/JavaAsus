package space;

public class MiningFacility implements Building {
	CelestialBody body;
	int posX, posY;
	public MiningFacility(CelestialBody body, int x, int y){
		posX = x;
		posY = y;
		this.body = body;
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public String getType() {
		// TODO Auto-generated method stub
		return "Mining facility";
	}

	@Override
	public void function() {
		// TODO Auto-generated method stub

	}

	public CelestialBody getLocation() {
		// TODO Auto-generated method stub
		return body;
	}
	

}
