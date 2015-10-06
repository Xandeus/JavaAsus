package Chess;

import java.awt.Color;

public class Rook extends GamePiece {

	public Rook(int x, int y, Color color, int posX, int posY) {
		super(x, y, color, posX, posY);
		setName("Rook");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		int posX = this.getPosX();
		int posY = this.getPosY();
		System.out.println(this.getColor());
		if (this.getColor() == Color.RED) {
			if (desiredMove.getPosX() < posX) {
				for (int x = posX - 1; x >= 0; x--) {
					if (pieces[x][posY].getName().equals("null")) {
						if (desiredMove.getPosX() == x) {
							return true;
						}
					}
				}
			} else if (desiredMove.getPosX() > posX) {
				for (int x = posX + 1; x < 8; x++) {
					if (pieces[x][posY].getName().equals("null")) {
						if (desiredMove.getPosX() == x) {
							return true;
						}
					}
				}
			} else if (desiredMove.getPosY() > posY) {
				for (int y = posY + 1; y < 8; y++) {
					if (pieces[posX][y].getName().equals("null")) {
						if (desiredMove.getPosY() == y) {
							return true;
						}
					} else
						break;
				}
			} else if (desiredMove.getPosY() < posY) {
				for (int y = posY - 1; y >= 0; y--) {
					if (pieces[posX][y].getName().equals("null")) {
						if (desiredMove.getPosY() == y) {
							return true;
						}
					} else
						break;
				}
			}
		}

		return false;
	}

}