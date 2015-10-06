package Chess;

import java.awt.Color;

public class EmptySpace extends GamePiece{

	public EmptySpace(int x, int y, Color color, int posX, int posY) {
		super(x, y, color, posX, posY);
		setName("null");
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		return false;
	}

}