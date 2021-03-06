package chess;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Rook extends GamePiece {
	ArrayList<GamePiece> validMoves;

	public Rook(int x, int y, boolean isWhite, int posX, int posY) {
		super(x, y, isWhite, posX, posY);
		try {
			if (isWhite())
				image = ImageIO.read(new File(
						"res/whiteRook.png"));
			else
				image = ImageIO.read(new File(
						"res/blackRook.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setImage(image);
		setName("Rook");
		// TODO Auto-generated constructor stub
	}

	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		validMoves = findValidMoves(pieces);
		for (GamePiece p : validMoves) {
			if (p==desiredMove) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<GamePiece> findValidMoves(GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		validMoves = new ArrayList<GamePiece>();
		int posX = this.getPosX();
		int posY = this.getPosY();
		for (int x = posX - 1; x >= 0; x--) {
			if (pieces[x][posY].getName().equals("null")) {
				validMoves.add(pieces[x][posY]);
			} else if (pieces[x][posY].isWhite() != this.isWhite()) {
				validMoves.add(pieces[x][posY]);
				break;
			} else
				break;
		}

		for (int x = posX + 1; x < 8; x++) {
			if (pieces[x][posY].getName().equals("null")) {
				validMoves.add(pieces[x][posY]);
			} else if (pieces[x][posY].isWhite() != this.isWhite()) {
				validMoves.add(pieces[x][posY]);
				break;
			} else
				break;
		}
		for (int y = posY + 1; y < 8; y++) {
			if (pieces[posX][y].getName().equals("null")) {
				validMoves.add(pieces[posX][y]);
			} else if (pieces[posX][y].isWhite() != this.isWhite()) {
				validMoves.add(pieces[posX][y]);
				break;
			} else
				break;
		}
		for (int y = posY - 1; y >= 0; y--) {
			if (pieces[posX][y].getName().equals("null")) {
				validMoves.add(pieces[posX][y]);
			} else if (pieces[posX][y].isWhite() != this.isWhite()) {
				validMoves.add(pieces[posX][y]);
				break;
			} else
				break;
		}
		return validMoves;
	}

}
