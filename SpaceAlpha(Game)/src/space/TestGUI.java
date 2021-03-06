package space;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGUI extends JPanel {
	static Image infoTab;
	static Image[] starSprites = new Image[9];
	static Image playerShip;
	static Image currentDamage;
	static Image damage1, damage2, damage3;
	static Image playerLaser;
	static Image enemyShip;
	static Image enemyLaser;
	static BackgroundStar[] stars;
	static int backgroundStarAmount = 1000;
	Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	Cursor defCursor = Cursor.getDefaultCursor();
	Cursor currentCursor;

	HandlerClass handler = new HandlerClass();
	ArrayList<CelestialBody> bodies = new ArrayList<CelestialBody>();
	ArrayList<ArrayList<CelestialBody>> systems = new ArrayList<ArrayList<CelestialBody>>();
	ArrayList<Point> systemLocations = new ArrayList<Point>();
	ArrayList<Building> buildings = new ArrayList<Building>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	static Random rand = new Random();
	public final int COMBATV = 0, INDIVIDUALV = 1, SYSTEMV = 2, SECTORV = 3;
	public int view = SECTORV;
	long tStart;
	long tEnd;
	int mX, mY;
	int highlightX, highlightY;
	int highlightR;
	int numSystems = 250;
	int systemPoint;
	int sysPointSize = 5;
	static int wWidth , wHeight;
	boolean temp = false;
	boolean systemH = false;
	boolean infoTabActive = false;
	boolean isBuilding = false;
	boolean isAutomate = true, isZooming = true;
	int count = 0;
	static boolean leftMouseDown = false, rightMouseDown = false;
	Point infoTabLoc = new Point();
	CelestialBody body;
	CelestialBody selectedBody;
	Player player = new Player(100, 100, 39, 30, body);
	Player enemyPlayer = new Player(100, 100, 39, 39, body);
	int numEnemies;
	boolean playerControlled;
	boolean enemyControlled;

	private Ellipse2D ellipse;
	public static void main(String[] args) throws InterruptedException {
		frame();
	}

	static JFrame frame = new JFrame("SPAAACE");

	public static void frame() throws InterruptedException {
		try {
			infoTab = ImageIO.read(new File("res/infoTab.png"));
			starSprites[0] = ImageIO.read(new File("res/star1.png"));
			starSprites[1] = ImageIO.read(new File("res/star2.png"));
			starSprites[2] = ImageIO.read(new File("res/star3.png"));
			starSprites[3] = ImageIO.read(new File("res/star4.png"));
			starSprites[4] = ImageIO.read(new File("res/star5.png"));
			starSprites[5] = ImageIO.read(new File("res/star6.png"));
			starSprites[6] = ImageIO.read(new File("res/star7.png"));
			starSprites[7] = ImageIO.read(new File("res/star8.png"));
			starSprites[8] = ImageIO.read(new File("res/star9.png"));

			playerShip = ImageIO.read(new File("res/playerShip1.png"));
			playerLaser = ImageIO.read(new File("res/laserBlue1.png"));

			enemyShip = ImageIO.read(new File("res/enemyRed1.png"));
			enemyLaser = ImageIO.read(new File("res/laserRed1.png"));

			damage1 = ImageIO.read(new File("res/playerShip1_damage1.png"));
			damage2 = ImageIO.read(new File("res/playerShip1_damage2.png"));
			damage3 = ImageIO.read(new File("res/playerShip1_damage3.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestGUI game = new TestGUI();
		Font logFont = new Font("Engravers MT", Font.PLAIN, 11);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
	    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.addMouseListener(game.handler);
//		frame.addMouseMotionListener(game.handler);
		frame.addKeyListener(game.handler);
	    wWidth = (int)(frame.getSize().getWidth());
	    wHeight = (int)(frame.getSize().getHeight());
		randomizeStars(); 
		while (true) {
			game.gameLoop();
		}
	}

	public void gameLoop() throws InterruptedException {
		if (temp == false) {
			generateSystems();
			temp = true;
		}
		if (view == COMBATV) {
			moveLasers(player.getLasers(), .2f, -1);
			enemyAI();
			for (Enemy e : enemies) {
				moveLasers(e.getLasers(), .2f, 1);
			}
			checkCollision();
		}
		if(isAutomate && bodies != null){
			switch(view){
				case SECTORV:
					bodies = systems.get((rand.nextInt(systems.size())));
					isZooming = true;
					int loc = systems.indexOf(bodies);
					highlightX = systemLocations.get(loc).x;
					highlightY = systemLocations.get(loc).y;
					highlightR = 50;
					Thread.sleep(2000);
					view--;			
					break;
				case SYSTEMV:
					Thread.sleep(3000);
					if(!isZooming && count >= bodies.size()){
						count = 0;
						view++;
					}
					else{
						body = bodies.get(count);
						view--;
					}
					break;
				case INDIVIDUALV:
					Thread.sleep(3000);
					isZooming = false;
					count++;
					view++;
					break;
			}
		}
		repaint();
	}

	public static void randomizeStars() {
		stars = new BackgroundStar[backgroundStarAmount];
		for (int i = 0; i < stars.length; i++) {
			stars[i] = new BackgroundStar(rand.nextInt(wWidth), rand.nextInt(wHeight), rand.nextInt(6) + 5,
					starSprites[rand.nextInt(9)]);
		}

	}

	public void setEnemies() {
		player.setHealth(4);
		enemies.clear();
		numEnemies = rand.nextInt(16) + 10;
	}

	public void checkControl(CelestialBody body) {
		enemyControlled = false;
		playerControlled = false;
		for (CelestialBody b : player.controlledBodies) {
			if (b == body) {
				playerControlled = true;
				break;
			}
		}
		for (CelestialBody b : enemyPlayer.controlledBodies) {
			if (b == body) {
				enemyControlled = true;
				break;
			}
		}

	}

	public void checkCollision() {
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy e = iterator.next();
			for (Iterator<Point> iterator2 = player.getLasers().iterator(); iterator2.hasNext();) {
				Point p = iterator2.next();
				if (p.getX() > e.getX() && p.getX() < e.getX() + e.getWidth()) {
					if (p.getY() < e.getY() + e.getHeight() && p.getY() > e.getY()) {
						iterator.remove();
						iterator2.remove();
						numEnemies--;
						break;
					}
				}

			}
			for (Iterator<Point> iterator3 = e.getLasers().iterator(); iterator3.hasNext();) {
				Point p = iterator3.next();
				if (p.getX() > player.getX() && p.getX() < player.getX() + player.getWidth()) {
					if (p.getY() < player.getY() + player.getHeight() && p.getY() > player.getY()) {
						player.decreaseHealth(1);
						switch (player.getHealth()) {
						case 3:
							currentDamage = damage1;
							break;
						case 2:
							currentDamage = damage2;
							break;
						case 1:
							currentDamage = damage3;
							break;
						case 0:
							view++;
						}
						iterator3.remove();
					}
				}

			}
		}

	}

	public void enemyAI() {
		// Distance in time
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		if (tDelta / 1000.0 > .5 && enemies.size() < 10) {
			enemies.add(new Enemy(rand.nextInt(20) * 50, -10, 39, 30, body));
			tStart = System.currentTimeMillis();
		}
		if (numEnemies > 0) {
			for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
				Enemy e = iterator.next();
				e.moveY(.2f);
				if (rand.nextInt(1000) == 5) {
					e.getLasers().add(new Point((int) (e.getX() + 8), (int) e.getY() + e.getHeight()));
					e.getLasers().add(new Point((int) (e.getX() + e.getWidth() - 8), (int) e.getY() + e.getHeight()));
				}
				if (e.getY() > wHeight) {
					iterator.remove();
				}
			}
		} else {
			enemyPlayer.controlledBodies.remove(body);
			player.controlledBodies.add(body);
			view++;
		}
	}

	public void moveLasers(ArrayList<Point> lasers, float speed, int direction) {
		for (Iterator<Point> iterator = lasers.iterator(); iterator.hasNext();) {
			Point l = iterator.next();
			if (direction == -1) {
				l.y -= speed;
			} else {
				l.y += 1;
			}
			if (l.y < 0) {
				iterator.remove();
			} else if (l.y > wHeight) {
				iterator.remove();
			}
		}
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("Display", Font.BOLD, 10));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		// Draw background stars
		if(stars != null){
			for (int i = 0; i < stars.length; i++) {
				g2d.drawImage(stars[i].getImage(), (int) stars[i].getX(), (int) stars[i].getY(), stars[i].getSize(),
						stars[i].getSize(), null);
			}
		}
		if(bodies != null)
		switch (view) {
		case COMBATV:
			g2d.drawImage(playerShip, (int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight(),
					null);
			g2d.drawImage(currentDamage, (int) player.getX(), (int) player.getY(), player.getWidth(),
					player.getHeight(), null);
			for (BackgroundStar s : stars) {
				s.incrementY(.4f);
				if (s.getY() > wHeight) {
					s.setY(0);
					s.setX(rand.nextInt(wWidth));
				}

			}
			for (Point l : player.getLasers()) {
				g2d.drawImage(playerLaser, l.x, l.y, 2, 10, null);
			}
			for (Enemy e : enemies) {
				g2d.drawImage(enemyShip, (int) e.getX(), (int) e.getY(), e.getWidth(), e.getHeight(), null);
				for (Point p : e.getLasers()) {
					g2d.drawImage(enemyLaser, p.x, p.y, 2, 10, null);
				}
			}
			break;
		case INDIVIDUALV:
			// Individual view of planets
			checkControl(body);
			Color[][] terrain;
			
			terrain = body.getTerrain();
			
			ellipse.setFrame(60, 25, wHeight - 60, wHeight - 60);
		//	g2d.fillOval(60, 25, wHeight - 60, wHeight - 60);
	//		g2d.drawImage(infoTab, wHeight + 10, 30, 300, 500, null);
//			g.setColor(Color.white);
//			g2d.drawString("Avaliable Constructions", wHeight + 100, 80);
//			if (body.getType().equalsIgnoreCase("Terrestrial planet"))
//				g2d.drawString("Mining Facility", wHeight + 50, 90);
//			else
//				g2d.drawString("You cannot construct anything here!", wHeight + 50, 90);
//			if (enemyControlled) {
//				g2d.setColor(Color.red);
//				g2d.drawString("Controlled by the enemy!", wHeight + 50, 100);
//			}
//
//			else if (playerControlled) {
//				g2d.setColor(Color.green);
//				g2d.drawString("Controlled by you.", wHeight + 50, 100);
//			}
//			if (enemyControlled) {
//				g2d.setColor(Color.cyan);
//				g2d.drawRect(wHeight + 55, 200, 100, 15);
//				g2d.setColor(Color.orange);
//				g2d.drawString("Initiate Battle", wHeight + 60, 210);
//			}
			for (Building b : buildings) {
				if (body == b.getLocation()) {
					g2d.setColor(Color.black);
					g2d.drawRect(b.getX(), b.getY(), 20, 20);
				}
			}
			if (body.hasAtmosphere()) {
				g2d.setColor(new Color(15, 100, 255, 50));
				g2d.fillOval(40, 5, wHeight - 20, wHeight - 20);
			}
			g2d.setClip(ellipse);
			for(int x = 0;x<terrain.length;x++){
				for(int y = 0;y<terrain[0].length;y++){
					g2d.setColor(terrain[x][y]);
					g2d.fillRect(x*5, y*5, 5, 5);
				}
			}
			break;
		case SYSTEMV:
			// Draw planets and stars from systems
			ellipse = new Ellipse2D.Float();
			for (CelestialBody b : bodies) {
				if(b.getTerrain() == null){
					b.generateTerrain(.007,(int)(Math.random()*5)+1,(int)(Math.random()*5)+1,(Math.random()*10),2);
				}
				terrain = b.getTerrain();
				int c = b.getRadius() * 2;
				int i = bodies.indexOf(b);
				g2d.setColor(Color.RED);
				if (b.getType().equals("Star")) {
					b.setWindowLocX(100);
					b.setWindowLocY(500);
				} else {
					// Sets planet position to the position of previous planet
					// plus radius and random variance
					b.setWindowLocX(bodies.get(i - 1).getWindowLocX() + (bodies.get(i - 1).getRadius() * 4)
							+ bodies.get(0).getRadius());
					b.setWindowLocY(500 + bodies.get(0).getRadius());
				}
				ellipse.setFrame(b.getWindowLocX(), b.getWindowLocY(), c, c);
				if (b.hasAtmosphere()) {
					g2d.setColor(new Color(15, 100, 255, 50));
					g2d.fillOval(b.getWindowLocX() - 5, b.getWindowLocY() - 5, c + 10, c + 10);
				}
				for (Building bu : buildings) {
					if (bu.getLocation() == b) {
						g2d.setColor(Color.black);
						g2d.fillRect(b.getWindowLocX() + (b.getRadius()), b.getWindowLocY() + (b.getRadius()), 5, 5);
					}
				}
				g2d.setClip(ellipse);
				for(int x = 0;x<terrain.length;x++){
					for(int y = 0;y<terrain[0].length;y++){
						g2d.setColor(terrain[x][y]);
						g2d.fillRect(b.getWindowLocX()+x, b.getWindowLocY()+y, 1, 1);
					}
				}
			}

			break;
		case SECTORV:
			for (Point p : systemLocations) {
				g2d.setColor(Color.white);
				g2d.fillRect(p.x, p.y, sysPointSize, sysPointSize);
			}
			break;
		}
		g2d.setColor(Color.RED);
		switch (view) {
		case SYSTEMV:
			g2d.drawOval(highlightX, highlightY, highlightR, highlightR);
			break;
		case SECTORV:
			g2d.drawOval(highlightX-(highlightR/2), highlightY-(highlightR/2), highlightR, highlightR);
			break;
		}
		if (infoTabActive) {
			g2d.setColor(Color.green);
			g2d.drawImage(infoTab, infoTabLoc.x, infoTabLoc.y, 100, 150, null);
			g2d.setColor(Color.black);
			g2d.drawString(body.getType(), infoTabLoc.x + 5, infoTabLoc.y + 30);
			g2d.drawString("Mass: " + body.getMass(), infoTabLoc.x + 5, infoTabLoc.y + 40);
			g2d.drawString("Volume: " + body.getVolume(), infoTabLoc.x + 5, infoTabLoc.y + 50);
			g2d.drawString("Density: " + body.getDensity(), infoTabLoc.x + 5, infoTabLoc.y + 60);
			g2d.drawString("Resources: " + body.getResources(), infoTabLoc.x + 5, infoTabLoc.y + +70);
			g2d.drawString(body.getResourceTotal() + "", infoTabLoc.x + 5, infoTabLoc.y + 80);
		}
		if (isBuilding) {
			g2d.setColor(Color.black);
			g2d.drawRect(mX, mY, 20, 20);
		}
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, wWidth, 25);
		if (view == COMBATV) {
			g2d.setColor(Color.black);
			g2d.drawString("Enemies remaining: " + numEnemies, 10, 10);
		}
	}

	public ArrayList<CelestialBody> generateSolarSystem() {
		ArrayList<CelestialBody> b = new ArrayList<CelestialBody>();
		b.add(new Star());
		for (int i = 0; i < rand.nextInt(8) + 4; i++) {
			b.add(new Planet());
		}
		systems.add(b);
		return b;
	}

	public void generateSystems() {
		for (int i = 0; i < numSystems; i++) {
			systemLocations
					.add(new Point((rand.nextInt((frame.getWidth()) - 11)), rand.nextInt((frame.getHeight()) - 11)));
			generateSolarSystem();
		}
	}

	// Type differentiates between highlight collision and mouse click collision
	public boolean checkBodyCollision(int x, int y, int type) {
		for (CelestialBody b : bodies) {
			if (mX > b.getWindowLocX() && mX < b.getWindowLocX() + b.getRadius() * 2 && mY > b.getWindowLocY()
					&& mY < b.getWindowLocY() + b.getRadius() * 2) {
				highlightX = b.getWindowLocX();
				highlightY = b.getWindowLocY();
				highlightR = b.getRadius() * 2;
				if (type == 1)
					body = b;
				currentCursor = new Cursor(Cursor.HAND_CURSOR);
				return true;
			}
		}
		highlightX = 0;
		highlightY = 0;
		highlightR = 0;
		return false;
	}

	public void constructBuilding(CelestialBody b, int x, int y, int type) {
		buildings.add(new MiningFacility(b, x, y));
	}

	public boolean checkSystemCollision(int x, int y) {
		for (Point p : systemLocations) {
			if (mX > p.x && mX < p.x + sysPointSize && mY > p.y && mY < p.y + sysPointSize) {
				highlightX = p.x;
				highlightY = p.y;
				highlightR = sysPointSize;
				systemH = true;
				systemPoint = systemLocations.indexOf(p);
				return true;
			}
		}
		highlightX = 0;
		highlightY = 0;
		highlightR = 0;
		systemH = false;
		bodies = null;
		return false;
	}

	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			mX = e.getX() - 3;
			mY = e.getY() - 30;
			frame.setCursor(currentCursor);
			switch (view) {
			case COMBATV:
				player.setPos(mX - player.getWidth() / 2, mY - player.getHeight() / 2);
				break;
			case SYSTEMV:
				if (checkBodyCollision(mX, mY, 0))
					currentCursor = handCursor;
				else
					currentCursor = defCursor;
				break;

			case SECTORV:
				if (checkSystemCollision(mX, mY))
					currentCursor = handCursor;
				else
					currentCursor = defCursor;
				break;
			default:
				currentCursor = defCursor;
				break;
			}
			frame.setCursor(currentCursor);
		}

		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mX = e.getX() - 3;
			mY = e.getY() - 30;
			frame.setCursor(currentCursor);
			switch (view) {
			case COMBATV:
				player.setPos(mX - player.getWidth() / 2, (mY - player.getHeight() / 2) - 10);
				break;
			case SYSTEMV:
				if (checkBodyCollision(mX, mY, 0))
					currentCursor = handCursor;
				else
					currentCursor = defCursor;
				break;

			case SECTORV:
				if (checkSystemCollision(mX, mY))
					currentCursor = handCursor;
				else
					currentCursor = defCursor;
				break;
			default:
				currentCursor = defCursor;
				break;
			}
			frame.setCursor(currentCursor);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == e.BUTTON1)
				leftMouseDown = true;
			else if (e.getButton() == e.BUTTON3) {
				rightMouseDown = true;
			}
			player.getLasers().add(new Point((int) player.getX(), (int) player.getY() + 5));
			player.getLasers()
					.add(new Point((int) player.getX() + (int) player.getWidth() - 3, (int) player.getY() + 5));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mX = e.getX() - 3;
			mY = e.getY() - 30;
			selectedBody = body;
			// Changes view to planet view
			if (infoTabActive && checkBodyCollision(mX, mY, 1) && (selectedBody == body)) {
				randomizeStars();
				view--;
			}
			infoTabActive = false;
			// Changes view if click system to system view
			if (view == SECTORV && checkSystemCollision(mX, mY)) {
				randomizeStars();
				bodies = systems.get(systemPoint);
				for (CelestialBody b : bodies) {
					if (!b.getType().equals("Star") && rand.nextBoolean())
						enemyPlayer.controlledBodies.add(b);
				}
				view--;
			}
			if (isBuilding) {
				constructBuilding(body, mX, mY, 0);
				isBuilding = false;
			} else if (view == INDIVIDUALV) {
				checkControl(body);
				if (enemyControlled && mX > wHeight + 55 && mX < wHeight + 55 + 95) {
					if (mY > 200 && mY < 215) {
						setEnemies();
						view--;
					}
				} else if (playerControlled && body.getType().equalsIgnoreCase("Terrestrial Planet") && !isBuilding && mX > 600)
					isBuilding = true;
			}
			// Places building and disables building
			// Changing info tab display
			else if (view == SYSTEMV && checkBodyCollision(mX, mY, 1)) {
				infoTabActive = true;
				infoTabLoc.setLocation(body.getWindowLocX() + body.getRadius() * 2 + 5, body.getWindowLocY());
			}
			if (e.getButton() == e.BUTTON3) {
				if (view < SECTORV) {
					view++;
					randomizeStars();
				}
				infoTabActive = false;
				isBuilding = false;
			}
			if (e.getButton() == e.BUTTON1) {
				leftMouseDown = false;
			} else if (e.getButton() == e.BUTTON3) {
				rightMouseDown = false;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == e.VK_BACK_SPACE) {
				if (view < SECTORV) {
					randomizeStars();
					view++;
					infoTabActive = false;
					highlightR = 0;
				}
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
