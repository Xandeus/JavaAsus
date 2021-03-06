package hexagon_world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fractals.Fractals;
import fractals.Shape;


public class GUIMain extends JPanel {
	HandlerClass handler = new HandlerClass();
	WorldGenerator n = new WorldGenerator(null, 3f, 10, 10);
    float[][] noise = n.getNoise();
    Tile[][] tiles = n.toTiles();
    int offsetX = 0;
    int offsetY = 0;
	static JFrame frame = new JFrame("Hexagon");
	public static void main(String[] args) throws InterruptedException {
		frame();
	}
	public static void frame() throws InterruptedException {
		GUIMain game = new GUIMain();
		frame.add(game);
		frame.setResizable(true);
		frame.setSize(1500, 900);
		frame.setLocation(300, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		frame.addKeyListener(game.handler);
		
	}
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		for(int x = 0; x<noise.length;x++){
//			for(int y = 0;y<noise[0].length;y++){
//				g.setColor(new Color(0,(int)(noise[x][y]+10)*5,0));
//				g.drawRect(x, y, 1, 1);
//			}
//		}
		for(int y = 0;y<tiles[0].length;y++){
			for(int x = 0;x<tiles.length;x++){
				if(y%2==0)
					g.drawImage(tiles[x][y].getImage(), (x*40)+20, (y*40)-10,40,40, null);
				else{
					g.drawImage(tiles[x][y].getImage(), (x*40), (y*40),40,40, null);
				}
			}
		}
	}
	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			
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
			if (keyCode == event.VK_C){
				n.initialise();
				repaint();
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
