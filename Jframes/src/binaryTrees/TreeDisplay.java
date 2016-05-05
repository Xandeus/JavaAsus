package binaryTrees;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TreeDisplay extends JPanel {
	HandlerClass handler = new HandlerClass();
	ArrayList<TreeNode> t;
	ArrayList<Point> p;

	static boolean lClickD = false, rClickD = false, mClickD = false;
	static int clicks = 0;
	BinarySearchTree tree = new BinarySearchTree();
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		int width = 1000;
		int height = 800;
		if(t!=null){
			for(int i = 0; i<t.size();i++){
				g.drawOval(p.get(i).x,p.get(i).y,50,50);
				g.drawString(""+t.get(i).getValue(),p.get(i).x+23,p.get(i).y+23);

			}
		}
	}
	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}
	public void incrementXPoints(int val){
		for(Point point: p){
			p.set(p.indexOf(point), new Point(point.x+val,point.y));
		}
	}
	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("Binary Tree");

	public static void frame() throws InterruptedException {
		TreeDisplay game = new TreeDisplay();
		frame.add(game);
		frame.setResizable(false);
		frame.setSize(1000, 800);
		frame.setLocation(300, 10);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);

		while (true) {
			game.repaint();
			Thread.sleep(25);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		frame();

	}
//	public void sortArray(){
//		while
//	}
	
	private class HandlerClass implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			if (event.getButton() == MouseEvent.BUTTON1) {

			}
			if (event.getButton() == MouseEvent.BUTTON3) {

			}
			if (event.getButton() == MouseEvent.BUTTON2) {

			}

		}

		public void mouseReleased(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
			}
			if (event.getButton() == MouseEvent.BUTTON3) {

			}
		}

		public void mouseEntered(MouseEvent event) {
			tree.add(100);
			tree.add(20);
			tree.add(15);
			tree.add(40);
			tree.add(5);
			tree.add(220);
			tree.add(35);

//			tree.add(22);
//			tree.add(17);
//			tree.add(1);
			t = tree.toArray();
			p = tree.toPointArray();
			incrementXPoints(500);
		}

		public void mouseExited(MouseEvent event) {

		}

		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			if (lClickD) {
			} else if (rClickD) {
			} else if (mClickD) {
			}
		}

		public void mouseMoved(MouseEvent event) {

		}
	}

}

