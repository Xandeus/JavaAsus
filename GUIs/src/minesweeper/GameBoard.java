package minesweeper;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameBoard extends JPanel {
	HandlerClass handler = new HandlerClass();
	static Random rand = new Random();
	static Image flag, mine, space, emptySpace;
	static Image num1, num2, num3, num4;
	static int x,y;
	static int width = 800;
	static int height = 800;
	static int bWidth = width / 15;
	static int bHeight = height / 15;
	static int numMines = 10;
	ArrayList<Flag> flags = new ArrayList<Flag>();
	static GameObject[][] spaces = new GameObject[width/bWidth][height/bHeight];

	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	static JFrame frame = new JFrame("MINESWEEPER");

	public static void frame() throws InterruptedException {
		fillBoard();
		GameBoard game = new GameBoard();
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(900, 900);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);

		try {
			flag = ImageIO.read(new File("res/flag.png"));
			mine = ImageIO.read(new File("res/mine.png"));
			space = ImageIO.read(new File("res/button.png"));
			emptySpace = ImageIO.read(new File("res/emptySpace.png"));
			num1 = ImageIO.read(new File("res/number1.png"));
			num2 = ImageIO.read(new File("res/number2.png"));
			num3 = ImageIO.read(new File("res/number3.png"));
			num4 = ImageIO.read(new File("res/number4.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int x = 0; x < spaces.length; x++) {
			for (int y = 0; y < spaces[0].length; y ++) {
				Image img;
				GameObject obj = spaces[x][y];
				if (!obj.isHidden())
					if (obj.isMine())
						img = mine;
					else
						img = emptySpace;
				else
					img = space;
				g.drawImage(img, obj.getX(), obj.getY(), bWidth, bHeight, null);
			}
		}

		for (Flag f : flags)
			g.drawImage(flag, f.getX(), f.getY(), bWidth, bHeight, null);
	}

	public static void fillBoard() {
		for (int x = 0; x < spaces.length; x++) {
			for (int y = 0; y < spaces[0].length; y ++) {
				spaces[x][y] = new GameObject(x * bHeight, y * bWidth, false);
			}
		}
		addMines(numMines);
	}
	public static void addMines(int numMines){
		int totalMines = 0;
		int x = 0;
		int y = 0;
		while(totalMines < numMines){
			if(rand.nextInt(bWidth)==5){
				spaces[x][y] = new GameObject(x * bHeight, y * bWidth, true);
				totalMines++;
			}
			x++;
			if(x>=spaces.length)
				y++;
			if(x>= spaces.length)
				x=0;
			if(y>=spaces[0].length){
				y=0;
				System.out.println("Y reset");
			}
		}
	}
	private class HandlerClass implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x1 = event.getX() - 5;
			int y1 = event.getY() - 32;
			if (x1 < width && y1 < height) {
				x = (x1 - (x1 % (bWidth)));
				y = (y1 - (y1 % (bHeight)));
				if (SwingUtilities.isRightMouseButton(event)) {
					for (Flag f : flags)
						if (f.getX() == x && y == f.getY()) {
							flags.remove(f);
							repaint();
							return;
						}
					if(spaces[x/bWidth][y/bHeight].isHidden())
						flags.add(new Flag(x, y));
				}
				if (SwingUtilities.isLeftMouseButton(event)) {
					spaces[x/bWidth][y/bHeight].setHidden(false);
				}
			}
			
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
