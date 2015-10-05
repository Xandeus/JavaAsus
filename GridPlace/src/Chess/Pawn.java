package Chess;

import java.awt.Color;

public class Pawn extends GamePiece {
	String name;
	public Pawn(int x, int y, Color color,int posX, int posY) {
		super(x, y, color,posX,posY);
		name = "Pawn";
	}

	public void move(int x, int y) {
		// TODO Auto-generated method stub

	}

	public void avaliableMoves(GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		int posX = this.getPosX();
		int posY = this.getPosY();
		for (int y = posY+ 1; y < posY + 3; y++) {
			if (pieces[posX][y] == null) {
				System.out.println("Spot at " + posX + " " + y);
			}
		}
	}
	public String getName(){
		return name;
	}

}
