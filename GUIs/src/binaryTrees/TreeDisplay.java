package binaryTrees;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;

public class TreeDisplay extends JPanel {
	static private JTextField userText;
	static private JTextArea chatWindow;

	HandlerClass handler = new HandlerClass();

	static ArrayList<TreeNode> t;
	static ArrayList<TreeNode> inOrder;
	static ArrayList<Point> p;
	static ArrayList<Point> pOrder;

	static int orderCount = 0;
	static Point px;
	static boolean lClickD = false, rClickD = false, mClickD = false;
	static boolean iO = false;
	static int clicks = 0;
	static BinarySearchTree tree = new BinarySearchTree();

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		int width = 1000;
		int height = 800;
		if (t == null) {
			for(int i = 0; i<25;i++){
				tree.add((int)(Math.random()*1000));
			}
			updateTree();
		} else {
			for (int i = 0; i < t.size(); i++) {
				g.setColor(Color.black);
				g.drawOval(p.get(i).x, p.get(i).y, 700/t.size(), 700/t.size());
				//g.drawString("" + t.get(i).getValue(), p.get(i).x + ((500/t.size())/2), p.get(i).y + ((500/t.size())/2));
			}
			if(iO){
				g.drawString("Displaying: InOrder", 0, 100);
				g.setColor(Color.green);
				g.drawOval(p.get(t.indexOf(inOrder.get(orderCount))).x, p.get(t.indexOf(inOrder.get(orderCount))).y, 700/t.size(), 700/t.size());
				orderCount++;
				if(orderCount>=t.size()){
					orderCount = 0;
				}
			}
		}
	}
	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	public static void incrementXPoints(int val) {
		for (Point point : p) {
			p.set(p.indexOf(point), new Point(point.x + val, point.y));
		}
	}

	public static void updateTree() {
		t = tree.toArray();
		p = tree.toPointArray(5000/t.size(),1000/t.size());
		inOrder = tree.inOrder();
		incrementXPoints(500);
		checkCollisions();
		frame.repaint();
	}

	public static void addToTree(int val) {
		tree.add(val);
		updateTree();
	}
	
	public static void checkCollisions(){
		Set<Point> s = new HashSet<Point>();
		for(Point x : p){
			if(!s.add(x)){
				x.translate(10, 10);
			}
		}
	}
	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("Binary Tree");

	public static void frame() throws InterruptedException {
		TreeDisplay game = new TreeDisplay();
		userText = new JTextField();
		userText.setEditable(true);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addToTree((Integer.parseInt(event.getActionCommand())));
				userText.setText("");
			}
		});
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
			Thread.sleep(750);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		frame();

	}

	private class HandlerClass implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent event) {

		}

		public void mousePressed(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			if (event.getButton() == MouseEvent.BUTTON1) {
				iO = !iO;
				System.out.println("sdf");
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