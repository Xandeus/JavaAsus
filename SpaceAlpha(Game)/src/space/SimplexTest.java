package space;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SimplexTest extends JPanel {
	HandlerClass handler = new HandlerClass();
	TextHandler txt = new TextHandler();
	static boolean lClickD = false, rClickD = false, mClickD = false;
	static int clicks = 0;
	static int count = 0;
	static int octaves = 1;
	static double persistence = 1, amplitude = 1, frequency = 1;
	static String current;
	int mouseX, mouseY;
	private Ellipse2D ellipse = new Ellipse2D.Float();
	ArrayList<Line> lines = new ArrayList();
	//private Ellipse2D ellipse2 = new Ellipse2D.Float();

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		
		for(Line l : lines){
			if(l.y > 500)
				g2d.setColor(new Color(165,42,42));
			else{
				g2d.setColor(Color.green);
			}
			g2d.drawLine((l.x)-(count),l.y,l.x1-(count),l.y1);
		}
	}
	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	static JFrame frame = new JFrame("Simplex Test");

	static JTextField textField = new JTextField();

	public static void frame() throws InterruptedException {
		SimplexTest game = new SimplexTest();
		// Create the radio buttons.
		JRadioButton octavesButton = new JRadioButton("Octaves " + octaves);
		octavesButton.setActionCommand("Octaves");

		JRadioButton persistenceButton = new JRadioButton("Persistence " + persistence);
		persistenceButton.setActionCommand("Persistence");

		JRadioButton frequencyButton = new JRadioButton("Frequency " + frequency);
		frequencyButton.setActionCommand("Frequency");

		JRadioButton amplitudeButton = new JRadioButton("Amplitude " + amplitude);
		amplitudeButton.setActionCommand("Amplitude");

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(octavesButton);
		group.add(persistenceButton);
		group.add(frequencyButton);
		group.add(amplitudeButton);

		// Register a listener for the radio buttons.
		octavesButton.addActionListener(game.handler);
		persistenceButton.addActionListener(game.handler);
		frequencyButton.addActionListener(game.handler);
		amplitudeButton.addActionListener(game.handler);

		// Put the radio buttons in a column in a panel.
		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		radioPanel.add(octavesButton);
		radioPanel.add(persistenceButton);
		radioPanel.add(frequencyButton);
		radioPanel.add(amplitudeButton);

		textField.addActionListener(game.txt);
		frame.add(game, BorderLayout.CENTER);
		frame.add(radioPanel, BorderLayout.SOUTH);
		radioPanel.add(textField, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.setSize(1000, 800);
		frame.setLocation(300, 10);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addMouseListener(game.handler);
		frame.addMouseMotionListener(game.handler);
		frame.addKeyListener(game.handler);
		while (true) {
			game.repaint();
			game.loop();
			Thread.sleep(5);
		}
	}

	public void actionPerformed(ActionEvent e) {

	}

	public void loop() {
		if(count >= (lines.size()*5)){
			count = 0;
		}
		else{
			count++;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		frame();

	}

	private class TextHandler implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			
		    switch(current){
			    case "Frequency":
			    	frequency = Double.parseDouble(textField.getText());
			    	break;
			    case "Amplitude":
			    	amplitude = Double.parseDouble(textField.getText());
			    	break;
			    case "Persistence":
			    	persistence = Double.parseDouble(textField.getText());
			    	break;
			    case "Octaves":
			    	octaves = (int)(Double.parseDouble(textField.getText()));
			    	break;
		    }
		    textField.setText("");
		    lines.clear();
		    int y = 500;
		    int width = 1000;
			int height = 800;
			int box = 2;
			float scale = .0075f;
			for(int x = 0; x<width*100;x++ ){
				double h = SimplexNoise.OctavePerlin((x * scale)+count, 1 * scale, octaves, persistence, frequency,amplitude); 
				int c = (int) (h*5);
				lines.add(new Line(x,y,x+1,y+c));
				y +=c;
			}
		}
		
	}
	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
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
			mouseY = event.getY();
			mouseX = event.getX();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();

			if (keyCode == e.VK_A) {
				System.out.println("BOOP");
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

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (e.getActionCommand()) {
			case "Octaves":
				current = e.getActionCommand();
				break;
			case "Persistence":
				current = e.getActionCommand();
				break;
			case "Frequency":
				current = e.getActionCommand();
				break;
			case "Amplitude":
				current = e.getActionCommand();
				break;
			}
		}
	}

}
