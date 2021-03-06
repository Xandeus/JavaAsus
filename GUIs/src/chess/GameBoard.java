package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameBoard extends JPanel {
	HandlerClass handler = new HandlerClass();
	int x = 0, y = 0;
	int width = 800;
	int height = 800;
	int numMoves = 0;
	static boolean gameStarted = false;
	static boolean aiActive = false;
	boolean whiteTurn;
	static boolean gameOver;
	GamePiece[][] pieces;
	ArrayList<GamePiece> whitePieces = new ArrayList<GamePiece>();
	ArrayList<GamePiece> blackPieces = new ArrayList<GamePiece>();
	ArrayList<GamePiece> moves = new ArrayList<GamePiece>();

	GamePiece whiteKing;
	GamePiece blackKing;
	GamePiece pSelected = null;

	Random random = new Random();

	static JTextField text = new JTextField();
	static JTextArea gameLog = new JTextArea();
	static JScrollPane scrollPane = new JScrollPane(gameLog);
	static JFrame frame = new JFrame("CHESS");
	static final int GAMELOG_WINDOW_LIMIT = 10;

	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	public static void frame() throws InterruptedException {

		GameBoard game = new GameBoard();
		Font logFont = new Font("Engravers MT", Font.PLAIN, 12);
		Font turnFont = new Font("Engravers MT", Font.BOLD, 24);
		text.setEditable(false);
		text.setBackground(Color.WHITE);
		text.setFont(turnFont);
		text.setText("White Move");
		gameLog.setEditable(false);
		gameLog.setBackground(Color.WHITE);
		gameLog.setFont(logFont);
		scrollPane.setPreferredSize(new Dimension(300, 10));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.add(text, BorderLayout.SOUTH);
		frame.add(scrollPane, BorderLayout.EAST);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(1100, 870);
		frame.setLocation(400, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		while (true) {
			if (gameOver) {
				game.start();
				Thread.sleep(1000);

			} else {
				if (!gameStarted) {
					game.start();
				}
				if (aiActive) {
					game.AI();
				}
				Thread.sleep(100);
			}
		}

	}

	public void start() {
		pieces = new GamePiece[8][8];
		whiteTurn = true;
		gameOver = false;
		fillBoard();
		gameStarted = true;
		repaint();
	}

	public void AI() {
		if (whiteTurn) {
			text.setText("White Move Number of moves: " + numMoves);
			ArrayList<GamePiece> temp1;
			do {
				pSelected = whitePieces.get(random.nextInt(whitePieces.size()));
				temp1 = pSelected.findValidMoves(pieces);
			} while (temp1.size() == 0);
			makeMove(temp1.get(random.nextInt(temp1.size())));
		} else {
			text.setText("Black Move Number of moves: " + numMoves);
			ArrayList<GamePiece> temp;
			do {
				pSelected = blackPieces.get(random.nextInt(blackPieces.size()));
				temp = pSelected.findValidMoves(pieces);
			} while (temp.size() == 0);
			makeMove(temp.get(random.nextInt(temp.size())));

		}
	}

	public void fillBoard() {
		// Fill board with empty spaces
		for (int x = 0; x < pieces.length; x++) {
			for (int y = 0; y < pieces[0].length; y++) {
				pieces[x][y] = new EmptySpace(x * (width / 8), y * (height / 8), x, y);
			}
		}
		// Draw white side
		for (int x = 0; x < pieces.length; x++) {
			for (int y = 6; y <= 7; y++) {
				if (y == 6) {
					pieces[x][y] = new Pawn(x * (width / 8), y * (height / 8), true, x, y);
				} else if (y == 7 && (x == 0 || x == 7)) {
					pieces[x][y] = new Rook(x * (width / 8), y * (height / 8), true, x, y);
				} else if (y == 7 && (x == 1 || x == 6)) {
					pieces[x][y] = new Knight(x * (width / 8), y * (height / 8), true, x, y);
				} else if (y == 7 && (x == 2 || x == 5)) {
					pieces[x][y] = new Bishop(x * (width / 8), y * (height / 8), true, x, y);
				} else if (y == 7 && x == 3) {
					pieces[x][y] = new Queen(x * (width / 8), y * (height / 8), true, x, y);
				} else if (y == 7 && x == 4) {
					pieces[x][y] = new King(x * (width / 8), y * (height / 8), true, x, y);
				}
			}
		}
		// Draw Black side
		for (int x = 0; x < pieces.length; x++) {
			for (int y = 0; y <= 1; y++) {
				if (y == 1) {
					pieces[x][y] = new Pawn(x * (width / 8), y * (height / 8), false, x, y);
				} else if (y == 0 && (x == 0 || x == 7)) {
					pieces[x][y] = new Rook(x * (width / 8), y * (height / 8), false, x, y);
				} else if (y == 0 && (x == 1 || x == 6)) {
					pieces[x][y] = new Knight(x * (width / 8), y * (height / 8), false, x, y);
				} else if (y == 0 && (x == 2 || x == 5)) {
					pieces[x][y] = new Bishop(x * (width / 8), y * (height / 8), false, x, y);
				} else if (y == 0 && x == 3) {
					pieces[x][y] = new Queen(x * (width / 8), y * (height / 8), false, x, y);
				} else if (y == 0 && x == 4) {
					pieces[x][y] = new King(x * (width / 8), y * (height / 8), false, x, y);
				}
			}
		}
		for (GamePiece[] gamePiece : pieces) {
			for (GamePiece p : gamePiece) {
				if (p.isWhite) {
					whitePieces.add(p);
				} else if (!p.isWhite && !p.getName().equals("null")) {
					blackPieces.add(p);
				}
			}
		}
	}

	public void checkMoves() {
		moves.clear();
		for (GamePiece[] gamePiece : pieces) {
			for (GamePiece p : gamePiece) {
				if (!p.getName().equals("null") && (p.isWhite() == whiteTurn)) {
					for (GamePiece move : p.findValidMoves(pieces)) {
						if (p.isMoveValid(move, pieces) && isMoveValid(move, p)) {
							if (move.getName().equals("null") || (move.isWhite() != p.isWhite()))
								moves.add(move);
						}
					}
				}
			}
		}
		if (moves.size() == 0 || (blackPieces.size() == 1 || whitePieces.size() == 1)) {
			gameLog.append("Game Over");
			gameOver = true;
		}
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		boolean colorWhite = true;
		for (int y = 0; y < height; y += (height / 8)) {
			for (int x = 0; x < width; x += (width / 8)) {
				if (colorWhite) {
					g2d.setColor(Color.WHITE);
					colorWhite = !colorWhite;
				} else {
					g2d.setColor(Color.BLACK);
					colorWhite = !colorWhite;
				}
				g.fillRect(x, y, width / 8, height / 8);
			}
			colorWhite = !colorWhite;
		}
		for (GamePiece[] gamePiece : pieces) {
			for (GamePiece p : gamePiece) {
				if (p != null && !p.getName().equals("null")) {
					if (p.getName().equals("King")) {
						if (p.isWhite) {
							whiteKing = p;
						} else {
							blackKing = p;
						}
					}
					g.drawImage(p.getImage(), p.getPosX() * (width / 8), p.getPosY() * (height / 8), width / 8,
							height / 8, null);
				}
			}
		}
		// Selection square
		g.setColor(Color.green);
		if (pSelected != null)
			g.drawRect(x, y, (height / 8), (width / 8));
		if (gameOver) {
			g.drawString("GAME OVER!", 200, 200);
		}
	}

	public boolean isMoveValid(GamePiece desiredMove, GamePiece pSelected) {
		GamePiece start = desiredMove;
		GamePiece temp = new EmptySpace(pSelected.getPosX() * (width / 8), pSelected.getPosY() * (height / 8),
				pSelected.getPosX(), pSelected.getPosY());
		pieces[pSelected.getPosX()][pSelected.getPosY()] = temp;
		pSelected.setPosX(desiredMove.getPosX());
		pSelected.setPosY(desiredMove.getPosY());
		pieces[desiredMove.getPosX()][desiredMove.getPosY()] = pSelected;
		if (whiteTurn) {
			for (GamePiece[] gamePiece : pieces) {
				for (GamePiece p : gamePiece) {
					if (p != null && !p.getName().equals("null") && !p.isWhite()) {
						for (GamePiece move : p.findValidMoves(pieces)) {
							if (move == whiteKing) {
								pieces[desiredMove.getPosX()][desiredMove.getPosY()] = start;
								pSelected.setPosX(temp.getPosX());
								pSelected.setPosY(temp.getPosY());
								pieces[temp.getPosX()][temp.getPosY()] = pSelected;
								return false;
							}
						}
					}
				}
			}
			if ((!desiredMove.getName().equals("null")) && (desiredMove.isWhite() == pSelected.isWhite())) {
				pieces[desiredMove.getPosX()][desiredMove.getPosY()] = start;
				pSelected.setPosX(temp.getPosX());
				pSelected.setPosY(temp.getPosY());
				pieces[temp.getPosX()][temp.getPosY()] = pSelected;
				return false;
			}
		} else {
			for (GamePiece[] gamePiece : pieces) {
				for (GamePiece p : gamePiece) {
					if (p != null && !p.getName().equals("null") && p.isWhite) {
						for (GamePiece move : p.findValidMoves(pieces)) {
							if (move == blackKing) {
								pieces[desiredMove.getPosX()][desiredMove.getPosY()] = start;
								pSelected.setPosX(temp.getPosX());
								pSelected.setPosY(temp.getPosY());
								pieces[temp.getPosX()][temp.getPosY()] = pSelected;
								return false;
							}
						}
					}
				}
			}
			if ((!desiredMove.getName().equals("null")) && (desiredMove.isWhite() == pSelected.isWhite())) {
				pieces[desiredMove.getPosX()][desiredMove.getPosY()] = start;
				pSelected.setPosX(temp.getPosX());
				pSelected.setPosY(temp.getPosY());
				pieces[temp.getPosX()][temp.getPosY()] = pSelected;
				return false;
			}
		}
		pieces[desiredMove.getPosX()][desiredMove.getPosY()] = start;
		pSelected.setPosX(temp.getPosX());
		pSelected.setPosY(temp.getPosY());
		pieces[temp.getPosX()][temp.getPosY()] = pSelected;
		return true;
	}

	public String getColor(boolean color) {
		if (color)
			return "White ";
		else
			return "Black ";
	}

	public GamePiece getArrayPos(int x, int y) {
		// TODO Auto-generated method stub
		for (GamePiece[] gamePiece : pieces) {
			for (GamePiece p : gamePiece) {
				if (p.getPosX() * (width / 8) == x && p.getPosY() * (height / 8) == y) {
					return p;
				}
			}
		}
		return null;
	}

	public boolean makeMove(GamePiece desiredMove) {
		if (pSelected.isMoveValid(desiredMove, pieces) && isMoveValid(desiredMove, pSelected)) {
			pSelected.movePiece(pSelected, pieces, width, height, desiredMove);
			if (desiredMove.getName().equals("null"))
				gameLog.append(getColor(whiteTurn) + pSelected.getName() + " moved to " + desiredMove.getPosX() + " "
						+ desiredMove.getPosY() + "\n");

			else
				gameLog.append(getColor(pSelected.isWhite()) + pSelected.getName() + " captured "
						+ getColor(desiredMove.isWhite()) + desiredMove.getName() + " at " + desiredMove.getPosX() + " "
						+ desiredMove.getPosY() + "\n");
			pSelected = null;
			whiteTurn = !whiteTurn;
			repaint();
		}
		checkMoves();
		if (pSelected == null) {
			if (blackPieces.contains(desiredMove)) {
				blackPieces.remove(desiredMove);
			} else if (whitePieces.contains(desiredMove)) {
				whitePieces.remove(desiredMove);
			}
			numMoves++;
			return true;
		}
		return false;
	}

	private class HandlerClass implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON3) {
				aiActive = !aiActive;
			}
		}

		public void mousePressed(MouseEvent event) {
			int x1 = event.getX() - 5;
			int y1 = event.getY() - 32;
			GamePiece piece = getArrayPos(x1 - (x1 % (width / 8)), y1 - (y1 % (height / 8)));
			if (!piece.getName().equals("null") && whiteTurn == piece.isWhite()) {
				pSelected = piece;
			}
			if (x1 <= width && y1 <= height && piece != null && pSelected != null) {
				if (!pSelected.getName().equals("null") && !piece.getName().equals("null")
						&& (pSelected.isWhite() == piece.isWhite())) {
					// Move position of highlight box
					x = (x1 - (x1 % (width / 8)));
					y = (y1 - (y1 % (height / 8)));
				} else {
					GamePiece desiredMove = getArrayPos(x1 - (x1 % (width / 8)), y1 - (y1 % (height / 8)));
					makeMove(desiredMove);
				}
			}
			repaint();

		}

		public void mouseReleased(MouseEvent event) {

		}

		public void mouseEntered(MouseEvent event) {

		}

		public void mouseExited(MouseEvent event) {

		}

		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {

		}

		public void mouseMoved(MouseEvent event) {

		}
	}
}
