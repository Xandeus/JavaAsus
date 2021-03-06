package machinelearning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StringLearner extends JPanel {
	HandlerClass handler = new HandlerClass();

	static boolean lClickD = false, rClickD = false, mClickD = false;
	static int clicks = 0;
	int largestGeneration = 100;
	static boolean isLearning = false;
	// Hashtable to store mutation values with generations
	Hashtable<Double, Integer> generations = new Hashtable<Double, Integer>();

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		int width = 1500;
		int height = 800;
		float count = 0;

		for (int x = 200; x < width; x += (width - 200) / 11) {
			g.drawLine(x, 700, x, 710);
			g.drawString((count / 100) + "", x, 725);
			count++;

		}
		for (int y = 60; y < 710; y += 20) {
			g.drawLine(200, y, 210, y);
		}
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Phrase: " + DNA.target, 0, 50);
		g.drawString("Mutation Rate (0.01f)", 720, 740);
		g.drawString("Generations (" + largestGeneration + ")", 0, 200);
		g.drawLine(200, 710, 1500, 710);
		g.drawLine(200, 710, 200, 60);

		if (!isLearning){
			for (double i = 1; i <= generations.size(); i++) {
				g.fillRect((int)((i*width/11)+200),Math.abs(generations.get(i)-height), 5, 5);
			}
		}
	}

	public Color randColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("String Learner");

	public static void frame() throws InterruptedException {
		StringLearner game = new StringLearner();
		text = new JTextField();
		text.setEditable(true);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				isLearning = true;
				text.setText("");
				game.learnString(event.getActionCommand());
			}
		});
		frame.add(text, BorderLayout.SOUTH);
		frame.add(game);
		frame.setResizable(false);
		frame.setSize(1500, 800);
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

	public void learnString(String phrase) {
		DNA.setTarget(phrase);
		Double mutationRate;
		int totalPopulation = 150;
		DNA[] population;
		Random rand = new Random();
		ArrayList<DNA> matingPool = new ArrayList<DNA>();

		mutationRate = 1.0;
		while (mutationRate <= 10f) {
			System.out.println("Mutation Rate: " + mutationRate);
			population = new DNA[totalPopulation];
			for (int i = 0; i < population.length; i++) {
				population[i] = new DNA();
			}
			DNA h = population[0];
			int cycle = 1;
			while (h.fitness != 1 && cycle < 1000) {
				matingPool.clear();
				for (int i = 0; i < population.length; i++) {
					population[i].fitness();
				}

				for (int i = 0; i < population.length; i++) {
					int n = (int) (population[i].fitness * 100);
					for (int j = 0; j < n; j++) {
						matingPool.add(population[i]);
					}
				}
				// Showing DNA over a certain fitnesh
				h = population[0];
				for (DNA x : population) {
					if (x.fitness > h.fitness) {
						h = x;
					}
				}
				System.out.println(h.getPhrase());
				// Mating
				for (int i = 0; i < population.length; i++) {
					int a = rand.nextInt(matingPool.size());
					int b = rand.nextInt(matingPool.size());
					DNA partnerA = matingPool.get(a);
					DNA partnerB = matingPool.get(b);
					DNA child = partnerA.crossover(partnerB);
					child.mutate((mutationRate / 100));

					population[i] = child;
				}
				cycle++;
			}
			generations.put(mutationRate, cycle);
			System.out.println("Generations: " + cycle);
			mutationRate += 1;
		}
		isLearning = false;
		System.out.println(generations);
	}
}
