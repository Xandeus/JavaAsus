import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.omg.PortableServer.ServantRetentionPolicyOperations;

public class Server extends JFrame {
	private JTextField userText;
	private JTextArea chatWindow;
	private DrawPane mainFrame;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private Player pServer = new Player(0, 0);
	private Player pClient;
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	private boolean pConnected = false;

	public Server() {
		super("Box Party - SERVER");
		System.out.println("Enter your name");
		Scanner scan = new Scanner(System.in);
		pServer.setName(scan.nextLine());
		mainFrame = new DrawPane();
		chatWindow = new JTextArea();
		add(chatWindow, BorderLayout.SOUTH);
		chatWindow.setPreferredSize(new Dimension(400, 50));
		addKeyListener(new AL());
		addMouseListener(new MouseAL());
		setSize(800, 600);
		setAlwaysOnTop(true);
		setLocation(100, 150);
		setVisible(true);
		setResizable(false);
		setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainFrame);
	//	createNPCS(10);
	}

	public void createNPCS(int num) {
		for (int i = 0; i < num; i++)
			npcs.add(new NPC((int) (Math.random() * 400), (int) (Math.random() * 400)));
	}

	public class MouseAL implements MouseListener {
		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {

			System.out.println("Clicked at X: " + e.getX() + " Y: " + e.getY());
		}

	}

	public class AL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			int keyCode = event.getKeyCode();
			if (keyCode == event.VK_LEFT) {
				pServer.setXVel(-1);
				pServer.setYVel(0);
			}
			if (keyCode == event.VK_RIGHT) {
				pServer.setXVel(1);
				pServer.setYVel(0);
			}
			if (keyCode == event.VK_UP) {
				pServer.setYVel(-1);
				pServer.setXVel(0);
			}
			if (keyCode == event.VK_DOWN) {
				pServer.setYVel(1);
				pServer.setXVel(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent event) {
			int keyCode = event.getKeyCode();
			if (keyCode == event.VK_LEFT) {
				pServer.setXVel(0);

			}
			if (keyCode == event.VK_RIGHT) {
				pServer.setXVel(0);
			}
			if (keyCode == event.VK_UP) {
				pServer.setYVel(0);
			}
			if (keyCode == event.VK_DOWN) {
				pServer.setYVel(0);
			}
		}

	}

	// create a component that you can actually draw on.
	class DrawPane extends JPanel {
		public void paintComponent(Graphics g) {
			// draw on g here e.g.
			int nLength;
			g.setFont(new Font("Tahoma", Font.PLAIN, 10));
			g.setColor(Color.RED);
			g.fillRect(pServer.getX(), pServer.getY(), 10, 10);
			g.setColor(Color.WHITE);
			nLength = (g.getFontMetrics().stringWidth(pServer.getName())-10)/2;
			g.drawString(pServer.getName(), pServer.getX()-nLength, pServer.getY()-5);
			nLength = (g.getFontMetrics().stringWidth(pServer.getMessage())-10)/2;
			g.drawString(pServer.getMessage(), pServer.getX()-nLength, pServer.getY()+15);
			if (pClient != null) {
				g.setColor(Color.BLUE);
				g.fillRect(pClient.getX(), pClient.getY(), 10, 10);
				g.setColor(Color.WHITE);
				nLength = (g.getFontMetrics().stringWidth(pClient.getName())-10)/2;
				g.drawString(pClient.getName(), pClient.getX()-5, pClient.getY()-5);
				nLength = (g.getFontMetrics().stringWidth(pClient.getMessage())-10)/2;
				g.drawString(pClient.getMessage(), pClient.getX()-nLength, pClient.getY()+15);
			}
			if (pConnected)
				for (NPC npc : npcs) {
					g.setColor(npc.getColor());
					g.fillRect(npc.getX(), npc.getY(), 10, 10);
					npc.randomMove();
				}

			gameLoop();
		}
	}

	public void gameLoop() {
		pServer.move();
		repaint();
		if (pConnected) {
			sendMessage(npcs);
			sendMessage(pServer);
		}
		Scanner scan = new Scanner(System.in);
	}

	// Set up server
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);
			while (true) {
				try {
					waitForConnection();
					setupStreams();
					whileChatting();
				} catch (EOFException eofException) {
					showMessage("\n Server ended connection");
				} finally {
					closeStreams();
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// Wait for connection, then display info
	private void waitForConnection() throws IOException {
		showMessage("Waiting for connection... \n");
		connection = server.accept();
		showMessage(" Now connected to " + connection.getInetAddress().getHostName());

	}

	// Get stream to send and receive data
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams setup");
	}

	// During the chat conversation
	private void whileChatting() throws IOException {
		String message = "You are connected";
		Object temp;
		pConnected = true;

		do {
			try {
				temp = input.readObject();
				if (temp instanceof Player)
					pClient = (Player) temp;
			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("\n error");
			}
		} while (!message.equals("CLIENT - END"));
	}

	private void closeStreams() {
		showMessage("\n Closing connections");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// send message to client
	private void sendMessage(String message) {
		try {
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nSERVER - " + message);
		} catch (IOException ioException) {
			chatWindow.append("\n Cannot send message");
		}
	}

	private void sendMessage(int num) {
		try {
			output.writeInt(num);
			output.flush();
		} catch (IOException ioException) {
			// chatWindow.append("\n Error while sending message");
		}
	}

	private void sendMessage(Player player) {
		try {
			output.reset();
			output.writeObject(player);
			output.flush();
		} catch (IOException ioException) {
			// chatWindow.append("\n Error while sending message");
		}
	}

	private void sendMessage(ArrayList array) {
		try {
			output.reset();
			output.writeObject(array);
			output.flush();
		} catch (IOException ioException) {
			// chatWindow.append("\n Error while sending message");
		}
	}

	// Update chat window
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// chatWindow.append(text);
			}
		});
	}

	// Change user ability to type
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// userText.setEditable(tof);
			}
		});
	}
}
