package physicsGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	Player player = new Player(500, 600, 10, 10);
	ArrayList<Player> boxes = new ArrayList<Player>();
	int dir = 0;
	static int wHeight = 800;
	static int wWidth = 1500;
	static JFrame frame = new JFrame("**");
	static boolean keyDown = false;

	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	public static void frame() throws InterruptedException {
		MainGui game = new MainGui();
		frame.add(game);
		frame.setResizable(true);
		frame.setSize(wWidth, wHeight);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
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
		}
		else{
			if(Math.abs(player.getdX()) < 1)
				player.incX(.01f * dir);
		}
		player.updatePos();
	}

	public void checkCollisions() {
		if (player.getdY() > 0 && player.getY() >= (700 - player.getHeight())) {
			player.setColliding(true);
			player.setdY(0);
		} 
		else {
			player.setColliding(false);
		}
		if(player.getX() < 0){
			player.setdX(0);
		}
		if(player.getX()+player.getWidth() > wWidth-20){
			player.setdX(0);
		}
		
		
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);
		g.fillRect(0, 700, wWidth, wHeight);
		g.setColor(Color.red);
		g.drawRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	}

	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			boxes.add(new Player(x,y,10,10));
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
			if (keyCode == event.VK_C) {
				player.setY(0);
			}

		}

		@Override
		public void keyReleased(KeyEvent event) {
			// TODO Auto-generated method stub
			keyDown = false;
		}

		@Override
		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub

		}
	}
}