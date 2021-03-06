package chess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bishop extends GamePiece {
	ArrayList<GamePiece> validMoves;

	public Bishop(int x, int y, boolean isWhite, int posX, int posY) {
		super(x, y, isWhite, posX, posY);
		// TODO Auto-generated constructor stub
		try {
			if (isWhite())
				image = ImageIO.read(new File(
						"res/whiteBishop.png"));
			else
				image = ImageIO.read(new File(
						"res/blackBishop.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setImage(image);
		setName("Bishop");
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
		int posX = this.getPosX();
		int posY = this.getPosY();
		validMoves = new ArrayList<GamePiece>();
		// Right and Up
		for (int i = 1; i < 8 - posX; i++) {
			if (posY-i >= 0)
				if (pieces[posX + i][posY - i].getName().equals("null")) {
					validMoves.add(pieces[posX + i][posY - i]);
				} else if (pieces[posX + i][posY - i].isWhite() != this.isWhite()) {
					validMoves.add(pieces[posX + i][posY - i]);
					break;
				} else
					break;
		}
		// Left and Up
		for (int i = 1; i <= posX; i++) {
			if (posY-i >= 0)
				if (pieces[posX - i][posY - i].getName().equals("null")) {
					validMoves.add(pieces[posX - i][posY - i]);
				} else if (pieces[posX - i][posY - i].isWhite() != this.isWhite()) {
					validMoves.add(pieces[posX - i][posY - i]);
					break;
				} else
					break;
		}
		// Right and Down
		for (int i = 1; i < 8 - posX; i++) {
			if (posY+i <= 7)
				if (pieces[posX + i][posY + i].getName().equals("null")) {
					validMoves.add(pieces[posX + i][posY + i]);
				} else if (pieces[posX + i][posY + i].isWhite() != this.isWhite()) {
					validMoves.add(pieces[posX + i][posY + i]);
					break;
				} else
					break;
		}
		// Left and Down
		for (int i = 1; i <= posX; i++) {
			if (posY+i <= 7)
				if (pieces[posX - i][posY + i].getName().equals("null")) {
					validMoves.add(pieces[posX - i][posY + i]);
				} else if (pieces[posX - i][posY + i].isWhite() != this.isWhite()) {
					validMoves.add(pieces[posX - i][posY + i]);
					break;
				} else
					break;
		}

		return validMoves;
	}
}
