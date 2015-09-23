import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Server extends JFrame{
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent event){
				sendMessage(event.getActionCommand());
				userText.setText("");
			}
		});
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
	}
	//Set up server
	public void startRunning(){
		try{
			server = new ServerSocket(6789,100);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException){
					showMessage("\n Server ended connection");
				}finally{
					closeStreams();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//Wait for connection, then display info
	private void waitForConnection() throws IOException{
		showMessage("Waiting for connection... \n");
		connection = server.accept();
		showMessage(" Now connected to " + connection.getInetAddress().getHostName());
		
	}
	//Get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams setup");
	}
	//During the chat conversation
	private void whileChatting() throws IOException{
		String message = "You are connected";
		sendMessage(message);
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n error");
			}
		}while(!message.equals("CLIENT - END"));
	}
	private void closeStreams(){
		showMessage("\n Closing connections");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//send message to client
	private void sendMessage(String message){
		try {
			output.writeObject("SERVER - " + message);
			
			output.flush();
			showMessage("\nSERVER - " + message);
		}catch(IOException ioException){
			chatWindow.append("\n Cannot send message");
		}
	}
	//Update chat window
	private void showMessage(final String text){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				chatWindow.append(text);
			}
		});
	}
	
	//Change user ability to type
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(tof);
			}
		});
	}
}
