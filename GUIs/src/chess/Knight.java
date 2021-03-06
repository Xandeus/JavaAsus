package chess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight extends GamePiece {
	public Knight(int x, int y, boolean isWhite, int posX, int posY) {
		super(x, y, isWhite, posX, posY);
		// TODO Auto-generated constructor stub
		try {
			if (isWhite())
				image = ImageIO.read(new File(
						"res/whiteKnight.png"));
			else
				image = ImageIO.read(new File(
						"res/blackKnight.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setImage(image);
		setName("Knight");
	}

	ArrayList<GamePiece> validMoves;

	@Override
	public boolean isMoveValid(GamePiece desiredMove, GamePiece[][] pieces) {
		validMoves = findValidMoves(pieces);
		for (GamePiece p : validMoves) {
			if ((p.getPosX() == desiredMove.getPosX()) && (p.getPosY() == desiredMove.getPosY())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<GamePiece> findValidMoves(GamePiece[][] pieces) {
		// TODO Auto-generated method stub
		validMoves = new ArrayList<GamePiece>();
		int posX = this.getPosX();
		int posY = this.getPosY();
		if(posX<7){
			if(posX < 6){
				if(posY <7)
					validMoves.add(pieces[posX+2][posY+1]);
				if(posY >0)
					validMoves.add(pieces[posX+2][posY-1]);
			}
			if(posY < 6)
				validMoves.add(pieces[posX+1][posY+2]);
			if(posY > 1)
				validMoves.add(pieces[posX+1][posY-2]);
		}
		if(posX>0){
			if(posX >1){
				if(posY <7)
					validMoves.add(pieces[posX-2][posY+1]);
				if(posY >0)
					validMoves.add(pieces[posX-2][posY-1]);
			}
			if(posY < 6)
				validMoves.add(pieces[posX-1][posY+2]);
			if(posY > 1)
				validMoves.add(pieces[posX-1][posY-2]);
		}
			
		return validMoves;
	}
}
