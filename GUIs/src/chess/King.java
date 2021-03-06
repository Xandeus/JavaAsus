package chess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class King extends GamePiece {
	ArrayList<GamePiece> validMoves;

	public King(int x, int y, boolean isWhite, int posX, int posY) {
		super(x, y, isWhite, posX, posY);
		try {
			if (isWhite())
				image = ImageIO.read(new File("res/whiteKing.png"));
			else
				image = ImageIO.read(new File("res/blackKing.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setImage(image);
		setName("King");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		validMoves = findValidMoves(pieces);
		ArrayList<GamePiece> otherMoves = new ArrayList<GamePiece>();
		for (GamePiece p : validMoves) {
			if (p == desiredMove) {
				for (GamePiece[] x : pieces)
					for (GamePiece y : x) {
						if (y != this && !y.getName().equals("null") && y.isWhite() != this.isWhite()) {
							if (y instanceof Pawn)
								otherMoves = ((Pawn) y).findValidAttacks(pieces);
							else
								otherMoves = y.findValidMoves(pieces);
							for (GamePiece move : otherMoves) {
								if (move == desiredMove)
									return false;
							}

						}
					}
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<GamePiece> findValidMoves(GamePiece[][] pieces) {
		validMoves = new ArrayList<GamePiece>();
		int posX = this.getPosX();
		int posY = this.getPosY();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				if (posX + x >= 0 && posX + x <= 7 && posY + y >= 0 && posY + y <= 7)
					validMoves.add(pieces[posX + x][posY + y]);
			}
		}
		return validMoves;
	}

}
