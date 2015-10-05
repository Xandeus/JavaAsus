package Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameBoard extends JPanel {
	HandlerClass handler = new HandlerClass();
	int x = 0, y = 0;
	int width = 800;
	int height = 800;
	GamePiece[][] pieces = new GamePiece[8][8];
	GamePiece pSelected = null;
	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("CHESS");

	public static void frame() throws InterruptedException {
        
		GameBoard game = new GameBoard();
		Font f = new Font("Engravers MT", Font.BOLD, 23);
		text.setEditable(false);
		text.setBackground(Color.GRAY);
		text.setFont(f);
		text.setText("Under Construction");
		frame.add(text, BorderLayout.SOUTH);
		frame.add(game);
		frame.setResizable(false);
		frame.setSize(900, 870);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
	}

	public void fillBoard() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 2; y++) {
				pieces[x][y] = new Pawn(x * (width / 8), y * (height / 8), Color.RED,x,y);
			}
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
				if (p != null) {
					g.setColor(p.getColor());
					g.fillRect(p.getX(), p.getY(), 10, 10);
				}
			}
		}
		
		// Selection square
		g.setColor(Color.green);
		g.drawRect(x, y, (height / 8), (width / 8));
	}

	private class HandlerClass implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x1 = event.getX() - 5;
			int y1 = event.getY() - 32;
			GamePiece piece = getPiece(x1 - (x1 % (width / 8)), y1 - (y1 % (height / 8)));
			pSelected = piece;
			pSelected.avaliableMoves(pieces);
			if (x1 <= width && y1 <= height) {
				if (piece != null) {
					x = (x1 - (x1 % (width / 8)));
					y = (y1 - (y1 % (height / 8)));
				}

			}
			repaint();
		}

		private GamePiece getPiece(int x, int y) {
			// TODO Auto-generated method stub
			for (GamePiece[] gamePiece : pieces) {
				for (GamePiece p : gamePiece) {
					if (p != null && p.getX() == x && p.getY() == y) {
						return p;
					}
				}
			}
			return null;
		}

		public void mouseReleased(MouseEvent event) {

		}

		public void mouseEntered(MouseEvent event) {
			fillBoard();
			repaint();
		}

		public void mouseExited(MouseEvent event) {

		}

		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;

		}

		public void mouseMoved(MouseEvent event) {

		}
	}
}
