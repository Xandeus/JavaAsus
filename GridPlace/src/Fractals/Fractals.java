package Fractals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fractals extends JPanel {
	Random rand = new Random();
	HandlerClass handler = new HandlerClass();
	static JFrame frame = new JFrame("Fractals");
	int count = 0;
	int xVariance = 0, yVariance = 0;
	int rLimit = 90;
	int dX2 = 5;
	int dY2 = 5;
	int numBranches = 2;
	int size = 100;
	int incVal = 1;
    float theta=0;

	boolean zoom = false;
	boolean rave = false;
	boolean forceMaxB = false;
	boolean isFall = false;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Font f = new Font("Engravers MT", Font.BOLD, 12);

	public static void frame() throws InterruptedException {
		Fractals game = new Fractals();
		frame.add(game);
		frame.setResizable(false);
		frame.setSize(1500, 900);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		frame.addKeyListener(game.handler);

		while (true) {
			game.repaint();
			Thread.sleep(1);
		}
	}

	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		g.fillRect(0, 0, 1500, 900);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString("Size: " + size, 0, 10);
		g.drawString("X Variance: " + xVariance, 0, 20);
		g.drawString("Y Variance: " + yVariance, 0, 30);
		g.drawString("Rotation Limit: " + rLimit, 0, 40);
		g.drawString("Number of allowed Branches: " + numBranches, 0, 50);
		g.drawString("Maximize Branches: " + forceMaxB, 0, 60);
		g.drawString("Is fall: " + isFall, 0, 70);

		if (count < shapes.size() && shapes.size() > 0) {
			shapes.get(count).changeState();
			count++;
		}
		for (Shape c : shapes) {
			if (c.isActive) {
				if (rave) {
					c.setColor(randColor());
					g.setColor(randColor());
				} else
					g.setColor(c.getColor());

				// g.drawLine(c.getX(), c.getY(), c.getX(), c.getY()-c.getR());
				// g.drawLine(c.getX(), c.getY()-c.getR(), c.getX()-c.getR(),
				// c.getY()-2*c.getR());
				// g.drawLine(c.getX(), c.getY()-c.getR(), c.getX()-c.getR(),
				// c.getY()-2*c.getR());
				// Draw triangles
				// g.drawLine(c.getX(), c.getY(), c.getX()+c.getR(), c.getY());
				// g.drawLine(c.getX(), c.getY(), c.getX()+c.getR()/2,
				// c.getY()-c.getR());
				// g.drawLine(c.getX()+c.getR()/2, c.getY()-c.getR(),
				// c.getX()+c.getR(), c.getY());
				// draw
				// g.drawOval(c.getX(), c.getY(), c.getR(), c.getR());
				g.drawLine(c.getX(), c.getY(), c.getX() + c.getDX(), c.getY() - c.getDY());
				if (zoom && c.getDY() <= 10) {
					// c.change(rand.nextInt(2) + 1, rand.nextInt(5) - 2, 0);
					c.alter(rand.nextInt(3), 0);
				}
			}
		}

	}

	public int getDistance(int x, int y, int x1, int y1) {
		int distance = 0;
		distance = (int) (Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)));
		return distance;
	}

	// v is variance
	public void drawLine(int x, int y, int dX, int dY, int xV, int yV) {
		Color color;
		int t;
		if (getDistance(x,y,x+dX,y-dY) > 10)
			color = new Color(165 + (rand.nextInt(21) - 10), 42 + (rand.nextInt(21) - 10), 0);
		else if (isFall)
			color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), 0);
		else
			color = new Color(0, (int) (Math.random() * 255), 0);
		shapes.add(new Shape(x, y, dX, dY, color));
		if (getDistance(x, y, x + dX, y - dY) > 2) {
			int totalBranches;
			if (forceMaxB)
				totalBranches = numBranches;
			else
				totalBranches = rand.nextInt(numBranches) + 1;
			t = rand.nextInt(11)-5;
			dX2 = ((int)(dX*Math.cos(t*Math.PI/180)))-((int)(dY * Math.sin(t*Math.PI/180)));
			dY2 = ((int)(dX*Math.sin(t*Math.PI/180)))+((int)(dY * Math.cos(t*Math.PI/180))); 
			dX2/=2f-(xV*.1f);
			dY2/=2f-(yV*.1f);
			drawLine(x + dX, y - dY, dX2, dY2, xV,
					yV);
			for (int i = 0; i < totalBranches; i++){
				//System.out.println("DY2: " + dY2);
				t = (rand.nextInt(rLimit*2+1)-rLimit);
				dX2 = ((int)(dX*Math.cos(t*Math.PI/180)))-((int)(dY * Math.sin(t*Math.PI/180)));
				dY2 = ((int)(dX*Math.sin(t*Math.PI/180)))+((int)(dY * Math.cos(t*Math.PI/180))); 
				dX2/=2;
				dY2/=2;
				drawLine(x + dX, y - dY, dX2, dY2, xV,
						yV);
			}
			

		}
	}

	public void drawShape(int x, int y, int r) {
		shapes.add(new Shape(x, y, r));
		if (r > 10) {

			// Pascal's triangle
			// drawShape(x,y,r/2);
			// drawShape(x+r/2,y,r/2);
			// drawShape(x+r/4,y-r/2,r/2);

			// Squares
			drawShape(x - r / 2, y + r / 4, r / 2);
			drawShape(x + r / 4, y - r / 2, r / 2);

			drawShape(x + r, y + r / 4, r / 2);
			drawShape(x + r / 4, y + r, r / 2);
		}

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
			if (event.getButton() == MouseEvent.BUTTON1) {
				drawLine(x, y, 0, size, xVariance, yVariance);
			}
			if (event.getButton() == MouseEvent.BUTTON2) {
				System.out.println(getDistance(5, 5, 0, 10));
			}

		}

		public void mouseReleased(MouseEvent event) {

		}

		public void mouseEntered(MouseEvent event) {

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

		@Override
		public void keyPressed(KeyEvent event) {
			// TODO Auto-generated method stub
			int keyCode = event.getKeyCode();
			if (keyCode == event.VK_C) {
				count = 0;
				shapes.clear();
			}
			if (keyCode == event.VK_X) {
				rave = !rave;
			}
			if (keyCode == event.VK_Z) {
				zoom = !zoom;
			}
			if (keyCode == event.VK_Q) {
				size += incVal;
			}
			if (keyCode == event.VK_A) {
				size -= incVal;
			}
			if (keyCode == event.VK_W) {
				xVariance += incVal;
			}
			if (keyCode == event.VK_S) {
				xVariance -= incVal;
			}
			if (keyCode == event.VK_E) {
				yVariance += incVal;
			}
			if (keyCode == event.VK_D) {
				yVariance -= incVal;
			}
			if (keyCode == event.VK_UP) {
				rLimit += incVal;
			}
			if (keyCode == event.VK_DOWN) {
				rLimit -= incVal;
			}
			if (keyCode == event.VK_R) {
				if (numBranches < 10)
					numBranches += incVal;
			}
			if (keyCode == event.VK_F) {
				if (numBranches > 1)
					numBranches -= incVal;
			}
			if (keyCode == event.VK_B) {
				forceMaxB = !forceMaxB;
			}
			if (keyCode == event.VK_N) {
				isFall = !isFall;
			}
		}

		@Override
		public void keyReleased(KeyEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub

		}
	}
}
