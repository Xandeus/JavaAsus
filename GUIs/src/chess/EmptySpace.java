package chess;

import java.awt.Color;
import java.util.ArrayList;

public class EmptySpace extends GamePiece{

	public EmptySpace(int x, int y, int posX, int posY) {
		super(x, y, posX, posY);
		setName("null");
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<GamePiece> findValidMoves(GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		return null;
	}

}
