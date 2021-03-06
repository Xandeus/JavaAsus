package cellularAutomata;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Water extends JPanel {
	HandlerClass handler = new HandlerClass();
	Cell[][] cells;
	ArrayList<Cell> aliveCells = new ArrayList<Cell>();
	ArrayList<Cell> deadCells = new ArrayList<Cell>();
	static boolean lClickD = false, rClickD = false, mClickD = false;
	static boolean gameStarted = false;
	static boolean pauseGame = false;
	static boolean done = false;
	static int clicks = 0;
	static int updateCount = 100000;
	int blockSize = 2;
	int bx;
	int by;
	boolean water = true;
	static int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	static int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	Random rand = new Random();
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		if (gameStarted) {
			for (Cell[] x : cells) {
				for (Cell c : x) {
					if (c.isAlive()) {
						g.setColor(c.getColor());
						g.fillRect(c.getX(), c.getY(), blockSize, blockSize);
					}
				}
			}
		}
	}

	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("Water");

	public static void frame() throws InterruptedException {
		Water game = new Water();
		Font f = new Font("Engravers MT", Font.BOLD, 23);
		text.setEditable(false);
		text.setBackground(Color.GRAY);
		text.setFont(f);
		frame.add(game);
		frame.setResizable(false);
		frame.setSize(width, height);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		frame.addKeyListener(game.handler);
		while (true) {
			game.repaint();
			if (gameStarted && !pauseGame) {
				game.checkCells();
				game.updateCells();
			}
			Thread.sleep(35);
		}
	}

	public void checkCells() {
		for (Iterator<Cell> iterator = aliveCells.iterator(); iterator.hasNext();) {
			Cell c = iterator.next();
	
			int t = c.getType();
			Cell[] n = getNeighbors(c.getPosX(), c.getPosY());
			if (c.isAlive()) {
				if (t == Cell.DIRT) {
				} else {
					deadCells.add(c);
					if (!n[0].isAlive()) {
						aliveCells.add(n[0]);
					} else if (c.getVel() == 0 && !n[3].isAlive() && !n[2].isAlive()) {
						if (rand.nextBoolean()) {
							n[3].setVel(c.getVel() - 1);
							aliveCells.add(n[3]);
						} else {
							n[3].setVel(c.getVel() + 1);
							aliveCells.add(n[2]);

						}
					} else if (!n[2].isAlive()) {
						n[2].setVel(c.getVel() - 1);
						aliveCells.add(n[2]);
					} else if (!n[3].isAlive()) {
						n[3].setVel(c.getVel() - 1);
						aliveCells.add(n[3]);
					} else {
						if (c.getVel() > 0) {
							n[2].setVel(c.getVel() - 1);
							aliveCells.add(n[2]);
						} else if (c.getVel() < 0) {
							n[3].setVel(c.getVel() + 1);
							aliveCells.add(n[3]);
						} else
							aliveCells.add(c);
					}

				}
			}
		}
	}

	public void updateCells() {
		for (Cell c : deadCells) {
			c.setAlive(false);
			c.setVel(0);
		}
		for (Cell c : aliveCells) {
			c.setAlive(true, c.getType());
		}
		aliveCells.clear();
		deadCells.clear();
	}

	public Cell[] getNeighbors(int px, int py) {
		Cell[] n = new Cell[4];
		// Down
		if (py + 1 < cells[0].length)
			n[0] = cells[px][py + 1];
		// Up
		if (py - 1 > 0)
			n[1] = cells[px][py - 1];
		// Right
		if (px + 1 < cells.length)
			n[2] = cells[px + 1][py];
		// Left
		if (px - 1 > 0)
			n[3] = cells[px - 1][py];

		return n;
	}

	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			bx = x - (x % blockSize);
			by = y - (y % blockSize);
			if (water)
				cells[bx / blockSize][by / blockSize].setAlive(true, Cell.WATER);
			else {
				cells[bx / blockSize][by / blockSize].setAlive(true, Cell.DIRT);

			}
			for(Cell[] cell :cells){
				for(Cell c : cell){
					if(c.isAlive())
						aliveCells.add(c);
				}
			}
		}

		public void mouseReleased(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
			}
			if (event.getButton() == MouseEvent.BUTTON3) {

			}
		}

		public void mouseEntered(MouseEvent event) {
			if (!gameStarted) {
				cells = new Cell[width / blockSize][height / blockSize];
				for (int x = 0; x < width / blockSize; x++) {
					for (int y = 0; y < height / blockSize; y++) {
						cells[x][y] = new Cell(x * blockSize, y * blockSize, x, y);
						if (x == 0 || y == 0 || x == (width / blockSize) - 1 || y == (height / blockSize) - 1) {
							cells[x][y].setType(Cell.DIRT);
							cells[x][y].setAlive(true);
						}
					}
				}
			}
			gameStarted = true;

		}

		public void mouseExited(MouseEvent event) {

		}

		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			bx = x - (x % blockSize);
			by = y - (y % blockSize);
			if (water)
				cells[bx / blockSize][by / blockSize].setAlive(true, Cell.WATER);
			else {
				cells[bx / blockSize][by / blockSize].setAlive(true, Cell.DIRT);

			}
		}

		public void mouseMoved(MouseEvent event) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();

			if (keyCode == e.VK_SPACE) {
				pauseGame = !pauseGame;
			}
			if (keyCode == e.VK_C) {
				for (Cell[] x : cells) {
					for (Cell c : x) {
						c.setAlive(false);
					}
				}
			}
			if (keyCode == e.VK_W) {
				water = !water;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}