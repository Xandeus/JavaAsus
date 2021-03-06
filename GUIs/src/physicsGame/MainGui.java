package physicsGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGui extends JPanel {
	HandlerClass handler = new HandlerClass();
	Player player = new Player(500, 600, 10, 10, Color.DARK_GRAY);
	ArrayList<Player> boxes = new ArrayList<Player>();
	int dir = 0;
	static int wHeight = 800;
	static int wWidth = 1500;
	static JFrame frame = new JFrame("**");
	static boolean keyDown = false;
	static boolean boxFall = true;
	static boolean highlightBox = false;
	static int bX,bY,bW,bH;
	static int dX,dY;
	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	public static void frame() throws InterruptedException {
		MainGui game = new MainGui();
		game.setBackground(new Color(135, 206, 255));
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		frame.add(game);
		frame.setResizable(true);
		frame.setSize(wWidth, wHeight);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		frame.addKeyListener(game.handler);
		while (true) {
			game.repaint();
			game.loop();
			Thread.sleep(1);
		}
	}

	public void loop() {
		checkCollisions();
		if (!player.isColliding()) {
			player.incY(.001f);
		}
		if (!keyDown) {
			if (player.getdX() > 0) {
				player.incX(-.001f);
			} else if (player.getdX() < 0) {
				player.incX(.001f);
			}
			if (Math.abs(player.getdX()) < .0001f) {
				player.setdX(0);
			}
		} else {
			if (Math.abs(player.getdX()) < .75)
				player.incX(.01f * dir);
		}
		player.updatePos();
		for (Player b : boxes) {
			if (boxFall && !b.isColliding()) {
				b.incY(.001f);
			}
			b.updatePos();
		}

	}

	public void checkCollisions() {
		if (player.getdY() >= 0 && player.getY() >= (700 - player.getHeight())) {
			player.setColliding(true);
			player.setdY(0);
		} else {
			player.setColliding(false);
		}
		if (player.getX() <= 0) {
			player.setdX(0);
		}
		if (player.getX() + player.getWidth() >= wWidth - 20) {
			player.setdX(0);
		}
		for (Player b : boxes) {
			if (b.getX() <= 0) {
				b.setdX(0);
			}
			if (b.getX() + b.getWidth() >= wWidth - 20) {
				b.setdX(0);
			}
			if (player.getdY() >= 0 && player.getY() + player.getHeight() > b.getY() && player.getY() < b.getY() + b.getHeight()) {
				if (player.getX() + player.getWidth() > b.getX() && player.getX() < b.getX() + b.getWidth()) {
						player.setColliding(true);
						player.setdY(0);
						if(boxFall && Math.abs(player.getdX())>0){
							b.setdX(player.getdX());
						}
				}
			}
			if (b.getdY() >= 0 && b.getY() >= (700 - b.getHeight())) {
				b.setColliding(true);
				b.setdY(0);
			} 
			else  if (b.getY() + b.getHeight() >= player.getY() && b.getX() + b.getWidth() > player.getX()
					&& b.getX() < player.getX() + player.getWidth()) {
				if (b.getY() <= player.getY() + player.getHeight()) {
					b.setColliding(true);
					b.setdY(0);
				}
			}else {
				b.setColliding(false);
			}
			for (Player x : boxes) {
				if (x != b) {
					if (b.getY() + b.getHeight() >= x.getY() && b.getX() + b.getWidth() > x.getX()
							&& b.getX() < x.getX() + x.getWidth()) {
						if (b.getY() <= x.getY() + x.getHeight()) {
							b.setColliding(true);
							b.setdY(0);
						}
					}
				}
			}
		}

	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);
		g.fillRect(0, 700, wWidth, wHeight);
		g.setColor(player.getColor());
		g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		for (Player b : boxes) {
			g.setColor(b.getColor());
			g.drawImage(b.getImage(), b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);
		}
		if(highlightBox){
			g.setColor(Color.green);
			g.drawRect(bX, bY, bW, bH);
		}
	}

	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {
		public void mouseClicked(MouseEvent event) {
			int x = event.getX()-10;
			int y = event.getY()-38;
			boxes.add(new Player(x, y, 20, (int) (Math.random() * 21) + 5));
		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX()-10;
			int y = event.getY()-38;
			bX = x;
			bY = y;
		}

		public void mouseReleased(MouseEvent event) {
			if(highlightBox){
				boxes.add(new Player(bX,bY,bW,bH));
				highlightBox = false;
			}
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {

		}

		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {
			int x = event.getX()-10;
			int y = event.getY()-38;
			highlightBox = true;
			bW = Math.abs(x-bX);
			bH = Math.abs(y-bY);
			
		}

		public void mouseMoved(MouseEvent event) {

		}

		@Override
		public void keyPressed(KeyEvent event) {
			// TODO Auto-generated method stub
			int keyCode = event.getKeyCode();
			if (keyCode == event.VK_A) {
				dir = -1;
				keyDown = true;
			}
			if (keyCode == event.VK_D) {
				dir = 1;
				keyDown = true;
			}

			if (keyCode == event.VK_SPACE) {
				player.setdY(-.5f);
				
			}
			if (keyCode == event.VK_Q) {
				boxFall = !boxFall;
			}
			if (keyCode == event.VK_C) {
				boxes.clear();
			}
			if (keyCode == event.VK_SHIFT) {
				for(Player b : boxes){
					b.setdY(-.5f);
					if(b.getX() > player.getX()){
						b.setdX(2f);
					}
					else{
						b.setdX(-2f);
					}
				}
			}

		}

		@Override
		public void keyReleased(KeyEvent event) {
			// TODO Auto-generated method stub
			int keyCode = event.getKeyCode();

			if(keyCode != event.VK_SPACE)
				keyDown = false;
		}

		@Override
		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub

		}
	}
}
