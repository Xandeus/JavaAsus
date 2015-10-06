package Chess;

import java.awt.Color;

public class Pawn extends GamePiece {
	int endVal =3;
	public Pawn(int x, int y, Color color, int posX, int posY) {
		super(x, y, color, posX, posY);
		setName("pawn");
	}

	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		// System.out.println(desiredMove.getName());
		int posX = this.getPosX();
		int posY = this.getPosY();
		System.out.println(this.getColor());
		if (this.getColor() == Color.RED){
			for (int y = posY + 1; y < posY + endVal; y++) {
				//Moving forward 
				if (pieces[posX][y].getName().equals("null")){
					if (desiredMove.getPosX() == posX && desiredMove.getPosY() == y) {
						endVal =2;
						return true;
					}
				}
				if(pieces[posX][y].getColor() != Color.red){
					if (!pieces[posX-1][posY+1].getName().equals("null") && desiredMove.getPosX() == posX-1 && desiredMove.getPosY() == posY+1) {
						return true;
					}
					else if (!pieces[posX+1][posY+1].getName().equals("null") && desiredMove.getPosX() == posX+1 && desiredMove.getPosY() == posY+1) {
						return true;
					}
				}
				else
					return false;
			}
		}
		else
			for (int y = posY - 1; y > posY - endVal; y--) {
				//Moving forward 
				if (pieces[posX][y].getName().equals("null")){
					if (desiredMove.getPosX() == posX && desiredMove.getPosY() == y) {
						endVal =2;
						return true;
					}
				}
				if(pieces[posX][y].getColor() != Color.cyan){
					if (!pieces[posX-1][posY-1].getName().equals("null") && desiredMove.getPosX() == posX-1 && desiredMove.getPosY() == posY-1) {
						return true;
					}
					else if (!pieces[posX+1][posY-1].getName().equals("null") && desiredMove.getPosX() == posX+1 && desiredMove.getPosY() == posY-1) {
						return true;
					}
				}
				else
					return false;
			
			}
		return false;
	}
}